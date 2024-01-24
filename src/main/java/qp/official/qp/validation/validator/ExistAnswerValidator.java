package qp.official.qp.validation.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import qp.official.qp.apiPayload.code.status.ErrorStatus;
import qp.official.qp.repository.AnswerRepository;
import qp.official.qp.validation.annotation.ExistAnswer;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

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

        boolean isValid = true;

        boolean isExist = answerRepository.findById(answerId).isPresent();
        if (!isExist) {
            isValid = false;
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.ANSWER_NOT_FOUND.toString()).addConstraintViolation();
        }

        return isValid;
    }
}
