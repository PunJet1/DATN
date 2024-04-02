package com.watch.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.watch.dao.NewsDao;
import com.watch.entity.News;
import com.watch.service.NewsService;

@Service
public class NewsServiceImpl implements NewsService{
	@Autowired
	NewsDao newsDao;

	@Override
	public <S extends News> S save(S entity) {
		return newsDao.save(entity);
	}

	@Override
	public List<News> findAll() {
		return newsDao.findAll();
	}

	@Override
	public Optional<News> findById(Integer id) {
		return newsDao.findById(id);
	}

	@Override
	public void deleteById(Integer id) {
		newsDao.deleteById(id);
	}

	@Override
	public void delete(News entity) {
		newsDao.delete(entity);
	}

	@Override
	public News getById(Integer id) {
		return newsDao.getById(id);
	}
	
	
}
