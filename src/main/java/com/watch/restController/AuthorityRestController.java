package com.watch.restController;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.watch.entity.Authorities;
import com.watch.service.AccountService;
import com.watch.service.AuthoritiesService;
import com.watch.service.RolesService;

@CrossOrigin("*")
@RestController
public class AuthorityRestController {
	
	@Autowired
	AuthoritiesService authorityService;
	@Autowired
	AccountService accountService;
	@Autowired
	RolesService roleService;

//	@GetMapping("/rest/autho")
//	public Map<String, Object> getAutho(){
//		Map<String, Object> data = new HashMap();
//		data.put("authorities", authorityService.findAll());
//		data.put("roles", roleService.findAll());
//		data.put("accounts", accountService.findAll());
//		return data;
//	}
//	
//	@PostMapping("/rest/autho")
//	public Authorities create(@RequestBody Authorities authority) {
//		return authorityService.create(authority);
//	}
//	
//	@DeleteMapping("/rest/autho/{id}")
//	public void delete2(@PathVariable("id") Integer id) {
//		authorityService.delete(id);
//	}
	
	@GetMapping("rest/authorities")
	public List<Authorities> findAll(@RequestParam("admin") Optional<Boolean> admin) {
		if (admin.orElse(true)) {
			return authorityService.findAuthoritiesOfAdminstrators();
		}
		return authorityService.findAll();
	}
	@PostMapping("rest/authorities")
	public Authorities create(@RequestBody Authorities authority) {
		return authorityService.save(authority);
	}
	
	@DeleteMapping("rest/authorities/{id}")
	public void delete2(@PathVariable("id") Long id) {
		authorityService.deleteById(id);
	}

}