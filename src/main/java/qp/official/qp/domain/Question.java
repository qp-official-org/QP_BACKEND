package qp.official.qp.domain;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import qp.official.qp.domain.common.BaseEntity;
import qp.official.qp.domain.mapping.QuestionHashTag;
import qp.official.qp.web.dto.QuestionRequestDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Question extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Builder.Default
    private Long hit = 0L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    @Builder.Default
    private List<QuestionHashTag> questionHashTagList = new ArrayList<>();

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Answer> answers = new ArrayList<>();

    public Question addHit() {
        this.hit++;
        return this;
    }

    public void setUser(User user) {
        // 기존에 이미 등록되어 있던 관계를 제거
        if (this.user != null) {
            this.user.getQuestionList().remove(this);
        }

        this.user = user;
        user.getQuestionList().add(this);
    }

    public void update(QuestionRequestDTO.UpdateDTO request){
        this.title = request.getTitle();
        this.content = request.getContent();
    }
}
