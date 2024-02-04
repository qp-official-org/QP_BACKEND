package qp.official.qp.web.dto;

import lombok.*;


public class UserRequestDTO {

    /**
     * 유저 정보 수정
     */
    @Getter
    public static class UpdateUserInfoRequestDTO {
        private String nickname;
        private String profile_image;
    }
    /**
     * 자동 로그인
     */
    @Getter
    public static class AutoLoginRequestDTO {
        private Long userId;
    }


}
