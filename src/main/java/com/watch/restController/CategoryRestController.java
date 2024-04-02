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

import com.watch.entity.Category;
import com.watch.service.CategoryService;



@CrossOrigin("*")
@RestController
@RequestMapping("/rest/categories")
public class CategoryRestController {

	@Autowired
	CategoryService categoryService;
	
	@GetMapping()
	public List<Category> getAll(){
		return categoryService.findAll();
	}
	
	@PostMapping 
	public Category create(@RequestBody Category category) {
		return categoryService.save(category);
	}
	
	@PutMapping("{id}")
	public Category update(@PathVariable("id") Integer id, @RequestBody Category category) {
		return categoryService.save(category);
	}
	
	@PutMapping("/delete/{id}")
	public Category deleteThai(@PathVariable("id") Integer id, @RequestBody Category category) {
		category.setStatus(false);
		return categoryService.save(category);
	}
	
	@DeleteMapping("{id}")
	public void delete(@PathVariable("id") Integer id) {
		categoryService.deleteById(id);
	}
	
	
	@GetMapping("/timKiem/{name}/{status}")
	public List<Category> timKiem(@PathVariable("name") String name,@PathVariable("status") String status){
		//System.out.println("tên= "+name + " status= "+ status);
		if (status.equals("null")) {
			System.out.println("tên= "+name);
			return categoryService.findByName1("%"+name+"%");
		} else {
			boolean in  = Boolean.parseBoolean(status);
			System.out.println("tên= "+name + " status="+ in);
			return categoryService.findByName("%"+name+"%" , in);
		}
	}
	
	@GetMapping("/timKiem/{status}")
	public List<Category> getStatus(@PathVariable("status") Boolean status){
		return categoryService.findByStatus(status);
	}
	
	@GetMapping("{name}")
	public Category getName(@PathVariable("name") String name) {
		return categoryService.findByName2(name);
	}
	
}