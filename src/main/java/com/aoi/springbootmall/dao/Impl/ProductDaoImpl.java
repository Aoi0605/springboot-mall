package com.aoi.springbootmall.dao.Impl;

import com.aoi.springbootmall.dao.ProductDao;
import com.aoi.springbootmall.dto.ProductQueryParams;
import com.aoi.springbootmall.dto.ProductRequest;
import com.aoi.springbootmall.model.Product;
import com.aoi.springbootmall.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer countProduct(ProductQueryParams params) {

        String sql = "select count(*) from product WHERE 1=1 ";

        Map<String, Object> map = new HashMap<>();

        //查詢條件
        sql = addFilteringSql(sql, map, params);

        //queryForObject() 通常用於取得 count 的值。
        //Integer.class 表示將 count 的值，轉換成 Integer 類型的值
        Integer total = jdbcTemplate.queryForObject(sql, map, Integer.class);

        return total;
    }

    @Override
    public List<Product> findAllProduct(ProductQueryParams params) {
        //加上 WHERE 1=1 是為了要讓 " and category=:category" 拼接起來所加的，在 SQL 語法 1=1 是廢話。
        //如果 category 為 null 則 sql 語句會維持原樣。
        String sql = "select product_id, product_name, category, image_url, price, stock, description," +
                " created_date, last_modified_date from product WHERE 1=1";

        Map<String, Object> map = new HashMap<>();

        sql = addFilteringSql(sql, map, params);

        //這部分只能用拼接方式出來 ORDER BY 相關語句，需要用拼接的。
        //拼接 sql 語句，須預留空白鍵。
        sql += " ORDER BY " + params.getOrderBy() + " " + params.getSort();

        //limit and offset 拼接在 ORDER BY 的後面
        sql += " limit :limit offset :offset ";
        map.put("limit", params.getLimit());
        map.put("offset", params.getOffset());

        List<Product> productList = jdbcTemplate.query(sql, map, new ProductRowMapper());
        return productList;
    }

    @Override
    public Product getProductById(Integer productId) {
        String sql = "select product_id,product_name, category, image_url, price, stock, description," +
                " created_date, last_modified_date from product where product_id= :product_id";
        Map<String, Object> map = new HashMap<>();
        map.put("product_id", productId);

        List<Product> productList = jdbcTemplate.query(sql, map, new ProductRowMapper());

        if (productList != null && productList.size() > 0) {
            return productList.get(0);
        }else{
            return null;
        }
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        String sql = "INSERT INTO product(product_name, category, image_url, price, stock, description," +
                " created_date, last_modified_date)" +
                "VALUES (:productName, :category, :imageUrl, :price, :stock, :description, " +
                ":createdDate, :lastModifiedDate)";

        //將前端傳來 productRequest 一個個加入 map 當中。
        Map<String, Object> map = new HashMap<>();
        map.put("productName", productRequest.getProductName());
        //注意 從 enum 轉換成 String，後存入資料庫。
        map.put("category", productRequest.getCategory().name());
        map.put("imageUrl", productRequest.getImageUrl());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description", productRequest.getDescription());

        //記錄當下時間點，並放入創建時間及最後修改時間當中，並加入到 map 當中。
        Date now = new Date();
        map.put("createdDate", now);
        map.put("lastModifiedDate", now);
        //使用 keyHolder 儲存資料庫自動生成的 productId。
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
        //取得 productId 並回傳出去
        int productId = keyHolder.getKey().intValue();
        return productId;
    }

    @Override
    public void updateProductById(Integer productId, ProductRequest productRequest) {
        //lastModifiedDate 商品修改最後時間，不可忘也。
        String sql = "UPDATE product SET product_name = :productName, category = :category, image_url = :imageUrl," +
                "price = :price, stock = :stock, description = :description, last_modified_date = :lastModifiedDate " +
                "WHERE product_id= :productId";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);

        map.put("productName", productRequest.getProductName());
        map.put("category", productRequest.getCategory().name());
        map.put("imageUrl", productRequest.getImageUrl());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description", productRequest.getDescription());

        map.put("lastModifiedDate", new Date());

        jdbcTemplate.update(sql, map);
    }

    @Override
    public void updateStock(Integer productId, Integer stock) {
        String sql = "UPDATE product SET stock = :stock, last_modified_date = :last_modified_date " +
                "WHERE product_id= :product_id";
        Map<String, Object> map = new HashMap<>();
        map.put("product_id", productId);
        map.put("stock", stock);
        map.put("last_modified_date", new Date());

        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public void deleteProductById(Integer productId) {

        String sql = "DELETE FROM product WHERE product_id= :productId";
        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);

        jdbcTemplate.update(sql, map);
    }

    //將 findAllProduct() and getProductById() 當中兩個相同的 if 判斷式擷取出來，讓程式重複利用並提升維護。
    private String addFilteringSql(String sql, Map<String, Object> map, ProductQueryParams params){
        if(params.getCategory() != null) {
            sql += " and category=:category";
            map.put("category", params.getCategory().name());
        }

        if (params.getSearch() != null) {
            sql += " and product_name like :search";
            map.put("search", "%" + params.getSearch() + "%");
        }

        return sql;
    }
}
