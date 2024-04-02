package com.watch.service;

import java.util.List;
import java.util.Optional;

import com.watch.entity.Authorities;

public interface AuthoritiesService {

	Authorities getById(Long id);

	void delete(Authorities entity);

	void deleteById(Long id);

	Optional<Authorities> findById(Long id);

	List<Authorities> findAll();

	 Authorities save(Authorities entity);
	
	public List<Authorities> findAuthoritiesOfAdminstrators();

}
