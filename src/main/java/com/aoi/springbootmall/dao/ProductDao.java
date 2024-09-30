package com.aoi.springbootmall.dao;

import com.aoi.springbootmall.dto.ProductRequest;
import com.aoi.springbootmall.model.Product;

import java.util.List;


public interface ProductDao {

    List<Product> findAllProduct();
    //根據 ID 查詢商品數據
    Product getProductById(Integer productId);
    //Integer 為返回值
    Integer createProduct(ProductRequest productRequest);

    void updateProductById(Integer productId,ProductRequest productRequest);

    void deleteProductById(Integer productId);
}
