package com.watch.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.watch.entity.Accounts;
import com.watch.entity.Authorities;

@Repository
public interface AuthoritiesDao extends JpaRepository<Authorities, Long>{

	@Query("SELECT DISTINCT a FROM Authorities a WHERE a.account IN ?1")
	List<Authorities> authoritiesOf(List<Accounts> account);
}
