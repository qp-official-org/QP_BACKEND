package qp.official.qp.validation.validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import qp.official.qp.apiPayload.code.status.ErrorStatus;
import qp.official.qp.domain.Question;
import qp.official.qp.repository.QuestionRepository;

import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExistQuestionValidatorTest {
    @Mock
    private QuestionRepository questionRepository;
    @Mock
    private ConstraintValidatorContext context;
    @Mock
    private ConstraintValidatorContext.ConstraintViolationBuilder builder;

    @InjectMocks
    private ExistQuestionValidator validator;

    @Test
    void whenQuestionIdIsNull_thenReturnFalse() {
        // given
        Long questionId = null;

        // when
        when(context.buildConstraintViolationWithTemplate(ErrorStatus.QUESTION_ID_NULL.toString()))
                .thenReturn(builder);

        boolean result = validator.isValid(questionId, context);

        // then
        assertFalse(result);
    }

    @Test
    void whenQuestionIdIsNotNullAndQuestionNotFound_thenReturnFalse() {
        // given
        Long questionId = 1L;
        // when
        when(context.buildConstraintViolationWithTemplate(ErrorStatus.QUESTION_NOT_FOUND.toString()))
                .thenReturn(builder);
        when(questionRepository.findById(questionId)).thenReturn(Optional.ofNullable(null));

        boolean result = validator.isValid(questionId, context);

        // then
        assertFalse(result);
    }

    @Test
    void whenQuestionIdIsNotNullAndUserFound_thenReturnTrue() {
        // given
        Long questionId = 1L;
        // when
        when(questionRepository.findById(questionId)).thenReturn(Optional.of(Question.builder().build()));

        boolean result = validator.isValid(questionId, context);

        // then
        assertTrue(result);
    }
}
