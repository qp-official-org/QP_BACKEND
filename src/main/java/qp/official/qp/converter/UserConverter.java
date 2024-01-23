package qp.official.qp.converter;

import qp.official.qp.domain.User;
import qp.official.qp.domain.enums.UserStatus;
import qp.official.qp.web.dto.UserRequestDTO;
import qp.official.qp.web.dto.UserResponseDTO;

public class UserConverter {

    public static UserResponseDTO.JoinResultDTO toUserTestDTO() {
        return UserResponseDTO.JoinResultDTO.builder()
                .userId(1L)
                .build();
    }

    public static UserResponseDTO.JoinResultDTO createTestUser(User user) {
        return UserResponseDTO.JoinResultDTO.builder()
                .userId(user.getUserId())
                .createdAt(user.getCreatedAt())
                .build();
    }

    public static UserResponseDTO.LoginResultDTO toUserLoginDTO() {
        return UserResponseDTO.LoginResultDTO.builder()
                .userId(1L)
                .build();
    }

    public static UserResponseDTO.LogoutResultDTO toUserLogoutDTO() {
        return UserResponseDTO.LogoutResultDTO.builder()
                .userId(1L)
                .build();
    }

    public static UserResponseDTO.GetUserInfoDTO toUserGetInfoDTO(User user) {
        return UserResponseDTO.GetUserInfoDTO.builder()
                .nickname(user.getNickname())
                .profile_image(user.getProfileImage())
                .point(user.getPoint())
                .build();
    }

    public static UserResponseDTO.UpdateUserInfoDTO toUserUpdateDTO(User user) {
        return UserResponseDTO.UpdateUserInfoDTO.builder()
                .userId(user.getUserId())
                .build();
    }

    public static UserResponseDTO.deleteUserDTO toUserDeleteDTO() {
        return UserResponseDTO.deleteUserDTO.builder()
                .userId(1L)
                .status(UserStatus.DELETED)
                .build();
    }

    public static UserResponseDTO.LoginResultDTO toUserAutoLoginDTO() {
        return UserResponseDTO.LoginResultDTO.builder()
                .userId(1L)
                .build();
    }
}
