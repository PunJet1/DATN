package com.watch.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.watch.dao.GlassDao;
import com.watch.entity.Glass_material;
import com.watch.service.GlassService;

@Service
public class GlassServiceImpl implements GlassService{
	
	@Autowired
	private GlassDao glassDao;

	@Override
	public <S extends Glass_material> S save(S entity) {
		return glassDao.save(entity);
	}

	@Override
	public Page<Glass_material> findAll(Pageable pageable) {
		return glassDao.findAll(pageable);
	}

	@Override
	public List<Glass_material> findAll() {
		return glassDao.findAll();
	}

	@Override
	public Optional<Glass_material> findById(Integer id) {
		return glassDao.findById(id);
	}

	@Override
	public void deleteById(Integer id) {
		glassDao.deleteById(id);
	}

	@Override
	public void delete(Glass_material entity) {
		glassDao.delete(entity);
	}

	@Override
	public Glass_material getById(Integer id) {
		return glassDao.getById(id);
	}

	public List<Glass_material> findTop4Img() {
		// TODO Auto-generated method stub
		return glassDao.findTop4Img();
	}

	@Override
	public Glass_material findByName(String name) {
		// TODO Auto-generated method stub
		return glassDao.findByName(name);
	}
	
	@Override
	public List<Glass_material> getAllStatus() {
		// TODO Auto-generated method stub
		return glassDao.getAllStatus();
	}

	@Override
	public List<Glass_material> findByName1(String name) {
		// TODO Auto-generated method stub
		return glassDao.findByName1(name);
	}

	@Override
	public List<Glass_material> findByName(String name, Boolean status) {
		// TODO Auto-generated method stub
		return glassDao.findByName(name,status);
	}

	@Override
	public List<Glass_material> findByStatus(Boolean status) {
		// TODO Auto-generated method stub
		return glassDao.findByStatus(status);
	}
	
}
