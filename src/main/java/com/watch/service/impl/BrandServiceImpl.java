package com.watch.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.watch.dao.BrandDao;
import com.watch.entity.Brand;
import com.watch.service.BrandService;

@Service
public class BrandServiceImpl implements BrandService{
	
	@Autowired
	private BrandDao brandDao;

	@Override
	public <S extends Brand> S save(S entity) {
		return brandDao.save(entity);
	}

	@Override
	public Page<Brand> findAll(Pageable pageable) {
		return brandDao.findAll(pageable);
	}

	@Override
	public List<Brand> findAll() {
		return brandDao.findAll();
	}

	@Override
	public Optional<Brand> findById(Integer id) {
		return brandDao.findById(id);
	}

	@Override
	public void deleteById(Integer id) {
		brandDao.deleteById(id);
	}

	@Override
	public void delete(Brand entity) {
		brandDao.delete(entity);
	}

	@Override
	public Brand getById(Integer id) {
		return brandDao.getById(id);
	}

	public List<Brand> findTop4Img() {
		// TODO Auto-generated method stub
		return brandDao.findTop4Img();
	}

	@Override
	public Brand findByName(String name) {
		// TODO Auto-generated method stub
		return brandDao.findByName(name);
	}
	
	@Override
	public List<Brand> getAllStatus() {
		// TODO Auto-generated method stub
		return brandDao.getAllStatus();
	}

	@Override
	public List<Brand> findByName1(String name) {
		// TODO Auto-generated method stub
		return brandDao.findByName1(name);
	}

	@Override
	public List<Brand> findByName(String name, Boolean status) {
		// TODO Auto-generated method stub
		return brandDao.findByName(name,status);
	}

	@Override
	public List<Brand> findByStatus(Boolean status) {
		// TODO Auto-generated method stub
		return brandDao.findByStatus(status);
	}
	
}
