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

import com.watch.entity.Shell_material;
import com.watch.service.ShellService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/shell")
public class ShellRestController {
	@Autowired
	ShellService shellService;

	@GetMapping
	public List<Shell_material> getAll(){
	  return shellService.findAll();
	}
	@GetMapping("{name}")
	public Shell_material getName(@PathVariable("name") String name) {
		return shellService.findByName(name);
	}
	
	@PostMapping
	public Shell_material on(@RequestBody Shell_material Shell_material) {
		return shellService.save(Shell_material);
	}
	
	@PutMapping("{id}")
	public Shell_material update(@PathVariable("id") Integer id, @RequestBody Shell_material Shell_material) {
		return shellService.save(Shell_material);
	}
	
	@PutMapping("/delete/{id}")
	public Shell_material updatetrangthai(@PathVariable("id") Integer id, @RequestBody Shell_material Shell_material) {
		Shell_material.setStatus(false);
		return shellService.save(Shell_material);
	}
	
	@DeleteMapping("{id}")
	public void delete(@PathVariable("id") Integer id) {
		shellService.deleteById(id);
	}
	
	@GetMapping("/timKiem/{name}/{status}")
	public List<Shell_material> timKiem(@PathVariable("name") String name,@PathVariable("status") String status){
		//System.out.println("tên= "+name + " status= "+ status);
		if (status.equals("null")) {
			System.out.println("tên= "+name);
			return shellService.findByName1("%"+name+"%");
		} else {
			boolean in  = Boolean.parseBoolean(status);
			System.out.println("tên= "+name + " status="+ in);
			return shellService.findByName("%"+name+"%" , in);
		}
	}
	
	@GetMapping("/timKiem/{status}")
	public List<Shell_material> getStatus(@PathVariable("status") Boolean status){
		return shellService.findByStatus(status);
	}
	
	
}