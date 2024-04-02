package com.watch.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.watch.entity.Strap_material;

public interface StrapService {

	Strap_material getById(Integer id);

	void delete(Strap_material entity);

	void deleteById(Integer id);

	Optional<Strap_material> findById(Integer id);

	List<Strap_material> findAll();

	Page<Strap_material> findAll(Pageable pageable);

	<S extends Strap_material> S save(S entity);

	List<Strap_material> findTop4Img();

	Strap_material findByName(String name);
	
	List<Strap_material> getAllStatus();
	
	List<Strap_material> findByName1(String string);

	List<Strap_material> findByName(String name,Boolean status);

	List<Strap_material> findByStatus(Boolean status);

}
