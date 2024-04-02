package com.watch.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.watch.entity.Brand;

public interface BrandService {

	Brand getById(Integer id);

	void delete(Brand entity);

	void deleteById(Integer id);

	Optional<Brand> findById(Integer id);

	List<Brand> findAll();

	Page<Brand> findAll(Pageable pageable);

	<S extends Brand> S save(S entity);

	List<Brand> findTop4Img();

	Brand findByName(String name);
	
	List<Brand> getAllStatus();
	
	List<Brand> findByName1(String string);

	List<Brand> findByName(String name,Boolean status);

	List<Brand> findByStatus(Boolean status);

}
