package com.aoi.springbootmall.rowmapper;

import com.aoi.springbootmall.model.OrderItem;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderItemRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(rs.getInt("order_id"));
        orderItem.setOrderItemId(rs.getInt("order_item_id"));
        orderItem.setProductId(rs.getInt("product_id"));
        orderItem.setQuantity(rs.getInt("quantity"));
        orderItem.setAmount(rs.getInt("amount"));

        //在有 join 其他 table 時的 RowMapper 寫法
        //可以擴充 orderItem model 新增 productName and imageUrl 兩個變數，或是新增一個 class 將 orderItem model 的變數全數加上。
        orderItem.setProductName(rs.getString("product_name"));
        orderItem.setImageUrl(rs.getString("image_url"));

        return orderItem;
    }
}
