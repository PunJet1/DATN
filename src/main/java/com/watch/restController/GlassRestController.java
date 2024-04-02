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

import com.watch.entity.Glass_material;
import com.watch.service.GlassService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/glass")
public class GlassRestController {
	@Autowired
	GlassService glassService;

	@GetMapping
	public List<Glass_material> getAll(){
	  return glassService.findAll();
	}
	@GetMapping("{name}")
	public Glass_material getName(@PathVariable("name") String name) {
		return glassService.findByName(name);
	}
	
	@PostMapping
	public Glass_material on(@RequestBody Glass_material Glass_material) {
		return glassService.save(Glass_material);
	}
	
	@PutMapping("{id}")
	public Glass_material update(@PathVariable("id") Integer id, @RequestBody Glass_material Glass_material) {
		return glassService.save(Glass_material);
	}
	
	@PutMapping("/delete/{id}")
	public Glass_material updatetrangthai(@PathVariable("id") Integer id, @RequestBody Glass_material Glass_material) {
		Glass_material.setStatus(false);
		return glassService.save(Glass_material);
	}
	
	@DeleteMapping("{id}")
	public void delete(@PathVariable("id") Integer id) {
		glassService.deleteById(id);
	}
	
	@GetMapping("/timKiem/{name}/{status}")
	public List<Glass_material> timKiem(@PathVariable("name") String name,@PathVariable("status") String status){
		//System.out.println("tên= "+name + " status= "+ status);
		if (status.equals("null")) {
			System.out.println("tên= "+name);
			return glassService.findByName1("%"+name+"%");
		} else {
			boolean in  = Boolean.parseBoolean(status);
			System.out.println("tên= "+name + " status="+ in);
			return glassService.findByName("%"+name+"%" , in);
		}
	}
	
	@GetMapping("/timKiem/{status}")
	public List<Glass_material> getStatus(@PathVariable("status") Boolean status){
		return glassService.findByStatus(status);
	}
	
	
}