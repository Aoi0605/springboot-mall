package com.aoi.springbootmall.service;

import com.aoi.springbootmall.dto.UserRegisterRequest;
import com.aoi.springbootmall.model.User;

public interface UserService {

    User getUserById(Integer userId);

    Integer register(UserRegisterRequest userRegisterRequest);
}
