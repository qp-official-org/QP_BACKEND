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

    @PostMapping("/test")
    public ApiResponse<UserResponseDTO.UserTestDTO> testAPI(){
        return ApiResponse.onSuccess(SuccessStatus.Question_OK.getCode(), SuccessStatus.Question_OK.getMessage(), UserConverter.toUserTestDTO());
    }

    @PostMapping("/sign_up")
    public ApiResponse<UserResponseDTO.UserTestDTO> signUp(){
        return ApiResponse.onSuccess(SuccessStatus.Question_OK.getCode(), SuccessStatus.Question_OK.getMessage(), UserConverter.toUserTestDTO());
    }

    @PostMapping("/sign_in")
    public ApiResponse<UserResponseDTO.UserTestDTO> signIn(){
        return ApiResponse.onSuccess(SuccessStatus.Question_OK.getCode(), SuccessStatus.Question_OK.getMessage(), UserConverter.toUserTestDTO());
    }

    @PostMapping("/{userId}")
    public ApiResponse<UserResponseDTO.UserTestDTO> getUserInfo(){
        return ApiResponse.onSuccess(SuccessStatus.Question_OK.getCode(), SuccessStatus.Question_OK.getMessage(), UserConverter.toUserTestDTO());

    }

    @PatchMapping("/{userId}")
    public ApiResponse<UserResponseDTO.UserTestDTO> updateUserInfo(){
        return ApiResponse.onSuccess(SuccessStatus.Question_OK.getCode(), SuccessStatus.Question_OK.getMessage(), UserConverter.toUserTestDTO());
    }

    @PatchMapping("/sign_out")
    public ApiResponse<UserResponseDTO.UserTestDTO> signOut(){
        return ApiResponse.onSuccess(SuccessStatus.Question_OK.getCode(), SuccessStatus.Question_OK.getMessage(), UserConverter.toUserTestDTO());
    }

    @PostMapping("/auto_sign_in")
    public ApiResponse<UserResponseDTO.UserTestDTO> autoSignIn(){
        return ApiResponse.onSuccess(SuccessStatus.Question_OK.getCode(), SuccessStatus.Question_OK.getMessage(), UserConverter.toUserTestDTO());
    }


}
