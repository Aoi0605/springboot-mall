package com.aoi.springbootmall.service;

import com.aoi.springbootmall.constant.ProductCategory;
import com.aoi.springbootmall.dto.ProductRequest;
import com.aoi.springbootmall.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAllProduct(ProductCategory category, String search);
    Product getProductById(Integer productId);
    Integer createProduct(ProductRequest productRequest);
    void updateProductById(Integer productId,ProductRequest productRequest);
    void deleteProductById(Integer productId);
}
