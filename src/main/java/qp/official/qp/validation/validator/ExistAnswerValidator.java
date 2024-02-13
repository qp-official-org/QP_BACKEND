package qp.official.qp.validation.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import qp.official.qp.apiPayload.code.status.ErrorStatus;
import qp.official.qp.repository.AnswerRepository;
import qp.official.qp.validation.annotation.ExistAnswer;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@RequiredArgsConstructor
public class ExistAnswerValidator implements ConstraintValidator<ExistAnswer, Long> {

    private final AnswerRepository answerRepository;
    @Override
    public void initialize(ExistAnswer constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long answerId, ConstraintValidatorContext context) {
        ErrorStatus errorStatus;

        boolean isValid;

        if (answerId == null) {
            errorStatus = ErrorStatus.ANSWER_ID_NULL;
            isValid = false;
        } else {
            errorStatus = ErrorStatus.ANSWER_NOT_FOUND;
            isValid = answerRepository.findById(answerId).isPresent();
        }

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(errorStatus.toString()).addConstraintViolation();
        }

        return isValid;
    }
}
