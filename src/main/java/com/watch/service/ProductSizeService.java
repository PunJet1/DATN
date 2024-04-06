package com.watch.service;

import java.util.List;

import com.watch.entity.ProductSize;


public interface ProductSizeService {

    ProductSize getById(Integer id);

	void delete(ProductSize entity);

	void deleteById(Integer id);

	List<ProductSize> findById(Integer id);

	List<ProductSize> findAllById(Iterable<Integer> ids);

	List<ProductSize> findAll();

	<S extends ProductSize> S save(S entity);
	
	ProductSize update(ProductSize productSize);
}
