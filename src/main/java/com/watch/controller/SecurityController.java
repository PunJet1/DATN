package com.watch.controller;


import com.watch.config.Utility;
import com.watch.dao.AccountDao;
import com.watch.dto.UserDto;
import com.watch.entity.Accounts;
import com.watch.entity.Brand;
import com.watch.entity.Product;
import com.watch.entity.Size;
import com.watch.entity.Strap_material;
import com.watch.entity.UserAcounts;
import com.watch.service.AccountService;
import com.watch.service.BrandService;
import com.watch.service.ProductService;
import com.watch.service.RolesService;
import com.watch.service.SizeService;
import com.watch.service.StrapService;
import com.watch.service.impl.CustomerNotFoundException;
import net.bytebuddy.utility.RandomString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.List;

@Controller
public class SecurityController {
	private static final Logger log = LoggerFactory.getLogger(AccountController.class);

	@InitBinder
	public void intbinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}

	@Autowired
	AccountService accountService;

	@Autowired
	RolesService rolesService;
	@Autowired
	JavaMailSender mailSender;
	@Autowired
	JavaMailSenderImpl mailSendeImpl;
	@Autowired
    UserAcounts useAcc;

	@Autowired
	AccountDao accountDao;
	
	@Autowired 
    HttpSession session;
	@Autowired
	SizeService sizeSV;
	@Autowired
	StrapService strapSv;
	@Autowired
	BrandService brandService;
	
	@Autowired
	ProductService productSV;

	// Đăng Nhập
	@RequestMapping("/auth/login/form")
	public String loginForm() {
		return "/user/login/dangNhap";
	}

	@RequestMapping("/auth/login/success")
	public String loginSuccess(Model model,HttpServletRequest request) {
		
		Accounts acount = useAcc.User();
		if(acount!=null) {
			if(acount.isStatus() == true) {
				return"redirect:/itbalo";
			}else {
				return"redirect:/login";
			}
		}
		model.addAttribute("message", "Đăng nhập thành công");
		return "redirect:/itbalo";
		 //return "/user/login/test";
	}

	@RequestMapping("/auth/login/unauthoried")
	public String unauthoried(Model model) {
		model.addAttribute("message", "Không có quyền truy xuất!");
		return "/user/login/dangNhap";
	}

	@RequestMapping("/auth/login/error")
	public String loginError(Model model) {
		model.addAttribute("message", "Sai thông tin đăng nhập!");
		return "/user/login/dangNhap";
	}

	// đăng nhập fb gg
	@RequestMapping("/oauth2/login/success")
	public String Success(OAuth2AuthenticationToken oauth2) {
		accountService.loginFormOAuth2(oauth2);
		return "forward:/auth/login/success";
	}

	// Đăng Ký
	@GetMapping("/register")
	public String register(UserDto userDto, Model model) {
		model.addAttribute("userDTO", userDto);
		List<Strap_material> straps = strapSv.findAll();
		model.addAttribute("straps", straps);
		List<Size> sizes = sizeSV.findAll();
		model.addAttribute("sizes",sizes);
		
		List<Brand> listBrand = brandService.findAll();
		model.addAttribute("brands", listBrand);
		return "/user/login/dangKyTK";
	}

	@PostMapping("/register")
	public String save(@Valid @ModelAttribute("userDTO") UserDto userDto, BindingResult bindingResult,
			RedirectAttributes ra, HttpServletRequest request, Model model) {
		List<Strap_material> straps = strapSv.findAll();
		model.addAttribute("straps", straps);
		List<Size> sizes = sizeSV.findAll();
		model.addAttribute("sizes",sizes);
		
		List<Brand> listBrand = brandService.findAll();
		model.addAttribute("brands", listBrand);
		if (accountService.existsByUsername(userDto.getUsername())) {
			bindingResult.addError(new FieldError("userDTO", "username", "Tài khoản trùng với tài khoản đã đăng ký!!!"));
		}
		if (accountService.existsByEmail(userDto.getEmail())) {
			bindingResult.addError(new FieldError("userDTO", "email", "Email trùng với Email đã đăng ký!!!"));
		}
		if (userDto.getPassword() != null && userDto.getRpassword() != null) {
			if (!userDto.getPassword().equals(userDto.getRpassword())) {
				bindingResult.addError(new FieldError("userDTO", "rpassword", "Mật khẩu mới không trùng khớp"));
			}
		}

		if (bindingResult.hasErrors()) {
			return "user/login/dangKyTK";
		}
//        log.info(">>UseDto : {}", userDto.toString());
		ra.addFlashAttribute("message", "Đăng ký thành công");
		//gửi mail đăng ký

		String email = request.getParameter("email");
		System.out.println("Email :" + email);
		try {
			sendEmailRegister(email);
		} catch (UnsupportedEncodingException | MessagingException e) {
			// TODO: handle exception
			model.addAttribute("error", "Lỗi khi gửi email");
		}
		accountService.register(userDto);

		return "redirect:/auth/login/form";
	}

	public void sendEmailRegister(String recipientEmail) throws MessagingException, UnsupportedEncodingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message,"UTF-8");

		helper.setFrom("khoalnapc03346@fpt.edu.vn", "Đăng Ký Tài Khoản Thành Công");
		helper.setTo(recipientEmail);

		String subject = "Đây là mail xác nhận đăng ký tài khoản của bạn đã thành công";

		String content = "<p>Chào bạn,</p>" + "<p>Bạn đã đăng ký thành công tài khoản của mình.</p>"
				+ "<p>Cảm ơn bạn đã tin tưởng ITBalo.COM, "
				+ "hãy đăng nhập và đặt mua chiếc balo mà bạn thích ngay nào!!!</p>";

		helper.setSubject(subject);

		helper.setText(content, true);

		mailSender.send(message);
	}

	// Quên mật khẩu
	@GetMapping("/forgot_password")
	public String showForgotPasswordForm() {
		return "/user/quenMK";
	}

	@PostMapping("/forgot_password")
	public String processForgotPassword(HttpServletRequest request, Model model) {
		List<Strap_material> straps = strapSv.findAll();
		model.addAttribute("straps", straps);
		List<Size> sizes = sizeSV.findAll();
		model.addAttribute("sizes",sizes);
		
		List<Brand> listBrand = brandService.findAll();
		model.addAttribute("brands", listBrand);
		String email = request.getParameter("email");
		String token = RandomString.make(30);
		System.out.println("Email" + email);
		System.out.println("Token" + token);

		try {
			accountService.updateResetPasswordToken(token, email);
			String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;
			sendEmail(email, resetPasswordLink);
			model.addAttribute("message",
					"Chúng tôi đã gửi một liên kết đặt lại mật khẩu đến email của bạn. Hãy kiểm tra.");

		} catch (CustomerNotFoundException ex) {
			model.addAttribute("error", ex.getMessage());
		} catch (UnsupportedEncodingException | MessagingException e) {
			model.addAttribute("error", "Lỗi khi gửi email");
		}

		return "/user/quenMK";
	}

	public void sendEmail(String recipientEmail, String link) throws MessagingException, UnsupportedEncodingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message,"UTF-8");
		helper.setFrom("khoalnapc03346@fpt.edu.vn", "khoalnapc03346@fpt.edu.vn");
		helper.setTo(recipientEmail);

		String subject = "Đây là liên kết để đặt lại mật khẩu của bạn";

		String content = "<p>Xin chào!,</p>" + "<p>Bạn đã yêu cầu đặt lại mật khẩu của mình.</p>"
				+ "<p>Nhấp vào liên kết bên dưới để thay đổi mật khẩu của bạn:</p>" + "<p><a href=\"" + link
				+ "\">Thay đổi mật khẩu của bạn</a></p>" + "<br>"
				+ "<p>Hãy bỏ qua email này nếu bạn nhớ mật khẩu của mình, "
				+ "hoặc nếu bạn đã không thực hiện yêu cầu này.</p>";
	
		helper.setSubject(subject);

		helper.setText(content, true);
		mailSender.send(message);
	}

	@GetMapping("/reset_password")
	public String showResetPasswordForm(@Param(value = "token") String token, Model model) {
		Accounts accounts = accountService.getByResetPasswordToken(token);
		model.addAttribute("token", token);
		List<Strap_material> straps = strapSv.findAll();
		model.addAttribute("straps", straps);
		List<Size> sizes = sizeSV.findAll();
		model.addAttribute("sizes",sizes);
		
		List<Brand> listBrand = brandService.findAll();
		model.addAttribute("brands", listBrand);

		if (accounts == null) {
			model.addAttribute("message", "Mã không hợp lệ");
			return "user/reset_password_form";
		}

		return "user/reset_password_form";
	}

	@PostMapping("/reset_password")
	public String processResetPassword(HttpServletRequest request, Model model) {
		String token = request.getParameter("token");
		String password = request.getParameter("password");

		Accounts accounts = accountService.getByResetPasswordToken(token);
//        model.addAttribute("title", "Reset your password");

		if (accounts == null) {
			model.addAttribute("message", "Mã không hợp lệ");
			return "user/reset_password_form";
		} else {
			accountService.updatePassword(accounts, password);

			model.addAttribute("message", "Bạn đã thay đổi thành công mật khẩu của bạn.");
		}

		return "user/reset_password_form";
	}

	// Đổi mật khẩu
	@GetMapping("/change_password")
	public String showChangePassword(Model model) {
		List<Strap_material> straps = strapSv.findAll();
		model.addAttribute("straps", straps);
		List<Size> sizes = sizeSV.findAll();
		model.addAttribute("sizes",sizes);
		
		List<Brand> listBrand = brandService.findAll();
		model.addAttribute("brands", listBrand);
		return "/user/account/doiMatKhau";
	}

	@PostMapping("/change_password")
	public String changePassword(@RequestParam("oldPassword") String oldPassword,
			@RequestParam("password") String password, Principal principal, Model model, RedirectAttributes ra) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		String username = principal.getName();
		Accounts currentUser = accountDao.findUserByUsername(username);
		List<Strap_material> straps = strapSv.findAll();
		model.addAttribute("straps", straps);
		List<Size> sizes = sizeSV.findAll();
		model.addAttribute("sizes",sizes);
		
		List<Brand> listBrand = brandService.findAll();
		model.addAttribute("brands", listBrand);
		if (bCryptPasswordEncoder.matches(oldPassword, currentUser.getPassword())) {
			// change
			currentUser.setPassword(bCryptPasswordEncoder.encode(password));
			accountService.save(currentUser);

		} else {
			// error
			model.addAttribute("message", "Vui lòng điền đúng mật khẩu");
			return "/user/account/doiMatKhau";
		}
		model.addAttribute("message", "Đổi mật khẩu thành công");
		// ra.addFlashAttribute("message", "Đổi mật khẩu thành công");
		// return "redirect:/auth/login/form";
		return "/user/account/doiMatKhau";
	}
	
	@RequestMapping(value = "/403", method = RequestMethod.GET)
    public String accessDenied(Model model, Principal principal) {
		List<Strap_material> straps = strapSv.findAll();
		model.addAttribute("straps", straps);
		List<Size> sizes = sizeSV.findAll();
		model.addAttribute("sizes",sizes);
		
		List<Brand> listBrand = brandService.findAll();
		model.addAttribute("brands", listBrand);
        return "/403Page";
    }

}