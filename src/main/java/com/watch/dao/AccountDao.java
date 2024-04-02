package com.watch.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.watch.entity.Accounts;

public interface AccountDao extends JpaRepository<Accounts, Long>{
	Optional<Accounts> findByUsername(String username);
    Optional<Accounts> findByEmail(String email);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    Accounts findUserByEmail(String email);
    Accounts findByResetPasswordToken(String token);
    Accounts findUserByUsername(String username);  
    @Query("SELECT a FROM Accounts a Where a.username=?1")
	Object findByUsername1(String username);
    
    @Query("SELECT DISTINCT ar.account FROM Authorities ar WHERE ar.role.roleId IN ('1','3')")
    List<Accounts> getAdministrators();

	/*
	 * @Query("SELECT o FROM Accounts o WHERE o.username LIKE ?1 and status = ?2")
	 */
    @Query(value = "SELECT o.* FROM Accounts o join authorities ar on o.account_id = ar.account_id \r\n"
			+ "							join roles r on ar.role_id = r.role_id  \r\n"
			+ "							where o.username LIKE ?1 and status = ?2 and r.role_id in ('1','3')",nativeQuery = true)
	List<Accounts> findByName(String username,Boolean status);

	/* @Query("SELECT o FROM Accounts o WHERE o.status = ?1") */
	
	 @Query(value = "SELECT o.* FROM Accounts o join authorities ar on o.account_id = ar.account_id \r\n"
				+ "							join roles r on ar.role_id = r.role_id  \r\n"
				+ "							where o.status = ?1  and r.role_id in ('1','3')",nativeQuery = true)
	List<Accounts> findByStatus(Boolean status);

	/* @Query(value = "SELECT o FROM Accounts o WHERE o.username LIKE ?1") */
	@Query(value = "SELECT o.* FROM Accounts o join authorities ar on o.account_id = ar.account_id \r\n"
			+ "							join roles r on ar.role_id = r.role_id  \r\n"
			+ "							where o.username LIKE ?1 and r.role_id in ('1','3')",nativeQuery = true)
	List<Accounts> findByName1(String username);
	
	@Query("SELECT COUNT(a.accountId) FROM Accounts a")
	Long getTotalAccount();
	
	@Query("SELECT DISTINCT ar.account FROM Authorities ar WHERE ar.role.roleId IN ('2')")
	List<Accounts> findAllCustomer();
	
	@Query("SELECT DISTINCT ar.account FROM Authorities ar WHERE ar.role.roleId IN ('1')")
	List<Accounts> findAllAccout();
	
	@Query("select count(*) from  Accounts where email = ?1")
	int getTrungEmail(String email);
	
	@Query("select count(*) from  Accounts where phone = ?1")
	int getTrungPhone(String phone);
	
	@Query(value = "SELECT account_id,address,email,fullname,image,password,phone,provider\r\n"
			+ ",reset_password_token,status ,CONVERT(date, birthdate, 101) as birthdate,username,\r\n"
			+ "CONVERT(date, create_date, 101) as create_date \r\n"
			+ "FROM accounts WHERE account_id = ?1",nativeQuery = true)
	Accounts getByIdARD(Long id);
	
	@Query("SELECT DISTINCT ar.account FROM Authorities ar WHERE ar.role.roleId IN ('2')")
	List<Accounts> getAdminstratorsC();
	
	@Query(value = "SELECT o.* FROM Accounts o join authorities ar on o.account_id = ar.account_id \r\n"
			+ "							join roles r on ar.role_id = r.role_id  \r\n"
			+ "							where o.username LIKE ?1 and r.role_id in ('2')",nativeQuery = true)
	List<Accounts> findByName1C(String string);
	
	@Query(value = "SELECT o.* FROM Accounts o join authorities ar on o.account_id = ar.account_id \r\n"
			+ "							join roles r on ar.role_id = r.role_id  \r\n"
			+ "							where o.username LIKE ?1 and status = ?2 and r.role_id in ('2')",nativeQuery = true)
	List<Accounts> findByNameC(String string, boolean in);
	
	@Query(value = "SELECT o.* FROM Accounts o join authorities ar on o.account_id = ar.account_id \r\n"
			+ "							join roles r on ar.role_id = r.role_id  \r\n"
			+ "							where o.status = ?1  and r.role_id in ('2')",nativeQuery = true)
	List<Accounts> findByStatusC(Boolean status);

}
