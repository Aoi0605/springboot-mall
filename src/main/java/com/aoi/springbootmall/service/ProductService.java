package com.aoi.springbootmall.service;

import com.aoi.springbootmall.dto.ProductRequest;
import com.aoi.springbootmall.model.Product;

public interface ProductService {

    Product getProductById(Integer productId);
    Integer createProduct(ProductRequest productRequest);
    void updateProduct(Integer productId,ProductRequest productRequest);
}
