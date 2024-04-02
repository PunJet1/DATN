package com.watch.service;

import java.util.List;

import com.watch.entity.ImageProduct;
import com.watch.entity.ProductSize;

public interface ProductSizeService {

	ProductSize getById(Integer id);

	List<ProductSize> findAll();

	List<ProductSize> findById(Integer id);

	ProductSize update(ProductSize productsize);

	void delete(ProductSize entity);

	<S extends ProductSize> S save(S entity);

	void deleteById(Integer id);

}
