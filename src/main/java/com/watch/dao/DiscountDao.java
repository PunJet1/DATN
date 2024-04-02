package com.watch.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.watch.entity.Discount;

@Repository
public interface DiscountDao extends JpaRepository<Discount, Integer>{
	@Query("SELECT o FROM Discount o WHERE o.status = ?1")
	List<Discount> findByStatus(Boolean status);
	@Query("SELECT o FROM Discount o WHERE o.status = 'true'")
	List<Discount> findAllStatusTrue();
}
