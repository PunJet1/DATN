package com.watch.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.watch.dao.SizeDao;
import com.watch.entity.Size;
import com.watch.service.SizeService;

@Service
public class SizeServiceImpl implements SizeService{
	
	@Autowired
	private SizeDao sizeDao;

	@Override
	public <S extends Size> S save(S entity) {
		return sizeDao.save(entity);
	}

	@Override
	public Page<Size> findAll(Pageable pageable) {
		return sizeDao.findAll(pageable);
	}

	@Override
	public List<Size> findAll() {
		return sizeDao.findAll();
	}

	@Override
	public Optional<Size> findById(Integer id) {
		return sizeDao.findById(id);
	}

	@Override
	public void deleteById(Integer id) {
		sizeDao.deleteById(id);
	}

	@Override
	public void delete(Size entity) {
		sizeDao.delete(entity);
	}

	@Override
	public Size getById(Integer id) {
		return sizeDao.getById(id);
	}

	public List<Size> findTop4Img() {
		// TODO Auto-generated method stub
		return sizeDao.findTop4Img();
	}

	@Override
	public Size findByName(String name) {
		// TODO Auto-generated method stub
		return sizeDao.findByName(name);
	}
	
	@Override
	public List<Size> getAllStatus() {
		// TODO Auto-generated method stub
		return sizeDao.getAllStatus();
	}

	@Override
	public List<Size> findByName1(String name) {
		// TODO Auto-generated method stub
		return sizeDao.findByName1(name);
	}

	@Override
	public List<Size> findByName(String name, Boolean status) {
		// TODO Auto-generated method stub
		return sizeDao.findByName(name,status);
	}

	@Override
	public List<Size> findByStatus(Boolean status) {
		// TODO Auto-generated method stub
		return sizeDao.findByStatus(status);
	}
	
}
