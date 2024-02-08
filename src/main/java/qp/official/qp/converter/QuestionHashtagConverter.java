package qp.official.qp.converter;

import qp.official.qp.domain.Hashtag;
import qp.official.qp.domain.Question;
import qp.official.qp.domain.mapping.QuestionHashTag;

import java.util.List;
import java.util.stream.Collectors;

public class QuestionHashtagConverter {
    public static List<Hashtag> toHashtagList(List<QuestionHashTag> list) {
        return list.stream()
        .map(QuestionHashTag::getHashtag)
        .collect(Collectors.toList());
    }

    public static List<QuestionHashTag> toQuestionHashTagList(Question question, List<Hashtag> hashtags) {
        return hashtags.stream()
        .map(hashtag -> {
            QuestionHashTag questionHashTag = QuestionHashTag.builder().build();
            questionHashTag.setQuestion(question);
            questionHashTag.setHashtag(hashtag);
            return questionHashTag;
        })
        .collect(Collectors.toList());
    }
}
