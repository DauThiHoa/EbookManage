package com.ManageBookStore.ManageBookStore.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ManageBookStore.ManageBookStore.entity.Order;
import com.ManageBookStore.ManageBookStore.entity.OrderDetail;
import com.ManageBookStore.ManageBookStore.service.OrderAdmService;
import com.ManageBookStore.ManageBookStore.service.OrderDetailAdmService;

@Controller
@RequestMapping("/admin/order")
public class OrderAdmController 
{
	 private Logger log = Logger.getLogger(OrderAdmController.class);
	 
	 @Autowired
	 OrderAdmService orderAdmSer;
	 
	 @Autowired
	 private OrderDetailAdmService orderDetailAdmService;
	 
	 @Autowired
	 OrderDetailAdmService orderDetAdmSer;
	 
	 @GetMapping("/view")
     public String orderList(Model map)
     {
		 List<OrderDetail> orderList = orderDetailAdmService.getAllOrders();
		 map.addAttribute("orders", orderList);
    	 return "admin_order_view";
     }

//	 hiển thị các đơn đặt hàng đang chờ xử lý
	 @GetMapping("/pending-orders")
	 public String pendingOrders(HttpSession session, Model map) {
			String aemail = (String) session.getAttribute("aemail");
			if(aemail == null) {
				return "redirect:/home";
			}
			String orderStatus = "Pending";
//			truy xuất danh sách OrderDetailcác đối tượng có trạng thái đơn hàng đã chỉ định
			List<OrderDetail> pending = orderDetailAdmService.getOrdersByStatus(orderStatus);
			//log.info("Pending Orders ::"+pending);
			map.addAttribute("pending", pending);
		 return "admin_order_pending";
	 }

//	  hiển thị các đơn hàng đã giao
	 @GetMapping("/delivered-orders")
	 public String deleveredOrders(HttpSession session, Model map) {
			String aemail = (String) session.getAttribute("aemail");
			if(aemail == null) {
//				ếu email của quản trị viên là null, nghĩa là người dùng chưa được xác thực,
//				thì phương thức này sẽ chuyển hướng họ đến trang "/home"
				return "redirect:/home";
			}
			String orderStatus = "Delivered";
//			truy xuất danh sách OrderDetailcác đối tượng có trạng thái đơn hàng đã chỉ định
			List<OrderDetail> delivered = orderDetailAdmService.getOrdersByStatus(orderStatus);
			//log.info("delivered Orders ::"+delivered);
//		 Danh sách đã truy xuất của các đơn đặt hàng đã giao được thêm vào Model
			map.addAttribute("delivered", delivered);
		 return "admin_order_delivered";
	 }

//	 hiển thị các đơn hàng đã hủy trong bảng quản trị
	 @GetMapping("/cancelled-orders")
	 public String cancelledOrders(HttpSession session, Model map) {
			String aemail = (String) session.getAttribute("aemail");
			if(aemail == null) {
				return "redirect:/home";
			}
			String orderStatus = "Cancelled";
//			truy xuất danh sách OrderDetailcác đối tượng có trạng thái đơn hàng đã chỉ định
			List<OrderDetail> cancel = orderDetailAdmService.getOrdersByStatus(orderStatus);
			//log.info("Cancelled Orders ::"+cancel);
			map.addAttribute("cancel", cancel);
		 return "admin_order_cancelled";
	 }

//	 thay đổi trạng thái của đơn hàng trong bảng quản trị
	 @GetMapping("/changeStatus/{orderStatus}/{id}")
	 public String changeStatusOfOrders(@PathVariable("orderStatus") String  orderStatus, @PathVariable("id") Long id,
			 RedirectAttributes rda, HttpServletRequest request, HttpSession session)
	 {
		 String aemail = (String) session.getAttribute("aemail");
			if(aemail == null) {
				return "redirect:/home";
			}
			log.info("orderStatus :: "+orderStatus +" id :: "+id);

//			thay đổi trạng thái đơn hàng của đơn hàng đã chỉ định.
			orderAdmSer.changeOrderStatus(orderStatus, id);
			String backUrl = request.getHeader("referer");
			log.info("back URL : "+backUrl);
			if (orderStatus.equals("Delivered")) {
				rda.addFlashAttribute("delivered", "delivered");
				String newUrl[] = backUrl.split("admin/");
				log.info("new Url : "+newUrl[1]);
				return "redirect:/admin/"+newUrl[1];
			} else if (orderStatus.equals("Pending")) {
				rda.addFlashAttribute("pendings", "pending");
				String newUrl[] = backUrl.split("admin/");
				log.info("new Url : "+newUrl[1]);
				return "redirect:/admin/"+newUrl[1];
			} else if (orderStatus.equals("Cancelled")) {
				if(aemail.equals("email")) {
					log.info("in if");
					rda.addFlashAttribute("cancelled", "cancelled");
					return "redirect:/orders/my-orders";
				}
				rda.addFlashAttribute("cancelled", "cancelled");
				String newUrl[] = backUrl.split("admin/");
				log.info("new Url : "+newUrl[1]);
				return "redirect:/admin/"+newUrl[1];
			} else {
				return "redirect:/home";
			}
	 }

//	 xóa đơn hàng trong bảng quản trị
	 @GetMapping("/delete/{id}")
	 public String removeOrder(@PathVariable("id") Long orderId, Order order, 
			HttpServletRequest request ,HttpSession session, RedirectAttributes rda) {
		 String aemail = (String) session.getAttribute("aemail");
			if(aemail == null || orderId == 0) {
//				Nếu email của quản trị viên là null hoặc orderIdlà 0,
//				cho biết rằng người dùng chưa được xác thực hoặc ID đơn đặt hàng không hợp lệ,
				return "redirect:/home";
			}
			try {
				log.info("id :: "+orderId);

//				xóa đơn hàng có ID đã chỉ định khỏi bảng Đơn hàng
				orderAdmSer.deleteOrdersById(orderId);
				log.info("deleted from Order Table.");
				order.setId(orderId);

//				xóa chi tiết đơn hàng được liên kết với ID đơn hàng đã chỉ định khỏi bảng OrderDetail
				orderAdmSer.deleteOrderDetailById(order);
				log.info("deleted from OrderDetail Table.");
				rda.addFlashAttribute("delete", "delete");
				String backUrl = request.getHeader("referer");
				log.info("back URL : "+backUrl);
				String newUrl[] = backUrl.split("admin/");
				log.info("new Url : "+newUrl[1]);
				return "redirect:/admin/"+newUrl[1];
			} catch (Exception e) {
				rda.addFlashAttribute("error", "error");
				e.printStackTrace();
				return "redirect:/home";
			}
	 }
}
