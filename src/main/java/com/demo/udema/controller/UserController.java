package com.demo.udema.controller;

import com.demo.udema.entity.User;
import com.demo.udema.service.*;
import com.demo.udema.utility.Utility;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;


@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private UserValidator userValidator;
    @Autowired
    private JavaMailSender mailSender;

    @GetMapping("/registration")
    public String registration(Model model) {
        if (securityService.isAuthenticated()) {
            return "redirect:/";
        }
        model.addAttribute("userForm", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") User userForm, Model model, BindingResult bindingResult, HttpServletRequest request) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }
        userService.save(userForm);
        User user = userService.findByEmail(userForm.getEmail());
        System.out.println(user.getUsername());

        String email = userForm.getEmail();
        String verificationToken = RandomString.make(64);
        user.setVerificationToken(verificationToken);

        try {
            userServiceImpl.updateVerificationToken(verificationToken, email);

            String verifyAccountLink = Utility.getSiteURL(request) + "/verification?token=" + verificationToken;
            sendEmailForVerification(email, verifyAccountLink);
        } catch (MessagingException e) {
            model.addAttribute("error", e.getMessage());
        } catch (UserNotFoundException e) {
            model.addAttribute("error", e.getMessage());
        } catch (UnsupportedEncodingException e) {
            model.addAttribute("error", "Error while sending email!");
        }
        //securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());
        return "redirect:registrationMessage";
    }

    private void sendEmailForVerification(String email, String verifyAccountLink) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("contact@udema.com", "Udema Support");
        helper.setTo(email);

        String subject = "Here's the link to verify your Udema account";
        String content = "<p>Hello,</p>" +
                "<p>You have created an account in Udema!</p>" +
                "<p>Click the link below to verify it:</p>" +
                "<p><b><a href=\""+ verifyAccountLink + "\">Verify account</a><b></p>" +
                "<p>Or manually copy and paste this URL to a new web browser's tab and enter the page: </p>" +  verifyAccountLink +
                "<p>Ignore this if you have not requested your password reset</p>" +
                "<br><br>" +
                "<p>Regards,</p>" +
                "<p>Udema</p>";

        helper.setSubject(subject);
        helper.setText(content, true);

        mailSender.send(message);
    }

    @GetMapping("/registrationMessage")
    public ModelAndView registrationMessage(RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView("redirect:/message");
        redirectAttributes.addFlashAttribute("title", "Registration Confirmation");
        redirectAttributes.addFlashAttribute("message", "Verification link has been sent to your email. Please check and verify.");
        return modelAndView;
    }

    @GetMapping("/verification")
    public String verifyAccount(@Param(value = "token") String token, Model model) {
        User user = userServiceImpl.getByVerificationToken(token);
        if (user == null) {
            model.addAttribute("title", "Account Verification");
            model.addAttribute("warning", "This link is no longer valid! You might have activated your account before." +
                    " Please try to log in. Contact our Support Center at Udema if you have any problems.");
            return "message";
        }
        userServiceImpl.verifyAccount(user, token);
        model.addAttribute("title", "Account Verification");
        model.addAttribute("message", "Your account has been verified!");
        return "message";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (securityService.isAuthenticated()) {
            return "redirect:/";
        }
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");
        //This account is not activated! Please check your email for verification details.
        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");
        return "login";
    }

    @GetMapping("/userPage")
    public String viewDetails(@AuthenticationPrincipal UserDetails loggerUser, Model model) {
        String username = loggerUser.getUsername();
        User user = userService.findByUsername(username);
        model.addAttribute("user", user);
        return "admin-page/user-profile";
    }

    @PostMapping("/userPage/update")
    public String updateDetails(@ModelAttribute("user") User user, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        userValidator.validate(user, bindingResult);
        if (user.getOldPassword().equals("") && user.getNewPassword().equals("") && user.getPasswordNewConfirm().equals("")) {
            if (!(user.getOldEmail().equals("") || user.getNewEmail().equals("") || user.getEmailConfirm().equals(""))) {
                if (bindingResult.hasFieldErrors("name") || bindingResult.hasFieldErrors("surname") || bindingResult.hasFieldErrors("newEmail") || bindingResult.hasFieldErrors("emailConfirm") || bindingResult.hasFieldErrors("oldEmail")) {
                    redirectAttributes.addFlashAttribute("error", "An error occurred while saving the changes (name, surname, email)");
                    return "redirect:/userPage";
                }
                redirectAttributes.addFlashAttribute("message", "Name and Email saved successfully");
                user.setEmail(user.getNewEmail()); // Using, because in front error
                userService.saveNoPassword(user);
                return "redirect:/userPage";
            } else {
                if (bindingResult.hasFieldErrors("name") || bindingResult.hasFieldErrors("surname")) {
                    redirectAttributes.addFlashAttribute("error", "An error occurred while saving the changes (name, surname)");
                    return "redirect:/userPage";
                }
                redirectAttributes.addFlashAttribute("message", "Name saved successfully");
                userService.saveNoPassword(user);
                return "redirect:/userPage";
            }
        } else if (user.getOldEmail().equals("") || user.getNewEmail().equals("") || user.getEmailConfirm().equals("")) {
            if (!(user.getOldPassword().equals("") || user.getNewPassword().equals("") || user.getPasswordNewConfirm().equals(""))) {
                if (bindingResult.hasFieldErrors("name") || bindingResult.hasFieldErrors("surname") || bindingResult.hasFieldErrors("newPassword") || bindingResult.hasFieldErrors("passwordNewConfirm") || bindingResult.hasFieldErrors("oldPassword")) {
                    redirectAttributes.addFlashAttribute("error", "An error occurred while saving the changes (name, surname, password)");
                    return "redirect:/userPage";
                }
                user.setPassword(user.getNewPassword());
                userService.save(user);
                redirectAttributes.addFlashAttribute("message", "Name and Password saved successfully");
                return "redirect:/userPage";
            } else {
                if (bindingResult.hasFieldErrors("name") || bindingResult.hasFieldErrors("surname")) {
                    redirectAttributes.addFlashAttribute("error", "An error occurred while saving the changes (name, surname)");
                    return "redirect:/userPage";
                }
                userService.saveNoPassword(user);
                redirectAttributes.addFlashAttribute("message", "Name saved successfully");
                return "redirect:/userPage";
            }
        } else {
            if (bindingResult.hasFieldErrors("name") || bindingResult.hasFieldErrors("surname") || bindingResult.hasFieldErrors("newPassword") || bindingResult.hasFieldErrors("passwordNewConfirm") || bindingResult.hasFieldErrors("oldPassword") || bindingResult.hasFieldErrors("newEmail") || bindingResult.hasFieldErrors("emailConfirm") || bindingResult.hasFieldErrors("oldEmail")) {
                redirectAttributes.addFlashAttribute("error", "An error occurred while saving the changes (ALL)");
                return "redirect:/userPage";
            }
            user.setEmail(user.getNewEmail()); // Using, because in front error
            user.setPassword(user.getNewPassword());
            userService.save(user);
            redirectAttributes.addFlashAttribute("message", "Saved successfully");
            return "redirect:/userPage";
        }
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "welcome";
    }

    @GetMapping("/adminPage")
    public String adminPage() {
        return "admin-page/index";
    }
}



