package qp.official.qp.converter;

import qp.official.qp.web.dto.UserResponseDTO;

public class UserConverter {

    public static UserResponseDTO.UserTestDTO toUserTestDTO(){
        return UserResponseDTO.UserTestDTO.builder()
                .testString("This is Test!")
                .build();
    }
}
