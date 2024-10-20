package com.aoi.springbootmall.service.Impl;

import com.aoi.springbootmall.dao.UserDao;
import com.aoi.springbootmall.dto.UserLoginRequest;
import com.aoi.springbootmall.dto.UserRegisterRequest;
import com.aoi.springbootmall.model.User;
import com.aoi.springbootmall.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserServiceImpl implements UserService {

    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        //檢查註冊 email
        User user = userDao.getUserByEmail(userRegisterRequest.getEmail());
        if(user != null){
            //使用 {} 表示變數
            log.warn("該 email {} 已被註冊", userRegisterRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        //使用 MD5 生成密碼雜湊值
        //使用 DigestUtils.md5DigestAsHex() 方法，生成雜湊值，加上.getBytes() 將字串轉換成 byte 類型。
        String hashedPassword = DigestUtils.md5DigestAsHex(userRegisterRequest.getPassword().getBytes());
        userRegisterRequest.setPassword(hashedPassword);

            //創建帳號
        return userDao.createUser(userRegisterRequest);
    }

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public User login(UserLoginRequest userLoginRequest) {
        User user = userDao.getUserByEmail(userLoginRequest.getEmail());

        //檢查 User 是否存在
        if(user == null){
            log.warn("該 email {} 尚未註冊", userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST); //強制停止請求
        }

        //使用 MD5 生成密碼雜湊值
        //使用 DigestUtils.md5DigestAsHex() 方法，生成雜湊值，加上.getBytes() 將字串轉換成 byte 類型。
        String hashedPassword = DigestUtils.md5DigestAsHex(userLoginRequest.getPassword().getBytes());

        //比較 String 要用 equals() 方法
        if(user.getPassword().equals(hashedPassword)){ //如果前端傳來的值與資料庫一致
            return user;
        }else{
            log.warn("email {} 的密碼不正確", userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST); //強制停止請求
        }

    }
}
