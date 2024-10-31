package com.aoi.springbootmall.service;

import com.aoi.springbootmall.dto.CreateOrderRequest;
import com.aoi.springbootmall.model.Order;

public interface OrderService {

    Order getOrderById(Integer orderId);

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
}
