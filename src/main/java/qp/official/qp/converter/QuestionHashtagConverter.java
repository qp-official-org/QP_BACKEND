package qp.official.qp.converter;

import qp.official.qp.domain.Hashtag;
import qp.official.qp.domain.mapping.QuestionHashTag;

import java.util.List;
import java.util.stream.Collectors;

public class QuestionHashtagConverter {
    public static List<Hashtag> toHashtagList(List<QuestionHashTag> list) {
        return list.stream()
        .map(QuestionHashTag::getHashtag)
        .collect(Collectors.toList());
    }
}
