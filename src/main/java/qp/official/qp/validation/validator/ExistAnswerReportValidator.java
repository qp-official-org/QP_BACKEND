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

        if (reportId == null) {
            return true;
        }

        boolean isValid = true;

        boolean isExist = answerReportRepository.findById(reportId).isPresent();
        if (!isExist) {
            isValid = false;
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.ANSWERREPORT_NOT_FOUND.toString()).addConstraintViolation();
        }

        return isValid;
    }
}
