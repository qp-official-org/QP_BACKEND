package qp.official.qp.converter;

import qp.official.qp.domain.Answer;
import qp.official.qp.domain.User;
import qp.official.qp.domain.mapping.AnswerLikes;

public class AnswerLikesConverter {

    public static AnswerLikes toAnswerLike(Answer answer, User user){
        return AnswerLikes.builder()
            .answer(answer)
            .user(user)
            .build();
    }

}
