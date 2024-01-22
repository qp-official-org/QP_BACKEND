package qp.official.qp.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;
import qp.official.qp.domain.User;
import qp.official.qp.service.UserService.UserService;
import qp.official.qp.util.JwtUtil;
import qp.official.qp.web.dto.FullTokenResponse;
import qp.official.qp.web.dto.kakao.KakaoError;
import qp.official.qp.web.dto.kakao.KakaoUserInfo;
import qp.official.qp.web.dto.request.KakaoTokenRequest;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ApiOperation(value = "Login", notes = "Authenticate user by login and password")
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully authenticated"),
        @ApiResponse(code = 401, message = "Invalid credentials")
})
@ApiImplicitParams({
        @ApiImplicitParam(name = "Authorization", value = "Basic authentication credentials", required = true, dataType = "string", paramType = "header")
})
@RequestMapping(value = "/login", method = RequestMethod.POST)
public class KakaoLoginFilter extends OncePerRequestFilter {

    private final String SIGNUP_URI;
    private final String KAKAO_USER_INFO_URL = "https://kapi.kakao.com/v1/user/access_token_info";

    private final ObjectMapper objectMapper;
    private final UserService userService;

    public KakaoLoginFilter(String signupUri, ObjectMapper objectMapper, UserService userService) {
        this.SIGNUP_URI = signupUri;
        this.objectMapper = objectMapper;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (SIGNUP_URI.equals(request.getRequestURI()) && request.getMethod().equals("POST")) {
            KakaoTokenRequest kakaoTokenRequest = null;
            try {
                kakaoTokenRequest = getKakaoToken(request);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            String refreshToken = kakaoTokenRequest.getRefresh_token();
            String accessToken = kakaoTokenRequest.getAccess_token();

            // 1. 카카오 토큰으로 회원 정보 조회
            KakaoUserInfo kakaoUserInfo = getKaKaoUserInfo(accessToken);
            kakaoUserInfo.setRefreshToken(refreshToken);


            // 2. 회원 정보로 로그인/회원가입 처리
            // 회원가입 처리
            if (!userService.signUp(kakaoUserInfo)) {
                throw new InternalError("회원가입에 실패했습니다.");
            }

            // 3. JWT 토큰 발급
            User user = userService.getUser(kakaoUserInfo);
            FullTokenResponse fullTokenResponse = FullTokenResponse
                    .builder()
                    .access_token(JwtUtil.generateJwt(user))
                    .refresh_token(user.getRefreshToken())
                    .build();

            // 4. 응답
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(fullTokenResponse));
            return;
        }


        filterChain.doFilter(request, response);

    }

    private KakaoTokenRequest getKakaoToken(HttpServletRequest request) throws Exception {
        try {
            KakaoTokenRequest kakaoTokenRequest = objectMapper.readValue(request.getInputStream(), KakaoTokenRequest.class);
            if (kakaoTokenRequest.getAccess_token() == null || kakaoTokenRequest.getRefresh_token() == null) {
//                throw new TokenNotFoundException("access_token 또는 refresh_token이 없습니다.");
                throw new Exception();

            }

            return kakaoTokenRequest;
        } catch (IOException e) {
//            throw new TokenNotFoundException("카카오 토큰을 조회하는 중에 오류가 발생했습니다." + e.getMessage());
            throw new Exception();
        }
    }

    private KakaoUserInfo getKaKaoUserInfo(String accessToken) throws JsonProcessingException {
        // KAKAO_USER_INFO_URL로 accessToken을 보내서 회원 정보를 받아온다.
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(headers);

        try {
            ResponseEntity<KakaoUserInfo> response = restTemplate.exchange(KAKAO_USER_INFO_URL, HttpMethod.GET, httpEntity, KakaoUserInfo.class);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            KakaoError error = objectMapper.readValue(e.getResponseBodyAsString(), KakaoError.class);
            switch (error.getCode()) {
                case -401:
                case -2:
                    throw new RuntimeException("카카오 토큰이 유효하지 않습니다.");
                case -1:
                    throw new RuntimeException("카카오 서버 오류입니다.");
                default:
                    throw new RuntimeException("카카오 토큰을 조회하는 중에 오류가 발생했습니다.");
            }
        }
    }

}
