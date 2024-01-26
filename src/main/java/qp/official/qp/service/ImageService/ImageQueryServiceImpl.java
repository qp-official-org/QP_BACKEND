package qp.official.qp.service.ImageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qp.official.qp.domain.Image;
import qp.official.qp.repository.ImageRepository;

@Service
@Slf4j
@RequiredArgsConstructor
@Component
@Transactional(readOnly = true)
public class ImageQueryServiceImpl implements ImageQueryService {

    private final ImageRepository imageRepository;
    @Override
    public Image getImageByUrl(String url) {
        return imageRepository.findByUrl(url);
    }
}
