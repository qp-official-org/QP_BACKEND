package qp.official.qp.web.dto;

import lombok.*;
import qp.official.qp.domain.enums.Gender;
import qp.official.qp.domain.enums.Role;


public class UserRequestDTO {

    /**
     * 회원 가입
     */
    @Getter
    public static class JoinRequestDTO {
        private String email;
        private String name;
        private String nickname;
        private String profile_image;
        private Gender gender;
        private Role role;
    }

    /**
     * 로그인
     */
    @Getter
    public static class LoginRequestDTO {
        private String email;
    }

    /**
     * 로그아웃
     */
    @Getter
    public static class LogoutRequestDTO {
        private Long userId;
    }

    /**
     * 유저 정보 조회
     */
    @Getter
    public static class GetUserInfoRequestDTO {
        private Long userId;
    }

    /**
     * 유저 정보 수정
     */
    @Getter
    public static class UpdateUserInfoRequestDTO {
        private String nickname;
        private String profile_image;
    }

    /**
     * 회원 탈퇴
     */
    @Getter
    public static class DeleteUserRequestDTO {
        private Long userId;
    }

    /**
     * 자동 로그인
     */
    @Getter
    public static class AutoLoginRequestDTO {
        private Long userId;
    }


}
