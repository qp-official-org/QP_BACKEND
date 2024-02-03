package qp.official.qp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import qp.official.qp.domain.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {

    Image findByUrl(String url);
    boolean existsByUrl(String url);
    boolean existsByFileName(String fileName);
    void deleteAll();
}
