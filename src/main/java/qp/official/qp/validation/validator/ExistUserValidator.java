package qp.official.qp.validation.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import qp.official.qp.apiPayload.code.status.ErrorStatus;
import qp.official.qp.repository.UserRepository;
import qp.official.qp.validation.annotation.ExistUser;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@RequiredArgsConstructor
public class ExistUserValidator implements ConstraintValidator<ExistUser, Long> {

    private final UserRepository userRepository;

    @Override
    public void initialize(ExistUser constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long userId, ConstraintValidatorContext context) {
        ErrorStatus errorStatus;

        boolean isValid;

        // null is invalid
        if (userId == null) {
            errorStatus = ErrorStatus.USER_ID_NULL;
            isValid = false;
        } else {
            errorStatus = ErrorStatus.USER_NOT_FOUND;
            isValid = userRepository.findById(userId).isPresent();
        }

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(errorStatus.toString()).addConstraintViolation();
        }

        return isValid;
    }
}

