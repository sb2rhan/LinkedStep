package org.step.constraints.validator;

import org.step.constraints.Permission;
import org.step.constraints.TiktokGroupWillNotPass;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TiktokGroupValidator implements ConstraintValidator<TiktokGroupWillNotPass, String> {

    private Permission permission;

    @Override
    public void initialize(TiktokGroupWillNotPass constraintAnnotation) {
        this.permission = constraintAnnotation.permission();
    }

    @Override
    public boolean isValid(String groupName, ConstraintValidatorContext constraintValidatorContext) {
        if (groupName.equalsIgnoreCase("tiktok")) {
            switch (permission) {
                case ALLOWED:
                    return true;
                case DENIED:
                    return false;
            }
        }
        return true;
    }
}
