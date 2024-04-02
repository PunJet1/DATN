package com.watch.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.watch.entity.ProductSize;

public interface ProductSizeDao extends JpaRepository<ProductSize, Integer>{
	
	@Query("SELECT p FROM ProductSize p WHERE p.product.size.id=?1")
	List<ProductSize> findByProductId(Integer id);

}
