package com.aoi.springbootmall.dao.Impl;

import com.aoi.springbootmall.dao.UserDao;
import com.aoi.springbootmall.dto.UserRegisterRequest;
import com.aoi.springbootmall.model.User;
import com.aoi.springbootmall.rowmapper.UserRowMapper;
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
public class UserDaoImpl implements UserDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer createUser(UserRegisterRequest userRegisterRequest) {
        String sql = "INSERT INTO user(email, password, created_date, last_modified_date)" +
                " VALUES (:email, :password, :created_date, :last_modified_date)";

        Map map = new HashMap();
        map.put("email", userRegisterRequest.getEmail());
        map.put("password", userRegisterRequest.getPassword());


        map.put("created_date", new Date());
        map.put("last_modified_date", new Date());

        KeyHolder keyHoldery = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHoldery);

        int userId = keyHoldery.getKey().intValue();

        return userId;
    }

    @Override
    public User getUserById(Integer userId) {
        String sql = "select user_id, email, password, created_date, last_modified_date " +
                "from `user` where user_id = :user_id";

        Map map = new HashMap();
        map.put("user_id", userId);

        List<User> userList = namedParameterJdbcTemplate.query(sql, map, new UserRowMapper());

        if(userList != null && userList.size() > 0){
            return userList.get(0);
        }else {
            return null;
        }

    }
}
