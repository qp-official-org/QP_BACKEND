package qp.official.qp.service.HashtagService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import qp.official.qp.apiPayload.exception.handler.HashtagHandler;
import qp.official.qp.domain.Hashtag;
import qp.official.qp.repository.HashtagRepository;
import qp.official.qp.web.dto.HashtagRequestDTO;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HashtagCommandServiceTest {
    @Mock
    private HashtagRepository hashtagRepository;

    @InjectMocks
    private HashtagCommandServiceImpl hashtagCommandService;

    @Test
    @DisplayName("해시태그 조회")
    void findHashtag() {
        // given
        String hashtag = "testHashtag";

        HashtagRequestDTO.HashtagDTO request = HashtagRequestDTO.HashtagDTO.builder()
                .hashtag(hashtag)
                .build();

        Hashtag testHashtag = Hashtag.builder()
                .hashtag(hashtag)
                .questionHashTagList(new ArrayList<>())
                .build();

        //when
        // hashtagRepository.findByHashtag(hashtag)가 호출되면 testHashtag를 리턴하라는 의미
        when(hashtagRepository.existsByHashtag(request.getHashtag())).thenReturn(true);
        when(hashtagRepository.findByHashtag(request.getHashtag())).thenReturn(testHashtag);

        // hashtagCommandService.findHashtag 호출
        Hashtag findHashtag = hashtagCommandService.findHashtag(request);

        // then
        assertNotNull(findHashtag);
        assertEquals(testHashtag.getHashtag(), findHashtag.getHashtag());
    }

    @Test
    @DisplayName("존재하지 않는 해시태그 조회")
    void findExistedHashtag() {
        // given
        String hashtag = "testHashtag";

        HashtagRequestDTO.HashtagDTO request = HashtagRequestDTO.HashtagDTO.builder()
                .hashtag(hashtag)
                .build();

        // when
        when(hashtagRepository.existsByHashtag(request.getHashtag())).thenReturn(false);

        // then
        Assertions.assertThrows(HashtagHandler.class, () -> {
            hashtagCommandService.findHashtag(request);
        });
    }
}
