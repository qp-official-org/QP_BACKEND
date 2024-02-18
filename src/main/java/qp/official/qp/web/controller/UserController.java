package qp.official.qp.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import qp.official.qp.apiPayload.ApiResponse;
import qp.official.qp.apiPayload.code.status.SuccessStatus;
import qp.official.qp.converter.UserConverter;
import qp.official.qp.domain.User;
import qp.official.qp.service.TokenService.TokenService;
import qp.official.qp.service.UserService.UserService;
import qp.official.qp.validation.annotation.ExistUser;
import qp.official.qp.web.dto.TokenDTO.TokenResponseDTO;
import qp.official.qp.web.dto.UserRequestDTO;
import qp.official.qp.web.dto.UserResponseDTO;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Validated
@CrossOrigin
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final UserService userService;
    private final TokenService tokenService;


    @GetMapping("/sign_up")
    @Operation(summary = "회원 가입 API", description = "회원 가입을 위해 parameter로 accessToken을 입력하세요.")
    public ApiResponse<UserResponseDTO.UserSignUpResultDTO> getKaKaoCode(
            @RequestParam String accessToken
    ) throws IOException, ParseException {
        User newUser = userService.signUp(accessToken);
        TokenResponseDTO tokenResponseDTO = tokenService.createToken(newUser.getUserId());
        return ApiResponse.onSuccess(
                SuccessStatus.User_OK,
                UserConverter.toUserSignUpResultDTO(tokenResponseDTO, newUser)
        );
    }


    //    @ApiOperation(value = "유저 정보 조회", notes = "유저 정보 조회")
    @GetMapping("/{userId}")
    @Operation(
            summary = "유저 정보 조회 API"
            , description = "# Header에 accessToken 필요. path variable로 조회할 userId를 입력하세요. \n" +
            "## 유저의 ROLE 은 USER, ADMIN, CHILD, EXPERT 가 있습니다."
            , security = @SecurityRequirement(name = "accessToken")
    )
    public ApiResponse<UserResponseDTO.GetUserInfoDTO> getUserInfo(
            @PathVariable @ExistUser Long userId) {

        // accessToken으로 유효한 유저인지 인가
        tokenService.isValidToken(userId);

        System.out.println("controller: " + userId);
        User user = userService.getUserInfo(userId);

        return ApiResponse.onSuccess(
                SuccessStatus.User_OK,
                UserConverter.toUserGetInfoDTO(user)
        );
    }

    @PostMapping("/auto_sign_in")
    @Operation(
            summary = "자동 로그인 API"
            , description = "Header에 accessToken, refreshToken 필요. Request body에 로그인 할 userId를 입력하세요."
            , security = {@SecurityRequirement(name = "accessToken"), @SecurityRequirement(name = "refreshToken")}
    )
    public ApiResponse<UserResponseDTO.AutoLoginDTO> autoSignIn(
            @RequestBody UserRequestDTO.AutoLoginRequestDTO request
    ) {
        TokenResponseDTO tokenResponseDTO = tokenService.autoSignIn(request.getUserId());
        User findUser = userService.autoSignIn(request.getUserId());
        return ApiResponse.onSuccess(
                SuccessStatus.User_OK,
                UserConverter.toUserAutoLoginDTO(tokenResponseDTO, findUser)
        );
    }

    //    @ApiOperation(value = "유저 정보 수정", notes = "유저 정보 수정")
    @PatchMapping("/{userId}")
    @Operation(
            summary = "유저 정보 수정 API"
            , description = "Header에 accessToken 필요. path variable로 userId를 입력하고, Request body에 수정할 정보를 입력하세요."
            , security = @SecurityRequirement(name = "accessToken")
    )
    public ApiResponse<UserResponseDTO.UpdateUserInfoDTO> updateUserInfo(
            @PathVariable @ExistUser Long userId,
            @RequestBody UserRequestDTO.UpdateUserInfoRequestDTO requestDTO) {
        // accessToken으로 유효한 유저인지 인가
        tokenService.isValidToken(userId);

        User user = userService.updateUserInfo(userId, requestDTO);
        return ApiResponse.onSuccess(
                SuccessStatus.User_OK,
                UserConverter.toUserUpdateDTO(user)
        );
    }

    @PatchMapping("/delete/{userId}")
    @Operation(summary = "유저 삭제 API"
            , description = "현재 로그인 한 유저의 상태가 DELETED로 변경됩니다."
            , security = @SecurityRequirement(name = "accessToken")
    )
    public ApiResponse<UserResponseDTO.deleteUserDTO> delete(
            @PathVariable @ExistUser Long userId
    ) {
        // accessToken으로 유효한 유저인지 인가
        tokenService.isValidToken(userId);

        User user = userService.deleteUser(userId);
        return ApiResponse.onSuccess(
                SuccessStatus.User_OK,
                UserConverter.toUserDeleteDTO(user)
        );
    }

    @Operation(summary = "테스트 유저 생성", description =
            "# Test User를 생성합니다. 다른 기능을 테스트 할때 이용 하세요 \n" +
                    "새로운 유저의 token, refreshToken도 같이 반환합니다."
            , security = {@SecurityRequirement(name = "accessToken"), @SecurityRequirement(name = "refreshToken")}
    )
    @PostMapping("/test")
    public ApiResponse<UserResponseDTO.UserSignUpResultDTO> createTestUser() {
        User newTestUser = userService.createTestUser();
        return ApiResponse.onSuccess(
                SuccessStatus.User_OK,
                UserConverter.toUserSignUpResultDTO(
                        tokenService.createToken(newTestUser.getUserId()),
                        newTestUser
                )
        );
    }

    @PatchMapping("/point/{userId}")
    @Operation(
            summary = "유저 point 충전 또는 차감 API"
            , description = "Header에 accessToken 필요. path variable로 userId를 입력하고, Request body에 충전 또는 차감할 point를 입력하세요. \n" +
                            "Response의 point는 회원이 보유한 총 point입니다."
            , security = @SecurityRequirement(name = "accessToken")
    )
    public ApiResponse<UserResponseDTO.UpdateUserPointDTO> UpdateUserPoint(
            @PathVariable @ExistUser Long userId,
            @Valid @RequestBody UserRequestDTO.UpdateUserPointRequestDTO requestDTO) {
        // accessToken으로 유효한 유저인지 인가
        tokenService.isValidToken(userId);

        User user = userService.updateUserPoint(userId, requestDTO);
        return ApiResponse.onSuccess(
                SuccessStatus.User_OK,
                UserConverter.toUpdateUserPointDTO(user)
        );
    }

}
