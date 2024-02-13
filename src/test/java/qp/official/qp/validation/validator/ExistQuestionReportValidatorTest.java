package qp.official.qp.validation.validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import qp.official.qp.apiPayload.code.status.ErrorStatus;
import qp.official.qp.domain.mapping.QuestionReport;
import qp.official.qp.repository.QuestionReportRepository;

import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExistQuestionReportValidatorTest {
    @Mock
    private QuestionReportRepository questionReportRepository;
    @Mock
    private ConstraintValidatorContext context;
    @Mock
    private ConstraintValidatorContext.ConstraintViolationBuilder builder;

    @InjectMocks
    private ExistQuestionReportValidator validator;

    @Test
    void whenQuestionReportIdIsNull_thenReturnFalse() {
        // given
        Long questionReportId = null;

        // when
        when(context.buildConstraintViolationWithTemplate(ErrorStatus.QUESTIONREPORT_ID_NULL.toString()))
                .thenReturn(builder);

        boolean result = validator.isValid(questionReportId, context);

        // then
        assertFalse(result);
    }

    @Test
    void whenQuestionReportIdIsNotNullAndQuestionReportNotFound_thenReturnFalse() {
        // given
        Long questionReportId = 1L;
        // when
        when(context.buildConstraintViolationWithTemplate(ErrorStatus.QUESTIONREPORT_NOT_FOUND.toString()))
                .thenReturn(builder);
        when(questionReportRepository.findById(questionReportId)).thenReturn(Optional.ofNullable(null));

        boolean result = validator.isValid(questionReportId, context);

        // then
        assertFalse(result);
    }

    @Test
    void whenQuestionReportIdIsNotNullAndQuestionReportFound_thenReturnTrue() {
        // given
        Long questionReportId = 1L;
        // when
        when(questionReportRepository.findById(questionReportId)).thenReturn(Optional.of(QuestionReport.builder().build()));

        boolean result = validator.isValid(questionReportId, context);

        // then
        assertTrue(result);
    }
}
