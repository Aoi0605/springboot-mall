package com.aoi.springbootmall.controller;

import com.aoi.springbootmall.constant.ProductCategory;
import com.aoi.springbootmall.dto.ProductQueryParams;
import com.aoi.springbootmall.dto.ProductRequest;
import com.aoi.springbootmall.model.Product;
import com.aoi.springbootmall.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated //使用 @Max and @Min 加入這個註解才會生效
@RestController
public class ProductComtroller {

    @Autowired
    private ProductService productService;

    //查詢商品列表及根據分類查詢
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts(
            //從前端接取搜尋結果 enum 值，springboot 會根據 ProductCategory 搜尋相應的 enum
            //加上(required = false)代表這個參數不是必要，沒有加上則這個方法一定要參數。
            @RequestParam(required = false) ProductCategory category,

            //實作輸入搜尋
            @RequestParam(required = false) String search,
            //不使用 (required = false) 是因為預設都是最新的來排序，設定(defaultValue = "created_date")
            //代表若是前端沒有傳回參數，預設為 created_date 排序
            @RequestParam(defaultValue = "created_date") String orderBy,
            //sort 參數，用來控制升序還是降序，desc 為 sql 語法，降序之意，此預設為降序。
            @RequestParam(defaultValue = "desc") String sort,

            //分頁
            //limit and offset 與 sql 語法有關，這可以節省資料庫效能，不一次取得太多數據，以此為例預設只取出5筆數據
            //limit 表示要取得幾筆商品數據，這邊設定預設為5，代表只取出5筆數據
            //@Max 前端傳進來的數據最大不超過1000，最小為0避免傳負數
            @RequestParam(defaultValue = "5" ) @Max(1000) @Min(0) Integer limit,
            //表示要跳過多少筆數據，這邊預設為0代表不跳過任何一筆數據。
            @RequestParam(defaultValue = "0" ) @Min(0) Integer offset
    ) {
        ProductQueryParams params = new ProductQueryParams();
        params.setCategory(category);
        params.setSearch(search);
        params.setOrderBy(orderBy);
        params.setSort(sort);
        params.setLimit(limit);
        params.setOffset(offset);
        List<Product> productList = productService.getAllProduct(params);
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

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
        if (product != null) {
            //修改商品數據
            productService.updateProductById(productId, productRequest);
            //從資料庫取得更新後的商品數據
            Product updatedProduct = productService.getProductById(productId);
            //將修改好的數據回傳給前端
            return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
        } else {
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
