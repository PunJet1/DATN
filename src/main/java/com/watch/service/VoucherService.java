package com.watch.service;

import java.util.List;
import java.util.Optional;

import com.watch.entity.Vouchers;


public interface VoucherService {

	Vouchers getOne(String voucher_Name);

	Vouchers getById(String id);

	void delete(Vouchers entity);

	void deleteById(String id);

	Optional<Vouchers> findById(String id);

	List<Vouchers> findAll();

	<S extends Vouchers> S save(S entity);
	
	Vouchers create(Vouchers vouchers);

	Vouchers update(Vouchers vouchers);

	List<Vouchers> findByName(String name,Boolean status);

	List<Vouchers> findByStatus(Boolean status);

	List<Vouchers> findByName1(String name);

	List<Vouchers> findAllByDate();

}