package qp.official.qp.domain.mapping;

import jakarta.persistence.*;
import lombok.*;
import qp.official.qp.domain.Question;
import qp.official.qp.domain.User;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserQuestionAlarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long AlarmId;

    private Boolean IsAlarmed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

}
