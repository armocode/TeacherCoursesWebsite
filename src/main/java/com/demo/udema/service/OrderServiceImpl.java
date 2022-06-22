package com.demo.udema.service;

import com.demo.udema.entity.Orders;
import com.demo.udema.repositoryDAO.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    public OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void deleteByCourseId(int id) {
        orderRepository.deleteByCourseId(id);
    }

    @Override
    public List<Orders> findAllByCourseId(int id) {
        return orderRepository.findAllByCourseId(id);
    }
}
