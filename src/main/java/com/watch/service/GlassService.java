package com.watch.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.watch.entity.Glass_material;

public interface GlassService {

	Glass_material getById(Integer id);

	void delete(Glass_material entity);

	void deleteById(Integer id);

	Optional<Glass_material> findById(Integer id);

	List<Glass_material> findAll();

	Page<Glass_material> findAll(Pageable pageable);

	<S extends Glass_material> S save(S entity);

	List<Glass_material> findTop4Img();

	Glass_material findByName(String name);
	
	List<Glass_material> getAllStatus();
	
	List<Glass_material> findByName1(String string);

	List<Glass_material> findByName(String name,Boolean status);

	List<Glass_material> findByStatus(Boolean status);

}
