package com.watch.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.watch.entity.Brand;

@Repository
public interface BrandDao extends JpaRepository<Brand, Integer>{
	@Query(nativeQuery = true, value = "SELECT TOP 4 * FROM Brand o ORDER BY o.brand_id ASC")
	List<Brand> findTop4Img();
	
	@Query("SELECT o FROM Brand o WHERE o.name LIKE ?1")
	Brand findByName(String name);
	

	@Query("SELECT o FROM Brand o WHERE o.status = true")
	List<Brand> getAllStatus();

		@Query("SELECT o FROM Brand o WHERE o.name LIKE ?1 and status = ?2")
	List<Brand> findByName(String name,Boolean status);
	
	@Query("SELECT o FROM Brand o WHERE o.status = ?1")
	List<Brand> findByStatus(Boolean status);

	@Query("SELECT o FROM Brand o WHERE o.name LIKE ?1")
	List<Brand> findByName1(String name);
}
