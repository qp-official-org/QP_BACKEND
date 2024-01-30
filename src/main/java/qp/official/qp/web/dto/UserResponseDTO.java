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

    /**
     * 회원 가입
     */
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinResultDTO{
        Long userId;
        LocalDateTime createdAt;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserSignUpResultDTO{
        String accessToken;
        String refreshToken;
    }

    /**
     * 로그인
     */
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginResultDTO{
        Long userId;
    }

    /**
     * 로그아웃
     */
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LogoutResultDTO{
        Long userId;
    }

    /**
     * 유저 정보 조회
     */
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetUserInfoDTO {
        String name;
        String nickname;
        String profile_image;
        String email;
        Gender gender;
        Long point;
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
        String profile_image;
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
    public static class autoLoginDTO {
        Long userId;
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
