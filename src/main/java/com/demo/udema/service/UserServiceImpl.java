package com.demo.udema.service;

import com.demo.udema.controller.UserNotFoundException;
import com.demo.udema.entity.User;
import com.demo.udema.repositoryDAO.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public void save(User user) {
        if (!user.getPassword().equals("")) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        } else {
            User userTempID = userRepository.findById(user.getId());
            String userTempPassword = userTempID.getPassword();
            user.setPassword(userTempPassword);
        }
        userRepository.save(user);
    }
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void saveNoPassword(User user) {
        User userTempID = userRepository.findById(user.getId());
        String userTempPassword = userTempID.getPassword();
        user.setPassword(userTempPassword);
        userRepository.save(user);
    }
    @Override
    public List<String> findUsersWhoBoughtCourseByCourseTitle(String title) {
        return userRepository.findUsersWhoBoughtCourseByCourseTitle(title);
    }

    @Override
    public String findRoleByUsername(String username) {
        return userRepository.findRoleByUsername(username);
    }

    @Override
    public Integer findIdByUsername(String username) {
        return userRepository.findIdByUsername(username);
    }

    public void updateResetPasswordToken(String token, String email) throws UserNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user != null) {
            user.setResetPasswordToken(token);
            userRepository.save(user);
        } else {
            throw new UserNotFoundException("Could not find this user");
        }
    }

    public void updateVerificationToken(String verificationToken, String email) throws UserNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user != null) {
            user.setVerificationToken(verificationToken);
            userRepository.save(user);
        } else {
            throw new UserNotFoundException("Could not find this user");
        }
    }

    public User get(String resetPasswordToken) {
        return userRepository.findByResetPasswordToken(resetPasswordToken);
    }

    public void updatePassword(User user, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword =  passwordEncoder.encode(newPassword);

        user.setPassword(encodedPassword);
        user.setResetPasswordToken(null);

        userRepository.save(user);
    }

    public User getByVerificationToken(String verificationToken) {
        return userRepository.findByVerificationToken(verificationToken);
    }

    public void verifyAccount(User user, String verificationToken) {
        user.setVerificationToken(null);
        user.setEnabled(true);
        userRepository.save(user);
    }
}
