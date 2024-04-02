package com.watch.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.watch.entity.ImageProduct;

@Repository
public interface ImageProductDao extends JpaRepository<ImageProduct, Integer>{

	@Query("SELECT o FROM ImageProduct o WHERE o.product.id=?1")
	List<ImageProduct> findByIdproduct(Integer id);

}