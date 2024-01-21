package qp.official.qp.validation.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import qp.official.qp.apiPayload.code.status.ErrorStatus;
import qp.official.qp.repository.HashtagRepository;
import qp.official.qp.validation.annotation.ExistHashTag;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ExistHashTagValidator implements ConstraintValidator<ExistHashTag, List<Long>> {
    private final HashtagRepository hashtagRepository;

    @Override
    public void initialize(ExistHashTag constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(List<Long> hashtagList, ConstraintValidatorContext context) {

        boolean isValid = true;
        for (Long hashtagId : hashtagList) {

            // 존재 하는지 확인
            boolean isExist = hashtagRepository.findById(hashtagId).isPresent();

            // 존재하지 않으면 false
            if (isExist) {
                isValid = false;

                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(ErrorStatus.HASHTAG_NOT_FOUND.toString()).addConstraintViolation();

                break;
            }
        }

        return isValid;
    }
}
