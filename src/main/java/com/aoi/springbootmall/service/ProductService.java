package com.aoi.springbootmall.service;

import com.aoi.springbootmall.dto.ProductQueryParams;
import com.aoi.springbootmall.dto.ProductRequest;
import com.aoi.springbootmall.model.Product;

import java.util.List;

public interface ProductService {
    Integer countProduct(ProductQueryParams params);
    List<Product> getAllProduct(ProductQueryParams params);
    Product getProductById(Integer productId);
    Integer createProduct(ProductRequest productRequest);
    void updateProductById(Integer productId,ProductRequest productRequest);
    void deleteProductById(Integer productId);
}
