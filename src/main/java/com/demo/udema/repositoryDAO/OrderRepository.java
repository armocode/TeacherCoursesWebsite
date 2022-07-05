package com.demo.udema.repositoryDAO;

import com.demo.udema.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Integer> {
    List<Orders> findAllByCourseId(int id);
    @Modifying
    @Query(value = "DELETE FROM orders WHERE course_id = ?1", nativeQuery = true)
    void deleteByCourseId(int id);
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO orders(user_id, course_id, price)" +
            "VALUES(?1, ?1, ?1)", nativeQuery = true)
    void saveUserIdCourseId(int userId, int courseId, int price);

    @Query(value = "SELECT user_id FROM orders " +
            "WHERE user_id IN " +
            "(SELECT id FROM users" +
            " WHERE username LIKE ?1)", nativeQuery = true)
    Integer findOrderUserIdByUsername(String username);
}
