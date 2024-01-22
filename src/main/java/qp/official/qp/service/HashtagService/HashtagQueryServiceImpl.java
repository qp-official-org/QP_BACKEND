package qp.official.qp.service.HashtagService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qp.official.qp.apiPayload.code.status.ErrorStatus;
import qp.official.qp.apiPayload.exception.handler.HashtagHandler;
import qp.official.qp.converter.HashtagConverter;
import qp.official.qp.domain.Hashtag;
import qp.official.qp.repository.HashtagRepository;
import qp.official.qp.web.dto.HashtagRequestDTO.HashtagDTO;

@Service
@RequiredArgsConstructor
@Transactional
public class HashtagQueryServiceImpl implements HashtagQueryService {

    private final HashtagRepository hashtagRepository;

    private void checkIsEmpty(HashtagDTO request){
        if (request.getHashtag() == null || request.getHashtag().isEmpty()) {
            throw new HashtagHandler(ErrorStatus.HASHTAG_NOT_EXIST);
        }
    }

    @Override
    public Hashtag saveHashtag(HashtagDTO request) {

        checkIsEmpty(request);

        if (hashtagRepository.existsByHashtag(request.getHashtag())) {
            throw new HashtagHandler(ErrorStatus.HASHTAG_ALREADY_EXISTS);
        }
        Hashtag hashtag = HashtagConverter.toHashtag(request);
        return hashtagRepository.save(hashtag);
    }


    @Override
    public Hashtag deleteHashtag(Long hashtagId){
        Hashtag deleteHashtag = hashtagRepository.findById(hashtagId)
            .orElseThrow(() -> new HashtagHandler(ErrorStatus.HASHTAG_NOT_EXIST));
        hashtagRepository.deleteById(hashtagId);
        return deleteHashtag;
    }

}
