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

import com.watch.entity.News;
import com.watch.service.NewsService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/news")
public class NewsRestController {
	@Autowired
	private NewsService newsService;
	
	@GetMapping()
	public List<News> getNews(){
		return newsService.findAll();
	}
	
	@PostMapping()
	public News create(@RequestBody News news) {
		return newsService.save(news);
	}
	
	@PutMapping("{id}")
	public News update(@PathVariable("id") Integer id,@RequestBody News News) {
		return newsService.save(News);
	}
	
	@DeleteMapping("{id}")
	public void delete(@PathVariable("id") Integer id) {
		newsService.deleteById(id);
	}

}