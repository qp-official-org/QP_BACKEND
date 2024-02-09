package qp.official.qp.domain;

import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import qp.official.qp.domain.common.BaseEntity;
import qp.official.qp.domain.mapping.QuestionHashTag;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Hashtag extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hashtagId;

    @Column(nullable = false, length = 20)
    private String hashtag;

    @OneToMany(mappedBy = "hashtag")
    @Builder.Default
    private List<QuestionHashTag> questionHashTagList = new ArrayList<>();
}
