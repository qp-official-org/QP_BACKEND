package qp.official.qp.domain;

import lombok.*;
import qp.official.qp.domain.common.BaseEntity;
import qp.official.qp.domain.mapping.QuestionHashTag;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
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


    public void setUser(User user) {
        this.user = user;
        List<Question> userQuestList = user.getQuestionList();

        if (userQuestList.contains(this))
            return;

        userQuestList.add(this);
    }
}
