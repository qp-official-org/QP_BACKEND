package qp.official.qp.converter;

import qp.official.qp.domain.Hashtag;
import qp.official.qp.web.dto.HashtagRequestDTO;
import qp.official.qp.web.dto.HashtagResponseDTO;

public class HashtagConverter {

    public static HashtagResponseDTO.HashtagReturnDTO toHashtagResultDTO(Hashtag hashtag) {
        return HashtagResponseDTO.HashtagReturnDTO.builder()
                .hashtag(hashtag.getHashtag())
                .hashtagId(hashtag.getHashtagId())
                .build();
    }


    public static Hashtag toHashtag(HashtagRequestDTO.HashtagDTO request) {
        return Hashtag.builder()
                .hashtag(request.getHashtag())
                .build();
    }

    // 다른곳에서 사용하는 HashtagPreviewDTO
    public static HashtagResponseDTO.HastTagPreviewDTO toHashtagPreviewDTO(Hashtag hashtag) {
        return HashtagResponseDTO.HastTagPreviewDTO.builder()
                .hashtagId(hashtag.getHashtagId())
                .hashtag(hashtag.getHashtag())
                .build();
    }

}
