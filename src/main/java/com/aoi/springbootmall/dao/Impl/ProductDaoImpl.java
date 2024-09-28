package com.aoi.springbootmall.dao.Impl;

import com.aoi.springbootmall.dao.ProductDao;
import com.aoi.springbootmall.model.Product;
import com.aoi.springbootmall.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

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
}
