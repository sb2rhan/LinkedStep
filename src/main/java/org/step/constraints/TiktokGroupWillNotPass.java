package org.step.constraints;

import org.step.constraints.validator.TiktokGroupValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(value = {ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {TiktokGroupValidator.class})
@Documented
public @interface TiktokGroupWillNotPass {
    String message() default "Tiktok is not allowed here";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Permission permission() default Permission.DENIED;
}
