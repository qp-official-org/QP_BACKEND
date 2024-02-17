package qp.official.qp.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import qp.official.qp.domain.enums.Gender;
import qp.official.qp.domain.enums.Role;
import qp.official.qp.domain.enums.UserStatus;

import java.time.LocalDateTime;

public class UserResponseDTO {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserSignUpResultDTO {
        Long userId;
        Boolean isNew;
        String accessToken;
        String refreshToken;
    }


    /**
     * 유저 정보 조회
     */
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetUserInfoDTO {
        String nickname;
        String profileImage;
        String email;
        Gender gender;
        Role role;
        Long point;
        LocalDateTime createdAt;
    }

    /**
     * 유저 정보 수정
     */
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateUserInfoDTO {
        Long userId;
        String nickname;
        String profileImage;
        LocalDateTime updatedAt;
    }

    /**
     * 유저 탈퇴 (상태 변경)
     */
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class deleteUserDTO {
        Long userId;
        LocalDateTime updatedAt;
        UserStatus status;
    }

    /**
     * 자동 로그인
     */
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AutoLoginDTO {
        Long userId;
        String accessToken;
        String refreshToken;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserPreviewInQuestionDTO {
        Long userId;
        String profileImage;
        Role ROLE;
    }
}
