package com.watch.entity;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.watch.secutity.services.UserDetailsImpl;
@Service
public class UserAcounts {
	@Autowired 
	public  HttpSession session;
	public  Accounts User() {
		UserDetailsImpl user = (UserDetailsImpl) session.getAttribute("User");
		if(user==null) {
			return null;
		}else {
			Accounts account = new Accounts();
			account.setAccountId(user.getAccountId());
			account.setAddress(user.getAddress());
			account.setBirthdate(user.getBirthdate());
			account.setEmail(user.getEmail());
			account.setFullname(user.getFullname());
			account.setImage(user.getImage());
			account.setPassword(user.getPassword());
			account.setPhone(user.getPhone());
			account.setUsername(user.getUsername());
			account.setStatus(user.isStatus());
			return account;
		}
		
	}
}
