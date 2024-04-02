package com.watch.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.watch.dao.ShellDao;
import com.watch.entity.Shell_material;
import com.watch.service.ShellService;

@Service
public class ShellServiceImpl implements ShellService{
	
	@Autowired
	private ShellDao shellDao;

	@Override
	public <S extends Shell_material> S save(S entity) {
		return shellDao.save(entity);
	}

	@Override
	public Page<Shell_material> findAll(Pageable pageable) {
		return shellDao.findAll(pageable);
	}

	@Override
	public List<Shell_material> findAll() {
		return shellDao.findAll();
	}

	@Override
	public Optional<Shell_material> findById(Integer id) {
		return shellDao.findById(id);
	}

	@Override
	public void deleteById(Integer id) {
		shellDao.deleteById(id);
	}

	@Override
	public void delete(Shell_material entity) {
		shellDao.delete(entity);
	}

	@Override
	public Shell_material getById(Integer id) {
		return shellDao.getById(id);
	}

	public List<Shell_material> findTop4Img() {
		// TODO Auto-generated method stub
		return shellDao.findTop4Img();
	}

	@Override
	public Shell_material findByName(String name) {
		// TODO Auto-generated method stub
		return shellDao.findByName(name);
	}
	
	@Override
	public List<Shell_material> getAllStatus() {
		// TODO Auto-generated method stub
		return shellDao.getAllStatus();
	}

	@Override
	public List<Shell_material> findByName1(String name) {
		// TODO Auto-generated method stub
		return shellDao.findByName1(name);
	}

	@Override
	public List<Shell_material> findByName(String name, Boolean status) {
		// TODO Auto-generated method stub
		return shellDao.findByName(name,status);
	}

	@Override
	public List<Shell_material> findByStatus(Boolean status) {
		// TODO Auto-generated method stub
		return shellDao.findByStatus(status);
	}
	
}
