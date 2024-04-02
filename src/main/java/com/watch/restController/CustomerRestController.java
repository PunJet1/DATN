package com.watch.restController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.watch.entity.Accounts;
import com.watch.entity.Roles;
import com.watch.service.AccountService;
import com.watch.service.RolesService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/customer")
public class CustomerRestController {
	@Autowired
	AccountService customerService;

	@Autowired
    RolesService rolesService;
	
	@GetMapping
	public List<Accounts> getAll(){
	  return customerService.findAllCustomer();
	}
//	@GetMapping("{name}")
//	public Accounts getName(@PathVariable("name") String name) {
//		return customerService.findByName(name);
//	}
	
	@PostMapping
	public Accounts on(@RequestBody Accounts customer) {
		return customerService.save(customer);
	}
	
	@PutMapping("{id}")
	public Accounts update(@PathVariable("id") Integer id, @RequestBody Accounts customer) {
		
//		Roles roles = rolesService.findByName("ROLE_CUSTOMER");//nhân viên
//		customer.addRole(roles);
		return customerService.save(customer);
	}
	
//	@DeleteMapping("{id}")
//	public void delete(@PathVariable("id") Integer id) {
//		customerService.deleteById(id);
//	}
	
	@GetMapping("/timKiem/{name}/{status}")
	public List<Accounts> timKiem(@PathVariable("name") String name,@PathVariable("status") String status){
		if("null".equals(status) && "null".equals(name)) {
    		return customerService.getAdminstratorsC();
    	}
		if (status.equals("null")) {
			System.out.println("tên= "+name);
			return customerService.findByName1C("%"+name+"%");
		} else {
			boolean in  = Boolean.parseBoolean(status);
			System.out.println("tên= "+name + " status="+ in);
			return customerService.findByNameC("%"+name+"%" , in);
		}
	}
	
	@GetMapping("/timKiem/{status}")
	public List<Accounts> getStatus(@PathVariable("status") Boolean status){
		return customerService.findByStatusC(status);
	}
	
	
}