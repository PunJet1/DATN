package com.watch.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.watch.service.CategoryService;
import com.watch.service.VoucherService;

@Component
public class Globalinterceptoe implements HandlerInterceptor{

	@Autowired
	CategoryService categoryService;
	@Autowired
	VoucherService voucherService;
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		request.setAttribute("cates", categoryService.findAll());
		request.setAttribute("voucher", voucherService.findAll());
	}
}