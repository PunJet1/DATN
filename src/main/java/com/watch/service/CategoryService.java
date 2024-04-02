package com.watch.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.watch.entity.Category;

public interface CategoryService {

	Category getById(Integer id);

	void delete(Category entity);

	void deleteById(Integer id);

	Optional<Category> findById(Integer id);

	List<Category> findAll();

	Page<Category> findAll(Pageable pageable);

	<S extends Category> S save(S entity);
	
	 List<Category> findByName1(String string);

		List<Category> findByName(String name,Boolean status);

		List<Category> findByStatus(Boolean status);

		Category findByName2(String name);

}
