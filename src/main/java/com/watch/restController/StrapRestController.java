package com.watch.restController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.watch.entity.Strap_material;
import com.watch.service.StrapService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/strap")
public class StrapRestController {
	@Autowired
	StrapService strapService;

	@GetMapping
	public List<Strap_material> getAll(){
	  return strapService.findAll();
	}
	@GetMapping("{name}")
	public Strap_material getName(@PathVariable("name") String name) {
		return strapService.findByName(name);
	}
	
	@PostMapping
	public Strap_material on(@RequestBody Strap_material Strap_material) {
		return strapService.save(Strap_material);
	}
	
	@PutMapping("{id}")
	public Strap_material update(@PathVariable("id") Integer id, @RequestBody Strap_material Strap_material) {
		return strapService.save(Strap_material);
	}
	
	@PutMapping("/delete/{id}")
	public Strap_material updatetrangthai(@PathVariable("id") Integer id, @RequestBody Strap_material Strap_material) {
		Strap_material.setStatus(false);
		return strapService.save(Strap_material);
	}
	
	@DeleteMapping("{id}")
	public void delete(@PathVariable("id") Integer id) {
		strapService.deleteById(id);
	}
	
	@GetMapping("/timKiem/{name}/{status}")
	public List<Strap_material> timKiem(@PathVariable("name") String name,@PathVariable("status") String status){
		//System.out.println("tên= "+name + " status= "+ status);
		if (status.equals("null")) {
			System.out.println("tên= "+name);
			return strapService.findByName1("%"+name+"%");
		} else {
			boolean in  = Boolean.parseBoolean(status);
			System.out.println("tên= "+name + " status="+ in);
			return strapService.findByName("%"+name+"%" , in);
		}
	}
	
	@GetMapping("/timKiem/{status}")
	public List<Strap_material> getStatus(@PathVariable("status") Boolean status){
		return strapService.findByStatus(status);
	}
	
	
}