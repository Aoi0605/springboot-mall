package com.aoi.springbootmall.dao;

import com.aoi.springbootmall.model.Product;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


public interface ProductDao {

    //根據 ID 查詢商品數據
    Product getProductById(Integer productId);
}
