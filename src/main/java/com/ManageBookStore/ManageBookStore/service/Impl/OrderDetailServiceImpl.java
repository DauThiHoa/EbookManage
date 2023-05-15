package com.ManageBookStore.ManageBookStore.service.Impl;

import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailParseException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ManageBookStore.ManageBookStore.entity.Order;
import com.ManageBookStore.ManageBookStore.entity.OrderDetail;
import com.ManageBookStore.ManageBookStore.entity.Product;
import com.ManageBookStore.ManageBookStore.repository.OrderDetailRepository;
import com.ManageBookStore.ManageBookStore.service.OrderDetailService;

@Service
@Transactional
public class OrderDetailServiceImpl implements OrderDetailService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private JavaMailSender emailSender;

	@Autowired
	private Environment env;

	@Autowired
	private OrderDetailRepository orderDetailRepo;

//	Luu chi tiet don hang
	@Override
	public boolean saveOrderDetail(OrderDetail od) {
		boolean status = false;
		if (od != null) {
			status = true;
			orderDetailRepo.save(od);
		}
		return status;
	}

//	truy xuất chi tiết đơn hàng khớp với ID đơn hàng đã cho và email của khách hàng
	@Override
	public List<OrderDetail> getAllOrdersOrderId(Set<Long> id, String customerEmail) {
		return orderDetailRepo.findOrdersByOrderId(id, customerEmail);
	}

//	lưu chi tiết đơn hàng liên quan đến đơn hàng trong giỏ hàng
	@Override
	public boolean saveCartOrderDetail(Order orders, Product products, int quantity, double mrpPrice, double amount, double totalPrice,
			int paymentId, String orderStatus, String paymentMode) {
		boolean status = false;
		if (orders != null) {
			status = true;
			orderDetailRepo.saveOrderDetails(orders, products, quantity, mrpPrice, amount, totalPrice, paymentId, orderStatus, paymentMode);
		}
		return status;
	}

//	xóa chi tiết đơn đặt hàng khỏi kho lưu trữ hoặc cơ sở dữ liệu dựa trên ID thanh toán được cung cấp
	@Override
	public void deleteOrderDetailByNum(int paymentId) {
		orderDetailRepo.deleteOrderDetailByNum(paymentId);
	}

	@Override
	public List<OrderDetail> getOrderByPayId(int paymentId) {
		return orderDetailRepo.findOrderByPayId(paymentId);
	}


//	truy xuất danh sách chi tiết đơn đặt hàng được liên kết với ID thanh toán cụ thể từ kho lưu trữ hoặc cơ sở dữ liệu
	@Override
	public void saveOrderDetails(List<OrderDetail> orderDetails) {
		orderDetailRepo.saveAll(orderDetails);
	}

//	gửi email có tệp đính kèm PDF
	@Override
	public void sendPdfEmail(String name, String email, int OrderNum, byte[] bytes) {
		MimeMessage message = emailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
			String content = "Hi, <b>" + name + "</b> Thank you for your order. PFB Order Details.<br>";
			helper.setSubject("Dharmesh General Store: Order#" + OrderNum);
			helper.setText(content, true);
			helper.setTo(email);
			helper.addCc(env.getProperty("mailToCc"));
			helper.setFrom(env.getProperty("spring.mail.username"));

//			Tệp PDF được đính kèm vào email bằng addAttachmentphương pháp. Tên tệp được tạo dựa trên số thứ tự và mảng
//			byte được cung cấp dưới dạng nội dung tệp đính kèm
			String fileName = "order_"+OrderNum+".pdf";
			helper.addAttachment(fileName, new ByteArrayResource(bytes));
			emailSender.send(message);
			log.info("Email Sent With File : "+fileName);
		} catch (MessagingException e) {
			log.info("Exception Occurred While Sendimg Email.");
			throw new MailParseException(e);
		}	
	}

//	truy xuất các đơn đặt hàng cuối cùng từ cơ sở dữ liệu dựa trên ID của chúng theo thứ tự giảm dần
	@Override
	public List<OrderDetail> getLastOrderByIdDesc(Pageable pageable) {
		return orderDetailRepo.getLastOrder(pageable);
	}
}
