package com.watch.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.watch.entity.Water_resistant;

@Repository
public interface WaterDao extends JpaRepository<Water_resistant, Integer>{
	@Query(nativeQuery = true, value = "SELECT TOP 4 * FROM Water_resistant o ORDER BY o.id ASC")
	List<Water_resistant> findTop4Img();
	
	@Query("SELECT o FROM Water_resistant o WHERE o.name LIKE ?1")
	Water_resistant findByName(String name);
	

	@Query("SELECT o FROM Water_resistant o WHERE o.status = true")
	List<Water_resistant> getAllStatus();

		@Query("SELECT o FROM Water_resistant o WHERE o.name LIKE ?1 and status = ?2")
	List<Water_resistant> findByName(String name,Boolean status);
	
	@Query("SELECT o FROM Water_resistant o WHERE o.status = ?1")
	List<Water_resistant> findByStatus(Boolean status);

	@Query("SELECT o FROM Water_resistant o WHERE o.name LIKE ?1")
	List<Water_resistant> findByName1(String name);
}
