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

import com.watch.entity.ProductSize;
import com.watch.service.ProductSizeService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/productsizes")
public class ProductSizeRestController {
	
	@Autowired
	ProductSizeService productSizeService;
	
	@GetMapping
	public List<ProductSize> getAll(){
	     return	productSizeService.findAll();
	}
	
	@GetMapping("{id}")
	public List<ProductSize> findByProductId(@PathVariable("id") Integer id){
		return productSizeService.findById(id);
	}
	
	@PutMapping("{id}")
	public ProductSize update(@PathVariable("id") Integer id, @RequestBody ProductSize productSize) {
		return productSizeService.save(productSize);
	}
	@PostMapping
	public ProductSize create(@RequestBody ProductSize productSize) {
		return productSizeService.save(productSize);
	}
	@DeleteMapping("{id}")
	public void delete(@PathVariable("id") Integer id) {
		productSizeService.deleteById(id);
	}
}
