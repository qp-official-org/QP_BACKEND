package qp.official.qp.web.dto;

import lombok.*;
import qp.official.qp.domain.enums.Gender;
import qp.official.qp.domain.enums.Role;


public class UserRequestDTO {

    @Getter
    public static class JoinDTO {
        private String email;
        private String name;
        private String nickname;
        private String profile_image;
        private Gender gender;
        private Role role;

    }
}
