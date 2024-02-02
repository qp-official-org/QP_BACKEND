package qp.official.qp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import qp.official.qp.domain.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {

    Image findByUrl(String url);

    void deleteAll();
}
