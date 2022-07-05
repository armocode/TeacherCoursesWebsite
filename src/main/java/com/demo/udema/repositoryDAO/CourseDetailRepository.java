package com.demo.udema.repositoryDAO;

import com.demo.udema.entity.CourseDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseDetailRepository extends JpaRepository<CourseDetails, Integer> {
    @Query(value = "SELECT * FROM course_details" +
            " WHERE course_details.course_id IN" +
            "(SELECT courses.id FROM courses" +
            " WHERE courses.teacher_id IN" +
            "(SELECT users.id FROM users" +
            " WHERE users.username = ?1))", nativeQuery = true)
    List<CourseDetails> findAllTeacherCourseDetailsByUsername(String username);

    @Query(value = "SELECT * FROM course_details " +
            "WHERE course_id IN" +
            "(SELECT id FROM courses " +
            "WHERE courses.title LIKE ?1)", nativeQuery = true)
    CourseDetails findCourseDetailsByCourseTitle(String title);
}
