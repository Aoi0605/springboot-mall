package com.aoi.springbootmall.rowmapper;

import com.aoi.springbootmall.model.Order;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderRowMapper implements RowMapper {

    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {

        Order order = new Order();

        order.setOrderId(rs.getInt("order_id"));
        order.setUserId(rs.getInt("user_id"));
        order.setTotalAmount(rs.getInt("total_amount"));
        order.setCreateTime(rs.getDate("created_date"));
        order.setLastModifyDate(rs.getDate("last_modified_date"));

        return order;
    }
}
