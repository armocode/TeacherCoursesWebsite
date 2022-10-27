package com.demo.udema.controller;

import com.demo.udema.entity.User;
import com.demo.udema.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserValidator implements Validator {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserService userService;
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;
        User fromDbEmail = userService.findByUsername(user.getUsername());
        User fromDbPassword = userService.findByUsername(user.getUsername());

//----username----//
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
        if (user.getUsername().length() < 6 || user.getUsername().length() > 32) {
            errors.rejectValue("username", "Size.userForm.username");
        }

        if (userService.findByUsername(user.getUsername()) != null) {
            errors.rejectValue("username", "Duplicate.userForm.username");
        }

//----email----//
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty");
        if (user.getEmail().length() < 6 || user.getEmail().length() > 32) {
            errors.rejectValue("email", "Size.userForm.email");

        }
        if (userService.findByEmail(user.getEmail()) != null) {
            errors.rejectValue("email", "Duplicate.userForm.email");
        }

//----newEmail----//
        if (user.getNewEmail() != null) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newEmail", "NotEmpty");
            if (user.getNewEmail().length() < 6 || user.getNewEmail().length() > 32) {
                errors.rejectValue("newEmail", "Size.userForm.newEmail");
            }
            if (userService.findByEmail(user.getNewEmail()) != null) {
                errors.rejectValue("newEmail", "Duplicate.userForm.newEmail");
            }

            if (!user.getNewEmail().equals(user.getEmailConfirm())) {
                errors.rejectValue("emailConfirm", "Diff.userForm.emailConfirm");
            }

            if (!user.getOldEmail().equals(fromDbEmail.getEmail())) {
                errors.rejectValue("oldEmail", "Diff.userForm.oldEmail");
            }
        }

//----name----//
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty");
        if (user.getName().length() < 2 || user.getName().length() > 32) {
            errors.rejectValue("name", "Size.userForm.name");
        }

//----surname----//
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "surname", "NotEmpty");
        if (user.getSurname().length() < 2 || user.getSurname().length() > 32) {
            errors.rejectValue("surname", "Size.userForm.surname");
        }

//----password----//
        if (user.getPassword() != null) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
            if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
                errors.rejectValue("password", "Size.userForm.password");
            }
        }

        if (user.getPasswordConfirm() != null) {
            if (!user.getPasswordConfirm().equals(user.getPassword())) {
                errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
            }
        }

//----NewPassword----//
        if (user.getNewPassword() != null) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newPassword", "NotEmpty");
            if (user.getNewPassword().length() < 8 || user.getNewPassword().length() > 32) {
                errors.rejectValue("newPassword", "Size.userForm.newPassword");
            }
        }

        if (user.getOldPassword() != null) {
            if (!(bCryptPasswordEncoder.matches(user.getOldPassword(), fromDbPassword.getPassword()))) {
                errors.rejectValue("oldPassword", "Diff.userForm.oldPassword");
            }
        }

        if (user.getPasswordNewConfirm() != null) {
            if (!user.getPasswordNewConfirm().equals(user.getNewPassword())) {
                errors.rejectValue("passwordNewConfirm", "Diff.userForm.getPasswordNewConfirm");
            }
        }

    }

    public String validateResetPassword(String password, String confirmPassword) {
        if (password == "" || confirmPassword == "") {
            return "You must enter both fields!";
        }
        if (!password.equals(confirmPassword)) {
            return "Passwords do not match!";
        }
        if (password.length() < 8) {
            return "Password must be at least 8 characters long!";
        }
        Matcher matcher = pattern.matcher(password);
        if (!matcher.matches()) {
            return "Password must have at least 1 number, 1 uppercase letter and 1 special symbol!";
        }
        return null;
    }
}
