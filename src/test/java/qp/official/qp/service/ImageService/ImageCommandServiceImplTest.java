package qp.official.qp.service.ImageService;

import static org.aspectj.weaver.tools.cache.SimpleCacheFactory.path;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import com.amazonaws.services.s3.AmazonS3Client;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
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

}