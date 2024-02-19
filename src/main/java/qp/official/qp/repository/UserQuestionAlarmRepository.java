package qp.official.qp.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import qp.official.qp.domain.Question;
import qp.official.qp.domain.User;
import qp.official.qp.domain.mapping.UserQuestionAlarm;

public interface UserQuestionAlarmRepository extends JpaRepository<UserQuestionAlarm, Long> {

    Page<UserQuestionAlarm> findByUserOrderByCreatedAtDesc(User user, Pageable pageable);
    List<UserQuestionAlarm> findByQuestionOrderByCreatedAt(Question question);
    boolean existsByUserAndQuestion(User user, Question question);
    boolean existsByQuestion(Question question);
    boolean existsByUser(User user);

}
