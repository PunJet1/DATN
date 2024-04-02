package com.watch.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.watch.dao.VoucherDao;
import com.watch.entity.Vouchers;
import com.watch.service.VoucherService;

@Service
public class VoucherServiceImpl implements VoucherService{
	@Autowired
	private VoucherDao vdao;
	
	@Override
	public <S extends Vouchers> S save(S entity) {
		return vdao.save(entity);
	}

	@Override
	public List<Vouchers> findAll() {
		return vdao.findAll();
	}

	@Override
	public Optional<Vouchers> findById(String id) {
		return vdao.findById(id);
	}

	@Override
	public void deleteById(String id) {
		vdao.deleteById(id);
	}

	@Override
	public void delete(Vouchers entity) {
		vdao.delete(entity);
	}

	@Override
	public Vouchers getById(String id) {
		return vdao.getById(id);
	}
	
	@Override
	public Vouchers getOne(String voucher_Name) {
		return vdao.findById(voucher_Name).get();
	}
	
	@Override
	public Vouchers create(Vouchers vouchers) {
		// TODO Auto-generated method stub
		return vdao.save(vouchers);
	}
	@Override
	public Vouchers update(Vouchers vouchers) {
		// TODO Auto-generated method stub
		return vdao.save(vouchers);
	}
	
	@Override
	public List<Vouchers> findByName(String name, Boolean status) {
		// TODO Auto-generated method stub
		return vdao.findByName(name,status);
	}
	@Override
	public List<Vouchers> findByStatus(Boolean status) {
		// TODO Auto-generated method stub
		return vdao.findByStatus(status);
	}
	@Override
	public List<Vouchers> findByName1(String name) {
		// TODO Auto-generated method stub
		return vdao.findByName1(name);
	}

	@Override
	public List<Vouchers> findAllByDate() {
		// TODO Auto-generated method stub
		return vdao.findAllByDate();
	}

}