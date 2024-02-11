package qp.official.qp.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import qp.official.qp.domain.common.BaseEntity;
import qp.official.qp.domain.enums.Gender;
import qp.official.qp.domain.enums.Role;
import qp.official.qp.domain.enums.UserStatus;
import qp.official.qp.domain.mapping.AnswerLikes;
import qp.official.qp.web.dto.TokenDTO.RefreshTokenDTO;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "users")
@Builder
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, length = 20)
    private String nickname;

    @Column(columnDefinition = "TEXT")
    private String profileImage;

    @Column(nullable = false, length = 40)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(10) DEFAULT 'DEFAULT'")
    private Gender gender;

    @ColumnDefault("0")
    private Long point;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(10) DEFAULT 'USER'")
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column
    private UserStatus status;

    private LocalDateTime lastLogin;

    @Column(name = "refresh_token")
    private String refreshToken;

    private LocalDateTime refreshTokenExpiresAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Question> questionList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Answer> answerList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<AnswerLikes> answerLikesList = new ArrayList<>();

    public void updateNickname(String nickname){
        this.nickname = nickname;
    }
    public void updateProfileImage(String profileImage){
        this.profileImage = profileImage;
    }

    public void setRefreshToken(RefreshTokenDTO refreshTokenDTO) {
        this.refreshToken = refreshTokenDTO.getRefreshToken();
        this.refreshTokenExpiresAt = refreshTokenDTO.getExpiredAt().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    public void addAnswer(Answer answer) {
        this.answerList.add(answer);
        answer.setUser(this);
    }
  
    public void updateStatus(UserStatus status) {
        this.status = status;
    }

}
