package com.watch.controller;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.watch.entity.Accounts;
import com.watch.entity.Product;
import com.watch.entity.UserAcounts;
import com.watch.entity.WishList;
import com.watch.service.AccountService;
import com.watch.service.ProductService;
import com.watch.service.WishListService;

@Controller
public class WishListController {
	
	@Autowired
	WishListService wishListService;
    @Autowired
    ProductService productService;
    @Autowired
    AccountService accountService;
    @Autowired 
	public  HttpSession session;
	@Autowired
	HttpServletRequest request;
	@Autowired
    UserAcounts useAcc;
	@Autowired
	HttpServletResponse response;
	
//	@GetMapping("")
//	public String findByUsername(Model model){
//		String username = request.getRemoteUser();
//		List<WishList> items = wishListService.findByUsername(username);
//		model.addAttribute("items", items);
//		return "/user/account/sanPhamYeuThich";
//	}
	
	@RequestMapping("/appLike/{id}")
	public String appLike(WishList item, @PathVariable("id") Integer id, Model model) {
	    Product Product = productService.getById(id);
	    Accounts Accounts =	useAcc.User();
	    if(Accounts==null) {
	    	return "redirect:/login";
	    }else {
	    	WishList w = new WishList();
		    w.setAccount(Accounts);
			w.setProduct(Product);
			w.setLikeDate(new Date());
		    
		    WishList was = wishListService.findBy(Product.getProductId(),Accounts.getAccountId());
		    
		    
		    if (was == null) {
		    	System.out.println("Chưa Like" + id);
				try {
					wishListService.save(w);
					System.out.println("Like thành công !" + id);			
				} catch (Exception e) {
					System.out.println("Like thất bại " + e);
				}
			} else {
				w.setWishListId(was.getWishListId());
				wishListService.save(w);
				System.out.println("Đã Like" + id);
			}
			//return "user/product/ChiTietSP";
			//return "redirect:/itbalo/product/" + Product.getProductId();
		    return "redirect:/shoesstore/account/favorite";
	    }
	    
	    
	}

}