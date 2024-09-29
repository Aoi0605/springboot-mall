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
    public ResponseEntity<Product> getProductById(@PathVariable Integer productId) {
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

    //productRequest 內的參數允許修改，故沿用。
    @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId,
                                                 @RequestBody @Valid ProductRequest productRequest) {
        Product product = productService.getProductById(productId);
        //檢查欲修改商品是否存在
        if(product != null) {
            //修改商品數據
            productService.updateProductById(productId, productRequest);
            //從資料庫取得更新後的商品數據
            Product updatedProduct = productService.getProductById(productId);
            //將修改好的數據回傳給前端
            return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer productId) {
        //刪除的 API 不用加上是否有這個商品的判斷，商品只要不存在即可。
        productService.deleteProductById(productId);
        //204 NO_CONTENT 表示數據已被刪除。
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
