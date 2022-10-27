package com.demo.udema.controller;

import com.demo.udema.entity.User;
import com.demo.udema.service.UserServiceImpl;
import com.demo.udema.utility.Utility;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@Controller
public class ForgotPasswordController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserValidator userValidator;

    @GetMapping("/forgot_password")
    public String showForgotPasswordForm(Model model) {
        model.addAttribute("pageTitle", "Forgot Password");
        return "forgot-password-form";
    }

    @PostMapping("/forgot_password")
    public String processForgotPasswordForm(HttpServletRequest request, Model model) {
        String email = request.getParameter("email");
        String token = RandomString.make(30);

        try {
            userService.updateResetPasswordToken(token, email);
            String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;

            sendEmail(email, resetPasswordLink);

            model.addAttribute("message", "We have sent you a reset password link to your email. Please check.");

        } catch (UserNotFoundException e) {
            model.addAttribute("error", e.getMessage());
        } catch (UnsupportedEncodingException | MessagingException e) {
            model.addAttribute("error", "Error while sending email!");
        }

        model.addAttribute("pageTitle", "Forgot Password");
        return "forgot-password-form";
    }

    private void sendEmail(String email, String resetPasswordLink) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("contact@udema.com", "Udema Support");
        helper.setTo(email);

        String subject = "Here's the link to reset your password for Udema website login";
        String content = "<p>Hello,</p>" +
                "<p>You have requested to reset the password</p>" +
                "<p>Click the link below to change your password:</p>" +
                "<p><b><a href=\""+ resetPasswordLink + "\">Change my password</a><b></p>" +
                "<p>Or manually copy and paste this URL to a new web browser's tab: </p>" +  resetPasswordLink +
                "<p>Ignore this if you have not requested your password reset</p>" +
                "<br><br>" +
                "<p>Regards,</p>" +
                "<p>Udema</p>";

        helper.setSubject(subject);
        helper.setText(content, true);

        mailSender.send(message);
    }

    @GetMapping("/reset_password")
    public String showResetPasswordForm(@Param(value = "token") String token, Model model) {

        User user = userService.get(token);
        if (user == null) {
            model.addAttribute("title", "Reset your password");
            model.addAttribute("warning", "This link has been already used or it is invalid.");
            return "message";
        }
        model.addAttribute("userForm", new User());
        model.addAttribute("token", token);
        model.addAttribute("pageTitle", "Reset Your Password");
        return "reset-password-form";
    }

    @PostMapping("/reset_password")
    public ModelAndView processResetPassword(HttpServletRequest request, Model model, RedirectAttributes redirAtt) {

        String token = request.getParameter("token");
        String newPassword = request.getParameter("newPassword");
        String confirmNewPassword = request.getParameter("passwordNewConfirm");
        User user = userService.get(token);

        if (user == null) {
            ModelAndView mv = new ModelAndView("redirect:message");
            redirAtt.addFlashAttribute("title", "Reset Your Password");
            redirAtt.addFlashAttribute("warning", "This link has been already used or it is invalid.");
            return mv;
        }

        user.setNewPassword(newPassword);
        user.setPasswordNewConfirm(confirmNewPassword);

        String checkValidation = userValidator.validateResetPassword(newPassword, confirmNewPassword);

        if (checkValidation == null) {
            userService.updatePassword(user, newPassword);
            ModelAndView mv = new ModelAndView();
            mv.setViewName("redirect:message");
            redirAtt.addFlashAttribute("message", "Password has been changed successfully!");
            return mv;
        } else {
            model.addAttribute("warning", checkValidation);
            String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;
            ModelAndView mv = new ModelAndView("redirect:" + resetPasswordLink);
            redirAtt.addFlashAttribute("warning", checkValidation);
            return mv;
        }
    }

    @GetMapping("/message")
    public String showMessage(HttpServletRequest request, Model model) {
        return "message";
    }

}
