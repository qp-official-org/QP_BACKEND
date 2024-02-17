package qp.official.qp.converter;

import java.time.LocalDateTime;
import qp.official.qp.domain.User;
import qp.official.qp.domain.enums.Gender;
import qp.official.qp.domain.enums.Role;
import qp.official.qp.domain.enums.UserStatus;
import qp.official.qp.web.dto.TokenDTO.TokenResponseDTO;
import qp.official.qp.web.dto.UserResponseDTO;

public class UserConverter {

    public static UserResponseDTO.GetUserInfoDTO toUserGetInfoDTO(User user) {
        return UserResponseDTO.GetUserInfoDTO.builder()
                .nickname(user.getNickname())
                .profileImage(user.getProfileImage())
                .email(user.getEmail())
                .role(user.getRole())
                .gender(user.getGender())
                .point(user.getPoint())
                .createdAt(user.getCreatedAt())
                .build();
    }

    public static UserResponseDTO.UpdateUserInfoDTO toUserUpdateDTO(User user) {
        return UserResponseDTO.UpdateUserInfoDTO.builder()
                .userId(user.getUserId())
                .nickname(user.getNickname())
                .profileImage(user.getProfileImage())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    public static UserResponseDTO.deleteUserDTO toUserDeleteDTO(User user) {
        return UserResponseDTO.deleteUserDTO.builder()
                .userId(user.getUserId())
                .status(user.getStatus())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    public static UserResponseDTO.AutoLoginDTO toUserAutoLoginDTO(TokenResponseDTO response, User user) {
        return UserResponseDTO.AutoLoginDTO.builder()
                .userId(user.getUserId())
                .accessToken(response.getAccessToken())
                .refreshToken(response.getRefreshToken())
                .build();
    }

    public static UserResponseDTO.UserPreviewInQuestionDTO toUserPreviewWithQuestionDTO(User user) {
        return UserResponseDTO.UserPreviewInQuestionDTO.builder()
                .userId(user.getUserId())
                .ROLE(user.getRole())
                .profileImage(user.getProfileImage())
                .build();
    }

    public static UserResponseDTO.UserSignUpResultDTO toUserSignUpResultDTO(TokenResponseDTO response, User user) {
        return UserResponseDTO.UserSignUpResultDTO.builder()
                .userId(user.getUserId())
                .isNew(user.getIsNew())
                .accessToken(response.getAccessToken())
                .refreshToken(response.getRefreshToken())
                .build();
    }

    public static User toUser(String email, String nickname) {
        return User.builder()
                .gender(Gender.DEFAULT)
                .profileImage("https://qp-backend-bucket.s3.ap-northeast-2.amazonaws.com/qp/icon.png")
                .role(Role.USER)
                .point(0L)
                .status(UserStatus.ACTIVE)
                .lastLogin(LocalDateTime.now())
                .email(email)
                .nickname(nickname)
                .build();
    }


}
