package qp.official.qp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import qp.official.qp.domain.Answer;
import qp.official.qp.domain.User;
import qp.official.qp.domain.mapping.AnswerLikes;

@Repository
public interface AnswerLikesRepository extends JpaRepository<AnswerLikes, Long> {

    boolean existsByAnswerAndUser(Answer answer, User user);

    void deleteByAnswerAndUser(Answer answer, User user);

}
