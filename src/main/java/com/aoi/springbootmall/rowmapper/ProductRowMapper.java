package com.aoi.springbootmall.rowmapper;

import com.aoi.springbootmall.constant.ProductCategory;
import com.aoi.springbootmall.model.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRowMapper implements RowMapper<Product> {

    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        //轉換數據
        Product product = new Product();
        product.setProductId(rs.getInt("product_id"));
        product.setProductName(rs.getString("product_name"));

        //將資料庫取出的 String 轉換為 enum
        //String categoryStr = rs.getString("category");
        //ProductCategory category = ProductCategory.valueOf(categoryStr);
        //簡潔寫法
        product.setCategory(ProductCategory.valueOf(rs.getString("category")));
        //enum 可以確保資料庫的值符合 enum 參數，避免資料庫出現奇怪的值，傳到前端而不自知。

        product.setImageUrl(rs.getString("image_url"));
        product.setPrice(rs.getInt("price"));
        product.setStock(rs.getInt("stock"));
        product.setDescription(rs.getString("description"));
        product.setCreateData(rs.getDate("created_date"));
        product.setLastModifiedDate(rs.getDate("last_modified_date"));

        return product;
    }
}
