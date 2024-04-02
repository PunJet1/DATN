package com.watch.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BrandController {

	//brand
		@GetMapping("/itbalo/brand")
		public String thuongHieu() {
			return"/user/product/sanPham";
		}
	
}
