package com.cdut.miaosha.validator;

import com.cdut.miaosha.util.VaildatorUil;
import com.mysql.cj.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author ：yinmy
 * @date ：Created on 2020/1/16 16:22
 */
public class IsMobileValidator implements ConstraintValidator<IsMobile,String> {

    private boolean required = false;

    @Override
    public void initialize(IsMobile constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(required){
            return VaildatorUil.isMobile(value);
        }else {
            if(StringUtils.isEmptyOrWhitespaceOnly(value)){
                return true;
            }else {
                return VaildatorUil.isMobile(value);
            }
        }
    }


}
