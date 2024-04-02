package com.watch.service;

import java.util.List;

import com.watch.entity.ImageProduct;

public interface ImageProductService {

	ImageProduct getById(Integer id);

	void delete(ImageProduct entity);

	void deleteById(Integer id);

	List<ImageProduct> findById(Integer id);

	List<ImageProduct> findAllById(Iterable<Integer> ids);

	List<ImageProduct> findAll();

	<S extends ImageProduct> S save(S entity);
	
	ImageProduct update(ImageProduct imageProduct);

}
