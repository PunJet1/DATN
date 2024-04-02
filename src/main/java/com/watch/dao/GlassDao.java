package com.watch.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.watch.entity.Glass_material;

@Repository
public interface GlassDao extends JpaRepository<Glass_material, Integer>{
	@Query(nativeQuery = true, value = "SELECT TOP 4 * FROM Glass_material o ORDER BY o.id ASC")
	List<Glass_material> findTop4Img();
	
	@Query("SELECT o FROM Glass_material o WHERE o.name LIKE ?1")
	Glass_material findByName(String name);
	

	@Query("SELECT o FROM Glass_material o WHERE o.status = true")
	List<Glass_material> getAllStatus();

		@Query("SELECT o FROM Glass_material o WHERE o.name LIKE ?1 and status = ?2")
	List<Glass_material> findByName(String name,Boolean status);
	
	@Query("SELECT o FROM Glass_material o WHERE o.status = ?1")
	List<Glass_material> findByStatus(Boolean status);

	@Query("SELECT o FROM Glass_material o WHERE o.name LIKE ?1")
	List<Glass_material> findByName1(String name);
}
