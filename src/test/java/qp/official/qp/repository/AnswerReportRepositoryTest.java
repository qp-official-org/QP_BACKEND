package qp.official.qp.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import qp.official.qp.domain.mapping.AnswerReport;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AnswerReportRepositoryTest {
    private final AnswerReportRepository answerReportRepository;


    @Autowired
    AnswerReportRepositoryTest(AnswerReportRepository answerReportRepository) {
        this.answerReportRepository = answerReportRepository;
    }

    @Test
    void test() {
        System.out.println("test");
    }

    @Test
    void saveAnswerReportTest() {
        // given
        String content = "내용1";
        AnswerReport answerReport = AnswerReport.builder()
                .content(content)
                .build();

        // when
        AnswerReport newAnswerReport = answerReportRepository.save(answerReport);

        // then
        assertEquals(newAnswerReport, answerReportRepository.findById(newAnswerReport.getAnswerReportId()).get());
    }


    @Test
    void findByIdTest() {
        // given
        String content = "내용1";
        AnswerReport answerReport = AnswerReport.builder()
                .content(content)
                .build();
        AnswerReport newAnswerReport = answerReportRepository.save(answerReport);

        // when
        AnswerReport findAnswerReport = answerReportRepository.findById(newAnswerReport.getAnswerReportId()).get();

        // then
        assertEquals(answerReport, findAnswerReport);
        assertEquals(content, findAnswerReport.getContent());

    }

}