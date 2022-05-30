package com.demo.udema.service;

import com.demo.udema.entity.User;
import com.demo.udema.repositoryDAO.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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


}
