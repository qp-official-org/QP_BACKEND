package qp.official.qp.service.HashtagService;

import qp.official.qp.domain.Hashtag;
import qp.official.qp.web.dto.HashtagRequestDTO;
import qp.official.qp.web.dto.HashtagRequestDTO.HashtagDTO;

public interface HashtagService{

    Hashtag saveHashtag(HashtagRequestDTO.HashtagDTO request);

    Hashtag findHashtag(HashtagRequestDTO.HashtagDTO request);

    Hashtag deleteHashtag(HashtagRequestDTO.HashtagDTO request, Long hashtagId) throws Exception;

}
