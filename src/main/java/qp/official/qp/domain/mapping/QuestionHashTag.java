package qp.official.qp.domain.mapping;

import lombok.*;
import qp.official.qp.domain.Hashtag;
import qp.official.qp.domain.Question;

import javax.persistence.*;

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

    public void setQuestion(Question question) {
        this.question = question;
        if (question.getQuestionHashTagList().contains(this))
            return;
        question.getQuestionHashTagList().add(this);
    }

    public void setHashtag(Hashtag hashtag) {
        this.hashtag = hashtag;
        if (hashtag.getQuestionHashTagList().contains(this))
            return;
        hashtag.getQuestionHashTagList().add(this);
    }

}
