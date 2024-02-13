package qp.official.qp.validation.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import qp.official.qp.apiPayload.code.status.ErrorStatus;
import qp.official.qp.repository.AnswerReportRepository;
import qp.official.qp.validation.annotation.ExistAnswerReport;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@RequiredArgsConstructor
public class ExistAnswerReportValidator implements ConstraintValidator<ExistAnswerReport, Long> {

    private final AnswerReportRepository answerReportRepository;
    @Override
    public void initialize(ExistAnswerReport constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long reportId, ConstraintValidatorContext context) {
        ErrorStatus errorStatus;

        boolean isValid;

        if (reportId == null) {
            errorStatus = ErrorStatus.ANSWERREPORT_ID_NULL;
            isValid = false;
        } else {
            errorStatus = ErrorStatus.ANSWERREPORT_NOT_FOUND;
            isValid = answerReportRepository.findById(reportId).isPresent();
        }

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(errorStatus.toString()).addConstraintViolation();
        }

        return isValid;
    }
}
