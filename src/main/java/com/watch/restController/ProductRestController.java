package com.watch.restController;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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

import com.watch.dao.ProductDao;
import com.watch.entity.Product;
import com.watch.service.ProductService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/products")
public class ProductRestController {
	@Autowired
	ProductService productService;
	@Autowired
	ProductDao productDao;
	
	//Load toàn bộ sản phẩm
	@GetMapping()
	public List<Product> getAll() {
//		return productService.findAll();
		return productDao.getAllProduct();
	}

	@GetMapping("{productId}")
	public Optional<Product> getOne(@PathVariable("productId") Integer productId) {
		return productService.findById(productId);
	}

	@PostMapping()
	public Product create(@RequestBody Product product) {
		return productService.save(product);
	}
	
	@PutMapping("{id}")
	public Product update(@PathVariable("id") Integer id,@RequestBody Product product) {
		product.setUpdateDate(new Date());
		return productService.save(product);
	}
	
	@PutMapping("/delete/{id}")
	public Product updatetrangthai(@PathVariable("id") Integer id,@RequestBody Product product) {
		product.setStatus(false);
		return productService.save(product);
	}
	
	@DeleteMapping("{id}")
	public void delete(@PathVariable("id") Integer id) {
		 productService.deleteById(id);
	}
	
	@GetMapping("/timKiem/{name}/{status}")
	public List<Product> timKiem(@PathVariable("name") String name,@PathVariable("status") String status){
		//System.out.println("tên= "+name + " status= "+ status);
		if (status.equals("null")) {
			System.out.println("tên= "+name);
			return productService.findByName1("%"+name+"%");
		} else {
			boolean in  = Boolean.parseBoolean(status);
			System.out.println("tên= "+name + " status="+ in);
			return productService.findByName("%"+name+"%" , in);
		}
	}

	@GetMapping("/timKiem/{status}")
	public List<Product> getStatus(@PathVariable("status") Boolean status){
		return productService.findByStatus(status);
	}
	
	@PostMapping("/checkName")
	public Product checkName(@RequestBody Product product){
		return productService.checkName(product.getName());
	}
}