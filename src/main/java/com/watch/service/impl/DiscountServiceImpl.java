package com.watch.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.watch.dao.DiscountDao;
import com.watch.entity.Discount;
import com.watch.service.DiscountService;
@Service
public class DiscountServiceImpl implements DiscountService{
	@Autowired
	DiscountDao discountDao;

	@Override
	public <S extends Discount> S save(S entity) {
		return discountDao.save(entity);
	}

	@Override
	public List<Discount> findAll() {
		return discountDao.findAll();
	}

	@Override
	public Optional<Discount> findById(Integer id) {
		return discountDao.findById(id);
	}

	@Override
	public void deleteById(Integer id) {
		discountDao.deleteById(id);
	}

	@Override
	public void delete(Discount entity) {
		discountDao.delete(entity);
	}
	
	@Override
	public List<Discount> findByStatus(Boolean status) {
		// TODO Auto-generated method stub
		return discountDao.findByStatus(status);
	}

	@Override
	public List<Discount> findAllStatusTrue() {
		return discountDao.findAllStatusTrue();
	}
	
	
	
}
