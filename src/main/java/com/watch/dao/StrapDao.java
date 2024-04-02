package com.watch.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.watch.entity.Strap_material;

@Repository
public interface StrapDao extends JpaRepository<Strap_material, Integer>{
	@Query(nativeQuery = true, value = "SELECT TOP 4 * FROM Strap_material o ORDER BY o.id ASC")
	List<Strap_material> findTop4Img();
	
	@Query("SELECT o FROM Strap_material o WHERE o.name LIKE ?1")
	Strap_material findByName(String name);
	

	@Query("SELECT o FROM Strap_material o WHERE o.status = true")
	List<Strap_material> getAllStatus();

		@Query("SELECT o FROM Strap_material o WHERE o.name LIKE ?1 and status = ?2")
	List<Strap_material> findByName(String name,Boolean status);
	
	@Query("SELECT o FROM Strap_material o WHERE o.status = ?1")
	List<Strap_material> findByStatus(Boolean status);

	@Query("SELECT o FROM Strap_material o WHERE o.name LIKE ?1")
	List<Strap_material> findByName1(String name);
}
