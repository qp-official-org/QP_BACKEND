package qp.official.qp.validation.validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import qp.official.qp.apiPayload.code.status.ErrorStatus;
import qp.official.qp.domain.Hashtag;
import qp.official.qp.repository.HashtagRepository;

import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExistHashTagValidatorTest {
    @Mock
    private HashtagRepository hashtagRepository;
    @Mock
    private ConstraintValidatorContext context;
    @Mock
    private ConstraintValidatorContext.ConstraintViolationBuilder builder;

    @InjectMocks
    private ExistHashTagValidator validator;

    @Test
    void whenHashTagIdIsNull_thenReturnFalse() {
        // given
        List<Long> hashtagList = null;

        // when
        when(context.buildConstraintViolationWithTemplate(ErrorStatus.HASHTAG_ID_NULL.toString()))
                .thenReturn(builder);

        boolean result = validator.isValid(hashtagList, context);

        // then
        assertFalse(result);
    }

    @Test
    void whenHashTagIdIsNotNullAndHashTagNotFound_thenReturnFalse() {
        // given
        List<Long> hashtagList = new ArrayList<>(Arrays.asList(1L));
        // when
        when(context.buildConstraintViolationWithTemplate(ErrorStatus.HASHTAG_NOT_FOUND.toString()))
                .thenReturn(builder);
        for (Long hashtagId : hashtagList) {
            when(hashtagRepository.findById(hashtagId)).thenReturn(Optional.ofNullable(null));
        }
        boolean result = validator.isValid(hashtagList, context);

        // then
        assertFalse(result);
    }

    @Test
    void whenHashTagIdIsNotNullAndHashTagFound_thenReturnTrue() {
        // given
        List<Long> hashtagList = new ArrayList<>(Arrays.asList(1L));
        // when
        for (Long hashtagId : hashtagList) {
            when(hashtagRepository.findById(hashtagId)).thenReturn(Optional.of(Hashtag.builder().build()));
        }
        boolean result = validator.isValid(hashtagList, context);

        // then
        assertTrue(result);
    }
}
