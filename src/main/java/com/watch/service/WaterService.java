package com.watch.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.watch.entity.Water_resistant;

public interface WaterService {

	Water_resistant getById(Integer id);

	void delete(Water_resistant entity);

	void deleteById(Integer id);

	Optional<Water_resistant> findById(Integer id);

	List<Water_resistant> findAll();

	Page<Water_resistant> findAll(Pageable pageable);

	<S extends Water_resistant> S save(S entity);

	List<Water_resistant> findTop4Img();

	Water_resistant findByName(String name);
	
	List<Water_resistant> getAllStatus();
	
	List<Water_resistant> findByName1(String string);

	List<Water_resistant> findByName(String name,Boolean status);

	List<Water_resistant> findByStatus(Boolean status);

}
