package qp.official.qp.service.HashtagService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qp.official.qp.apiPayload.code.status.ErrorStatus;
import qp.official.qp.apiPayload.exception.handler.HashtagHandler;
import qp.official.qp.domain.Hashtag;
import qp.official.qp.repository.HashtagRepository;
import qp.official.qp.web.dto.HashtagRequestDTO.HashtagDTO;

@Service
@RequiredArgsConstructor
@Transactional
public class HashtagCommandServiceImpl implements HashtagCommandService {

    private final HashtagRepository hashtagRepository;

    private void checkIsEmpty(HashtagDTO request){
        if (request.getHashtag() == null || request.getHashtag().isEmpty()) {
            throw new HashtagHandler(ErrorStatus.HASHTAG_NOT_EXIST);
        }
    }

    @Override
    public Hashtag findHashtag(HashtagDTO request) {
        checkIsEmpty(request);

        if (!hashtagRepository.existsByHashtag(request.getHashtag())){
            throw new HashtagHandler(ErrorStatus.HASHTAG_NOT_EXIST);
        }
        return hashtagRepository.findByHashtag(request.getHashtag());
    }

}

