package com.demo.udema.repositoryDAO;

import com.demo.udema.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findById(int id);
    User findByEmail(String email);
}
