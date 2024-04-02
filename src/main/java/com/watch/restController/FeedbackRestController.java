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

import com.watch.entity.Feedback;
import com.watch.service.AccountService;
import com.watch.service.FeedbackService;
import com.watch.service.ProductService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/feedback")
public class FeedbackRestController {
	
	@Autowired
	FeedbackService feedbackService;
	@Autowired
	AccountService accountService;
	@Autowired
	ProductService productService;
	
	@GetMapping()
	public List<Feedback> getFeedback(){
		return feedbackService.findAll();
	}
	
	
	
	@GetMapping("{id}")
	public List<Feedback> getFeedback(@PathVariable("id") Integer id){
		return feedbackService.findByProductID(id);
	}


	@PostMapping("{id}")
	public Feedback create(@RequestBody Feedback feedback,@PathVariable("id") Integer id) {		
		return feedbackService.addFeedback(feedback, id);
	}

	@PutMapping("{id}")
	public Feedback update() {
		return null;
	}
	
	@DeleteMapping("{id}")
	public void delete(@PathVariable("id") Integer id) {
		feedbackService.delete(id); 
	}
	
	
}