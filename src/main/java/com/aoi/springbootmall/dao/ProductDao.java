package com.aoi.springbootmall.dao;

import com.aoi.springbootmall.dto.ProductQueryParams;
import com.aoi.springbootmall.dto.ProductRequest;
import com.aoi.springbootmall.model.Product;

import java.util.List;


public interface ProductDao {

    Integer countProduct(ProductQueryParams params);

    List<Product> findAllProduct(ProductQueryParams params);
    //根據 ID 查詢商品數據
    Product getProductById(Integer productId);
    //Integer 為返回值
    Integer createProduct(ProductRequest productRequest);

    void updateProductById(Integer productId,ProductRequest productRequest);

    void updateStock(Integer productId, Integer stock); // stock 這個 stock 為更新過後的數據

    void deleteProductById(Integer productId);
}
