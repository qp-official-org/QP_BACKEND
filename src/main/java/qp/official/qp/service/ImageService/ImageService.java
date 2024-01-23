package qp.official.qp.service.ImageService;

import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;
import qp.official.qp.domain.Image;

public interface ImageService {

    public String upload(MultipartFile multipartFile) throws IOException;

    public Image saveImage(MultipartFile multipartFile) throws IOException;




}
