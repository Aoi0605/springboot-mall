package com.aoi.springbootmall.controller;

import com.aoi.springbootmall.model.Product;
import com.aoi.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductComtroller {

    @Autowired
    private ProductService productService;

    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProductBy(@PathVariable Integer productId) {
        Product product = productService.getProductById(productId);

        //如果值所找到的數據不為 null 則回傳 200 ok 靜態碼
        if (product != null) {
            return ResponseEntity.status(HttpStatus.OK).body(product);
        }else {
            //找不到，回傳 404 not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
