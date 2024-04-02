package com.watch.service;

import java.util.List;
import java.util.Optional;

import com.watch.entity.Roles;

public interface RolesService {

	Roles getById(Integer id);

	void delete(Roles entity);

	void deleteById(Integer id);

	Optional<Roles> findById(Integer id);

	List<Roles> findAll();

	<S extends Roles> S save(S entity);

	Roles findByName(String string);

}
