package com.watch.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.watch.dao.ProductSizeDao;
import com.watch.entity.ImageProduct;
import com.watch.entity.ProductSize;
import com.watch.service.ProductSizeService;

@Service
public class ProductSizeServiceImpl implements ProductSizeService {

    @Autowired
    ProductSizeDao productSizeDao;

    @Override
    public ProductSize getById(Integer id) {
        return productSizeDao.getById(id);
    }

    @Override
    public void delete(ProductSize entity) {
        productSizeDao.delete(entity);
    }

    @Override
    public void deleteById(Integer id) {
        productSizeDao.deleteById(id);
    }

    @Override
    public List<ProductSize> findById(Integer id) {
        return productSizeDao.findByProductId(id);
    }

    @Override
    public List<ProductSize> findAllById(Iterable<Integer> ids) {
        return productSizeDao.findAllById(ids);
    }

    @Override
    public List<ProductSize> findAll() {
        return productSizeDao.findAll();
    }

    @Override
    public ProductSize update(ProductSize productSize) {
        return productSizeDao.save(productSize);
    }

    @Override
    public <S extends ProductSize> S save(S entity) {
        return productSizeDao.save(entity);
    }

}
