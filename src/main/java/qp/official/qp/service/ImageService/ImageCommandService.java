package qp.official.qp.service.ImageService;

import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;
import qp.official.qp.domain.Image;

public interface ImageCommandService {


     Image saveImage(MultipartFile multipartFile) throws IOException;

     void deleteImage(String url) throws IOException;

     void deleteAllImages() throws IOException;






}
