package com.aoi.springbootmall.controller;

import com.aoi.springbootmall.constant.ProductCategory;
import com.aoi.springbootmall.dto.ProductQueryParams;
import com.aoi.springbootmall.dto.ProductRequest;
import com.aoi.springbootmall.model.Product;
import com.aoi.springbootmall.service.ProductService;
import com.aoi.springbootmall.util.Page;
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

    @GetMapping("/products")
    //<Page<Product>> 表示要回傳的是 Page 類型的 Product 數據。
    public ResponseEntity<Page<Product>> getAllProducts(
            @RequestParam(required = false) ProductCategory category,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "created_date") String orderBy,
            @RequestParam(defaultValue = "desc") String sort,
            @RequestParam(defaultValue = "5" ) @Max(1000) @Min(0) Integer limit,
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

        //表示用 countProduct() 方法，會根據傳入的 params 參數，計算商品總數有多少筆。
        //商品總數的值，會因為 params 的參數不同而有不同，如用 enum 查詢，符合參數的總筆為為何？
        //用戶輸入查詢，符合參數的總比數為何，都會不同；所以商品總筆數會根據查詢條件不同而改變。
        Integer total = productService.countProduct(params);

        //創建 Page 物件的變數
        Page<Product> page = new Page<>();
        //將前端傳來的值放入，返回給前端
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(total);
        //將查詢出的商品放入 Results。
        page.setResults(productList);

        return new ResponseEntity<>(page, HttpStatus.OK);
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
