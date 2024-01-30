package qp.official.qp.web.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import qp.official.qp.apiPayload.ApiResponse;
import qp.official.qp.apiPayload.code.status.SuccessStatus;
import qp.official.qp.converter.UserConverter;
import qp.official.qp.domain.User;
import qp.official.qp.service.UserService.UserService;
import qp.official.qp.validation.annotation.ExistUser;
import qp.official.qp.web.dto.UserRequestDTO;
import qp.official.qp.web.dto.UserResponseDTO;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/users")
public class UserRestController {

    private final UserService userService;

    @PostMapping("/sign_up")
    public ApiResponse<UserResponseDTO.JoinResultDTO> signUp() {
        return ApiResponse.onSuccess(
                SuccessStatus.Question_OK.getCode(),
                SuccessStatus.Question_OK.getMessage(),
                UserConverter.toUserTestDTO()
        );
    }

    @PostMapping("/sign_in")
    public ApiResponse<UserResponseDTO.LoginResultDTO> signIn() {
        return ApiResponse.onSuccess(
                SuccessStatus.Question_OK.getCode(),
                SuccessStatus.Question_OK.getMessage(),
                UserConverter.toUserLoginDTO()
        );
    }

    @PatchMapping("/sign_out")
    public ApiResponse<UserResponseDTO.LogoutResultDTO> signOut() {
        return ApiResponse.onSuccess(
                SuccessStatus.Question_OK.getCode(),
                SuccessStatus.Question_OK.getMessage(),
                UserConverter.toUserLogoutDTO()
        );
    }

    @ApiOperation(value = "유저 정보 조회", notes = "유저 정보 조회")
    @GetMapping("/{userId}")
    @Operation(summary = "유저 정보 조회 API",description = "특정 유저 정보를 조회하는 API입니다. path variable로 조회할 userId를 주세요.")
    public ApiResponse<UserResponseDTO.GetUserInfoDTO> getUserInfo(
            @PathVariable @ExistUser Long userId) {
        System.out.println("controller: " + userId);
        User user = userService.getUserInfo(userId);

        return ApiResponse.onSuccess(
                SuccessStatus.Question_OK.getCode(),
                SuccessStatus.Question_OK.getMessage(),
                UserConverter.toUserGetInfoDTO(user)
        );
    }

    @ApiOperation(value = "유저 정보 수정", notes = "유저 정보 수정")
    @PatchMapping("/{userId}")
    @Operation(summary = "유저 정보 수정 API",description = "특정 유저 정보를 수정하는 API입니다. path variable로 수정할 userId를 주세요.")
    public ApiResponse<UserResponseDTO.UpdateUserInfoDTO> updateUserInfo(
            @PathVariable @ExistUser Long userId,
            @RequestBody UserRequestDTO.UpdateUserInfoRequestDTO requestDTO) {
        User user = userService.updateUserInfo(userId, requestDTO);
        return ApiResponse.onSuccess(
                SuccessStatus.Question_OK.getCode(),
                SuccessStatus.Question_OK.getMessage(),
                UserConverter.toUserUpdateDTO(user)
        );
    }

    @PatchMapping("/delete")
    public ApiResponse<UserResponseDTO.deleteUserDTO> delete() {
        return ApiResponse.onSuccess(
                SuccessStatus.Question_OK.getCode(),
                SuccessStatus.Question_OK.getMessage(),
                UserConverter.toUserDeleteDTO()
        );
    }

    @PostMapping("/auto_sign_in")
    public ApiResponse<UserResponseDTO.LoginResultDTO> autoSignIn() {
        return ApiResponse.onSuccess(
                SuccessStatus.Question_OK.getCode(),
                SuccessStatus.Question_OK.getMessage(),
                UserConverter.toUserAutoLoginDTO()
        );
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

}
