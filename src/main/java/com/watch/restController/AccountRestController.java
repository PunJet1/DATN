package com.watch.restController;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.watch.dao.AccountDao;
import com.watch.entity.Accounts;
import com.watch.entity.Roles;
import com.watch.secutity.services.UserDetailsImpl;
import com.watch.service.AccountService;
import com.watch.service.RolesService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/accounts")
public class AccountRestController {
    @Autowired
    AccountService accountService;
    
    @Autowired
    RolesService rolesService;
    
    @Autowired
    AccountDao accountDao;
    
    @Autowired 
    HttpSession session;

//    @GetMapping
//    public List<Accounts> getAccounts(){
//        return accountService.findAllAccout();
//    }
    
  @GetMapping("/checkRole")
  public boolean getAccountsName(){
	  UserDetailsImpl Acount =  (UserDetailsImpl) session.getAttribute("User");
	  boolean account1=false;
	  if(Acount!=null) {
		  for (GrantedAuthority element : Acount.getAuthorities()) {
  			if(element != null) {
  				if(element.getAuthority().equals("ROLE_ADMIN")) {
  					account1= true;
                  }
  			}
          }	  
	  }
      return account1; 
  }
  
  @GetMapping("/online")
  public UserDetailsImpl getAccountsOnline(){
	  UserDetailsImpl Acount =  (UserDetailsImpl) session.getAttribute("User");
	  if(Acount!=null) {
		  return Acount;
	  }
	  return null;
  }
  
  

    @PostMapping
    public Accounts create(@RequestBody Accounts account) {
    	if(!"".equals(account.getPhone())) {
    		int chk1 = accountService.getTrungPhone(account.getPhone());
    		if(chk1 > 0 ) {
    			account.setPhone("");
    			return account;
    		}
    	}
    	if(!"".equals(account.getEmail())) {
    		int chk2 = accountService.getTrungEmail(account.getEmail());
    		if(chk2 > 0 ) {
    			account.setEmail("");
    			return account;
    		}
    	}
    	
    	int index1 = account.getPassword().indexOf("$");
    	if(index1 == -1) {
    		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        	String encodedPassword = passwordEncoder.encode(account.getPassword());
        	account.setPassword(encodedPassword);
    	}
    	Roles roles = rolesService.findByName("ROLE_USER");//nhân viên
    	account.addRole(roles);
    	
    	return accountService.save(account);
    }

    @PutMapping("{username}")
    public Accounts update(@PathVariable("username") String username, @RequestBody Accounts account) {
    	int index1 = account.getPassword().indexOf("$");
    	if(index1 == -1) {
    		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        	String encodedPassword = passwordEncoder.encode(account.getPassword());
        	account.setPassword(encodedPassword);
    	}
    	int r = 0;
    	if(null != account.getRoles()) {
    		for (Roles element : account.getRoles()) {
    			if(element != null) {
    				if(element.getName().equals("ROLE_USER")) {
                    	r++;
                    }
    			}
            }
    	}
    	if(r == 0) {
    		Roles roles = rolesService.findByName("ROLE_USER");//nhân viên
        	account.addRole(roles);
    	}
    	
        return accountService.save(account);
    }
    
    @PutMapping("/delete/{username}")
    public Accounts updateTrangthai(@PathVariable("username") String username, @RequestBody Accounts account) {
    	int index1 = account.getPassword().indexOf("$");
    	if(index1 == -1) {
    		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        	String encodedPassword = passwordEncoder.encode(account.getPassword());
        	account.setPassword(encodedPassword);
    	}
    	int r = 0;
    	if(null != account.getRoles() ) {
    		for (Roles element : account.getRoles()) {
    			if(element != null) {
    				 if(element.getName().equals("ROLE_USER")) {
    	                	r++;
    	              }
    			}
               
            }
    	}
    	if(r == 0) {
    		Roles roles = rolesService.findByName("ROLE_USER");//nhân viên
        	account.addRole(roles);
    	}
    	account.setStatus(false);
    	
        return accountService.save(account);
    }
    
    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id) {
    	accountDao.deleteById(id);
    }
    //phân quyền
    @GetMapping
	public List<Accounts> getAccountRole(@RequestParam("admin") Optional<Boolean> admin){
		if(admin.orElse(false)) {
			UserDetailsImpl Acount =  (UserDetailsImpl) session.getAttribute("User");
			  int account1=0;
			  int account2=0;
			  if(Acount!=null) {
				  for (GrantedAuthority element : Acount.getAuthorities()) {
		  			if(element != null) {
		  				if(element.getAuthority().equals("ROLE_ADMIN")) {
		  					account1++;
		                  }else if(element.getAuthority().equals("ROLE_USER")) {
		                	  account2++;
		                  }
		  			}
		          }	  
			  }
			if(account1 > 0) {
				return accountService.getAdminstrators();
			}else if(account2 >0) {
				List<Accounts> lst = new ArrayList<Accounts>();
				Accounts accounts = new Accounts();
				lst.add(accounts);
				return lst;
			}
		}else {
			return accountService.getAdminstrators();
		}
		return accountService.findAll();
	}
    
    @GetMapping("/acountUser")
	public List<Accounts> acountUser(){
    	UserDetailsImpl Acount =  (UserDetailsImpl) session.getAttribute("User");
		if(Acount!=null) {
			return accountService.findByName1(Acount.getUsername());
//			return accountDao.getUser();
		}
		return null;
	}
    //tìm kiếm
    @GetMapping("/timKiem/{name}/{status}")
	public List<Accounts> timKiem(@PathVariable("name") String name,@PathVariable("status") String status){
		System.out.println("tên= "+name + " status= "+ status);
    	if("null".equals(status) && "null".equals(name)) {
    		return accountService.getAdminstrators();
    	}
		if (status.equals("null")) {
			//System.out.println("tên= "+name);
			return accountService.findByName1("%"+name+"%");
		} else {
			boolean in  = Boolean.parseBoolean(status);
			//System.out.println("tên= "+name + " status="+ in);
			return accountService.findByName("%"+name+"%" , in);
		}
	}
	
	@GetMapping("/timKiem/{status}")
	public List<Accounts> getStatus(@PathVariable("status") Boolean status){
		return accountService.findByStatus(status);
	}
	
	@RequestMapping(value = "/checkemail/{email}", method = RequestMethod.POST)
	public int getTrungEmail(@PathVariable("email") String email){
		return accountService.getTrungEmail(email);
	}
	
	@RequestMapping(value = "/checkphone/{sdt}", method = RequestMethod.POST)
	public int getTrungPhone(@PathVariable("sdt") String phone){
		return accountService.getTrungPhone(phone);
	}
    
}