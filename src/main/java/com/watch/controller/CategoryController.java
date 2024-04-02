package com.watch.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CategoryController {
	
	
	//Category
		@GetMapping("/itbalo/category")
		public String danhmuc(Model model) {	
			return"/user/product/sanPham";
		}
	
}
