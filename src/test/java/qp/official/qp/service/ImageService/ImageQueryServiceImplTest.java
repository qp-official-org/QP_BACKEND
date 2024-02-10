package qp.official.qp.service.ImageService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import qp.official.qp.apiPayload.exception.handler.ImageHandler;
import qp.official.qp.domain.Image;
import qp.official.qp.repository.ImageRepository;

@ExtendWith(MockitoExtension.class)
class ImageQueryServiceImplTest {
    @Mock
    private ImageRepository imageRepository;

    @InjectMocks
    private ImageQueryServiceImpl imageQueryService;
    @Test
    @DisplayName("URL에 해당 하는 이미지가 존재 하는 경우")
    void getImageByUrlWhenExist() {
        // given
        Long imageId = 1L;
        String url = "https://example.com/image.jpg";

        Image image = Image.builder()
            .imageId(imageId)
            .url(url)
            .build();

        // imageRepository.existsByUrl
        when(imageRepository.existsByUrl(url)).thenReturn(true);

        // imageRepository.findByUrl
        when(imageRepository.findByUrl(url)).thenReturn(image);

        // when
        Image result = imageQueryService.getImageByUrl(url);

        // then
        assertEquals(imageId, result.getImageId());
        assertEquals(url, result.getUrl());
    }

    @Test
    @DisplayName("URL에 해당 하는 이미지가 존재 하지 않는 경우")
    void getImageByUrlWhenNotExist() {
        // given
        String url = "https://example.com/invalid_image.jpg";

        // imageRepository.existsByUrl
        when(imageRepository.existsByUrl(url)).thenReturn(false);

        // when, then
        assertThrows(ImageHandler.class, () -> {
            imageQueryService.getImageByUrl(url);
        });
    }
}