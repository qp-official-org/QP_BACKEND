package qp.official.qp.domain.mapping;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import qp.official.qp.domain.Hashtag;
import qp.official.qp.domain.Question;

import javax.persistence.*;

@Entity
@Getter
@Builder
@DynamicUpdate
@DynamicInsert
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
        // 기존에 이미 등록되어 있던 관계를 제거
        if (this.question != null) {
            this.question.getQuestionHashTagList().remove(this);
        }

        this.question = question;
        question.getQuestionHashTagList().add(this);
    }

    public void setHashtag(Hashtag hashtag) {
        // 기존에 이미 등록되어 있던 관계를 제거
        if (this.hashtag != null) {
            this.hashtag.getQuestionHashTagList().remove(this);
        }

        this.hashtag = hashtag;
        hashtag.getQuestionHashTagList().add(this);
    }

}
