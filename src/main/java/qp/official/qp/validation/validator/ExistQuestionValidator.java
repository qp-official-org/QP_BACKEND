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

        // isExist Question
        boolean isValid = questionRepository.findById(questionId).isPresent();

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.QUESTION_NOT_FOUND.toString()).addConstraintViolation();
        }

        return isValid;
    }
}



