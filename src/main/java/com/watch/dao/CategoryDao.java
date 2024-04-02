package com.watch.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.watch.entity.Category;
@Repository
public interface CategoryDao extends JpaRepository<Category, Integer>{

	@Query("SELECT o FROM Category o WHERE o.name LIKE ?1 and status = ?2")
	List<Category> findByName(String name,Boolean status);

	@Query("SELECT o FROM Category o WHERE o.status = ?1")
	List<Category> findByStatus(Boolean status);

	@Query("SELECT o FROM Category o WHERE o.name LIKE ?1")
	List<Category> findByName1(String name);
	
	@Query("SELECT o FROM Category o WHERE o.name LIKE ?1")
	Category findByName2(String name);
}
