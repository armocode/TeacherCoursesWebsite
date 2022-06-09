package com.demo.udema.repositoryDAO;

import com.demo.udema.entity.CourseReviews;
import com.demo.udema.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findById(int id);
    User findByEmail(String email);


    @Query(value = "SELECT * FROM Users" +
            " JOIN orders ON users.id = user_id" +
            " JOIN courses ON courses.id = course_id", nativeQuery = true)
    User findUserWhoBoughtCourse();

}
