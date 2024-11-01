package com.aoi.springbootmall.dto;

import com.aoi.springbootmall.constant.ProductCategory;
import jakarta.validation.constraints.NotNull;

//這個 class 定義負責接住前端傳來的參數
public class ProductRequest {


    //private Integer productId; ID 由資料庫生成，故前端不須傳來

    //加入@NotNull 驗證參數的值
    @NotNull
    private String productName;

    @NotNull
    private ProductCategory category;

    @NotNull
    private String imageUrl;

    @NotNull
    private Integer price;

    @NotNull
    private Integer stock;

    private String description;


    //讓 springboot 設定即可，故前端不須傳來
//    private Date createData;
//    private Date lastModifiedDate;


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
