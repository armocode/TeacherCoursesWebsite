package com.demo.udema.repositoryDAO;

import com.demo.udema.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Integer> {
    List<Orders> findAllByCourseId(int id);

    @Modifying
    @Query(value = "DELETE FROM orders WHERE course_id = ?1", nativeQuery = true)
    void deleteByCourseId(int id);
}
