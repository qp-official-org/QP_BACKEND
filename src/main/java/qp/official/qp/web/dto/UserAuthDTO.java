package qp.official.qp.web.dto;

import javax.validation.constraints.NotBlank;
import lombok.*;


public class UserAuthDTO {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserSignUpDTO {
        @NotBlank
        String name;
        @NotBlank
        String nickname;
        @NotBlank
        String email;
        @NotBlank
        String profileImage;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class KaKaoUserInfoDTO {
        Long id;
        String connected_at;
        KaKaoPropertiesDTO properties;
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
        @NotBlank
        String email;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class KaKaoPropertiesDTO{
        @NotBlank
        String nickname;
        @NotBlank
        String profile_image;
        @NotBlank
        String thumbnail_image;
    }
}
