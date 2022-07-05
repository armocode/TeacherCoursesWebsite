package com.demo.udema.service;


import com.demo.udema.entity.Orders;

import java.util.List;

public interface OrderService {
    void deleteByCourseId(int id);
    List<Orders> findAllByCourseId(int id);
    void save(Orders orders);
    void saveUserIdCourseId(int userId, int courseId, int price);
    Integer findOrderUrlByUsername(String username);
}
