package qp.official.qp.service.HashtagService;

import qp.official.qp.domain.Hashtag;
import qp.official.qp.web.dto.HashtagRequestDTO;

public interface HashtagQueryService {

    Hashtag saveHashtag(HashtagRequestDTO.HashtagDTO request);

    Hashtag deleteHashtag(Long hashtagId) throws Exception;

}
