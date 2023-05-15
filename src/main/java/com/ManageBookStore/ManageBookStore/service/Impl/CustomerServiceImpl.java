package com.ManageBookStore.ManageBookStore.service.Impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ManageBookStore.ManageBookStore.entity.Customer;
import com.ManageBookStore.ManageBookStore.repository.CustomerRepository;
import com.ManageBookStore.ManageBookStore.service.CustomerService;
import com.ManageBookStore.ManageBookStore.util.CustomerUtility;


@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	CustomerRepository customerRepository;	
	
	@Autowired
	private JavaMailSender emailSender;

	@Autowired
	private Environment env;

//	lưu một đối tượng khách hàng vào cơ sở dữ liệu
	@Override
	public void saveCustomer(Customer customer) {
		try {
			customerRepository.save(customer);	
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}

//	xác minh thông tin đăng nhập của khách hàng trong quá trình đăng nhập.
	@Override
	public boolean loginCustomer(String email, String password) {
		return customerRepository.findValidCustomer(email, password);
	}

//	lấy một khách hàng từ cơ sở dữ liệu dựa trên email của họ
	@Override
	public Customer findCustomerByEmail(String email) {
		return customerRepository.checkCustomerByEmail(email);
	}

//	lấy mật khẩu của khách hàng từ cơ sở dữ liệu dựa trên email của họ
	@Override
	public String findCustomerPassword(String email) {
		return customerRepository.getCustomerPasswordByEmail(email);
	}

//	gửi email cho khách hàng với mục đích đặt lại mật khẩu của họ
	@Override
	public void sendMail(Customer customer) {
		try {
			if (customer != null) {
//				// Truy xuất URL gọi lại email từ cấu hình môi trường
				String emailCallBackUrl = env.getProperty("email.call.back.url");
//				// Tạo mã thông báo mới để đặt lại mật khẩu
				String token = CustomerUtility.generateNewToken();
				String email = customer.getEmail();
				String encodeEmail = Base64.getEncoder().encodeToString(email.getBytes());
// Lấy ngày giờ hiện tại
				Date todayDateTime = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				// Định dạng ngày giờ dưới dạng chuỗi
				String dataTime = sdf.format(todayDateTime);
				Date d1 = null;
				try {

					// Phân tích chuỗi ngày và giờ đã định dạng trở lại đối tượng Date
				    d1 = sdf.parse(dataTime);
				} catch (ParseException e) {
				    e.printStackTrace();
				}

				//Chuyển đối tượng Date thành chuỗi
				String data = String.valueOf(d1);
				System.out.println("In Service ="+data+ " "+dataTime);

				// Mã hóa chuỗi ngày giờ bằng mã hóa Base64
				String encodeData = Base64.getEncoder().encodeToString(dataTime.getBytes());
				MimeMessage message = emailSender.createMimeMessage();
				// use the true flag to indicate you need a multipart message

				// Tạo một MimeMessage mới để gửi email
				MimeMessageHelper helper = new MimeMessageHelper(message, true);

				// Xây dựng nội dung email dưới dạng chuỗi HTML
				String content = "Hi <b>" + customer.getName()
						+ "</b>,<br>A password reset for your account was requested.<br>"
						+ "Please click the button below to change your password.<br>"
						+ "Note that this link is valid for <b>24</b> hours. After the time limit has expired, you<br>"
						+ "will have to resubmit the request for a password reset.<br><br>"
						+ "<a class='boxed-btn blank2' style='display: inline-block; text-align: center; line-height: 50px; font-size: 14px; border-radius: 4px; color: #fff; background-color: #ee2d50; width: 180px; height: 50px; font-weight: 600; border: 1px solid #ee2d50; text-transform: uppercase; text-decoration: none;' href='"
						+ emailCallBackUrl + "?email="+encodeEmail+"&token="+token+"&data="+encodeData+"'>Change Password</a>";

				// Đặt chủ đề email
				helper.setSubject("Reset Password");

				// Đặt nội dung email là HTML
				helper.setText(content, true); // set to html

				// Đặt người gửi email
				helper.setFrom(env.getProperty("spring.mail.username"));
				// Đặt người nhận email
				helper.setTo(customer.getEmail());
				//helper.addCc(env.getProperty("mailToCc"));
				// Gửi thư điện tử
				emailSender.send(message);
				System.out.println("Email Sending Done...");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
	}

//	 cập nhật mật khẩu của khách hàng trong cơ sở dữ liệu
	@Override
	public void updatePassword(String email, String password) {
		customerRepository.changePassword(email, password);
	}

//	tìm một khách hàng trong cơ sở dữ liệu dựa trên số điện thoại của họ
	@Override
	public Customer findCustomerByPhone(String phone) {
		return customerRepository.checkCustomerPhone(phone);
	}

//	cập nhật thông tin của khách hàng trong cơ sở dữ liệu dựa trên ID của họ
	@Override
	public void updateCustomerById(Long id, String name, String address, String gender, String phone, String pinCode) {
		customerRepository.updateMyCustomer(id, name, address, gender, phone, pinCode);
	}

//	lấy ID của khách hàng dựa trên email của họ
	@Override
	public Long getCustomerId(String customerEmail) {
		return customerRepository.findCustomerId(customerEmail);
	}

}
