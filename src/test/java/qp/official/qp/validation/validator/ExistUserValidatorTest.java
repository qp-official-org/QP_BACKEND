package qp.official.qp.validation.validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import qp.official.qp.apiPayload.code.status.ErrorStatus;
import qp.official.qp.domain.User;
import qp.official.qp.repository.UserRepository;

import javax.validation.ConstraintValidatorContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExistUserValidatorTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private ConstraintValidatorContext context;
    @Mock
    private ConstraintValidatorContext.ConstraintViolationBuilder builder;

    @InjectMocks
    private ExistUserValidator validator;

    @Test
    void whenUserIdIsNull_thenReturnTrue() {
        // given
        Long userId = null;

        // when
        when(context.buildConstraintViolationWithTemplate(ErrorStatus.USER_ID_NULL.toString()))
                .thenReturn(builder);

        boolean result = validator.isValid(userId, context);

        // then
        assertFalse(result);
    }

    @Test
    void whenUserIdIsNotNullAndUserNotFound_thenReturnFalse() {
        // given
        Long userId = 1L;
        // when
        when(context.buildConstraintViolationWithTemplate(ErrorStatus.USER_NOT_FOUND.toString()))
                .thenReturn(builder);
        when(userRepository.findById(userId)).thenReturn(Optional.ofNullable(null));

        boolean result = validator.isValid(userId, context);

        // then
        assertFalse(result);
    }

    @Test
    void whenUserIdIsNotNullAndUserFound_thenReturnTrue() {
        // given
        Long userId = 1L;
        // when
        when(userRepository.findById(userId)).thenReturn(Optional.of(User.builder().build()));

        boolean result = validator.isValid(userId, context);

        // then
        assertTrue(result);
    }
}