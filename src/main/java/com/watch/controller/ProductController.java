package com.watch.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.watch.entity.Brand;
import com.watch.entity.Category;
import com.watch.entity.Product;
import com.watch.entity.Size;
import com.watch.entity.Strap_material;
import com.watch.entity.UserAcounts;
import com.watch.service.BrandService;
import com.watch.service.CategoryService;
import com.watch.service.FeedbackService;
import com.watch.service.ImageProductService;
import com.watch.service.ProductService;
import com.watch.service.SizeService;
import com.watch.service.StrapService;

@Controller
public class ProductController {
	
	@Autowired
	public  BrandService brandService;

	@Autowired
	public  CategoryService categoryService;

	@Autowired
	public  ProductService productService;
	
	@Autowired
	ImageProductService imageProductService;
	@Autowired
	FeedbackService feedbackService;
	@Autowired
	StrapService strapService;
	
	@Autowired
	SizeService sizeSV;
	
	 List<Category> categories;
	 List<Brand> brands;
	 List<Product> productBest;
	 List<Strap_material> straps;
	 List<Size> sizes;
	 
	 double price1 = 0;
		double price2 = 0;
		private Page<Product> products;
		String the;
	public void dulieu() {
		 categories = categoryService.findAll();
		 brands = brandService.findAll();
		 productBest = productService.findTop2();
		 straps = strapService.findAll();
		 sizes = sizeSV.findAll();
	}
	
	//search strap
	@GetMapping("itbalo/product/strap/{value}")
	public String searchStrap(Model model,@PathVariable("value") Long strap, @RequestParam("p") Optional<Integer> p ) {
		dulieu();
		Pageable pageable = PageRequest.of(p.orElse(0), 8);
		Page<Product> products;
		products = productService.getProductByStrap(strap, pageable);
		if (products.isEmpty()) {
			model.addAttribute("message", "Không tìm thấy sản phẩm nào phù hợp với tiêu chí bạn chọn!");
			model.addAttribute("listproduct", products);
		} else {
			model.addAttribute("listproduct", products);
		}
		model.addAttribute("pric", strap);
		model.addAttribute("brands", brands);
		model.addAttribute("straps", straps);
		model.addAttribute("sizes",sizes);
		model.addAttribute("listCategory", categories);
		model.addAttribute("productBest", productBest);
		model.addAttribute("number",products.getNumber());
		model.addAttribute("totalPages",products.getTotalPages());
		model.addAttribute("totalElements",products.getTotalElements());
		model.addAttribute("size",products.getSize());
		return "/user/product/childSanPham";
	}
	
	
	//seacher price
	@GetMapping("itbalo/product/price/{value}")
	public String searchPrice(Model model,@PathVariable("value") String pric, @RequestParam("p") Optional<Integer> p) {
		dulieu();
		Pageable pageable = PageRequest.of(p.orElse(0), 8);
		Page<Product> products;
		checkprice(pric);
		products = productService.searchBCP1(price1, price2, pageable);
		if (products.isEmpty()) {
			model.addAttribute("message", "Không tìm thấy sản phẩm nào phù hợp với tiêu chí bạn chọn!");
			model.addAttribute("listproduct", products);
		} else {
			model.addAttribute("listproduct", products);
		}
		model.addAttribute("pric", pric);
		model.addAttribute("brands", brands);
		model.addAttribute("straps", straps);
		model.addAttribute("sizes",sizes);
		model.addAttribute("listCategory", categories);
		model.addAttribute("productBest", productBest);
		model.addAttribute("number",products.getNumber());
		model.addAttribute("totalPages",products.getTotalPages());
		model.addAttribute("totalElements",products.getTotalElements());
		model.addAttribute("size",products.getSize());
		return "/user/product/childSanPham";
	}
	
	//search size
	@GetMapping("itbalo/product/size/{value}")
	public String searchSize(Model model,@PathVariable("value") Long pric, @RequestParam("p") Optional<Integer> p) {
		dulieu();
		Pageable pageable = PageRequest.of(p.orElse(0), 8);
		Page<Product> products;
		products = productService.getProductBySize(pric, pageable);
		if (products.isEmpty()) {
			model.addAttribute("message", "Không tìm thấy sản phẩm nào phù hợp với tiêu chí bạn chọn!");
			model.addAttribute("listproduct", products);
		} else {
			model.addAttribute("listproduct", products);
		}
		model.addAttribute("pric", pric);
		model.addAttribute("brands", brands);
		model.addAttribute("straps", straps);
		model.addAttribute("sizes",sizes);
		model.addAttribute("listCategory", categories);
		model.addAttribute("productBest", productBest);
		model.addAttribute("number",products.getNumber());
		model.addAttribute("totalPages",products.getTotalPages());
		model.addAttribute("totalElements",products.getTotalElements());
		model.addAttribute("size",products.getSize());
		return "/user/product/childSanPham";
	}
	
	//search header
	@GetMapping("/itbalo/search")
	public String Search(Model model, @RequestParam("nameSearch") String name, Optional<Integer> p) {
		Pageable pageable = PageRequest.of(p.orElse(0), 8);	
		dulieu();
		Page<Product> page = productService.findSearch(name,pageable);
		model.addAttribute("listproduct", page);
		model.addAttribute("brands", brands);
		model.addAttribute("straps", straps);
		model.addAttribute("sizes",sizes);
		model.addAttribute("listCategory", categories);
		model.addAttribute("productBest", productBest);
		model.addAttribute("number",page.getNumber());
		model.addAttribute("totalPages",page.getTotalPages());
		model.addAttribute("totalElements",page.getTotalElements());
		model.addAttribute("size",page.getSize());
		System.out.println("toanf 09876: "+page.getSize());
		model.addAttribute("name",name);
		return "/user/product/childSanPham";
	}
	@Autowired
	UserAcounts useAcc;
	
	// Product
	@GetMapping("/itbalo/product")
	public String sanpham(Model model, @RequestParam("p") Optional<Integer> p) {
	
			Pageable pageable = PageRequest.of(p.orElse(0), 8);
			dulieu();
			Page<Product> page = productService.findAll(pageable);
			model.addAttribute("listproduct", page);
			model.addAttribute("brands", brands);
			model.addAttribute("straps", straps);
			model.addAttribute("sizes",sizes);
			model.addAttribute("listCategory", categories);
			model.addAttribute("productBest", productBest);
			model.addAttribute("number",page.getNumber());
			model.addAttribute("totalPages",page.getTotalPages());
			System.out.println(page.getTotalPages());
			System.out.println(page.getTotalElements());
			model.addAttribute("size",page.getTotalElements());
			return "/user/product/childSanPham";
		
		
	}

	@GetMapping({"/itbalo/product/{id}"})
	public String loaiSp(Model model, @PathVariable("id") Integer id, @RequestParam("p") Optional<Integer> p) {
		//System.out.println("id: " + id);
		dulieu();
		Pageable pageable = PageRequest.of(p.orElse(0), 8);
		Page<Product> page = productService.selectProductSpIdCategory(id, pageable);
		model.addAttribute("listproduct", page);
		model.addAttribute("brands", brands);
		model.addAttribute("straps", straps);
		model.addAttribute("sizes",sizes);
		model.addAttribute("listCategory", categories);
		model.addAttribute("productBest", productBest);
		model.addAttribute("number",page.getNumber());
		model.addAttribute("totalPages",page.getTotalPages());
		model.addAttribute("totalElements",page.getTotalElements());
		model.addAttribute("size",page.getSize());
		System.out.println("toanf 09876: "+page.getSize());
		return "/user/product/childSanPham";
	}
	
	@GetMapping({"/itbalo/brand/{name}"})
	public String thuonghieu(Model model, @PathVariable("name") String name, @RequestParam("p") Optional<Integer> p) {
//		System.out.println("id: " + id);
		dulieu();
		for(Brand b : brands) {
			if(name.equalsIgnoreCase(b.getName())) {
				Pageable pageable = PageRequest.of(p.orElse(0), 8);
				Page<Product> page = productService.selectProductSpThongHieu(b.getBrandId(), pageable);
				model.addAttribute("listproduct", page);
				model.addAttribute("number",page.getNumber());
				model.addAttribute("totalPages",page.getTotalPages());
				model.addAttribute("totalElements",page.getTotalElements());
				model.addAttribute("size",page.getSize());
				System.out.println("list product by brand: "+page.getSize());
			}
		}
//		Pageable pageable = PageRequest.of(p.orElse(0), 8);
//		Page<Product> page = productService.selectProductSpThongHieu(id, pageable);
//		model.addAttribute("listproduct", page);
		model.addAttribute("brands", brands);
		model.addAttribute("straps", straps);
		model.addAttribute("sizes",sizes);
		model.addAttribute("listCategory", categories);
		model.addAttribute("productBest", productBest);
//		model.addAttribute("number",page.getNumber());
//		model.addAttribute("totalPages",page.getTotalPages());
//		model.addAttribute("totalElements",page.getTotalElements());
//		model.addAttribute("size",page.getSize());
//		System.out.println("list product by brand: "+page.getSize());
		return "/user/product/childSanPham";
	}
	
	@GetMapping({"/itbalo/category/{id}"})
	public String loaisp2(Model model, @PathVariable("id") String id, @RequestParam("p") Optional<Integer> p) {
		System.out.println("id: " + id);
		dulieu();
		products = null;
		Pageable pageable = PageRequest.of(p.orElse(0), 8);
		Page<Product> page = selectAllcategoryName(pageable,id);
		model.addAttribute("listproduct", products);
		model.addAttribute("brands", brands);
		model.addAttribute("straps", straps);
		model.addAttribute("sizes",sizes);
		model.addAttribute("listCategory", categories);
		model.addAttribute("productBest", productBest);
		model.addAttribute("number",page.getNumber());
		model.addAttribute("totalPages",page.getTotalPages());
		model.addAttribute("totalElements",page.getTotalElements());
		model.addAttribute("size",page.getSize());
		System.out.println("toanf 09876: "+page.getSize());
		return "/user/product/childSanPham";
	}

//	double price1 = 0;
//	double price2 = 0;
//	private Page<Product> products;
//	String the;
	
	@PostMapping("/itbalo/product/search")
	public String search(Model model, @RequestParam("bran") String bran, @RequestParam("cate") String cate,
			@RequestParam("pric") String pric, @RequestParam("p") Optional<Integer> p) {
		dulieu();
		Pageable pageable = PageRequest.of(p.orElse(0), 8);
		Page<Product> products;
		int brandId;
		int categoryId;
		if (cate == "" && bran == "" && pric == "") {// Thêm không tìm thấy sản phẩm nào///
			products = productService.findAll(pageable);
		} else if (cate == "" && bran == "" && pric != "") {
			checkprice(pric);
			products = productService.searchBCP1(price1, price2, pageable);
		} else if (cate == "" && bran != "" && pric == "") {
			brandId = Integer.parseInt(bran);
			checkprice(pric);
			products = productService.searchBCP6(brandId, pageable);
		} else if (cate != "" && bran == "" && pric == "") {
			categoryId = Integer.parseInt(cate);
			products = productService.searchBCP3(categoryId, pageable);
		} else if (cate != "" && bran != "" && pric == "") {
			brandId = Integer.parseInt(bran);
			categoryId = Integer.parseInt(cate);
			products = productService.searchBCP4(brandId, categoryId, pageable);
		} else if (cate == "" && bran != "" && pric != "") {
			brandId = Integer.parseInt(bran);
			checkprice(pric);
			products = productService.searchBCP5(brandId, price1, price2, pageable);
		} else if (cate != "" && bran == "" && pric != "") {
			categoryId = Integer.parseInt(cate);
			checkprice(pric);
			products = productService.searchBCP2(categoryId, price1, price2, pageable);
		} else {
			checkprice(pric);
			brandId = Integer.parseInt(bran);
			categoryId = Integer.parseInt(cate);
			products = productService.searchBCP(brandId, categoryId, price1, price2, pageable);
		}
		System.out.println("listProduc:  " + products.isEmpty());
		if (products.isEmpty()) {
			model.addAttribute("message", "Không tìm thấy sản phẩm nào phù hợp với tiêu chí bạn chọn!");
			model.addAttribute("listproduct", products);
		} else {
			model.addAttribute("listproduct", products);
		}
		model.addAttribute("bran", bran);
		model.addAttribute("cate", cate);
		model.addAttribute("pric", pric);
		model.addAttribute("brands", brands);
		model.addAttribute("straps", straps);
		model.addAttribute("sizes",sizes);
		model.addAttribute("listCategory", categories);
		model.addAttribute("productBest", productBest);
		model.addAttribute("number",products.getNumber());
		model.addAttribute("totalPages",products.getTotalPages());
		model.addAttribute("totalElements",products.getTotalElements());
		model.addAttribute("size",products.getSize());
		System.out.println("toanf 09876: "+products.getSize());
		return "/user/product/childSanPham";
	}
	@GetMapping("itbalo/product/gender/{gender}")
	public String productGender(@RequestParam("p") Optional<Integer> p,Model model,@PathVariable("gender") String param) {
		dulieu();
		Long gender;
		Pageable pageable = PageRequest.of(p.orElse(0), 8);
		if(param.equalsIgnoreCase("nam")) {
			gender = 1L;
		}else {
			gender = 0L;
		}
		Page<Product> products = productService.findByGender(gender,pageable);
		if (products.isEmpty()) {
			model.addAttribute("message", "Không tìm thấy sản phẩm nào!");
			model.addAttribute("listproduct", products);
		} else {
			model.addAttribute("messagePage", "Danh sách sản phẩm mà bạn tìm kiếm");
			model.addAttribute("listproduct", products);
		}
		model.addAttribute("brands", brands);
		model.addAttribute("straps", straps);
		model.addAttribute("sizes",sizes);
		model.addAttribute("listCategory", categories);
		model.addAttribute("productBest", productBest);
		model.addAttribute("number",products.getNumber());
		model.addAttribute("totalPages",products.getTotalPages());
		model.addAttribute("totalElements",products.getTotalElements());
		model.addAttribute("size",products.getSize());
		System.out.println("gender: "+products.getSize());
		return "/user/product/childSanPham";
	}
	@GetMapping("itbalo/product/khuyenmai")
	public String phobien(Model model, @RequestParam("p") Optional<Integer> p) {
		dulieu();
		Pageable pageable = PageRequest.of(p.orElse(0), 8);
		Page<Product> products = productService.selectRandom1(pageable);
		if (products.isEmpty()) {
			model.addAttribute("message", "Không có sản phẩm nào đang khuyến mãi!");
			model.addAttribute("listproduct", products);
		} else {
			model.addAttribute("messagePage", "Danh sách sản phẩm đang khuyến mãi");
			model.addAttribute("listproduct", products);
		}
		model.addAttribute("brands", brands);
		model.addAttribute("straps", straps);
		model.addAttribute("sizes",sizes);
		model.addAttribute("listCategory", categories);
		model.addAttribute("productBest", productBest);
		model.addAttribute("number",products.getNumber());
		model.addAttribute("totalPages",products.getTotalPages());
		model.addAttribute("totalElements",products.getTotalElements());
		model.addAttribute("size",products.getSize());
		System.out.println("toanf 09876: "+products.getSize());
		return "/user/product/childSanPham";
	}

	@GetMapping("itbalo/product/banchay")
	public String banchay(Model model, @RequestParam("p") Optional<Integer> p) {
		dulieu();
		Pageable pageable = PageRequest.of(p.orElse(0), 8);
		Page<Product> products = productService.selectBanChayNhat(pageable);
		if (products.isEmpty()) {
			model.addAttribute("message", "Không tìm thấy sản phẩm nào!");
			model.addAttribute("listproduct", products);
		} else {
			model.addAttribute("messagePage", "Danh sách sản phẩm bán chạy");
			model.addAttribute("listproduct", products);
		}
		model.addAttribute("brands", brands);
		model.addAttribute("straps", straps);
		model.addAttribute("sizes",sizes);
		model.addAttribute("listCategory", categories);
		model.addAttribute("productBest", productBest);
		model.addAttribute("number",products.getNumber());
		model.addAttribute("totalPages",products.getTotalPages());
		model.addAttribute("totalElements",products.getTotalElements());
		model.addAttribute("size",products.getSize());
		System.out.println("toanf 09876: "+products.getSize());
		return "/user/product/childSanPham";
	}

	@GetMapping("itbalo/product/moinhat")
	public String moinhat(Model model, @RequestParam("p") Optional<Integer> p) {
		dulieu();
		Pageable pageable = PageRequest.of(p.orElse(0), 8);
		Page<Product> products = productService.selectDateNew(pageable);
		if (products.isEmpty()) {
			model.addAttribute("message", "Không tìm thấy sản phẩm nào!");
			model.addAttribute("listproduct", products);
		} else {
			model.addAttribute("messagePage", "Danh sách sản phẩm mới nhất");
			model.addAttribute("listproduct", products);
		}
		model.addAttribute("listproduct", products);
		model.addAttribute("brands", brands);
		model.addAttribute("straps", straps);
		model.addAttribute("sizes",sizes);
		model.addAttribute("listCategory", categories);
		model.addAttribute("productBest", productBest);
		model.addAttribute("number",products.getNumber());
		model.addAttribute("totalPages",products.getTotalPages());
		model.addAttribute("totalElements",products.getTotalElements());
		model.addAttribute("size",products.getSize());
		System.out.println("toanf 09876: "+products.getSize());
		return "/user/product/childSanPham";
	}
	
	@GetMapping("/itbalo/product/tags/{id}")
	public String tags(Model model, @RequestParam("p") Optional<Integer> p,@PathVariable("id") String id) {
		dulieu();
		Pageable pageable = PageRequest.of(p.orElse(0), 8);
		products = null;
		selectAllProperties(pageable,id);
		
		//Page<Product> products = productService.selectDateNew(pageable);
		if (products.isEmpty()) {
			model.addAttribute("message", "Không tìm thấy sản phẩm nào!");
			model.addAttribute("listproduct", products);
		} else {
			if(id.equals("mauvang")||id.equals("mautrang")||id.equals("mauden")||id.equals("mauxanh")) {
				checkColorWaterThick(id);
				model.addAttribute("messagePage", "==> Thẻ: Màu "+the);
			}
			// if(id.equals("3atm")||id.equals("5atm")||id.equals("10atm")||id.equals("20atm")) {
			// 	checkColorWaterThick(id);
			// 	model.addAttribute("messagePage", "==> Thẻ: Độ chịu nước: "+the);
			// }
			if(id.equals("36mm")||id.equals("38mm")||id.equals("40mm")||id.equals("42mm")) {
				checkColorWaterThick(id);
				model.addAttribute("messagePage", "==> Thẻ: Độ dày: "+the);
			}
			model.addAttribute("listproduct", products);
		}
		model.addAttribute("listproduct", products);
		model.addAttribute("brands", brands);
		model.addAttribute("straps", straps);
		model.addAttribute("sizes",sizes);
		model.addAttribute("listCategory", categories);
		model.addAttribute("productBest", productBest);
		model.addAttribute("number",products.getNumber());
		model.addAttribute("totalPages",products.getTotalPages());
		model.addAttribute("totalElements",products.getTotalElements());
		model.addAttribute("size",products.getSize());
		System.out.println("toanf 09876: "+products.getSize());
		return "/user/product/childSanPham";
	}
	//ChitietSp
			@GetMapping({"/itbalo/product/detail/{id}","/itbalo/product/tags/detail/{id}"
				,"/itbalo/product/tags/detail/detail/{id}","/itbalo/detail/{id}",
				"/itbalo/brand/detail/{id}","/itbalo/category/detail/{id}","/itbalo/product/price/detail/{id}",
				"/itbalo/product/strap/detail/{id}","/itbalo/account/detail/{id}","/chitietDH/itbalo/product/detail/{id}"})
			public String chitietSp(Model model ,@PathVariable("id") Integer id) {
				dulieu();
				Product item = productService.getById(id);
				model.addAttribute("item", item);
//				
//			    List<Product> top10 = productService.top10();
//			    model.addAttribute("top10", top10);
				model.addAttribute("brands", brands);
				model.addAttribute("straps", straps);
				model.addAttribute("sizes",sizes);
				model.addAttribute("listCategory", categories);
				model.addAttribute("productBest", productBest);
				return "user/product/ChiTietSP";
			}

	private String checkColorWaterThick(String id) {
		if(id.equalsIgnoreCase("mautrang")) {
			the="Trắng";
		}
		if(id.equalsIgnoreCase("mauvang")) {
			the="Vàng";
		}
		if(id.equalsIgnoreCase("mauden")) {
			the="Đen";
		}
		if(id.equalsIgnoreCase("mauxanh")) {
			the="Xanh";
		}
		if(id.equalsIgnoreCase("3atm")) {
			the="3atm";
		}
		if(id.equalsIgnoreCase("5atm")) {
			the="5atm";
		}
		if(id.equalsIgnoreCase("10atm")) {
			the="10atm";
		}
		if(id.equalsIgnoreCase("20atm")) {
			the="20atm";
		}
		if(id.equalsIgnoreCase("36mm")) {
			the="36mm";
		}
		if(id.equalsIgnoreCase("38mm")) {
			the="38mm";
		}
		if(id.equalsIgnoreCase("40mm")) {
			the="40mm";
		}
		if(id.equalsIgnoreCase("42mm")) {
			the="42mm";
		}	
		return the;
	}
	private Page<Product> selectAllcategoryName(Pageable pageable, String id) {
		if(id.equals("quandoi")) {
			String cate = "Đồng hồ quân đội";
			products = productService.selectProductSpLoai2(cate,pageable);
		}
		if(id.equals("rolex")) {
			String cate = "Đồng hồ Rolex";
			products = productService.selectProductSpLoai2(cate,pageable);
		}
		if(id.equals("thethao")) {
			String cate = "Đồng hồ thể thao";
			products = productService.selectProductSpLoai2(cate,pageable);
		}
		if(id.equals("thongminh")) {
			String cate = "Đồng hồ thông minh";
			products = productService.selectProductSpLoai2(cate,pageable);
		}
		if(id.equals("thachanh")) {
			String cate = "Đồng hồ thạch anh";
			products = productService.selectProductSpLoai2(cate,pageable);
		}
		if(id.equals("dientu")) {
			String cate = "Đồng Hồ Điện Tử";
			products = productService.selectProductSpLoai2(cate,pageable);
		}
		if(id.equals("co")) {
			String cate = "Đồng Hồ Cơ";
			products = productService.selectProductSpLoai2(cate,pageable);
		}
		if(id.equals("hybrid")) {
			String cate = "Hybrid Watch";
			products = productService.selectProductSpLoai2(cate,pageable);
		}	
		return products;
	}
	private Page<Product> selectAllProperties(Pageable pageable, String id) {
		if(id.equals("mautrang")) {
			String color = "Trắng";
			products = productService.selectAllColor(color,pageable);
		}else if(id.equals("mauvang")) {
			String color = "Vàng";
			products = productService.selectAllColor(color,pageable);
		}else if(id.equals("mauden")){
			String color = "Đen";
			products = productService.selectAllColor(color,pageable);
		}else if(id.equals("mauxanh")){
			String color = "Xanh";
			products = productService.selectAllColor(color,pageable);
		}else if(id.equals("3atm")){
			String water = "3 ATM";
			products = productService.selectAllWaterfoop(water,pageable);
		}else if(id.equals("5atm")){
			String water = "5 ATM";
			products = productService.selectAllWaterfoop(water,pageable);
		}else if(id.equals("10atm")){
			String water = "10 ATM";
			products = productService.selectAllWaterfoop(water,pageable);
		}else if(id.equals("20atm")){
			String water = "20 ATM";
			products = productService.selectAllWaterfoop(water,pageable);
		}else if(id.equals("36mm")){
			String thickness = "36mm";
			products = productService.selectAllthickness(thickness,pageable);
		}else if(id.equals("38mm")){
			String thickness = "38mm";
			products = productService.selectAllthickness(thickness,pageable);
		}else if(id.equals("40mm")){
			String thickness = "40mm";
			products = productService.selectAllthickness(thickness,pageable);
		}else if(id.equals("42mm")){
			String thickness = "42mm";
			products = productService.selectAllthickness(thickness,pageable);
		}
		//System.out.println(products.getSize());
		
		return products;
	}

	private void checkprice(String pric) {
		if (pric.equals("1")) {
			price1 = 0;
			price2 = 2000000;
		}
		if (pric.equals("2")) {
			price1 = 2000000;
			price2 = 5000000;
		}
		if (pric.equals("3")) {
			price1 = 5000000;
			price2 = 10000000;
		}
		if (pric.equals("4")) {
			price1 = 10000000;
			price2 = 15000000;
		}
		if (pric.equals("5")) {
			price1 = 15000000;
			price2 = 20000000;
		}
		if (pric.equals("6")) {
			price1 = 20000000;
			price2 = 25000000;
		}
		if (pric.equals("7")) {
			price1 = 30000000;
			price2 = 500000000;
		}
	}
	
	
	

}
