package qp.official.qp.service.HashtagService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import qp.official.qp.apiPayload.code.status.ErrorStatus;
import qp.official.qp.apiPayload.exception.handler.HashtagHandler;
import qp.official.qp.domain.Hashtag;
import qp.official.qp.repository.HashtagRepository;
import qp.official.qp.web.dto.HashtagRequestDTO;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HashtagQueryServiceTest {
    @Mock
    private HashtagRepository hashtagRepository;

    @InjectMocks
    private HashtagQueryServiceImpl hashtagQueryService;

    @Test
    @DisplayName("해시태그 생성")
    void saveHashtag() {
        // given
        String hashtag = "testHashtag";

        HashtagRequestDTO.HashtagDTO request = HashtagRequestDTO.HashtagDTO.builder()
                .hashtag(hashtag)
                .build();

        Hashtag testHashtag = Hashtag.builder()
                .hashtag(hashtag)
                .questionHashTagList(new ArrayList<>())
                .build();

        // when
        // hashtagRepository.save(hashtag)가 호출되면 testHashtag를 리턴하라는 의미
        when(hashtagRepository.existsByHashtag(request.getHashtag())).thenReturn(false);
        when(hashtagRepository.save(any(Hashtag.class))).thenReturn(testHashtag);

        // hashtagQueryService.saveHashtag 호출
        Hashtag newHashtag = hashtagQueryService.saveHashtag(request);

        // then
        assertNotNull(newHashtag);
        assertEquals(testHashtag.getHashtag(), newHashtag.getHashtag());
    }

    @Test
    @DisplayName("해시태그 생성 시 해시태그가 이미 존재할 때 이미 존재하는 해시태그 리턴")
    void saveExistedHashtag() {
        // given
        String hashtag = "testHashtag";

        HashtagRequestDTO.HashtagDTO request = HashtagRequestDTO.HashtagDTO.builder()
                .hashtag(hashtag)
                .build();

        Hashtag testHashtag = Hashtag.builder()
                .hashtag(hashtag)
                .questionHashTagList(new ArrayList<>())
                .build();

        // when
        // hashtagRepository.findByHashtag(hashtag)가 호출되면 testHashtag를 리턴하라는 의미
        when(hashtagRepository.existsByHashtag(request.getHashtag())).thenReturn(true);
        when(hashtagRepository.findByHashtag(request.getHashtag())).thenReturn(testHashtag);

        // hashtagQueryService.saveHashtag 호출
        Hashtag newHashtag = hashtagQueryService.saveHashtag(request);

        // then
        assertNotNull(newHashtag);
        assertEquals(testHashtag.getHashtag(), newHashtag.getHashtag());
    }

    @Test
    @DisplayName("해시태그 삭제")
    void deleteHashtag() {
        // given
        String hashtag = "testHashtag";
        Hashtag testHashtag = Hashtag.builder()
                .hashtag(hashtag)
                .questionHashTagList(new ArrayList<>())
                .build();

        when(hashtagRepository.findById(anyLong())).thenReturn(Optional.of(testHashtag));

        // when
        hashtagQueryService.deleteHashtag(1L);

        // then
        verify(hashtagRepository).deleteById(anyLong());
    }

    @Test
    @DisplayName("해시태그 삭제 시 해시태그가 존재하지 않을 때")
    void deleteNotExistedHashtag() {
        // given
        String hashtag = "testHashtag";
        Hashtag testHashtag = Hashtag.builder()
                .hashtag(hashtag)
                .questionHashTagList(new ArrayList<>())
                .build();

        // when
        when(hashtagRepository.findById(anyLong())).thenThrow(new HashtagHandler(ErrorStatus.HASHTAG_NOT_EXIST));

        // then
        Assertions.assertThrows(HashtagHandler.class, () -> {
            hashtagQueryService.deleteHashtag(1L);
        });
    }
}
