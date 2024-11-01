package com.aoi.springbootmall.controller;

import com.aoi.springbootmall.dto.CreateOrderRequest;
import com.aoi.springbootmall.dto.OrderQueryParams;
import com.aoi.springbootmall.model.Order;
import com.aoi.springbootmall.service.OrderService;
import com.aoi.springbootmall.util.Page;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/users/{userId}/oeders")
    public ResponseEntity<Page<Order>> getOrders(@PathVariable Integer userId,
                                                 @RequestParam(defaultValue = "10") @Max(1000) @Min(0) Integer limit,
                                                 @RequestParam(defaultValue = "0") @Min(0) Integer offset) {
        OrderQueryParams orderQueryParams = new OrderQueryParams();
        orderQueryParams.setUserId(userId);
        orderQueryParams.setLimit(limit);
        orderQueryParams.setOffset(offset);

        //取得 orederList
        List<Order> orderList = orderService.getOrders(orderQueryParams);

        //取得 order 總數
        Integer count = orderService.countOrders(orderQueryParams);

        //分頁
        Page<Order> page = new Page<>();
        page.setTotal(count);
        page.setLimit(limit);
        page.setOffset(offset);
        page.setResults(orderList);

        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @PostMapping("/users/{userId}/orders")
    public ResponseEntity<?> createOrder(@PathVariable Integer userId,
                                         @RequestBody @Valid CreateOrderRequest createOrderRequest) {

        Integer orderId = orderService.createOrder(userId, createOrderRequest);

        Order order = orderService.getOrderById(orderId);

        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }
}
