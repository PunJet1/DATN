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

import com.watch.entity.Water_resistant;
import com.watch.service.WaterService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/water")
public class WaterRestController {
	@Autowired
	WaterService waterService;

	@GetMapping
	public List<Water_resistant> getAll(){
	  return waterService.findAll();
	}
	@GetMapping("{name}")
	public Water_resistant getName(@PathVariable("name") String name) {
		return waterService.findByName(name);
	}
	
	@PostMapping
	public Water_resistant on(@RequestBody Water_resistant Water_resistant) {
		return waterService.save(Water_resistant);
	}
	
	@PutMapping("{id}")
	public Water_resistant update(@PathVariable("id") Integer id, @RequestBody Water_resistant Water_resistant) {
		return waterService.save(Water_resistant);
	}
	
	@PutMapping("/delete/{id}")
	public Water_resistant updatetrangthai(@PathVariable("id") Integer id, @RequestBody Water_resistant Water_resistant) {
		Water_resistant.setStatus(false);
		return waterService.save(Water_resistant);
	}
	
	@DeleteMapping("{id}")
	public void delete(@PathVariable("id") Integer id) {
		waterService.deleteById(id);
	}
	
	@GetMapping("/timKiem/{name}/{status}")
	public List<Water_resistant> timKiem(@PathVariable("name") String name,@PathVariable("status") String status){
		//System.out.println("tên= "+name + " status= "+ status);
		if (status.equals("null")) {
			System.out.println("tên= "+name);
			return waterService.findByName1("%"+name+"%");
		} else {
			boolean in  = Boolean.parseBoolean(status);
			System.out.println("tên= "+name + " status="+ in);
			return waterService.findByName("%"+name+"%" , in);
		}
	}
	
	@GetMapping("/timKiem/{status}")
	public List<Water_resistant> getStatus(@PathVariable("status") Boolean status){
		return waterService.findByStatus(status);
	}
	
	
}