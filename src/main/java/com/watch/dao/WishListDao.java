package com.watch.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.watch.entity.WishList;

@Repository
public interface WishListDao extends JpaRepository<WishList,Integer>{

	/*
	 * @Query("select p  from WishList p where p.account.userName=?1")
	 * List<WishList> findByUsername(String username);
	 */

	@Query("select p  from WishList p where p.product.productId=?1 and p.account.accountId=?2")
	WishList findBy(int productId, Long accountId);

	@Query("SELECT o FROM WishList o Where o.account.username=?1")
	List<WishList> findByUsername1(String username);
	
	@Query("SELECT o FROM WishList o WHERE o.product.name LIKE ?1")
	Page<WishList> findByKeywords(String string, Pageable pageable);
	
	@Query("SELECT o FROM WishList o Where o.account.username=?1")
	List<WishList> findByUsername(String username,Pageable pageable);

	@Query("SELECT o FROM WishList o Where o.account.accountId=?1")
	List<WishList> findByUserId(Long id);

	//@Query("delete WishList p where p.product.productId=?1 and p.account.accountId=?2")
	@Query(value = "delete wish_list  where product_id=?1 and account_id=?2",nativeQuery = true)
	int deleteByIdSp(Integer id, Long idac);
}