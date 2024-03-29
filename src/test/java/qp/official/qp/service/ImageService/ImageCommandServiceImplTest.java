package qp.official.qp.service.ImageService;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;
import qp.official.qp.apiPayload.code.status.ErrorStatus;
import qp.official.qp.apiPayload.exception.handler.ImageHandler;
import qp.official.qp.domain.Image;
import qp.official.qp.repository.ImageRepository;

@ExtendWith(MockitoExtension.class)
class ImageCommandServiceImplTest {

    @Mock
    private ImageRepository imageRepository;
    @Mock
    private AmazonS3Client amazonS3Client;
    @Mock
    private ImageQueryServiceImpl imageQueryService;
    @InjectMocks
    private ImageCommandServiceImpl imageCommandService;

    @BeforeEach
    void setUp(){
        ReflectionTestUtils.setField(imageCommandService, "bucket", "test");
    }
    @Test
    @DisplayName("이미지 업로드 테스트")
    void uploadTest() throws IOException {
        // given
        Long imageId = 1L;
        String fileName = "test_image.jpg";
        String bucket = "test";

        MultipartFile multipartFile = new MockMultipartFile("file", fileName, "image/jpeg", "test data".getBytes());

        String expectUrl = "https://example.com/qp" + fileName;
        String expectFileName = "qp/" + fileName;

        Image expectImage = Image.builder()
            .imageId(imageId)
            .url(expectUrl)
            .fileName(expectFileName)
            .build();

        // imageRepository.existsByFileName
        when(imageRepository.existsByFileName(fileName)).thenReturn(false);

        // amazonS3Client.putObject
        when(amazonS3Client.putObject(any())).thenReturn(null);

        // amazonS3Client.getUrl
        when(amazonS3Client.getUrl(bucket, expectFileName)).thenReturn(new URL(expectUrl));

        // imageRepository.save
        when(imageRepository.save(any())).thenReturn(expectImage);

        // when
        Image result = imageCommandService.saveImage(multipartFile);

        // then
        assertEquals(imageId, result.getImageId());
        assertEquals(expectUrl, result.getUrl());
        assertEquals(expectFileName, result.getFileName());
    }

    @Test
    @DisplayName("이미지 업로드 테스트 : 이미 동일한 파일 명이 존재하는 경우")
    void uploadTestWithExistingImage() throws IOException {
        // given
        String fileName = "test_image.jpg";

        MultipartFile multipartFile = new MockMultipartFile("file", fileName, "image/jpeg", "test data".getBytes());

        String expectUrl = "https://example.com/qp" + fileName;
        String expectFileName = "qp/" + fileName;

        // 이미지가 이미 존재하는 상황 가정
        // imageRepository.existsByFileName
        when(imageRepository.existsByFileName(fileName)).thenReturn(true);

        // when & then
         assertThrows(ImageHandler.class, () -> imageCommandService.saveImage(multipartFile), ErrorStatus.IMAGE_ALREADY_EXISTS.getMessage());
    }

    @Test
    void deleteImage() throws IOException {
        // given
        Long imageId = 1L;
        String fileName = "test_image.jpg";
        String bucket = "test";

        String expectUrl = "https://example.com/qp" + fileName;
        String expectFileName = "qp/" + fileName;

        Image expectImage = Image.builder()
            .imageId(imageId)
            .url(expectUrl)
            .fileName(fileName)
            .build();

        // imageQueryService.getImageByUrl
        when(imageQueryService.getImageByUrl(any())).thenReturn(expectImage);

        // when
        imageCommandService.deleteImage(expectUrl);

        // then
        verify(imageQueryService, times(1)).getImageByUrl(expectUrl);
        verify(imageRepository, times(1)).deleteById(expectImage.getImageId());
        verify(amazonS3Client, times(1)).deleteObject(bucket, expectFileName);

    }

    @Test
    void deleteAllImages() throws IOException {
        // given
        String bucket = "test";

        List<S3ObjectSummary> objectSummaries = new ArrayList<>();
        int expectObjectSummariesSize = 3;

        for (int i = 1; i <= expectObjectSummariesSize; i++){
            S3ObjectSummary s3ObjectSummary = new S3ObjectSummary();
            s3ObjectSummary.setKey("qp/image" + i + ".jpg");
            objectSummaries.add(s3ObjectSummary);
        }

        ListObjectsV2Result result = mock(ListObjectsV2Result.class);

        // amazonS3Client.listObjectsV2
        when(amazonS3Client.listObjectsV2(bucket)).thenReturn(result);

        // result.getObjectSummaries()
        when(result.getObjectSummaries()).thenReturn(objectSummaries);

        // when
        imageCommandService.deleteAllImages();

        // then

        // S3 버킷 이미지 삭제 확인
        verify(amazonS3Client, times(1)).deleteObject(bucket, "qp/image1.jpg");
        verify(amazonS3Client, times(1)).deleteObject(bucket, "qp/image2.jpg");

        // DB 이미지 삭제 확인
        verify(imageRepository, times(1)).deleteAll();

    }







}