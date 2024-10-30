package com.aoi.springbootmall.service.Impl;

import com.aoi.springbootmall.dao.Impl.ProductDaoImpl;
import com.aoi.springbootmall.dao.OrderDao;
import com.aoi.springbootmall.dao.ProductDao;
import com.aoi.springbootmall.dto.BuyItem;
import com.aoi.springbootmall.dto.CreateOrderRequest;
import com.aoi.springbootmall.model.OrderItem;
import com.aoi.springbootmall.model.Product;
import com.aoi.springbootmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;

    @Transactional //確保資料庫操作同時發生，若出現錯誤則回滾
    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {
        int totalAmount = 0;
        List<OrderItem> orderItemList = new ArrayList<>();

        for(BuyItem buyItem : createOrderRequest.getBuyItemList()){
            Product product = productDao.getProductById(buyItem.getProductId());

            //計算總價錢
            int amount = product.getPrice() * buyItem.getQuantity();
            totalAmount += amount;

            //轉換 butItem to orderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(buyItem.getProductId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setAmount(totalAmount);

            orderItemList.add(orderItem);
        }

        Integer orderId = orderDao.createOrder(userId, totalAmount);

        orderDao.createOrderItems(orderId, orderItemList);

        return orderId;
    }
}
