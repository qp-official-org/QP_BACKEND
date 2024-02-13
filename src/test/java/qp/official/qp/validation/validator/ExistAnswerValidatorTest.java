package qp.official.qp.validation.validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import qp.official.qp.apiPayload.code.status.ErrorStatus;
import qp.official.qp.domain.Answer;
import qp.official.qp.repository.AnswerRepository;

import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExistAnswerValidatorTest {
    @Mock
    private AnswerRepository answerRepository;
    @Mock
    private ConstraintValidatorContext context;
    @Mock
    private ConstraintValidatorContext.ConstraintViolationBuilder builder;

    @InjectMocks
    private ExistAnswerValidator validator;

    @Test
    void whenAnswerIdIsNull_thenReturnFalse() {
        // given
        Long answerId = null;

        // when
        when(context.buildConstraintViolationWithTemplate(ErrorStatus.ANSWER_ID_NULL.toString()))
                .thenReturn(builder);

        boolean result = validator.isValid(answerId, context);

        // then
        assertFalse(result);
    }

    @Test
    void whenAnswerIdIsNotNullAndAnswerNotFound_thenReturnFalse() {
        // given
        Long answerId = 1L;
        // when
        when(context.buildConstraintViolationWithTemplate(ErrorStatus.ANSWER_NOT_FOUND.toString()))
                .thenReturn(builder);
        when(answerRepository.findById(answerId)).thenReturn(Optional.ofNullable(null));

        boolean result = validator.isValid(answerId, context);

        // then
        assertFalse(result);
    }

    @Test
    void whenAnswerIdIsNotNullAndAnswerReportFound_thenReturnTrue() {
        // given
        Long answerId = 1L;
        // when
        when(answerRepository.findById(answerId)).thenReturn(Optional.of(Answer.builder().build()));

        boolean result = validator.isValid(answerId, context);

        // then
        assertTrue(result);
    }
}
