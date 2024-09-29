package com.aoi.springbootmall.controller;

import com.aoi.springbootmall.dto.ProductRequest;
import com.aoi.springbootmall.model.Product;
import com.aoi.springbootmall.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        } else {
            //找不到，回傳 404 not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest) {
        //productId 為新增商品的 ID
        Integer productId = productService.createProduct(productRequest);
        //宣告 product 物件，從 productId 取得新增內容
        Product product = productService.getProductById(productId);
        //新增內容回傳給前端，並返回 201 狀態馬表示新增完成。
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }
}
