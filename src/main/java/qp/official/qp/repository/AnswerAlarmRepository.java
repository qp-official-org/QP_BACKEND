package qp.official.qp.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import qp.official.qp.domain.Answer;
import qp.official.qp.domain.User;
import qp.official.qp.domain.mapping.AnswerAlarm;

public interface AnswerAlarmRepository extends JpaRepository<AnswerAlarm, Long> {

    List<AnswerAlarm> findByAnswer(Answer answer);
    boolean existsByUser(User user);

}
