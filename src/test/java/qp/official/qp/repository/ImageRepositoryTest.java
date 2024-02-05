package qp.official.qp.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import qp.official.qp.domain.Image;

@DataJpaTest
class ImageRepositoryTest {

    private final ImageRepository imageRepository;

    @Autowired
    ImageRepositoryTest(ImageRepository imageRepository){
        this.imageRepository = imageRepository;
    }

    @Test
    @DisplayName("findByUrl 메소드 테스트")
    void findByUrlTest(){
        // given
        String url = "http://test/image";
        String fileName = "testing";

        Image image = Image.builder()
            .fileName(fileName)
            .url(url)
            .build();

        // when
        imageRepository.save(image);

        // then
        Image findImage = imageRepository.findByUrl(url);

        assertEquals(url, findImage.getUrl());
        assertEquals(fileName, findImage.getFileName());
    }

    @Test
    @DisplayName("existByUrl 메소드 테스트")
    void existByUrlTest(){
        // given
        String url = "http://test/image";
        String fileName = "testing";

        Image image = Image.builder()
            .fileName(fileName)
            .url(url)
            .build();

        // when
        imageRepository.save(image);

        // then
        assertTrue(imageRepository.existsByUrl(url));
    }

    @Test
    @DisplayName("existByUrl 메소드 테스트: URL이 존재하지 않을 때")
    void existByUrlTest_UrlNotExist() {
        // given
        String nonExistingUrl = "http://test/non_existing_image";

        // when
        boolean isUrlExists = imageRepository.existsByUrl(nonExistingUrl);

        // then
        assertFalse(isUrlExists);
    }

    @Test
    @DisplayName("existByFileName 메소드 테스트")
    void existByFileNameTest(){
        // given
        String url = "http://test/image";
        String fileName = "testing";

        Image image = Image.builder()
            .fileName(fileName)
            .url(url)
            .build();

        // when
        imageRepository.save(image);

        // then
        assertTrue(imageRepository.existsByFileName(fileName));
    }

    @Test
    @DisplayName("existByFileName 메소드 테스트: File name이 존재하지 않을 때")
    void existByFileNameTest_UrlNotExist() {
        // given
        String fileName = "http://test/non_existing_image";

        // when
        boolean isFileNameExists = imageRepository.existsByUrl(fileName);

        // then
        assertFalse(isFileNameExists);
    }

    @Test
    @DisplayName("deleteAll 메소드 테스트")
    void deleteAllTest() {
        // given
        String url1 = "http://test/image1";
        String fileName1 = "image1";

        String url2 = "http://test/image2";
        String fileName2 = "image2";

        Image image1 = Image.builder()
            .fileName(fileName1)
            .url(url1)
            .build();

        Image image2 = Image.builder()
            .fileName(fileName2)
            .url(url2)
            .build();

        // when
        imageRepository.save(image1);
        imageRepository.save(image2);

        imageRepository.deleteAll();

        // then
        assertFalse(imageRepository.existsByUrl(url1));
        assertFalse(imageRepository.existsByUrl(url2));
    }





}