package qp.official.qp.converter;

import qp.official.qp.domain.Hashtag;
import qp.official.qp.web.dto.HashtagRequestDTO;
import qp.official.qp.web.dto.HashtagResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

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

    public static List<HashtagResponseDTO.HashtagReturnDTO> toHashtagResultDTOList(List<Hashtag> hashtags) {
        return hashtags.stream()
                .map(HashtagConverter::toHashtagResultDTO)
                .collect(Collectors.toList());
    }

}
