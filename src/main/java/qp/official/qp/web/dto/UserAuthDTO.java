package qp.official.qp.web.dto;

import lombok.*;


public class UserAuthDTO {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserSignUpDTO {
        String name;
        String nickname;
        String email;
        String profileImage;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class KaKaoUserInfoDTO {
        Long id;
        String connected_at;
        KaKaoAccountDTO kakao_account;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class KaKaoAccountDTO {
        Boolean has_email;
        Boolean email_needs_agreement;
        Boolean is_email_valid;
        Boolean is_email_verified;
        String email;
    }
}
