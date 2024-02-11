package qp.official.qp.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import qp.official.qp.domain.Hashtag;
import qp.official.qp.repository.HashtagRepository;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class HashTagRepositoryTest {
    private final HashtagRepository hashtagRepository;

    @Autowired
    public HashTagRepositoryTest(
        HashtagRepository hashtagRepository
    ) {
        this.hashtagRepository = hashtagRepository;
    }


    private static Hashtag testHashtag;

    @BeforeEach
    void setup() {
        //User
        String hashtag = "testHashtag";
        testHashtag = Hashtag.builder()
                .hashtag(hashtag)
                .questionHashTagList(new ArrayList<>())
                .build();
    }

    //HashtagRepository에 저장 되는지 확인
    @Test
    @DisplayName("해시태그 생성")
    public void saveHashtagTest() {
        // given-----------------------------------------------------------------------------------------
        // static testHashtag 사용

        // when------------------------------------------------------------------------------------------
        //HashRepository에 저장
        Hashtag saveHashtag = hashtagRepository.save(testHashtag);

        // then------------------------------------------------------------------------------------------
        //Answer
        assertThat(saveHashtag).isNotNull();
        assertThat(saveHashtag.getHashtag().equals(testHashtag.getHashtag()));
    }

    @Test
    @DisplayName("해시태그 조회")
    public void findHashtagTest() {
        // given-----------------------------------------------------------------------------------------
        // static testHashtag 사용
        hashtagRepository.save(testHashtag);

        // when------------------------------------------------------------------------------------------
        // testHashtag 조회
        List<Hashtag> allHashtag = hashtagRepository.findAll();

        // then------------------------------------------------------------------------------------------
        //Hashtag
        assertEquals(1, allHashtag.size());
        assertEquals(testHashtag.getHashtag(), allHashtag.stream().findAny().get().getHashtag());
    }

    @Test
    @DisplayName("해시태그 삭제")
    public void deleteQuestionTest() {
        // given-----------------------------------------------------------------------------------------
        // static testHashtag 사용
        //HashRepository에 저장
        Hashtag saveHashtag = hashtagRepository.save(testHashtag);

        // when------------------------------------------------------------------------------------------
        hashtagRepository.delete(saveHashtag);

        // then------------------------------------------------------------------------------------------
        //Question
        assertFalse(hashtagRepository.existsById(saveHashtag.getHashtagId()));
    }
}
