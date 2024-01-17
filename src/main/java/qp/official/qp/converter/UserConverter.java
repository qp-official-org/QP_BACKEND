package qp.official.qp.converter;

import qp.official.qp.domain.enums.UserStatus;
import qp.official.qp.web.dto.UserResponseDTO;

public class UserConverter {

    public static UserResponseDTO.JoinResultDTO toUserTestDTO(){
        return UserResponseDTO.JoinResultDTO.builder()
                .userId(1L)
                .build();
    }

    public static UserResponseDTO.LoginResultDTO toUserLoginDTO(){
        return UserResponseDTO.LoginResultDTO.builder()
                .userId(1L)
                .build();
    }

    public static UserResponseDTO.LogoutResultDTO toUserLogoutDTO(){
        return UserResponseDTO.LogoutResultDTO.builder()
                .userId(1L)
                .build();
    }

    public static UserResponseDTO.updateUserInfoDTO toUserUpdateDTO(){
        return UserResponseDTO.updateUserInfoDTO.builder()
                .userId(1L)
                .build();
    }

    public static UserResponseDTO.getUserInfoDTO toUserGetInfoDTO(){
        return UserResponseDTO.getUserInfoDTO.builder()
                .email("change.com")
                .name("name")
                .nickname("nickname")
                .profile_image("profile_image")
                .point(100L)
                .build();
    }

    public static UserResponseDTO.deleteUserDTO toUserDeleteDTO(){
        return UserResponseDTO.deleteUserDTO.builder()
                .userId(1L)
                .status(UserStatus.DELETED)
                .build();
    }

    public static UserResponseDTO.LoginResultDTO toUserAutoLoginDTO(){
        return UserResponseDTO.LoginResultDTO.builder()
                .userId(1L)
                .build();
    }
}
