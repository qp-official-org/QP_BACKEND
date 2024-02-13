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
        ErrorStatus errorStatus;

        boolean isValid;

        if (reportId == null) {
            errorStatus = ErrorStatus.QUESTIONREPORT_ID_NULL;
            isValid = false;
        } else {
            errorStatus = ErrorStatus.QUESTIONREPORT_NOT_FOUND;
            isValid = questionReportRepository.findById(reportId).isPresent();
        }

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(errorStatus.toString()).addConstraintViolation();
        }

        return isValid;
    }
}
