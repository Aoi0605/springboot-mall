package com.aoi.springbootmall.service.Impl;

import com.aoi.springbootmall.dao.ProductDao;
import com.aoi.springbootmall.model.Product;
import com.aoi.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public Product getProductById(Integer productId) {
        return productDao.getProductById(productId);
    }
}
