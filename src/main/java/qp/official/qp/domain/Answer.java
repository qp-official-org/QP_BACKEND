package qp.official.qp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import qp.official.qp.domain.common.BaseEntity;
import qp.official.qp.domain.enums.Category;
import qp.official.qp.domain.mapping.AnswerLikes;
import qp.official.qp.web.dto.AnswerRequestDTO;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Answer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerId;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(10)")
    private Category category;

    private Boolean is_updated;

    private Long answerGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    @OneToMany(mappedBy = "answer", cascade = CascadeType.ALL)
    private List<AnswerLikes> answerLikesList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @JsonIgnore
    private Answer parent;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Answer> children;


    public void setParent(Answer parent){
        this.parent = parent;
    }
    public void setChildren(Answer children){
        this.children.add(children);
    }

    public void setQuestion(Question question){
        this.question = question;}

    // user와 양방향 매핑하기
    public void setUser(User user){
        this.user = user;
    }


    public void addAnswerLike(AnswerLikes answerLikes){this.answerLikesList.add(answerLikes);}
    public void deleteAnswerLike(AnswerLikes answerLikes){this.answerLikesList.remove(answerLikes);}


    public void update(AnswerRequestDTO.AnswerUpdateDTO request) {
        this.title = request.getTitle();
        this.content = request.getContent();
    }

}
