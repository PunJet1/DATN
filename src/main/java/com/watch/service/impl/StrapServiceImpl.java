package com.watch.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.watch.dao.StrapDao;
import com.watch.entity.Strap_material;
import com.watch.service.StrapService;

@Service
public class StrapServiceImpl implements StrapService{
	
	@Autowired
	private StrapDao strapDao;

	@Override
	public <S extends Strap_material> S save(S entity) {
		return strapDao.save(entity);
	}

	@Override
	public Page<Strap_material> findAll(Pageable pageable) {
		return strapDao.findAll(pageable);
	}

	@Override
	public List<Strap_material> findAll() {
		return strapDao.findAll();
	}

	@Override
	public Optional<Strap_material> findById(Integer id) {
		return strapDao.findById(id);
	}

	@Override
	public void deleteById(Integer id) {
		strapDao.deleteById(id);
	}

	@Override
	public void delete(Strap_material entity) {
		strapDao.delete(entity);
	}

	@Override
	public Strap_material getById(Integer id) {
		return strapDao.getById(id);
	}

	public List<Strap_material> findTop4Img() {
		// TODO Auto-generated method stub
		return strapDao.findTop4Img();
	}

	@Override
	public Strap_material findByName(String name) {
		// TODO Auto-generated method stub
		return strapDao.findByName(name);
	}
	
	@Override
	public List<Strap_material> getAllStatus() {
		// TODO Auto-generated method stub
		return strapDao.getAllStatus();
	}

	@Override
	public List<Strap_material> findByName1(String name) {
		// TODO Auto-generated method stub
		return strapDao.findByName1(name);
	}

	@Override
	public List<Strap_material> findByName(String name, Boolean status) {
		// TODO Auto-generated method stub
		return strapDao.findByName(name,status);
	}

	@Override
	public List<Strap_material> findByStatus(Boolean status) {
		// TODO Auto-generated method stub
		return strapDao.findByStatus(status);
	}
	
}
