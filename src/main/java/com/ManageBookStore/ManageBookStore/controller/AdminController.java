package com.ManageBookStore.ManageBookStore.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ManageBookStore.ManageBookStore.entity.Admin;
import com.ManageBookStore.ManageBookStore.entity.OrderDetail;
import com.ManageBookStore.ManageBookStore.service.AdminService;
import com.ManageBookStore.ManageBookStore.service.OrderDetailAdmService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	private static final Logger log = LoggerFactory.getLogger(AdminController.class);

//	cấu hình trình liên kết dữ liệu được sử dụng để
//	liên kết dữ liệu giữa dữ liệu yêu cầu HTTP và các đối tượng Java
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor ste = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, ste);
	}

	@Autowired
	private AdminService adminService;
	
	@Autowired
	MailSender mailSender;

	@Autowired
	private OrderDetailAdmService orderDetailAdmService;

	@GetMapping(value = {"/", "", "/login"})
	String adminLoginPage(Model model) {
		model.addAttribute("AdminLoginForm", new Admin());
		return "admin";
	}
	
	@GetMapping("/forgot-password") 
	String adminForgootPasswordPage() {	
		return "admin_forgot_pass";
	}
	
	@GetMapping("/home") 
	String adminHomePage(HttpSession session, Model map) {
		String aemail = (String) session.getAttribute("aemail");
		if(aemail == null) {
			return "redirect:/home";
		}
//		định nghĩa một biến chuỗi orderStatusvà gán cho nó giá trị "Pending".
//		Điều này thể hiện trạng thái của các đơn đặt hàng được tìm nạp
		String orderStatus = "Pending";
//		tạo một Pageableđối tượng bằng cách sử dụng PageRequest.of()phương thức, chỉ định số trang (0) và số mục
//		trên mỗi trang (10). Điều này được sử dụng để phân trang trong việc truy xuất đơn đặt hàng.
		Pageable pageable = PageRequest.of(0, 10);

//		ấy danh sách 10 đơn đặt hàng đang chờ xử lý hàng đầu. Nó chuyển các đối tượng orderStatusvà pageabledưới dạng tham số
		List<OrderDetail> pending = orderDetailAdmService.getTop10Orders(orderStatus, pageable);
		//log.info("Pending Orders ::"+pending);
//		thêm pendingdanh sách dưới dạng một thuộc tính có tên "đang chờ xử lý" vào mapđối tượng.
//		Thuộc tính này có thể được truy cập trong chế độ xem để hiển thị
		map.addAttribute("pending", pending);
		return "home";
	}


	@GetMapping("/logout")
	public String AdminLogoutPage(HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		session = request.getSession();
//		đảm bảo rằng phiên của người dùng bị vô hiệu và các tiêu đề cần thiết được đặt để ngăn lưu thông tin vào bộ đệm
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setDateHeader("Expires", 0); // Proxies.
		session.invalidate();
		return "admin_logout";
	}

//	gửi biểu mẫu đăng nhập của quản trị viên
	@PostMapping("/home")
	String AdminLogin(@Valid @ModelAttribute("AdminLoginForm") Admin admin, BindingResult br, Model model,
			HttpSession session) {
		try {
			String email = admin.getEmail();
			String password = admin.getPassword();
			if (email == null && password == null) {
				return "admin";
			}
//			gọi getAdminByEmailphương thức để adminService kiểm tra xem
//			quản trị viên có email đã cho có tồn tại hay không
			Admin emailExists = adminService.getAdminByEmail(email);
			if (emailExists == null) {
				br.rejectValue("email", "error.admin", "This email is not registered.");
				log.info("This email is not registered.");
				return "admin";
			} else {
//				 gọi validateAdminphương thức để adminServicexác thực thông tin đăng nhập của quản trị viên
				admin = adminService.validateAdmin(email, password);
				if (admin != null) {
					String aname = admin.getName();
					session.setAttribute("aname", aname);
					session.setAttribute("id", admin.getId());
					session.setAttribute("aemail", email);
//					xác định trạng thái đơn hàng là "Đang chờ xử lý
					String orderStatus = "Pending";
//					gọi getOrdersByStatusphương thức của orderDetailAdmServiceđể
//					lấy danh sách các lệnh đang chờ xử lý
					List<OrderDetail> pending = orderDetailAdmService.getOrdersByStatus(orderStatus);
					//log.info("Pending Orders ::"+pending);

//					 thêm pendingdanh sách dưới dạng một thuộc tính có tên "đang chờ xử lý" vào modelđối tượng
					model.addAttribute("pending", pending);
					return "home";
				} else {
					br.rejectValue("password", "error.admin", "Password doesn't match.");
					log.info("Invalid Password.");
					return "admin";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/admin";
	}

//	kiểm tra xem quản trị viên có đăng nhập hay không
//	 => hiển thị thông tin tài khoản của quản trị viên
	@GetMapping("/my-account")
	String account(Admin admin, Model map, HttpSession session) {
		String aemail = (String) session.getAttribute("aemail");
		if(aemail == null) {
			return "redirect:/home";
		}
//		truy xuất đối tượng quản trị từ biến adminServicebằng cách sử dụng địa
//		chỉ email của quản trị viên được lưu trữ trong aemailbiến
		admin = adminService.getAdminByEmail(aemail);
		map.addAttribute("name",admin.getName());
		map.addAttribute("email",admin.getEmail());
		return "admin_account";
	}

//	cập nhật thông tin hồ sơ của quản trị
	@PostMapping("/updateProfile")
	String updateAdminProfile(@RequestParam("name") String name, @RequestParam("email") String email,
			final RedirectAttributes redirectAttributes, HttpSession session) {
		
		Long id = (Long) session.getAttribute("id");
		if(id == null) {
			return "redirect:/page404";
		}
		try {
//			gọi updateAdminByEmailphương thức adminServicecập nhật hồ sơ của quản trị viên với các giá trị namevà email
//			giá trị mới, dựa trên ID của quản trị viên
			adminService.updateAdminByEmail(name, email, id);
//			Những dòng này đặt thuộc tính "aname" và "aemail" trong phiên với giá trị tên và email được cập nhật.
			session.removeAttribute("aname");
			session.removeAttribute("email");
			
			session.setAttribute("aname", name);
			session.setAttribute("aemail", email);
			redirectAttributes.addFlashAttribute("success", "success");
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", "error");
		}
		return "redirect:/admin/my-account";
	}

	@GetMapping("/changepassword")
	public String changePassword() {
		return "admin_changepass";
	}


//	thay đổi mật khẩu của quản trị viên
	@PostMapping("/changeAdminPassword")
	ModelAndView changeCustomerPassword(@RequestParam("password") String password,
			@RequestParam("confirm_password") String confirmPassword, HttpServletRequest request, HttpSession session,
			final RedirectAttributes redirectAttributes) {
		session = request.getSession(false);
		String email = (String) session.getAttribute("aemail");
		log.info("email " + email + "password " + password + " confirmPassword " + confirmPassword);
		if (email != null && !email.equals("")) {
			if (password == null || confirmPassword == null) {
				log.info("Email & Password can't be empty.");
				redirectAttributes.addFlashAttribute("error", "error");
				return new ModelAndView("redirect:/admin/changepassword");
			}
			if (!password.equals(confirmPassword)) {
				redirectAttributes.addFlashAttribute("unmatched", "unmatched");
				return new ModelAndView("redirect:/admin/changepassword");
			}
//			truy xuất quản trị viên với email đã cho bằng cách sử dụng the adminServicevà gán nó cho adminExistbiến
			Admin adminExist = adminService.getAdminByEmail(email);
//			kiểm tra xem adminExistbiến có phải là null hay không, cho biết rằng quản trị viên có email đã cho tồn tại
			if (adminExist != null) {
				log.info("Admin Exist True.");

//				gọi changePasswordphương thức adminServicecập nhật mật khẩu của quản trị viên với giá trị mới
//				password, dựa trên email của quản trị viên
				adminService.changePassword(password, email);
				log.info("Update Done...");

//				thêm thuộc tính flash vào redirectAttributesđối tượng với khóa "success" và giá trị "Success".
//				Thuộc tính này cho biết thay đổi mật khẩu thành công
				redirectAttributes.addFlashAttribute("success", "Success");
				return new ModelAndView("redirect:/admin/changepassword");
			} else {
				log.info("Admin Exist False.");
				return new ModelAndView("redirect:/page404");
			}
		} else {
			return new ModelAndView("redirect:/page404");
		}
	}

//	khôi phục mật khẩu đã quên của quản trị viên
	@PostMapping("/forgotPassword")
	public String forgotPassword(@RequestParam(name ="email", required = false) String email, Admin admin, RedirectAttributes rda) {
		log.info("email ::" + email);
		if(email == null) {
//			iểm tra xem emailbiến có phải là null hay không. Nếu nó là null, nó sẽ thêm thuộc tính flash "trống" để cho
//			biết rằng email trống và chuyển hướng đến URL "/admin/quên-mật khẩu".
			log.info("Email is Empty.");
			rda.addFlashAttribute("blank", "blank");
			return "redirect:/admin/forgot-password";
		}
		try {
//			truy xuất quản trị viên với email đã cho
//			bằng cách sử dụng the adminServicevà gán nó cho adminbiến.
			admin = adminService.getAdminByEmail(email);
			if(admin == null) {
				rda.addFlashAttribute("notFound", "authno");
				return "redirect:/admin/forgot-password";
			}
//			tạo một phiên bản mới SimpleMailMessageđể soạn email.
			SimpleMailMessage msg = new SimpleMailMessage();
//			đặt người nhận email đến emailđịa chỉ đã cho
			msg.setTo(email);
//			đặt chủ đề của email.
			msg.setSubject("Dharmesh General Store : Admin Forgot Password");

//			đặt phần thân của email, bao gồm mật khẩu của quản trị viên lấy được từ adminđối tượng.
			msg.setText("Hi Your Password is: " + admin.getPassword());

//			gửi email bằng cách sử dụng mailSenderđối tượng chịu trách nhiệm gửi email.
			mailSender.send(msg);

//			thêm thuộc tính flash vào rdađối tượng với khóa "success" và giá trị "success". Thuộc tính này cho biết khôi phục mật khẩu thành công.
			rda.addFlashAttribute("success", "success");
			return "redirect:/admin/forgot-password";
		} catch (MailException e) {
			e.printStackTrace();
			rda.addFlashAttribute("error", "error");
			return "redirect:/admin/forgot-password";	
		}
	}
}
