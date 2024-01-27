package qp.official.qp.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import qp.official.qp.domain.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {

    Image findByUrl(String url);

    void deleteAll();
}
