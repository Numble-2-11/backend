package com.community.numble.app.user.validator;

import com.community.numble.app.user.dto.UserCreateDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.thymeleaf.util.StringUtils;

@Component
public class UserValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(UserCreateDto.class);
    }

    @Override
    public void validate(Object target, Errors errors) {

        UserCreateDto userCreateDto = (UserCreateDto)target;

        if(!StringUtils.equals(userCreateDto.getPassword(), userCreateDto.getPasswordConfirm())){
            errors.rejectValue("password", "패스워드가 일치하지 않습니다.");
        }

    }
}
