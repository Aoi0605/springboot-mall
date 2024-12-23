package com.aoi.springbootmall.dao;

import com.aoi.springbootmall.dto.UserRegisterRequest;
import com.aoi.springbootmall.model.User;

public interface UserDao {

    User getUserById(Integer userId);

    User getUserByEmail(String email);

    Integer createUser(UserRegisterRequest userRegisterRequest);
}
