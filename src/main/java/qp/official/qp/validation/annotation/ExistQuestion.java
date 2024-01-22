package qp.official.qp.validation.annotation;

import qp.official.qp.validation.validator.ExistQuestionValidator;
import qp.official.qp.validation.validator.ExistUserValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ExistQuestionValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistQuestion {
    String message() default "해당하는 질문이 존재하지 않습니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
