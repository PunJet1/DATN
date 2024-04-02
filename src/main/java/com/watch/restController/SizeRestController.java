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

import com.watch.entity.Size;
import com.watch.service.SizeService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/size")
public class SizeRestController {
	@Autowired
	SizeService sizeService;

	@GetMapping
	public List<Size> getAll(){
	  return sizeService.findAll();
	}
	@GetMapping("{name}")
	public Size getName(@PathVariable("name") String name) {
		return sizeService.findByName(name);
	}
	
	@PostMapping
	public Size on(@RequestBody Size Size) {
		return sizeService.save(Size);
	}
	
	@PutMapping("{id}")
	public Size update(@PathVariable("id") Integer id, @RequestBody Size Size) {
		return sizeService.save(Size);
	}
	
	@PutMapping("/delete/{id}")
	public Size updatetrangthai(@PathVariable("id") Integer id, @RequestBody Size Size) {
		Size.setStatus(false);
		return sizeService.save(Size);
	}
	
	@DeleteMapping("{id}")
	public void delete(@PathVariable("id") Integer id) {
		sizeService.deleteById(id);
	}
	
	@GetMapping("/timKiem/{name}/{status}")
	public List<Size> timKiem(@PathVariable("name") String name,@PathVariable("status") String status){
		//System.out.println("tên= "+name + " status= "+ status);
		if (status.equals("null")) {
			System.out.println("tên= "+name);
			return sizeService.findByName1("%"+name+"%");
		} else {
			boolean in  = Boolean.parseBoolean(status);
			System.out.println("tên= "+name + " status="+ in);
			return sizeService.findByName("%"+name+"%" , in);
		}
	}
	
	@GetMapping("/timKiem/{status}")
	public List<Size> getStatus(@PathVariable("status") Boolean status){
		return sizeService.findByStatus(status);
	}
	
	
}