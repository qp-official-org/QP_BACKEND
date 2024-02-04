package qp.official.qp.service.ImageService;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import qp.official.qp.apiPayload.code.status.ErrorStatus;
import qp.official.qp.apiPayload.exception.handler.ImageHandler;
import qp.official.qp.domain.Image;
import qp.official.qp.repository.ImageRepository;

@Service
@Slf4j
@RequiredArgsConstructor
@Component
@Transactional
public class ImageCommandServiceImpl implements ImageCommandService {

    private final ImageRepository imageRepository;
    private final ImageQueryService imageQueryService;
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    public String upload(MultipartFile multipartFile) throws IOException {
        File uploadFile = convert(multipartFile).orElseThrow(() -> new IllegalArgumentException("파일 전환 실패"));
        String fileName = "qp/" + uploadFile.getName();

        // 해당 파일 이름과 동일한 이름을 가진 이미지가 존재 하면, 에러 발생
        if (imageRepository.existsByFileName(uploadFile.getName())){
            removeLocalFile(uploadFile);
            throw new ImageHandler(ErrorStatus.IMAGE_ALREADY_EXISTS);
        }
        String url = putImage(uploadFile, fileName);
        removeLocalFile(uploadFile);
        return url;
    }

    @Override
    public Image saveImage(MultipartFile multipartFile) throws IOException {
        if (!multipartFile.isEmpty()){
            String url = upload(multipartFile);
            Image image = Image.builder().url(url).fileName(multipartFile.getOriginalFilename()).build();
            return imageRepository.save(image);
        }
        return null;
    }

    //  qp 폴더에 위치한 해당 url을 가진 이미지를 삭제함
    @Override
    public void deleteImage(String url) throws IOException {
        try {
            Image image = imageQueryService.getImageByUrl(url);
            imageRepository.deleteById(image.getImageId());
            amazonS3Client.deleteObject(bucket, "qp/" + image.getFileName());
        }catch (SdkClientException e){
            throw new IOException("이미지 삭제 중 오류가 발생 했습니다.", e);
        }
    }

    // qp 폴더 내 모든 이미지를 삭제함 (qp 폴더는 삭제 되지 않도록 조건 추가 함)
    @Override
    public void deleteAllImages() throws IOException {
        try {
            ListObjectsV2Result result = amazonS3Client.listObjectsV2(bucket);
            for (S3ObjectSummary s3Object : result.getObjectSummaries()){
                if (s3Object.getKey().startsWith("qp/")){

                    if (isDefaultValue(s3Object)){continue;}

                    amazonS3Client.deleteObject(bucket, s3Object.getKey());
                }
            }
             imageRepository.deleteAll();
        }catch (SdkClientException e){
            throw new IOException("이미지 삭제 중 오류가 발생 했습니다.", e);
        }
    }

    private static boolean isDefaultValue(S3ObjectSummary s3Object) {
        return s3Object.getKey().equals("qp/") || s3Object.getKey().equals("qp/icon.png");
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
