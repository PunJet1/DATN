package com.watch.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.watch.dao.CategoryDao;
import com.watch.entity.Category;
import com.watch.service.CategoryService;
@Service
public class CategoryServiceImpl implements CategoryService{
	@Autowired
	private CategoryDao categoryDao;

	@Override
	public <S extends Category> S save(S entity) {
		return categoryDao.save(entity);
	}

	@Override
	public Page<Category> findAll(Pageable pageable) {
		return categoryDao.findAll(pageable);
	}

	@Override
	public List<Category> findAll() {
		return categoryDao.findAll();
	}

	@Override
	public Optional<Category> findById(Integer id) {
		return categoryDao.findById(id);
	}

	@Override
	public void deleteById(Integer id) {
		categoryDao.deleteById(id);
	}

	@Override
	public void delete(Category entity) {
		categoryDao.delete(entity);
	}

	@Override
	public Category getById(Integer id) {
		return categoryDao.getById(id);
	}
	
	@Override
	public List<Category> findByName1(String name) {
		// TODO Auto-generated method stub
		return categoryDao.findByName1(name);
	}

	@Override
	public List<Category> findByName(String name, Boolean status) {
		// TODO Auto-generated method stub
		return categoryDao.findByName(name,status);
	}

	@Override
	public List<Category> findByStatus(Boolean status) {
		// TODO Auto-generated method stub
		return categoryDao.findByStatus(status);
	}

	@Override
	public Category findByName2(String name) {
		return categoryDao.findByName2(name);
	}
	
	
}
