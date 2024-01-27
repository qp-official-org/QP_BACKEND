package qp.official.qp.validation.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import qp.official.qp.apiPayload.code.status.ErrorStatus;
import qp.official.qp.repository.QuestionReportRepository;
import qp.official.qp.validation.annotation.ExistQuestionReport;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@RequiredArgsConstructor
public class ExistQuestionReportValidator implements ConstraintValidator<ExistQuestionReport, Long> {
    private final QuestionReportRepository questionReportRepository;
    @Override
    public void initialize(ExistQuestionReport constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long reportId, ConstraintValidatorContext context) {

        boolean isValid = true;

        boolean isExist = questionReportRepository.findById(reportId).isPresent();
        if (!isExist) {
            isValid = false;
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.QUESTIONREPORT_NOT_FOUND.toString()).addConstraintViolation();
        }

        return isValid;
    }
}
