package com.watch.service;

import java.util.List;
import java.util.Optional;

import com.watch.entity.Discount;

public interface DiscountService {

	void delete(Discount entity);

	void deleteById(Integer id);

	Optional<Discount> findById(Integer id);

	List<Discount> findAll();

	<S extends Discount> S save(S entity);
	
	List<Discount> findByStatus(Boolean status);

	List<Discount> findAllStatusTrue();

}
