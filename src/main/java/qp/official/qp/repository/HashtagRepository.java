package qp.official.qp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import qp.official.qp.domain.Hashtag;

@Repository
public interface HashtagRepository extends JpaRepository<Hashtag, Long> {

    Hashtag findByHashtag(String hashtag);

    boolean existsByHashtag(String hashtag);
}
