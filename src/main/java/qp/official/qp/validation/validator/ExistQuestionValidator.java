package qp.official.qp.validation.validator;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import qp.official.qp.apiPayload.code.status.ErrorStatus;
import qp.official.qp.repository.QuestionRepository;
import qp.official.qp.validation.annotation.ExistQuestion;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@RequiredArgsConstructor
public class ExistQuestionValidator implements ConstraintValidator<ExistQuestion, Long> {
    private final QuestionRepository questionRepository;

    @Override
    public void initialize(ExistQuestion constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long questionId, ConstraintValidatorContext context) {
        ErrorStatus errorStatus;
        boolean isValid;

        // null check
        if (questionId == null) {
            errorStatus = ErrorStatus.QUESTION_ID_NULL;
            isValid = false;
        } else {
            errorStatus = ErrorStatus.QUESTION_NOT_FOUND;
            // isExist Question
            isValid = questionRepository.findById(questionId).isPresent();
        }

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(errorStatus.toString()).addConstraintViolation();
        }

        return isValid;
    }
}



