package com.watch.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.watch.entity.News;
import com.watch.entity.ViMoney;

public interface ViMoneyDao extends JpaRepository<ViMoney, String>{
	
	
	
}
