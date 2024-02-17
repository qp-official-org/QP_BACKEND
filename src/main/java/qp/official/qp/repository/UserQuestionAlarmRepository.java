package qp.official.qp.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import qp.official.qp.domain.Question;
import qp.official.qp.domain.User;
import qp.official.qp.domain.mapping.UserQuestionAlarm;

public interface UserQuestionAlarmRepository extends JpaRepository<UserQuestionAlarm, Long> {

    List<UserQuestionAlarm> findByQuestionOrderByCreatedAt(Question question);
    boolean existsByUserAndQuestion(User user, Question question);
    boolean existsByQuestion(Question question);

}
