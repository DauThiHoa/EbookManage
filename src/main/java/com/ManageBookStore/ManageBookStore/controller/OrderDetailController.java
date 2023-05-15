package com.ManageBookStore.ManageBookStore.controller;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ManageBookStore.ManageBookStore.entity.Order;
import com.ManageBookStore.ManageBookStore.entity.OrderDetail;
import com.ManageBookStore.ManageBookStore.service.OrderDetailService;
import com.ManageBookStore.ManageBookStore.service.OrderService;


@RequestMapping("orders")
@Controller
public class OrderDetailController {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private OrderDetailService orderDetailService;
	
	@Autowired
	private OrderService orderService;

//	xử lý yêu cầu hiển thị đơn đặt hàng của khách hàng
	@GetMapping("/my-orders")
	String showCustomerOrders(HttpServletRequest request, Order order, HttpSession session, Model model) {
		try {
			session = request.getSession(false);
//			truy xuất email của khách hàng
			String customerEmail = (String) session.getAttribute("email");
			if (customerEmail == null) {
				return "redirect:/home";
			}
//			 truy xuất một bộ ID đơn đặt hàng được liên kết với email của khách hàng. ID đơn đặt hàng
			Set<Long> orderIds = orderService.getOrderIdByEmail(customerEmail);
//			truy xuất danh sách OrderDetailcác đối tượng được liên kết với ID đơn đặt hàng và email của khách hàng
			List<OrderDetail> ordersList = orderDetailService.getAllOrdersOrderId(orderIds, customerEmail);
//			ghi lại kích thước của orderIdstập hợp và ordersListdanh sách
			log.info("orderIds size = "+orderIds.size());
			log.info("ordersList size = "+ordersList.size());
//			thêm the ordersListvào Model
			model.addAttribute("orders", ordersList);
			return "my-orders";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", "e");
			return "my-orders";
		}
	}

}
