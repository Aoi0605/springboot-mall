package com.aoi.springbootmall.service.Impl;

import com.aoi.springbootmall.dao.UserDao;
import com.aoi.springbootmall.dto.UserRegisterRequest;
import com.aoi.springbootmall.model.User;
import com.aoi.springbootmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        return userDao.createUser(userRegisterRequest);
    }

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }
}
