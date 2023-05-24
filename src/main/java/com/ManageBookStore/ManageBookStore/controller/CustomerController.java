package com.ManageBookStore.ManageBookStore.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

import com.ManageBookStore.ManageBookStore.entity.Customer;
import com.ManageBookStore.ManageBookStore.service.CustomerService;

@Controller
@RequestMapping("/customer") // Duong dan link
public class CustomerController {

//	Ghi nhật ký bằng cách sử dụng khung công tác ghi nhật ký SLF4J
//	log.info("Message")để ghi thông báo thông tin,
//	log.error("Error message", exception)ghi thông báo lỗi cùng với một ngoại lệ

	@Autowired
	private MessageSource messageSource;

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	// @InitBinder - pre process every string data.
	// removes the leading & trailing white spaces.
	// If string only has white space .... trim it to null.

//	Cấu hình thời gian hết hạn cho chức năng liên quan đến email (chẳng hạn như hết hạn mã thông báo) => 24h
//	=> Doi mat khau
	@Value("${email.expire.time}")
	private long timeInHours;

//	Cắt bỏ khoảng trắng khỏi String các trường trong yêu cầu gửi đến, đảm bảo rằng các chuỗi trống được coi là null.
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor ste = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, ste);
	}

//	Lop Xu ly ( lien quan toi cac xu ly bang Customer )
	@Autowired
	private CustomerService customerService;

	// Inject BCryptPasswordEncoder for encoding password, default length is 60.+
//	=> Ma hoa mat khau
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

//	 truy cập vào môi trường của ứng dụng và
//	 truy xuất các thuộc tính hoặc thực hiện các thao tác khác liên quan đến môi trường trong lớp
	@Autowired
	private Environment env;

	@GetMapping("/login")
	public String pageLogin(Customer customer, Model model) {
		model.addAttribute("customerLoginForm", new Customer());

		String login_Password = messageSource.getMessage("login_Password", null, LocaleContextHolder.getLocale());
		model.addAttribute("login_Password", login_Password); 	String contact_Subject = messageSource.getMessage("contact_Subject", null, LocaleContextHolder.getLocale());
		String login_Login = messageSource.getMessage("login_Login", null, LocaleContextHolder.getLocale());
		model.addAttribute("login_Login", login_Login);
		String login_Register = messageSource.getMessage("login_Register", null, LocaleContextHolder.getLocale());
		model.addAttribute("login_Register", login_Register);
		String login_ForgotPassword = messageSource.getMessage("login_ForgotPassword", null, LocaleContextHolder.getLocale());
		model.addAttribute("login_ForgotPassword", login_ForgotPassword);

		return "login";
	}

	@GetMapping("/register")
	public String pageRegister(Customer customer, Model model) {
		model.addAttribute("customerForm", new Customer());

		String register_YourName = messageSource.getMessage("register_YourName", null, LocaleContextHolder.getLocale());
		model.addAttribute("register_YourName", register_YourName); 	String contact_Subject = messageSource.getMessage("contact_Subject", null, LocaleContextHolder.getLocale());
		String register_Password = messageSource.getMessage("register_Password", null, LocaleContextHolder.getLocale());
		model.addAttribute("register_Password", register_Password);
		String register_Phone = messageSource.getMessage("register_Phone", null, LocaleContextHolder.getLocale());
		model.addAttribute("register_Phone", register_Phone);
		String register_Gender = messageSource.getMessage("register_Gender", null, LocaleContextHolder.getLocale());
		model.addAttribute("register_Gender", register_Gender);

		String register_SelectGender = messageSource.getMessage("register_SelectGender", null, LocaleContextHolder.getLocale());
		model.addAttribute("register_SelectGender", register_SelectGender);
		String register_Male = messageSource.getMessage("register_Male", null, LocaleContextHolder.getLocale());
		model.addAttribute("register_Male", register_Male);
		String register_Female = messageSource.getMessage("register_Female", null, LocaleContextHolder.getLocale());
		model.addAttribute("register_Female", register_Female);
		String register_PinCode = messageSource.getMessage("register_PinCode", null, LocaleContextHolder.getLocale());
		model.addAttribute("register_PinCode", register_PinCode);

		String register_Address = messageSource.getMessage("register_Address", null, LocaleContextHolder.getLocale());
		model.addAttribute("register_Address", register_Address);
		String register_Register = messageSource.getMessage("register_Register", null, LocaleContextHolder.getLocale());
		model.addAttribute("register_Register", register_Register);


		return "register";
	}

//	Đối tượng ModelAndView đóng gói cả dữ liệu mô hình và thông tin dạng xem. Nó cung cấp các phương thức để thiết lập
//	và truy xuất dữ liệu mô hình và tên khung nhìn.
	@GetMapping("/forgot-password")
	public ModelAndView CustomerForgotPasswordPage(Customer Customer) {
		return new ModelAndView("forgot-password", "forgot-password", Customer);
	}

	@GetMapping("/changepassword")
	public ModelAndView CustomerChangePassword() {
		return new ModelAndView("changepassword");
	}

	@GetMapping(value = { "/change-password/", "/change-password" })
	public ModelAndView CustomerChangePasswordPage(@RequestParam("email") String encodedEmail,
			@RequestParam("token") String token, @RequestParam("data") String data) {
		log.info("In change-password ...");
		if (encodedEmail == null || token == null || data == null) {
			return new ModelAndView("redirect:/page404"); // Hien thi trang loi
		}
//		Giải mã encodedEmail chuỗi từ mã hóa Base64 thành một mảng byte
		byte[] decodedBytes = Base64.getDecoder().decode(encodedEmail);

//		Chuyển đổi mảng byte đã giải mã thành biểu diễn Chuỗi của email.
		String email = new String(decodedBytes);

//		truy xuất khách hàng bằng email đã cho bằng cách sử dụng tệp customerService
		Customer emailExist = customerService.findCustomerByEmail(email);  // Lay khach hang co mail
		if (emailExist == null) {
			return new ModelAndView("redirect:/page404");
		} else {
//			 Giải mã datachuỗi từ mã hóa Base64 thành một mảng byte.
			byte[] decodedTime = Base64.getDecoder().decode(data);
//			Chuyển đổi mảng byte được giải mã thành chuỗi biểu diễn thời gian
			String time = new String(decodedTime);
			Date todayDateTime = new Date();
//			Tạo một SimpleDateFormatđối tượng với định dạng ngày được chỉ định
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			Định dạng ngày và giờ hiện tại thành một chuỗi sử dụng định dạng ngày đã chỉ định
			String dataTime = sdf.format(todayDateTime);
			log.debug("time = " + time + " emailTime = " + time + " dataTime = " + dataTime);
			Date d1 = null;
			Date d2 = null;
			try {
//				Phân tích cú pháp timevà dataTimechuỗi thành Datecác đối tượng sử dụng định dạng ngày đã chỉ định
				d1 = sdf.parse(time);
				d2 = sdf.parse(dataTime);
			} catch (ParseException e) {
				e.printStackTrace();
				return new ModelAndView("redirect:/page404");
			}
//			Tính chênh lệch tính bằng mili giây giữa d2và d1
			long diff = d2.getTime() - d1.getTime();
//			Tính chênh lệch về số giờ bằng cách chia chênh lệch tính bằng mili giây cho số mili giây trong một giờ
			long diffHours = diff / (60 * 60 * 1000);
			log.debug("dataTime = " + d1 + " currTime = " + d2 + " Diff=" + diff + " emailExpireTime=" + timeInHours
					+ " diffHours=" + diffHours);
			if (diffHours >= timeInHours) {
				log.info("Time expired.");
//				Kiểm tra xem chênh lệch về số giờ có lớn hơn hoặc bằng ngưỡng được xác định trước (timeInHours)
//				hay không. Nếu vậy, điều đó có nghĩa là thời hạn đã hết
				return new ModelAndView("redirect:/page404");
			}
		}
		return new ModelAndView("change-password");
	}

//	Dang xuat
	@GetMapping("/logout")
	public String CustomerLogoutPage(Customer customer, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		session = request.getSession();
//		phản hồi không được lưu vào bộ nhớ cache
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
//		chỉ định rằng phản hồi không được lưu vào bộ đệm.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
//		tiêu đề chỉ định ngày/thời gian hết hạn của phản hồi.
		response.setDateHeader("Expires", 0); // Proxies.
//		session.invalidate() => làm mất hiệu lực phiên hiện tại, xóa mọi dữ liệu phiên được liên kết.
		session.invalidate();
		return "logout";
	}

	@PostMapping("/saveCustomer")
	String createCustomer(@Valid @ModelAttribute("customerForm") Customer customer, BindingResult br, Model model,
			HttpSession session) {
		try {
//			br => Thong bao neu co the nao dien sai thong tin => Ve trang dang ki
			if (br.hasErrors()) {
				log.info("BindingResult Found an error.");

				String register_YourName = messageSource.getMessage("register_YourName", null, LocaleContextHolder.getLocale());
				model.addAttribute("register_YourName", register_YourName); 	String contact_Subject = messageSource.getMessage("contact_Subject", null, LocaleContextHolder.getLocale());
				String register_Password = messageSource.getMessage("register_Password", null, LocaleContextHolder.getLocale());
				model.addAttribute("register_Password", register_Password);
				String register_Phone = messageSource.getMessage("register_Phone", null, LocaleContextHolder.getLocale());
				model.addAttribute("register_Phone", register_Phone);
				String register_Gender = messageSource.getMessage("register_Gender", null, LocaleContextHolder.getLocale());
				model.addAttribute("register_Gender", register_Gender);

				String register_SelectGender = messageSource.getMessage("register_SelectGender", null, LocaleContextHolder.getLocale());
				model.addAttribute("register_SelectGender", register_SelectGender);

				String register_Male = messageSource.getMessage("register_Male", null, LocaleContextHolder.getLocale());
				model.addAttribute("register_Male", register_Male);
				String register_Female = messageSource.getMessage("register_Female", null, LocaleContextHolder.getLocale());
				model.addAttribute("register_Female", register_Female);
				String register_PinCode = messageSource.getMessage("register_PinCode", null, LocaleContextHolder.getLocale());
				model.addAttribute("register_PinCode", register_PinCode);

				String register_Address = messageSource.getMessage("register_Address", null, LocaleContextHolder.getLocale());
				model.addAttribute("register_Address", register_Address);
				String register_Register = messageSource.getMessage("register_Register", null, LocaleContextHolder.getLocale());
				model.addAttribute("register_Register", register_Register);

				return "register";
			} else {
//				Lấy thông tin của khách hàng (email, tên, địa chỉ, điện thoại, mật khẩu, mã pin) từ đối tượng customer
				String email = customer.getEmail();
				String name = customer.getName();
				String address = customer.getAddress();
				String phone = customer.getPhone();
				String password = customer.getPassword();
				String pinCode = customer.getPinCode();
//				 Mã hóa mật khẩu bằng một bCryptPasswordEncoderđối tượng
				String encryptedPassword = bCryptPasswordEncoder.encode(password); // Ma hoa mat khau
//				Đặt mật khẩu được mã hóa trong đối tượng khách hàng
				customer.setPassword(encryptedPassword);
//				Đặt ngày tạo của khách hàng là ngày hiện tại.
				customer.setCreateDate(new Date());
//				Đặt tính hợp lệ của khách hàng thành true.
				customer.setValid(true);

//				Kiểm tra xem một khách hàng với email đã cho đã tồn tại hay chưa bằng cách gọi phương findCustomerByEmailthức trong tệp customerService
				Customer emailExists = customerService.findCustomerByEmail(email);
				// Tim kiem khach hang theo email
//				Kiểm tra xem một khách hàng với số điện thoại đã cho đã tồn tại hay chưa bằng cách gọi phương findCustomerByPhonethức trong tệp customerService
				Customer phoneExists = customerService.findCustomerByPhone(phone); // Tim kiem khach hang theo sdt
				if (emailExists != null) { // Mail ton tai => Thong bao loi
					br.rejectValue("email", "error.customer", "This email already exists!");
					log.info("This email already exists!");

					String register_YourName = messageSource.getMessage("register_YourName", null, LocaleContextHolder.getLocale());
					model.addAttribute("register_YourName", register_YourName); 	String contact_Subject = messageSource.getMessage("contact_Subject", null, LocaleContextHolder.getLocale());
					String register_Password = messageSource.getMessage("register_Password", null, LocaleContextHolder.getLocale());
					model.addAttribute("register_Password", register_Password);
					String register_Phone = messageSource.getMessage("register_Phone", null, LocaleContextHolder.getLocale());
					model.addAttribute("register_Phone", register_Phone);
					String register_Gender = messageSource.getMessage("register_Gender", null, LocaleContextHolder.getLocale());
					model.addAttribute("register_Gender", register_Gender);

					String register_SelectGender = messageSource.getMessage("register_SelectGender", null, LocaleContextHolder.getLocale());
					model.addAttribute("register_SelectGender", register_SelectGender);
					String register_Male = messageSource.getMessage("register_Male", null, LocaleContextHolder.getLocale());
					model.addAttribute("register_Male", register_Male);
					String register_Female = messageSource.getMessage("register_Female", null, LocaleContextHolder.getLocale());
					model.addAttribute("register_Female", register_Female);
					String register_PinCode = messageSource.getMessage("register_PinCode", null, LocaleContextHolder.getLocale());
					model.addAttribute("register_PinCode", register_PinCode);

					String register_Address = messageSource.getMessage("register_Address", null, LocaleContextHolder.getLocale());
					model.addAttribute("register_Address", register_Address);
					String register_Register = messageSource.getMessage("register_Register", null, LocaleContextHolder.getLocale());
					model.addAttribute("register_Register", register_Register);

					return "register";
				} else if (phoneExists != null) {
					br.rejectValue("phone", "error.phone", "This phone already exists!");
					log.info("This phone already exists!");

					String register_YourName = messageSource.getMessage("register_YourName", null, LocaleContextHolder.getLocale());
					model.addAttribute("register_YourName", register_YourName); 	String contact_Subject = messageSource.getMessage("contact_Subject", null, LocaleContextHolder.getLocale());
					String register_Password = messageSource.getMessage("register_Password", null, LocaleContextHolder.getLocale());
					model.addAttribute("register_Password", register_Password);
					String register_Phone = messageSource.getMessage("register_Phone", null, LocaleContextHolder.getLocale());
					model.addAttribute("register_Phone", register_Phone);
					String register_Gender = messageSource.getMessage("register_Gender", null, LocaleContextHolder.getLocale());
					model.addAttribute("register_Gender", register_Gender);

					String register_SelectGender = messageSource.getMessage("register_SelectGender", null, LocaleContextHolder.getLocale());
					model.addAttribute("register_SelectGender", register_SelectGender);
					String register_Male = messageSource.getMessage("register_Male", null, LocaleContextHolder.getLocale());
					model.addAttribute("register_Male", register_Male);
					String register_Female = messageSource.getMessage("register_Female", null, LocaleContextHolder.getLocale());
					model.addAttribute("register_Female", register_Female);
					String register_PinCode = messageSource.getMessage("register_PinCode", null, LocaleContextHolder.getLocale());
					model.addAttribute("register_PinCode", register_PinCode);

					String register_Address = messageSource.getMessage("register_Address", null, LocaleContextHolder.getLocale());
					model.addAttribute("register_Address", register_Address);
					String register_Register = messageSource.getMessage("register_Register", null, LocaleContextHolder.getLocale());
					model.addAttribute("register_Register", register_Register);

					return "register";
				} else {
					log.info("in else, Saving Customer.");
//					Lưu thông tin của khách hàng
					customerService.saveCustomer(customer);
//					Kiểm tra xem độ dài của tên khách hàng có lớn hơn 15 ký tự hay không. Nếu vậy, nó sẽ chia tên
//					thành một mảng bằng cách sử dụng khoảng trắng làm dấu phân cách và lưu tên trong thuộc tính phiên "tên"
					if(name.length() > 15) {
						String[] names = name.split(" ");
						session.setAttribute("name", names[0]);
					} else {
						session.setAttribute("name", name);
					}
//					lưu trữ điện thoại, email, địa chỉ và mã pin của khách hàng
					session.setAttribute("phone", phone);
					session.setAttribute("email", email);
					session.setAttribute("address", address);
					session.setAttribute("pinCode", pinCode);
//					Dang nhap thanh cong ve trang home
					return "redirect:/home";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
//			Sai thong tin dang nhap => Ve trang dang ki

			String register_YourName = messageSource.getMessage("register_YourName", null, LocaleContextHolder.getLocale());
			model.addAttribute("register_YourName", register_YourName); 	String contact_Subject = messageSource.getMessage("contact_Subject", null, LocaleContextHolder.getLocale());
			String register_Password = messageSource.getMessage("register_Password", null, LocaleContextHolder.getLocale());
			model.addAttribute("register_Password", register_Password);
			String register_Phone = messageSource.getMessage("register_Phone", null, LocaleContextHolder.getLocale());
			model.addAttribute("register_Phone", register_Phone);
			String register_Gender = messageSource.getMessage("register_Gender", null, LocaleContextHolder.getLocale());
			model.addAttribute("register_Gender", register_Gender);

			String register_SelectGender = messageSource.getMessage("register_SelectGender", null, LocaleContextHolder.getLocale());
			model.addAttribute("register_SelectGender", register_SelectGender);
			String register_Male = messageSource.getMessage("register_Male", null, LocaleContextHolder.getLocale());
			model.addAttribute("register_Male", register_Male);
			String register_Female = messageSource.getMessage("register_Female", null, LocaleContextHolder.getLocale());
			model.addAttribute("register_Female", register_Female);
			String register_PinCode = messageSource.getMessage("register_PinCode", null, LocaleContextHolder.getLocale());
			model.addAttribute("register_PinCode", register_PinCode);

			String register_Address = messageSource.getMessage("register_Address", null, LocaleContextHolder.getLocale());
			model.addAttribute("register_Address", register_Address);
			String register_Register = messageSource.getMessage("register_Register", null, LocaleContextHolder.getLocale());
			model.addAttribute("register_Register", register_Register);

			return "register";
		}
	}

	@PostMapping("/loginCustomer")
	String validateCustomer(@Valid @ModelAttribute("customerLoginForm") Customer customer, BindingResult br,
			Model model, HttpSession session) {
		try {
//lấy email do khách hàng nhập từ customer đối tượng
			String email = customer.getEmail();
			String rawPassword = customer.getPassword();
//			gọi một phương thức dịch vụ findCustomerByEmailđể kiểm tra xem khách hàng
//			có email đã cho có tồn tại trong hệ thống hay không
			Customer emailExists = customerService.findCustomerByEmail(email);
			if (emailExists == null) {
//				Neu chua dang ki => Thong bao email chua duoc dang ki
				br.rejectValue("email", "error.customer", "This email is not registered.");
				log.info("This email is not registered.");

				String login_Password = messageSource.getMessage("login_Password", null, LocaleContextHolder.getLocale());
				model.addAttribute("login_Password", login_Password); 	String contact_Subject = messageSource.getMessage("contact_Subject", null, LocaleContextHolder.getLocale());
				String login_Login = messageSource.getMessage("login_Login", null, LocaleContextHolder.getLocale());
				model.addAttribute("login_Login", login_Login);
				String login_Register = messageSource.getMessage("login_Register", null, LocaleContextHolder.getLocale());
				model.addAttribute("login_Register", login_Register);
				String login_ForgotPassword = messageSource.getMessage("login_ForgotPassword", null, LocaleContextHolder.getLocale());
				model.addAttribute("login_ForgotPassword", login_ForgotPassword);


				return "login";
			} else {
				String encodedPassword = emailExists.getPassword(); // customerService.findCustomerPassword(email);
//				Giai ma Password
				boolean checkPassStatus = bCryptPasswordEncoder.matches(rawPassword, emailExists.getPassword());
				if (checkPassStatus) {
//					Kiem tra dang nhap
					boolean status = customerService.loginCustomer(email, encodedPassword);
					if (status) {
						String name = emailExists.getName();						
						String address = emailExists.getAddress();
						String phone = emailExists.getPhone();
						String pinCode = emailExists.getPinCode();
						log.info("in else, loging Customer.");
						if(name.length() > 15) {
							String[] names = name.split(" ");
							session.setAttribute("name", names[0]);
						} else {
							session.setAttribute("name", name);
						}
						session.setAttribute("phone", phone);
						session.setAttribute("email", email);
						session.setAttribute("address", address);
						session.setAttribute("pinCode", pinCode);
						String url = (String) session.getAttribute("backUrl");
						log.info("in Login, backUrl :: "+url);
						if (url != null) {
							if (url.contains("8081")) {
								log.info("In 8081");
								String[] newUrl = url.split("8081");
								String backUrl = newUrl[1];
								log.info("Back Url: " + backUrl);
								session.removeAttribute("url");
								return "redirect:" + backUrl;
							} else if (url.contains(".com")) {
								log.info("In Domain");
								String[] newUrl = url.split(".com");
								String backUrl = newUrl[1];
								log.info("Back Url: " + backUrl);
								session.removeAttribute("url");
								return "redirect:" + backUrl;
							} else {
								log.info("In else, No Back Url");
								session.removeAttribute("url");
								return "redirect:/home";
							}
						}
						return "redirect:/home";
					} else {
						br.rejectValue("password", "error.customer", "Password mismatch.");

						String login_Password = messageSource.getMessage("login_Password", null, LocaleContextHolder.getLocale());
						model.addAttribute("login_Password", login_Password); 	String contact_Subject = messageSource.getMessage("contact_Subject", null, LocaleContextHolder.getLocale());
						String login_Login = messageSource.getMessage("login_Login", null, LocaleContextHolder.getLocale());
						model.addAttribute("login_Login", login_Login);
						String login_Register = messageSource.getMessage("login_Register", null, LocaleContextHolder.getLocale());
						model.addAttribute("login_Register", login_Register);
						String login_ForgotPassword = messageSource.getMessage("login_ForgotPassword", null, LocaleContextHolder.getLocale());
						model.addAttribute("login_ForgotPassword", login_ForgotPassword);


						return "login";
					}
				} else {
					br.rejectValue("password", "error.customer", "Password doesn't match.");

					String login_Password = messageSource.getMessage("login_Password", null, LocaleContextHolder.getLocale());
					model.addAttribute("login_Password", login_Password); 	String contact_Subject = messageSource.getMessage("contact_Subject", null, LocaleContextHolder.getLocale());
					String login_Login = messageSource.getMessage("login_Login", null, LocaleContextHolder.getLocale());
					model.addAttribute("login_Login", login_Login);
					String login_Register = messageSource.getMessage("login_Register", null, LocaleContextHolder.getLocale());
					model.addAttribute("login_Register", login_Register);
					String login_ForgotPassword = messageSource.getMessage("login_ForgotPassword", null, LocaleContextHolder.getLocale());
					model.addAttribute("login_ForgotPassword", login_ForgotPassword);


					return "login";
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());

			String login_Password = messageSource.getMessage("login_Password", null, LocaleContextHolder.getLocale());
			model.addAttribute("login_Password", login_Password); 	String contact_Subject = messageSource.getMessage("contact_Subject", null, LocaleContextHolder.getLocale());
			String login_Login = messageSource.getMessage("login_Login", null, LocaleContextHolder.getLocale());
			model.addAttribute("login_Login", login_Login);
			String login_Register = messageSource.getMessage("login_Register", null, LocaleContextHolder.getLocale());
			model.addAttribute("login_Register", login_Register);
			String login_ForgotPassword = messageSource.getMessage("login_ForgotPassword", null, LocaleContextHolder.getLocale());
			model.addAttribute("login_ForgotPassword", login_ForgotPassword);


			return "login";
		}
	}

	@PostMapping("/forgotPassword")
	public ModelAndView forgotPassword(@Valid @ModelAttribute("forgot-password") Customer Customer,
			BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
		if (Customer.getEmail() == null) {
			log.info("in br, Email can't be empty.");
			return new ModelAndView("forgot-password");
		}
		String email = Customer.getEmail();
		log.info("Customer Email = " + email);

//		Nguoi dung co nhap thong tin Email
		Customer CustomerExist = customerService.findCustomerByEmail(email);
		if (CustomerExist != null) {
			log.info("Customer Exist True.");
			try {
//				Thuc hien gui mail
				customerService.sendMail(CustomerExist);
				log.info("Sending Done...");
//				Thong bao thanh cong
				redirectAttributes.addFlashAttribute("success", "Success");
			} catch (Exception e) {
				redirectAttributes.addFlashAttribute("error", "Error");
				log.debug("Exception while sending email = " + e);
			}
			return new ModelAndView("redirect:/customer/forgot-password");
		} else {
			log.info("Customer Exist False.");
			bindingResult.rejectValue("email", "error.Customer", "Could not find a Customer with this email.");
			return new ModelAndView("forgot-password");
		}
	}

	@PostMapping("/changePassword")
	ModelAndView changePassword(@RequestParam("email") String encodedEmail, @RequestParam("password") String password,
			@RequestParam("confirm-password") String confirmPassword, @RequestParam("token") String token,
			@RequestParam("data") String data, final RedirectAttributes redirectAttributes) {
		if (encodedEmail == null || token == null || data == null || password == null || confirmPassword == null) {
			return new ModelAndView("redirect:/page404");
		}
		byte[] decodedBytes = Base64.getDecoder().decode(encodedEmail);
		String email = new String(decodedBytes);
		byte[] decodedTime = Base64.getDecoder().decode(data);
		String time = new String(decodedTime);
		Date todayDateTime = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime = sdf.format(todayDateTime);
		log.debug("decodedTime = " + time + " currentTime = " + currentTime);
		Date d1 = null;
		Date d2 = null;
		try {
			d1 = sdf.parse(time);
			d2 = sdf.parse(currentTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long diff = d2.getTime() - d1.getTime();
		long diffHours = diff / (60 * 60 * 1000);
		long hours = Long.valueOf(diffHours);
		log.debug("dataTime = " + d1 + " currTime = " + d2 + " Diff=" + diff + " emailExpireTime=" + timeInHours
				+ " diffHours=" + hours);
		if (hours >= timeInHours) {
			log.info("Time expired.");
			return new ModelAndView("redirect:/page404");
		}
		String emailUrl = env.getProperty("email-call-back-url");
		log.info("encode email " + encodedEmail);
		log.info("decoded email " + email + "emailUrl " + emailUrl + " token " + token + " " + data);
		if (email != null && !email.equals("")) {
			if (password == null || confirmPassword == null) {
				log.info("Email & Password can't be empty.");
				redirectAttributes.addFlashAttribute("error", "error");
				return new ModelAndView(
						"redirect:" + emailUrl + "?email=" + encodedEmail + "&token=" + token + "&data=" + data + "");
			}
			if (!password.equals(confirmPassword)) {
				redirectAttributes.addFlashAttribute("unmatched", "unmatched");
				return new ModelAndView(
						"redirect:" + emailUrl + "?email=" + encodedEmail + "&token=" + token + "&data=" + data + "");
			}
			Customer customerExist = customerService.findCustomerByEmail(email);
			if (customerExist != null) {
				log.info("Customer Exist True.");
				log.info("Time okay.");
				String encryptedPassword = bCryptPasswordEncoder.encode(password);
				customerService.updatePassword(email, encryptedPassword);
				log.info("Update Done...");
				redirectAttributes.addFlashAttribute("success", "Success");
				return new ModelAndView(
						"redirect:" + emailUrl + "?email=" + encodedEmail + "&token=" + token + "&data=" + data + "");
			} else {
				log.info("Customer Exist False.");
				redirectAttributes.addFlashAttribute("notFound", "notFound");
				return new ModelAndView(
						"redirect:" + emailUrl + "?email=" + encodedEmail + "&token=" + token + "&data=" + data + "");
			}
		} else {
			return new ModelAndView("redirect:/page404");
		}
	}

	@PostMapping("/changeCustomerPassword")
	ModelAndView changeCustomerPassword(@RequestParam("password") String password,
			@RequestParam("confirm-password") String confirmPassword, HttpServletRequest request, HttpSession session,
			final RedirectAttributes redirectAttributes) {

		session = request.getSession(false);
		String email = (String) session.getAttribute("email");
		log.info("email " + email + "password " + password + " confirmPassword " + confirmPassword);
		if (email != null && !email.equals("")) {

//			Dien thong tin day du
			if (password == null || confirmPassword == null) {
				log.info("Email & Password can't be empty.");
				redirectAttributes.addFlashAttribute("error", "error");
				return new ModelAndView("redirect:/customer/changepassword");
			}
//		    xac nhan mat khau phai giong nhau
			if (!password.equals(confirmPassword)) {
				redirectAttributes.addFlashAttribute("unmatched", "unmatched");
				return new ModelAndView("redirect:/customer/changepassword");
			}
//			Tim khach hang co mail nhap vao

			Customer CustomerExist = customerService.findCustomerByEmail(email);
			if (CustomerExist != null) {
//				Thuc hien thay doi mat khau
				log.info("Customer Exist True.");
				log.info("Time okay.");
				String encryptedPassword = bCryptPasswordEncoder.encode(password);
				customerService.updatePassword(email, encryptedPassword);
				log.info("Update Done...");
				redirectAttributes.addFlashAttribute("success", "Success");
				return new ModelAndView("redirect:/customer/changepassword");
			} else {
				log.info("Customer Exist False.");
				return new ModelAndView("redirect:/page404");
			}
		} else {
			return new ModelAndView("redirect:/page404");
		}
	}

//	 Hien thi thong tin nguoi dung => De cap nhat thong tin khach hang
	@GetMapping("/my-account")
	public ModelAndView editCustomer(HttpServletRequest request, HttpSession session) {
		ModelAndView mav = null;
//		Lay Email nguoi dung
		String email = (String) session.getAttribute("email");
		log.info("update customer..." + email);
//		Khach hang co email
		Customer customer = customerService.findCustomerByEmail(email);
		if (customer != null) { // Neu co khach hang
//			Update cac thong tin vao cac the input tuong ung
			mav = new ModelAndView("my-account");
			log.info("In Account :: "+customer.getId() + " "+ customer.getGender());
			mav.addObject("customerUpdate", customer);
			mav.addObject("gender", customer.getGender());
			return mav;
		} else {
			mav = new ModelAndView("/logout");
			return mav;
		}
	}

//	CAP NHAT THONG TIN NGUOI DUNG
	@PostMapping("/updateCustomer")
	String updateCustomer(@Valid @ModelAttribute("customerUpdate") Customer customer, BindingResult br,
			HttpSession session, Model model) {
		try {
				Long id = customer.getId();
				String name = customer.getName();
				String address = customer.getAddress();
				String phone = customer.getPhone();
				String gender = customer.getGender();
				String pinCode = customer.getPinCode();
				log.info(customer.getId() + " "+ customer.getGender());
				if (id != null) {
					log.info("in else, Updating Customer.");
					customerService.updateCustomerById(id, name, address, gender, phone, pinCode);
					session.removeAttribute("name");
					session.removeAttribute("phone");
					session.removeAttribute("address");
					session.removeAttribute("pinCode");

					session.setAttribute("name", name);
					session.setAttribute("phone", phone);
					session.setAttribute("address", address);
					session.setAttribute("pinCode", pinCode);
					model.addAttribute("gender", gender);
					model.addAttribute("success", "success");
					return "my-account";
				}
			model.addAttribute("error", "error");
			return "my-account";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", "error");
			return "my-account";
		}
	}
}
