package com.aoi.springbootmall.service.Impl;

import com.aoi.springbootmall.dao.UserDao;
import com.aoi.springbootmall.dto.UserRegisterRequest;
import com.aoi.springbootmall.model.User;
import com.aoi.springbootmall.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserServiceImpl implements UserService {

    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        //檢查註冊 email
        User user = userDao.getUserByEmail(userRegisterRequest);
        if(user != null){
            //使用 {} 表示變數
            log.warn("該 email {} 已被註冊", userRegisterRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }else{
            //創建帳號
            return userDao.createUser(userRegisterRequest);
        }


    }

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }
}
