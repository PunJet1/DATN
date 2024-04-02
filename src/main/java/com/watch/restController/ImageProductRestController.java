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

import com.watch.entity.ImageProduct;
import com.watch.service.ImageProductService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/ImageProduct")
public class ImageProductRestController {
	
	@Autowired
	ImageProductService imageProductService;
	
	@GetMapping
	public List<ImageProduct> getAll(){
	     return	imageProductService.findAll();
	}
	
	@GetMapping("{id}")
	public List<ImageProduct> findByIdproduct(@PathVariable("id") Integer id){
		return imageProductService.findById(id);
	}
	
	@PutMapping("{id}")
	public ImageProduct update(@PathVariable("id") Integer id, @RequestBody ImageProduct imageProduct) {
		return imageProductService.save(imageProduct);
	}
	
	@PostMapping
	public ImageProduct create(@RequestBody ImageProduct imageProduct) {
		return imageProductService.save(imageProduct);
	}
	@DeleteMapping("{id}")
	public void delete(@PathVariable("id") Integer id) {
		imageProductService.deleteById(id);
	}
	
	

}