package com.watch.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.watch.dao.WishListDao;
import com.watch.entity.WishList;
import com.watch.service.WishListService;
@Service
public class WishListServiceImpl implements WishListService{
	@Autowired
	WishListDao wishListDao;

	@Override
	public <S extends WishList> S save(S entity) {
		return wishListDao.save(entity);
	}

	@Override
	public List<WishList> findAll() {
		return wishListDao.findAll();
	}

	@Override
	public Optional<WishList> findById(Integer id) {
		return wishListDao.findById(id);
	}

	@Override
	public void deleteById(Integer id) {
		wishListDao.deleteById(id);
	}

	@Override
	public void delete(WishList entity) {
		wishListDao.delete(entity);
	}

	@Override
	public WishList getById(Integer id) {
		return wishListDao.getById(id);
	}
	
//	@Override
//	public List<WishList> findByUsername(String username) {
//		// TODO Auto-generated method stub
//		return wishListDao.findByUsername(username);
//	}

	@Override
	public WishList findBy(int productId, Long accountId) {
		// TODO Auto-generated method stub
		return wishListDao.findBy(productId,accountId);
	}

	@Override
	public Object findByUsername1(String username) {
		// TODO Auto-generated method stub
		return wishListDao.findByUsername1(username);
	}
	
	@Override
	public Object findByUsername(String username, Pageable pageable) {
		wishListDao.findByUsername(username,pageable);
		return null;
	}

	@Override
	public List<WishList> findByUserId(Long id) {
		// TODO Auto-generated method stub
		return wishListDao.findByUserId(id);
	}

	
	public int deleteByIdSp(Integer id, Long idac) {
		 return wishListDao.deleteByIdSp(id,idac);
	}
}
