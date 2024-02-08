package qp.official.qp.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import qp.official.qp.domain.mapping.QuestionReport;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class QuestionReportRepositoryTest {

    private final QuestionReportRepository questionReportRepository;


    @Autowired
    QuestionReportRepositoryTest(QuestionReportRepository questionReportRepository) {
        this.questionReportRepository = questionReportRepository;
    }

    @Test
    void test() {
        System.out.println("test");
    }

    @Test
    void saveQuestionReportTest() {
        // given
        String content1 = "내용1";
        QuestionReport questionReport1 = QuestionReport.builder()
                .content(content1)
                .build();

        // when
        QuestionReport newQuestionReport1 = questionReportRepository.save(questionReport1);

        // then
        assertEquals(newQuestionReport1, questionReportRepository.findById(newQuestionReport1.getAnswerReportId()).get());
    }

    @Test
    void findByIdTest() {
        // given
        String content = "content";
        QuestionReport questionReport = QuestionReport.builder()
                .content(content)
                .build();
        QuestionReport newQuestionReport = questionReportRepository.save(questionReport);

        // when
        QuestionReport findQuestionReport = questionReportRepository.findById(newQuestionReport.getAnswerReportId()).get();

        // then
        assertEquals(questionReport, findQuestionReport);
        assertEquals(content, findQuestionReport.getContent());
    }
}