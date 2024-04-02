package com.watch.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.watch.dao.WaterDao;
import com.watch.entity.Water_resistant;
import com.watch.service.WaterService;

@Service
public class WaterServiceImpl implements WaterService{
	
	@Autowired
	private WaterDao waterDao;

	@Override
	public <S extends Water_resistant> S save(S entity) {
		return waterDao.save(entity);
	}

	@Override
	public Page<Water_resistant> findAll(Pageable pageable) {
		return waterDao.findAll(pageable);
	}

	@Override
	public List<Water_resistant> findAll() {
		return waterDao.findAll();
	}

	@Override
	public Optional<Water_resistant> findById(Integer id) {
		return waterDao.findById(id);
	}

	@Override
	public void deleteById(Integer id) {
		waterDao.deleteById(id);
	}

	@Override
	public void delete(Water_resistant entity) {
		waterDao.delete(entity);
	}

	@Override
	public Water_resistant getById(Integer id) {
		return waterDao.getById(id);
	}

	public List<Water_resistant> findTop4Img() {
		// TODO Auto-generated method stub
		return waterDao.findTop4Img();
	}

	@Override
	public Water_resistant findByName(String name) {
		// TODO Auto-generated method stub
		return waterDao.findByName(name);
	}
	
	@Override
	public List<Water_resistant> getAllStatus() {
		// TODO Auto-generated method stub
		return waterDao.getAllStatus();
	}

	@Override
	public List<Water_resistant> findByName1(String name) {
		// TODO Auto-generated method stub
		return waterDao.findByName1(name);
	}

	@Override
	public List<Water_resistant> findByName(String name, Boolean status) {
		// TODO Auto-generated method stub
		return waterDao.findByName(name,status);
	}

	@Override
	public List<Water_resistant> findByStatus(Boolean status) {
		// TODO Auto-generated method stub
		return waterDao.findByStatus(status);
	}
	
}
