package com.watch.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.watch.entity.Vouchers;
import com.watch.entity.WishList;
@Repository
public interface VoucherDao extends JpaRepository<Vouchers, String>{
	
	@Query("SELECT o FROM Vouchers o WHERE o.statustt = ?1")
	List<Vouchers> findByStatus(Boolean status);

	@Query("SELECT o FROM Vouchers o WHERE o.voucherName like ?1 and statustt = ?2")
	List<Vouchers> findByName(String name, Boolean status);
	
	@Query("SELECT o FROM Vouchers o WHERE o.voucherName like ?1")
	List<Vouchers> findByName1(String name);
	@Query(value="select * from vouchers where vouchers.statustt = 1 and ngay_bat_dau <= GETDATE() and  ngay_ket_thuc >= GETDATE() and vouchers.voucher_name not in (select vouchers.voucher_name from vouchers inner join orders on vouchers.voucher_name = orders.voucher_name\r\n"
			+ "where orders.account_id = ?1 and orders.status != 0)",nativeQuery = true)
	List<Vouchers> getVoucherWithAcc(Long accountId);
	//List<WishList> findByUsername(String username,Pageable pageable);
	
	@Query(value="select * from vouchers where voucher_name like ?1",nativeQuery = true) 
	Vouchers getVoucherWithOrder(String name);
	
	@Query(value="select * from vouchers where ngay_bat_dau <= GETDATE() and  ngay_ket_thuc >= GETDATE()",nativeQuery = true)
	List<Vouchers> findAllByDate();
}