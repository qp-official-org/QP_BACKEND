package qp.official.qp.validation.validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import qp.official.qp.apiPayload.code.status.ErrorStatus;
import qp.official.qp.domain.mapping.AnswerReport;
import qp.official.qp.repository.AnswerReportRepository;

import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExistAnswerReportValidatorTest {
    @Mock
    private AnswerReportRepository answerReportRepository;
    @Mock
    private ConstraintValidatorContext context;
    @Mock
    private ConstraintValidatorContext.ConstraintViolationBuilder builder;

    @InjectMocks
    private ExistAnswerReportValidator validator;

    @Test
    void whenAnswerReportIdIsNull_thenReturnFalse() {
        // given
        Long answerReportId = null;

        // when
        when(context.buildConstraintViolationWithTemplate(ErrorStatus.ANSWERREPORT_ID_NULL.toString()))
                .thenReturn(builder);

        boolean result = validator.isValid(answerReportId, context);

        // then
        assertFalse(result);
    }

    @Test
    void whenAnswerReportIdIsNotNullAndAnswerReportNotFound_thenReturnFalse() {
        // given
        Long answerReportId = 1L;
        // when
        when(context.buildConstraintViolationWithTemplate(ErrorStatus.ANSWERREPORT_NOT_FOUND.toString()))
                .thenReturn(builder);
        when(answerReportRepository.findById(answerReportId)).thenReturn(Optional.ofNullable(null));

        boolean result = validator.isValid(answerReportId, context);

        // then
        assertFalse(result);
    }

    @Test
    void whenAnswerReportIdIsNotNullAndAnswerReportFound_thenReturnTrue() {
        // given
        Long answerReportId = 1L;
        // when
        when(answerReportRepository.findById(answerReportId)).thenReturn(Optional.of(AnswerReport.builder().build()));

        boolean result = validator.isValid(answerReportId, context);

        // then
        assertTrue(result);
    }
}
