package com.cdut.miaosha.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author ：yinmy
 * @date ：Created on 2020/1/16 15:58
 */

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = {IsMobileValidator.class}
)
public @interface IsMobile {

    boolean required() default true;

    String message() default "手机号码格式有误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
