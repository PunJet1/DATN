package com.watch.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.watch.dao.OrderDetailDao;
import com.watch.entity.OrderDetail;
import com.watch.service.OrderDetailService;
@Service
public class OrderDetailServiceImpl implements OrderDetailService{
	@Autowired
	OrderDetailDao orderDetailDao;

	@Override
	public <S extends OrderDetail> S save(S entity) {
		return orderDetailDao.save(entity);
	}

	@Override
	public List<OrderDetail> findAll() {
		return orderDetailDao.findAll();
	}

	@Override
	public Optional<OrderDetail> findById(Integer id) {
		return orderDetailDao.findById(id);
	}

	@Override
	public void deleteById(Integer id) {
		orderDetailDao.deleteById(id);
	}

	@Override
	public void delete(OrderDetail entity) {
		orderDetailDao.delete(entity);
	}

	@Override
	public OrderDetail getById(Integer id) {
		return orderDetailDao.getById(id);
	}
	
	
}
