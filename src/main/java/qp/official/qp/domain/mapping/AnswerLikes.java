package qp.official.qp.domain.mapping;

import jakarta.persistence.*;
import lombok.*;
import qp.official.qp.domain.Answer;
import qp.official.qp.domain.User;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AnswerLikes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long AnswerLikeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "answer_id")
    private Answer answer;

}
