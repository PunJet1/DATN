package com.watch.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.watch.dao.OrderDetailDao;
import com.watch.dao.OrdersDao;
import com.watch.entity.Accounts;
import com.watch.entity.Orders;
import com.watch.entity.Product;
import com.watch.entity.UserAcounts;
import com.watch.entity.Vouchers;
import com.watch.service.ProductService;
import com.watch.service.VoucherService;

@Controller
public class CartItemController {
	@Autowired
	public VoucherService vser;
	@Autowired
	ProductService productService;
	@Autowired
	OrdersDao ordersDao;
	@Autowired
	OrderDetailDao ordersDetailService;
	@Autowired
	UserAcounts useAcc;

	// giỏ hàng
	@GetMapping("/shoesstore/cartItem")
	public String gioHang(Model model) {
		Accounts account = useAcc.User();
		if (useAcc.User() == null) {
			return "redirect:/login";
		}
		if (account.getAccountId() != null) {
			Orders or = ordersDao.getGanNhat1(account.getAccountId());
			if (or != null) {
				ordersDetailService.deleteAll(or.getOrderDetails());
				ordersDao.delete(or);
				System.out.println("Xóa Ok Order");
			}
		}
		List<Vouchers> voucher = vser.findAllByDate();
		List<Product> top10 = productService.top10a();
		model.addAttribute("account", account);
		model.addAttribute("top10", top10);
		model.addAttribute("cates", voucher);
		return "/user/GioHang";

	}
}