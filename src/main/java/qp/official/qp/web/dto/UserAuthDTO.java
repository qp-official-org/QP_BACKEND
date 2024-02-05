package qp.official.qp.web.dto;

import javax.validation.constraints.NotBlank;
import lombok.*;


public class UserAuthDTO {

    @Getter
    @Builder
    @NotBlank
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
    @NotBlank
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
    @NotBlank
    @NoArgsConstructor
    @AllArgsConstructor
    public static class KaKaoAccountDTO {
        Boolean has_email;
        Boolean email_needs_agreement;
        Boolean is_email_valid;
        Boolean is_email_verified;
        String email;
    }

    @Getter
    @Builder
    @NotBlank
    @NoArgsConstructor
    @AllArgsConstructor
    public static class KaKaoPropertiesDTO{
        String nickname;
        String profile_image;
        String thumbnail_image;
    }
}
