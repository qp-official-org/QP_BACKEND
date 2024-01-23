package qp.official.qp.service.ImageService;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import qp.official.qp.domain.Image;
import qp.official.qp.repository.ImageRepository;

@Service
@Slf4j
@RequiredArgsConstructor
@Component
@Transactional(readOnly = true)
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    public String upload(MultipartFile multipartFile) throws IOException {
        File uploadFile = convert(multipartFile).orElseThrow(() -> new IllegalArgumentException("파일 전환 실패"));
        String fileName = "qp/" + uploadFile.getName();
        String url = putImage(uploadFile, fileName);
        removeLocalFile(uploadFile);
        return url;
    }

    @Override
    @Transactional
    public Image saveImage(MultipartFile multipartFile) throws IOException {
        if (!multipartFile.isEmpty()){
            String storedFileName = upload(multipartFile);
            Image image = Image.builder().url(storedFileName).build();
            return imageRepository.save(image);
        }
        return null;
    }

    private String putImage(File file, String fileName){
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, file)
            .withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private void removeLocalFile(File file){
        if (file.delete()){
            log.info("target file is deleted.");
        }
        else {
            log.info("target file is NOT deleted.");
        }
    }

    private Optional<File> convert(MultipartFile file) throws  IOException {
        File convertFile = new File(file.getOriginalFilename());
        if(convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }
}
