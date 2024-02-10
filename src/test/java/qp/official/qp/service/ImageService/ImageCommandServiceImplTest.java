package qp.official.qp.service.ImageService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import com.amazonaws.services.s3.AmazonS3Client;
import java.io.IOException;
import java.net.URL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;
import qp.official.qp.repository.ImageRepository;

@ExtendWith(MockitoExtension.class)
class ImageCommandServiceImplTest {

    @Mock
    private ImageRepository imageRepository;
    @Mock
    private AmazonS3Client amazonS3Client;
    @InjectMocks
    private ImageCommandServiceImpl imageCommandService;

    @BeforeEach
    void setUp(){
        ReflectionTestUtils.setField(imageCommandService, "bucket", "test");
    }
    @Test
    void test() throws IOException {
        // given
        String fileName = "test_image.jpg";
        String bucket = "test";

        MultipartFile multipartFile = new MockMultipartFile("file", fileName, "image/jpeg", "test data".getBytes());

        String expectUrl = "https://example.com/qp" + fileName;
        String expectFileName = "qp/" + fileName;

        // imageRepository.existsByFileName
        when(imageRepository.existsByFileName(fileName)).thenReturn(false);
        // amazonS3Client.putObject
        when(amazonS3Client.putObject(any())).thenReturn(null);
        // amazonS3Client.getUrl
        when(amazonS3Client.getUrl(bucket, expectFileName)).thenReturn(new URL(expectUrl));

        // when
        String result = imageCommandService.upload(multipartFile);

        // then
        assertEquals(expectUrl, result);
    }

}