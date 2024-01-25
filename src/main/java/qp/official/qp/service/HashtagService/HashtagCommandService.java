package qp.official.qp.service.HashtagService;

import qp.official.qp.domain.Hashtag;
import qp.official.qp.web.dto.HashtagRequestDTO;

public interface HashtagCommandService {


    Hashtag findHashtag(HashtagRequestDTO.HashtagDTO request);


}
