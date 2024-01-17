package qp.official.qp.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import qp.official.qp.apiPayload.ApiResponse;
import qp.official.qp.apiPayload.code.status.SuccessStatus;
import qp.official.qp.converter.UserConverter;
import qp.official.qp.web.dto.UserResponseDTO;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/users")
public class UserRestController {

    @PostMapping("/sign_up")
    public ApiResponse<UserResponseDTO.JoinResultDTO> signUp(){
        return ApiResponse.onSuccess(SuccessStatus.Question_OK.getCode(), SuccessStatus.Question_OK.getMessage(), UserConverter.toUserTestDTO());
    }

    @PostMapping("/sign_in")
    public ApiResponse<UserResponseDTO.LoginResultDTO> signIn(){
        return ApiResponse.onSuccess(SuccessStatus.Question_OK.getCode(), SuccessStatus.Question_OK.getMessage(), UserConverter.toUserLoginDTO());
    }

    @PatchMapping("/sign_out")
    public ApiResponse<UserResponseDTO.LogoutResultDTO> signOut(){
        return ApiResponse.onSuccess(SuccessStatus.Question_OK.getCode(), SuccessStatus.Question_OK.getMessage(), UserConverter.toUserLogoutDTO());
    }

    @PostMapping("/{userId}")
    public ApiResponse<UserResponseDTO.getUserInfoDTO> getUserInfo(){
        return ApiResponse.onSuccess(SuccessStatus.Question_OK.getCode(), SuccessStatus.Question_OK.getMessage(), UserConverter.toUserGetInfoDTO());
    }

    @PatchMapping("/{userId}")
    public ApiResponse<UserResponseDTO.updateUserInfoDTO> updateUserInfo(){
        return ApiResponse.onSuccess(SuccessStatus.Question_OK.getCode(), SuccessStatus.Question_OK.getMessage(), UserConverter.toUserUpdateDTO());
    }

    @PatchMapping("/delete")
    public ApiResponse<UserResponseDTO.deleteUserDTO> delete(){
        return ApiResponse.onSuccess(SuccessStatus.Question_OK.getCode(), SuccessStatus.Question_OK.getMessage(), UserConverter.toUserDeleteDTO());
    }

    @PostMapping("/auto_sign_in")
    public ApiResponse<UserResponseDTO.LoginResultDTO> autoSignIn(){
        return ApiResponse.onSuccess(SuccessStatus.Question_OK.getCode(), SuccessStatus.Question_OK.getMessage(), UserConverter.toUserAutoLoginDTO());
    }

}
