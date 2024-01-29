package qp.official.qp.web.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import qp.official.qp.apiPayload.ApiResponse;
import qp.official.qp.apiPayload.code.status.SuccessStatus;
import qp.official.qp.converter.UserConverter;
import qp.official.qp.domain.User;
import qp.official.qp.service.UserService;
import qp.official.qp.web.dto.UserRequestDTO;
import qp.official.qp.web.dto.UserResponseDTO;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/users")
@Slf4j
public class UserRestController {

    private final UserService userService;

    @GetMapping("/sign_up")
    public ApiResponse<UserResponseDTO.UserSignUpResultDTO> getKakaoCode(@RequestParam String code) throws IOException {
        return ApiResponse.onSuccess(SuccessStatus.User_OK.getCode(), SuccessStatus.User_OK.getMessage(), userService.signUp(code));
    }

    @PostMapping("/sign_in")
    public ApiResponse<UserResponseDTO.LoginResultDTO> signIn() {
        return ApiResponse.onSuccess(SuccessStatus.Question_OK.getCode(), SuccessStatus.Question_OK.getMessage(), UserConverter.toUserLoginDTO());
    }

    @PatchMapping("/sign_out")
    public ApiResponse<UserResponseDTO.LogoutResultDTO> signOut() {
        return ApiResponse.onSuccess(SuccessStatus.Question_OK.getCode(), SuccessStatus.Question_OK.getMessage(), UserConverter.toUserLogoutDTO());
    }

    @ApiOperation(value = "유저 정보 조회", notes = "유저 정보 조회")
    @GetMapping("/{userId}")
    public ApiResponse<UserResponseDTO.GetUserInfoDTO> getUserInfo(@PathVariable Long userId) {
        System.out.println("controller: " + userId);
        User user = userService.getUserInfo(userId);

        return ApiResponse.onSuccess(SuccessStatus.Question_OK.getCode(), SuccessStatus.Question_OK.getMessage(), UserConverter.toUserGetInfoDTO(user));
    }

    @ApiOperation(value = "유저 정보 수정", notes = "유저 정보 수정")
    @PatchMapping("/{userId}")
    public ApiResponse<UserResponseDTO.UpdateUserInfoDTO> updateUserInfo(@PathVariable Long userId, @RequestBody UserRequestDTO.UpdateUserInfoRequestDTO requestDTO) {
        User user = userService.updateUserInfo(userId, requestDTO);
        return ApiResponse.onSuccess(SuccessStatus.Question_OK.getCode(), SuccessStatus.Question_OK.getMessage(), UserConverter.toUserUpdateDTO(user));
    }

    @PatchMapping("/delete")
    public ApiResponse<UserResponseDTO.deleteUserDTO> delete() {
        return ApiResponse.onSuccess(SuccessStatus.Question_OK.getCode(), SuccessStatus.Question_OK.getMessage(), UserConverter.toUserDeleteDTO());
    }

    @PostMapping("/auto_sign_in")
    public ApiResponse<UserResponseDTO.LoginResultDTO> autoSignIn() {
        return ApiResponse.onSuccess(SuccessStatus.Question_OK.getCode(), SuccessStatus.Question_OK.getMessage(), UserConverter.toUserAutoLoginDTO());
    }

    @Operation(summary = "테스트 유저 생성", description =
            "# Test User를 생성합니다. 다른 기능을 테스트 할때 이용 하세요"
    )
    @PostMapping("/test")
    public ApiResponse<UserResponseDTO.JoinResultDTO> createTestUser() {
        return ApiResponse.onSuccess(
                SuccessStatus.User_OK.getCode(),
                SuccessStatus.User_OK.getMessage(),
                UserConverter.createTestUser(userService.createTestUser())
        );
    }

    @RequestMapping("/reset")
    public void reset(){
        userService.reset();
    }

}
