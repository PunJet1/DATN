package com.watch.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.watch.dao.ProductSizeDao;
import com.watch.entity.ProductSize;
import com.watch.service.ProductSizeService;

@Service
public class ProductSizeServiceImpl implements ProductSizeService {

	@Autowired
	ProductSizeDao productSizeDao;

	@Override
	public ProductSize getById(Integer id) {
		// TODO Auto-generated method stub
		return productSizeDao.getById(id);
	}

	@Override
	public List<ProductSize> findAll() {
		// TODO Auto-generated method stub
		return productSizeDao.findAll();
	}


	@Override
	public ProductSize update(ProductSize productsize) {
		// TODO Auto-generated method stub
		return productSizeDao.save(productsize);
	}

	@Override
	public void delete(ProductSize entity) {
		productSizeDao.delete(entity);

	}

	@Override
	public <S extends ProductSize> S save(S entity) {
		// TODO Auto-generated method stub
		return productSizeDao.save(entity);
	}

	@Override
	public void deleteById(Integer id) {
		productSizeDao.deleteById(id);

	}

	@Override
	public List<ProductSize> findById(Integer id) {
		return productSizeDao.findByProductId(id);
	}

}
