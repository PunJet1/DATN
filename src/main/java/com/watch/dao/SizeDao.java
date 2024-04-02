package com.watch.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.watch.entity.Product;
import com.watch.entity.Size;

@Repository
public interface SizeDao extends JpaRepository<Size, Integer>{
	@Query(nativeQuery = true, value = "SELECT TOP 4 * FROM Size o ORDER BY o.id ASC")
	List<Size> findTop4Img();
	
	@Query("SELECT o FROM Size o WHERE o.name LIKE ?1")
	Size findByName(String name);
	

	@Query("SELECT o FROM Size o WHERE o.status = true")
	List<Size> getAllStatus();

		@Query("SELECT o FROM Size o WHERE o.name LIKE ?1 and status = ?2")
	List<Size> findByName(String name,Boolean status);
	
	@Query("SELECT o FROM Size o WHERE o.status = ?1")
	List<Size> findByStatus(Boolean status);

	@Query("SELECT o FROM Size o WHERE o.name LIKE ?1")
	List<Size> findByName1(String name);
	
	@Query(value = "select p from Size p where name =?1")
	Page<Size> selectAllSizes(String Size,Pageable pageable);
}
