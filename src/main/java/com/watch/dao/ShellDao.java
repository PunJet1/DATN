package com.watch.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.watch.entity.Shell_material;
import com.watch.entity.Shell_material;

@Repository
public interface ShellDao extends JpaRepository<Shell_material, Integer>{
	@Query(nativeQuery = true, value = "SELECT TOP 4 * FROM Shell_material o ORDER BY o.id ASC")
	List<Shell_material> findTop4Img();
	
	@Query("SELECT o FROM Shell_material o WHERE o.name LIKE ?1")
	Shell_material findByName(String name);
	

	@Query("SELECT o FROM Shell_material o WHERE o.status = true")
	List<Shell_material> getAllStatus();

		@Query("SELECT o FROM Shell_material o WHERE o.name LIKE ?1 and status = ?2")
	List<Shell_material> findByName(String name,Boolean status);
	
	@Query("SELECT o FROM Shell_material o WHERE o.status = ?1")
	List<Shell_material> findByStatus(Boolean status);

	@Query("SELECT o FROM Shell_material o WHERE o.name LIKE ?1")
	List<Shell_material> findByName1(String name);
}
