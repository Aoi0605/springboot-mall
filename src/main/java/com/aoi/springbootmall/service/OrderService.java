package com.aoi.springbootmall.service;

import com.aoi.springbootmall.dto.CreateOrderRequest;
import com.aoi.springbootmall.dto.OrderQueryParams;
import com.aoi.springbootmall.model.Order;

import java.util.List;

public interface OrderService {

    Integer countOrders(OrderQueryParams orderQueryParams);

    List<Order> getOrders(OrderQueryParams orderQueryParams);

    Order getOrderById(Integer orderId);

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
}
