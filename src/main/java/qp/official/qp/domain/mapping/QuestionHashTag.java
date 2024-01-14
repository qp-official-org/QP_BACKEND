package qp.official.qp.domain.mapping;

import jakarta.persistence.*;
import lombok.*;
import qp.official.qp.domain.Hashtag;
import qp.official.qp.domain.Question;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class QuestionHashTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long QuestionHashtagId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hashtag_id")
    private Hashtag hashtag;

}
