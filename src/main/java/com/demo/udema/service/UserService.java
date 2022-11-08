package com.demo.udema.service;

import com.demo.udema.entity.User;

import java.util.List;


public interface UserService {
    void save(User user);
    User findByUsername(String username);
    User findByEmail(String email);
    void saveNoPassword(User user);
    List<String> findUsersWhoBoughtCourseByCourseTitle(String title);
    String findRoleByUsername(String username);
    Integer findIdByUsername(String username);
}
