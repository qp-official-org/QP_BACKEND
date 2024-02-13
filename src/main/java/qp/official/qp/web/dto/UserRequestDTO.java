package qp.official.qp.web.dto;

import lombok.*;
import qp.official.qp.validation.annotation.ExistUser;

import javax.validation.constraints.NotNull;


public class UserRequestDTO {

    /**
     * 유저 정보 수정
     */
    @Getter
    public static class UpdateUserInfoRequestDTO {
        private String nickname;
        private String profileImage;
    }
    /**
     * 자동 로그인
     */
    @Getter
    public static class AutoLoginRequestDTO {
        @ExistUser
        private Long userId;
    }


}
