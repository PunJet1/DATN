package com.watch.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.watch.config.VnpayConfig;
import com.watch.dao.OrdersDao;
import com.watch.dao.VoucherDao;
import com.watch.entity.Accounts;
import com.watch.entity.Brand;
import com.watch.entity.OrderDetail;
import com.watch.entity.Orders;
import com.watch.entity.Product;
import com.watch.entity.Size;
import com.watch.entity.Strap_material;
import com.watch.entity.UserAcounts;
import com.watch.entity.Vouchers;
import com.watch.service.AccountService;
import com.watch.service.BrandService;
import com.watch.service.OrdersService;
import com.watch.service.ProductService;
import com.watch.service.SizeService;
import com.watch.service.StrapService;
import com.watch.service.VoucherService;

@Controller
public class OrderController {

	@Autowired
	OrdersService ordersService;
	@Autowired
	VoucherService voucherService;
	@Autowired
	AccountService accountService;

	@Autowired
	ProductService productService;

	@Autowired
	public JavaMailSender emailSender;

	@Autowired
	HttpServletRequest req;

	@Autowired
	HttpServletResponse resp;

	@Autowired
	public HttpSession session;
	@Autowired
	UserAcounts useAcc;
	@Autowired
	OrdersService orderService;
	@Autowired
	VoucherDao voucherDao;
	@Autowired
	OrdersDao orderDao;

	@Autowired
	OrdersDao odao;
	@Autowired
	SizeService sizeSV;
	@Autowired
	StrapService strapSv;
	@Autowired
	BrandService brandService;
	Accounts account;

	@GetMapping("/shoesstore/order/checkout")
	public String checkout(Model model) {
		if (useAcc.User() == null) {
			return "redirect:/login";
		} else {
			List<Product> list = productService.findTop6Img();
			model.addAttribute("items", list);
			List<Strap_material> straps = strapSv.findAll();
			model.addAttribute("straps", straps);
			List<Size> sizes = sizeSV.findAll();
			model.addAttribute("sizes", sizes);

			List<Brand> listBrand = brandService.findAll();
			model.addAttribute("brands", listBrand);
			// List<Vouchers> voucher=voucherService.findAll();
			account = useAcc.User();
			List<Vouchers> voucher = voucherDao.getVoucherWithAcc(account.getAccountId());
			model.addAttribute("account", account);
			model.addAttribute("cates", voucher);
			return "/user/vnp/ThanhToan";
		}

	}

	@PostMapping("/shoesstore/order/checkout")
	@ResponseBody
	public String checkoutPost(Model model, @ModelAttribute("account") Accounts accountKh) throws IOException {
		List<Strap_material> straps = strapSv.findAll();
		model.addAttribute("straps", straps);
		List<Size> sizes = sizeSV.findAll();
		model.addAttribute("sizes", sizes);

		List<Brand> listBrand = brandService.findAll();
		model.addAttribute("brands", listBrand);
		if (useAcc.User() == null) {
			return "redirect:/login";
		} else {
			System.out.println("acount: " + accountKh);
			/*
			 * accounts.setAccountId(account.getAccountId());
			 * accounts.setAddress(accountKh.getAddress());
			 * accounts.setEmail(accountKh.getEmail());
			 * accounts.setFullname(accountKh.getFullname());
			 * accounts.setPhone(accountKh.getPhone());
			 * accounts.setImage(account.getImage());
			 * accounts.setPassword(account.getPassword());
			 * accounts.setUsername(account.getUsername());
			 * accounts.setBirthdate(account.getBirthdate());
			 */
			// accountService.save(accounts);
			session.setAttribute("accountKh", accountKh);

			String pttt = req.getParameter("httt_ma");
			session.setAttribute("pttt", pttt);

			System.out.println("pttt: " + pttt);
			String maVoucher = req.getParameter("rdoResult");
			session.setAttribute("maVoucher", maVoucher);
			System.out.println("maVoucher: " + maVoucher);

			String vnp_Version = "2.1.0";
			String vnp_Command = "pay";
			String orderType = req.getParameter("ordertype");
			long amount = Long.parseLong(String.valueOf(Math.round(Double.parseDouble(req.getParameter("amount")))))
					* 100;
					System.out.println("đây là tổng tiền" + amount);
			String bankCode = req.getParameter("bankCode");

			String vnp_TxnRef = VnpayConfig.getRandomNumber(8);
			String vnp_IpAddr = VnpayConfig.getIpAddress(req);
			String vnp_TmnCode = VnpayConfig.vnp_TmnCode;

			Map<String, String> vnp_Params = new HashMap<>();
			vnp_Params.put("vnp_Version", vnp_Version);
			vnp_Params.put("vnp_Command", vnp_Command);
			vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
			vnp_Params.put("vnp_Amount", String.valueOf(amount));
			vnp_Params.put("vnp_CurrCode", "VND");
			System.out.println("tong tien: " + vnp_Params.get("vnp_Amount"));
			if (bankCode != null && !bankCode.isEmpty()) {
				vnp_Params.put("vnp_BankCode", bankCode);
			}
			vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
			vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
			vnp_Params.put("vnp_OrderType", orderType);

			String locate = req.getParameter("language");
			if (locate != null && !locate.isEmpty()) {
				vnp_Params.put("vnp_Locale", locate);
			} else {
				vnp_Params.put("vnp_Locale", "vn");
			}
			vnp_Params.put("vnp_ReturnUrl", VnpayConfig.vnp_Returnurl);
			vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

			Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
			String vnp_CreateDate = formatter.format(cld.getTime());
			vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

			cld.add(Calendar.MINUTE, 15);
			String vnp_ExpireDate = formatter.format(cld.getTime());
			vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

			List fieldNames = new ArrayList(vnp_Params.keySet());
			Collections.sort(fieldNames);
			StringBuilder hashData = new StringBuilder();
			StringBuilder query = new StringBuilder();
			Iterator itr = fieldNames.iterator();
			while (itr.hasNext()) {
				String fieldName = (String) itr.next();
				String fieldValue = (String) vnp_Params.get(fieldName);
				if ((fieldValue != null) && (fieldValue.length() > 0)) {
					// Build hash data
					hashData.append(fieldName);
					hashData.append('=');
					hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
					// Build query
					query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
					query.append('=');
					query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
					if (itr.hasNext()) {
						query.append('&');
						hashData.append('&');
					}
				}
			}
			com.google.gson.JsonObject job = new JsonObject();
			long amountFinal = Long.parseLong(String.valueOf(Math.round(Double.parseDouble(req.getParameter("amount")))));
			if (pttt.equals("1")) {
				Orders order = (Orders) session.getAttribute("OrderganNhat");
				order.setTotal(amountFinal);
				System.out.println("Gia tiền: " + order.getTotal());
				Accounts ttkh = (Accounts) session.getAttribute("accountKh");
				String address = ttkh.getAddress();
				String maVoucher2 = (String) session.getAttribute("maVoucher");
				// int amount = (int) session.getAttribute("amount");
				order.setAddress(address);

				if (maVoucher2 == null || maVoucher2.equals("")) {
					order.setVoucher(null);
				} else {
					Vouchers vouchers = voucherService.getById(maVoucher2);
					if (order.getVoucher() != vouchers || order.getVoucher() == null) {
						/* check xem đã có voucher chưa */
						List<Orders> lst = orderService.getByIdVoucher(order.getAccount().getAccountId());
						if (lst.isEmpty()) {
							int slVouchertru = vouchers.getQuantity() - 1;
							vouchers.setQuantity(slVouchertru);

							// Long amountTongg = (long) (order.getTotal() -
							// ((order.getTotal()*vouchers.getValued())/100));
							Long amountTongg = (long) (order.getTotal() - vouchers.getValued());
							order.setTotal(amountTongg);
							if (slVouchertru <= 0) {
								vouchers.setStatustt(false);
							}
							voucherService.save(vouchers);
							order.setVoucher(vouchers);
						} else {
							int stt = 0;
							for (int i = 0; i < lst.size(); i++) {
								String voucherName = lst.get(i).getVoucher().getVoucherName();
								if (voucherName.equals(maVoucher2)) {
									stt += 1;
								}
							}
							if (stt == 0) {
								int slVouchertru = vouchers.getQuantity() - 1;
								vouchers.setQuantity(slVouchertru);

								// Long amountTongg = (long) (order.getTotal() -
								// ((order.getTotal()*vouchers.getValued())/100));
								Long amountTongg = (long) (order.getTotal() - vouchers.getValued());
								order.setTotal(amountTongg);
								if (slVouchertru <= 0) {
									vouchers.setStatustt(false);
								}

								voucherService.save(vouchers);
								order.setVoucher(vouchers);
							} else {
								order.setVoucher(null);
							}
						}
					} else {
						order.setVoucher(null);
					}
					// order.setTotal(amount);
				}
				order.setStatus(1);
				order.setCreateDate(new Date());

				order.setTenNn(accountKh.getFullname());
				order.setSdtNn(accountKh.getPhone());
				// order.setEmailNn(accountKh.getEmail());
				// order.setDiaChiNn(accountKh.getAddress());
				// order.setTotal(amount1);
				order.setTthaiThanhToan(0);
				orderService.save(order);
				if (order.getTenNn().equals(null) || order.getSdtNn().equals(null) || order.getAddress().equals(null)) {
					// chỗ này chỉnh sửa đơn ảo
				}
				String paymentUrl = VnpayConfig.abc;
				job.addProperty("code", "00");
				job.addProperty("message", "success");
				job.addProperty("data", paymentUrl);
			} else {
				String queryUrl = query.toString();
				String vnp_SecureHash = VnpayConfig.hmacSHA512(VnpayConfig.vnp_HashSecret, hashData.toString());
				queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
				String paymentUrl = VnpayConfig.vnp_PayUrl + "?" + queryUrl;
				job.addProperty("code", "00");
				job.addProperty("message", "success");
				job.addProperty("data", paymentUrl);
			}

			Gson gson = new Gson();
			// resp.getWriter().write(gson.toJson(job));

			return gson.toJson(job);
		}

	}

	@GetMapping("/thanh-toan-thanh-cong")
	public String thanhcong(Model model) {

		String vnp_ResponseCode = req.getParameter("vnp_ResponseCode");

		String vnp_BankCode = req.getParameter("vnp_BankCode");
		String vnp_BankTranNo = req.getParameter("vnp_BankTranNo");
		String vnp_CardType = req.getParameter("vnp_CardType");
		String vnp_OrderInfo = "Thanh toán mua hàng";
		/* Format ngày tháng */
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		String strDate = formatter.format(date);
		System.out.println("Date Format with dd MMMM yyyy: " + strDate);
		String vnp_TransactionNo = req.getParameter("vnp_TransactionNo");

		model.addAttribute("vnp_BankCode", vnp_BankCode);
		model.addAttribute("vnp_BankTranNo", vnp_BankTranNo);
		model.addAttribute("vnp_CardType", vnp_CardType);
		model.addAttribute("vnp_OrderInfo", vnp_OrderInfo);
		model.addAttribute("strDate", strDate);
		model.addAttribute("vnp_CardType", vnp_CardType);

		System.out.println("mã: " + vnp_ResponseCode);
		if (vnp_ResponseCode.equals("00")) {
			Orders order = (Orders) session.getAttribute("OrderganNhat");
			Accounts accountKh = (Accounts) session.getAttribute("accountKh");
			String address = accountKh.getAddress();
			String maVoucher = (String) session.getAttribute("maVoucher");

			// int amount = (int) session.getAttribute("amount");
			order.setAddress(address);

			if (maVoucher == null || maVoucher.equals("")) {
				order.setVoucher(null);
			} else {
				/* chỉnh lại số lượng voucher */

				Vouchers vouchers = voucherService.getById(maVoucher);
				if (order.getVoucher() != vouchers || order.getVoucher() == null) {
					/* check xem đã có voucher chưa */
					List<Orders> lst = orderService.getByIdVoucher(order.getAccount().getAccountId());
					if (lst.isEmpty()) {
						int slVouchertru = vouchers.getQuantity() - 1;
						vouchers.setQuantity(slVouchertru);

						// Long amountTongg = (long) (order.getTotal() -
						// ((order.getTotal()*vouchers.getValued())/100));
						Long amountTongg = (long) (order.getTotal() - vouchers.getValued());
						order.setTotal(amountTongg);
						voucherService.save(vouchers);
						order.setVoucher(vouchers);
					} else {
						int stt = 0;
						for (int i = 0; i < lst.size(); i++) {
							String voucherName = lst.get(i).getVoucher().getVoucherName();
							if (voucherName.equals(maVoucher)) {
								stt += 1;
							}
						}
						if (stt == 0) {
							int slVouchertru = vouchers.getQuantity() - 1;
							vouchers.setQuantity(slVouchertru);
							// Long amountTongg = (long) (order.getTotal() -
							// ((order.getTotal()*vouchers.getValued())/100));
							Long amountTongg = (long) (order.getTotal() - vouchers.getValued());
							order.setTotal(amountTongg);
							voucherService.save(vouchers);
							order.setVoucher(vouchers);
						} else {
							order.setVoucher(null);
							System.out.println("Bạn đã sử dụng voucher này!");
						}
					}
				} else {
					order.setVoucher(null);
				}

				// order.setTotal(amount);
			}
			order.setTenNn(accountKh.getFullname());
			order.setSdtNn(accountKh.getPhone());
			// order.setEmailNn(accountKh.getEmail());
			// order.setDiaChiNn(accountKh.getAddress());

			order.setStatus(1);
			order.setTthaiThanhToan(1);
			order.setCreateDate(new Date());
			orderService.save(order);

			List<OrderDetail> orderDetail = (List<OrderDetail>) session.getAttribute("listOrderDetail");
			if (orderDetail.size() == 0 || orderDetail.equals(null)) {
				return "success";
			}
			// chỉnh sửa lại số lượng thanh toán
			for (int i = 0; i < orderDetail.size(); i++) {
				int productId = orderDetail.get(i).getProduct().getProductId();
				int slgmua = orderDetail.get(i).getQuantity();
				Product product = productService.getById(productId);
				int slgProduct = product.getQuantity();
				int slgMoi = slgProduct - slgmua;
				if (slgMoi < 0) {
					model.addAttribute("message", "Số lượng sản phẩm không đủ để giao tới bạn");
					return "redirect:/thanh-toan-khong-thanh-cong";
				}
				product.setQuantity(slgMoi);
				productService.save(product);
			}

			double vn = order.getTotal();
			float vn2 = (float) vn;
			long vnd = (long) vn2;
			Locale localeVN = new Locale("vi", "VN");
			NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
			String str1 = currencyVN.format(vnd);

			model.addAttribute("order", order);
			model.addAttribute("str1", str1);
			String email = orderDao.getEmail(order.getOrderId());
			sendSimpleEmail(email, order);
			return "success1";
		} else {

			return "redirect:/thanh-toan-khong-thanh-cong";
		}

	}

	@GetMapping("/thanh-toan-khong-thanh-cong")
	public String khongthanhcong(Model model) {
		List<Strap_material> straps = strapSv.findAll();
		model.addAttribute("straps", straps);
		List<Size> sizes = sizeSV.findAll();
		model.addAttribute("sizes", sizes);

		List<Brand> listBrand = brandService.findAll();
		model.addAttribute("brands", listBrand);
		return "unsuccess";
	}

	@GetMapping("/dat-hang-thanh-cong")
	public String okCOD(Model model) {
		List<Strap_material> straps = strapSv.findAll();
		model.addAttribute("straps", straps);
		List<Size> sizes = sizeSV.findAll();
		model.addAttribute("sizes", sizes);

		List<Brand> listBrand = brandService.findAll();
		model.addAttribute("brands", listBrand);
		// Orders order = (Orders) session.getAttribute("OrderganNhat");
		account = useAcc.User();
		Orders order1 = odao.getGanNhat(account.getAccountId());
		/* Format ngày tháng */
		Date date = new Date();
		date = order1.getCreateDate();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		String strDate = formatter.format(date);
		System.out.println("Date Format with dd MMMM yyyy: " + strDate);

		/* Format tiền */
		double vn = order1.getTotal();
		float vn2 = (float) vn;
		long vnd = (long) vn2;
		Locale localeVN = new Locale("vi", "VN");
		NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
		String str1 = currencyVN.format(vnd);
		System.out.println(str1);

		// load lai sl sp
		List<OrderDetail> orderDetail = (List<OrderDetail>) session.getAttribute("listOrderDetail");
		if (orderDetail.size() == 0 || orderDetail.equals(null)) {
			return "success";
		}
		// chỉnh sửa lại số lượng thanh toán
		for (int i = 0; i < orderDetail.size(); i++) {
			int productId = orderDetail.get(i).getProduct().getProductId();
			int slgmua = orderDetail.get(i).getQuantity();
			Product product = productService.getById(productId);
			int slgProduct = product.getQuantity();
			int slgMoi = slgProduct - slgmua;
			if (slgMoi < 0) {
				return "redirect:/thanh-toan-khong-thanh-cong";
			}
			product.setQuantity(slgMoi);
			productService.save(product);
		}
		String mail = orderDao.getEmail(order1.getOrderId());
		model.addAttribute("vnd", str1);
		model.addAttribute("date", strDate);
		model.addAttribute("order", order1);
		sendSimpleEmail(mail, order1);
		return "success1";
	}

	@RequestMapping("/order/detail/{id}")
	public String detail(@PathVariable("id") Integer id, Model model) {
		List<Product> list = productService.findTop6Img();
		model.addAttribute("items", list);
		List<Strap_material> straps = strapSv.findAll();
		model.addAttribute("straps", straps);
		List<Size> sizes = sizeSV.findAll();
		model.addAttribute("sizes", sizes);

		List<Brand> listBrand = brandService.findAll();
		model.addAttribute("brands", listBrand);
		model.addAttribute("order", ordersService.getById(id));

		System.out.println("order " + ordersService.getById(id).getClass());
		return "/user/DonHang1";
	}

	public void sendSimpleEmail(String email, Orders order) {

		// Create a Simple MailMessage.
		SimpleMailMessage message = new SimpleMailMessage();

		message.setTo(email);
		message.setSubject("Đặt hàng thành công tại ShoesStore.COM");
		message.setText("Cảm ơn bạn đã mua hàng tại ShoesStore.COM.\r\n"
				+ "Mã hóa đơn của bạn là: " + order.getOrderId() + "\r\n"
				+ "Vui lòng click vào đường link: http://localhost:8080/shoesstore/account/history/detail/"
				+ order.getOrderId() + " để xem chi tiết hóa đơn.\r\n"
				+ "Xin chân thành cảm ơn đã sử dụng dịch vụ.");
		emailSender.send(message);
	}

}