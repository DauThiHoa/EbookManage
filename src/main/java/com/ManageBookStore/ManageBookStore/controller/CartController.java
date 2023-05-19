package com.ManageBookStore.ManageBookStore.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ManageBookStore.ManageBookStore.entity.Cart;
import com.ManageBookStore.ManageBookStore.entity.Customer;
import com.ManageBookStore.ManageBookStore.entity.Order;
import com.ManageBookStore.ManageBookStore.entity.OrderDetail;
import com.ManageBookStore.ManageBookStore.entity.Product;
import com.ManageBookStore.ManageBookStore.service.CartService;
import com.ManageBookStore.ManageBookStore.service.CustomerService;
import com.ManageBookStore.ManageBookStore.service.OrderDetailService;
import com.ManageBookStore.ManageBookStore.service.OrderService;
import com.ManageBookStore.ManageBookStore.service.ProductService;

@Controller
@RequestMapping("/cart")
public class CartController {
	private static final Logger log = LoggerFactory.getLogger(CartController.class);
	
	@Autowired
	private ProductService productService;
	
	@Value("${count}")
	private int order_count;
	
	@Autowired
	private CustomerService customerService;

	@Autowired
	CartService cartService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderDetailService orderDetailService;

//	xử lý quy trình thanh toán cho các mặt hàng trong giỏ hàng
	@PostMapping("/saveCart")
	String orderCheckout(Customer customer, Product product, HttpServletRequest request, HttpSession session,
			RedirectAttributes rda) {
		try {
			session = request.getSession(false);
			String email = (String) session.getAttribute("email");
			if (email == null) {
				String backUrl = request.getHeader("referer");
				session.setAttribute("backUrl", backUrl);
				return "redirect:/customer/login";
			}
//			tìm customerServicemột Customerthực thể dựa trên email được cung cấp
			Customer emailExists = customerService.findCustomerByEmail(email);
//			truy xuất ID của Customerthực thể được tìm thấy dựa trên email
			Long customerId = emailExists.getId();
//			lấy giá trị của thông số "item_count" từ yêu cầu và chuyển đổi nó thành một số nguyên
			int count = Integer.parseInt(request.getParameter("item_count"));
			for (int i = 1; i <= count; i++) {
//				truy xuất các tham số khác nhau từ yêu cầu bằng cách sử dụng request.getParameter()
//				và thực hiện các chuyển đổi kiểu cần thiết
				log.info("item_id = " + Long.parseLong(request.getParameter("item_id_" + i)));
				log.info("item_name = " + request.getParameter("item_name_" + i));
				log.info("quantity = " + Integer.parseInt(request.getParameter("quantity_" + i)));
				log.info("amount = " + Double.parseDouble(request.getParameter("amount_" + i)));
				Long productId = Long.parseLong(request.getParameter("item_id_" + i));
				int quantity = Integer.parseInt(request.getParameter("quantity_" + i));
				double mrpPrice = Double.parseDouble(request.getParameter("mrp_" + i));
				double price = Double.parseDouble(request.getParameter("amount_" + i));
				double totalPrice = quantity * price;
				log.info("total_price = " + totalPrice);
				product.setId(productId);
				customer.setId(customerId);

//				tạo một Cartđối tượng mới với các giá trị được cung cấp cho số lượng, mrpPrice, giá, khách hàng, sản phẩm và tổng giá
				Cart cartItems = new Cart(quantity, mrpPrice, price, customer, product, totalPrice);
				List<Cart> cartItemsList = Arrays.asList(cartItems);
//				gọi saveCartItems phương thức cartServicelưu danh sách các mặt hàng trong giỏ hàng
				cartService.saveCartItems(cartItemsList);
				log.info("Cart Item Inserted :"+i);
			}
			rda.addFlashAttribute("cart", "cart");
			return "redirect:/home";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/home";
		}
	}

	@GetMapping("/my-cart")
	String showMyCartItems(Customer customer, HttpServletRequest request, HttpSession session, Model model) {
		try {
			session = request.getSession(false);
			String email = (String) session.getAttribute("email");
			if (email != null) {
//				phương thức tìm customerServicemột Customerthực thể dựa trên email được cung cấp
				Customer emailExists = customerService.findCustomerByEmail(email);
//				truy xuất ID của Customerthực thể được tìm thấy dựa trên email
				Long customerId = emailExists.getId();
				customer.setId(customerId);

//				gọi getCartItemsByCustomerIdphương thức của cartServiceđể lấy danh sách các mặt hàng trong giỏ hàng được liên kết với khách hàng
				List<Cart> cartItemsList = cartService.getCartItemsByCustomerId(customer);
//				Các dòng tiếp theo tính toán tổng số lượng, tổng MRP, tổng giá và tổng số tiền tiết kiệm được dựa trên các mặt hàng trong giỏ hàng được truy xuất
				double total_mrp = 0;
				int total_qty = 0;
				double total_price = 0;
				double total_saving = 0;
				for(Cart c: cartItemsList) {
					total_qty += c.getQuantity();
					total_mrp += c.getMrpPrice() * c.getQuantity();
					total_price += c.getPrice() * c.getQuantity();
				}
//				thêm các thuộc tính vào Modelđối tượng để truy cập chúng trong mẫu xem
//				total_saving = total_mrp - total_price;
				total_saving = total_price - total_mrp;
				model.addAttribute("total_saving", total_saving);
				model.addAttribute("total_mrp", total_mrp);
				model.addAttribute("total_qty", total_qty);
				model.addAttribute("total_price", total_price);
				model.addAttribute("items", cartItemsList);
				return "cart-list";
			}
			return "redirect:/home";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/home";
		}
	}

//	thêm một sản phẩm vào giỏ hàng.
	@PostMapping("/checkout")
	String productCheckout(@RequestParam("code") String code, HttpServletRequest request, HttpSession session, 
			Product product, Model model, Cart cart, Customer customer) {
		try {
			session = request.getSession(false);
			String email = (String) session.getAttribute("email");
			if(email == null) {
				String backUrl = request.getHeader("referer");
				log.info("backUrl :: "+backUrl);
				session.setAttribute("backUrl", backUrl);
				return "redirect:/customer/login";
			}
			log.info("Code :: "+code);
//			Nó kiểm tra xem codetham số có phải là null hay không và bắt đầu bằng chữ "P"
			if (code != null && code.startsWith("P")) {

//				gọi getProductByCodephương thức truy productServicexuất một Productthực thể dựa trên mã được cung cấp
				product = productService.getProductByCode(code);
				log.info("product :: " + product.getName());
				if (product != null) { // kiểm tra xem giá trị có phải productlà null hay không.
					product.setId(product.getId());

//					gọi findCustomerByEmailphương thức tìm customerServicemột Customerthực thể dựa trên email được cung cấp
					customer = customerService.findCustomerByEmail(email);
//					truy xuất ID của Customerthực thể được tìm thấy dựa trên email
					Long customerId = customer.getId();
//					đặt ID của Customerđối tượng với giá trị được lấy từ customerđối tượng
					customer.setId(customerId);
//					tạo một Cartđối tượng mới với các tham số
					cart = new Cart(1, product.getMrpPrice(), product.getPrice(), customer, product, product.getPrice());
//					gọi saveCartphương thức cartServicelưu Cartđối tượng vào cơ sở dữ liệu
					cartService.saveCart(cart);
					log.info("Cart : Item Inserted.");
					return "redirect:/cart/my-cart";
				}
			}
			return "redirect:/page404";
		}  catch (Exception e) {
			e.printStackTrace();
			return "redirect:/home";
		}
	}

//	xóa một mặt hàng trong giỏ hàng
	@GetMapping(value = "/remove/{cid}")
	String removeCartItems(@PathVariable("cid") Long cartId, HttpServletRequest request, HttpSession session,
			RedirectAttributes rda, Customer customer, Product product) {
		String email = "";
		try {
			session = request.getSession(false);
			email = (String) session.getAttribute("email");
			if (email != null) {
//				gọi getCustomerIdphương thức của customerServiceđể lấy ID của khách hàng được liên kết với email được cung cấp
				Long customerId = customerService.getCustomerId(email);
//				 đặt ID của Customerđối tượng với ID khách hàng đã truy xuất
				customer.setId(customerId);
//				gọi removeCartItemsphương thức cartServicexóa mặt hàng trong giỏ hàng được liên kết với ID giỏ hàng và khách hàng đã cung cấp
				cartService.removeCartItems(customer, cartId);
//				hêm một thuộc tính flash tên là "delete" với giá trị "delete".
//				Thuộc tính flash được sử dụng để truyền dữ liệu giữa các yêu cầu
				rda.addFlashAttribute("delete", "delete");
				return "redirect:/cart/my-cart";
			}
			return "redirect:/home";
		} catch (Exception e) {
			e.printStackTrace();
			rda.addFlashAttribute("error", "error");
			return "redirect:/cart/my-cart";
		}
	}

	@PostMapping("/order/checkout")
	String cartItemsCheckout(@RequestParam("customerName") String customerName,
			@RequestParam("customerPhone") String customerPhone, @RequestParam("address") String customerAddress,
			@RequestParam("addressType") String customerAddressType, @RequestParam("pinCode") String pinCode, HttpServletRequest request, HttpSession session,
			Order order, OrderDetail od, Product product, Customer customer, RedirectAttributes rda) {
		int x, y, count, orderNum, paymentId;
		x = y = orderNum = paymentId = count = 0;
//		Biến được gán giá trị “Chờ xử lý” cho biết đơn hàng đang ở trạng thái chờ xử lý.
		String orderStatus = "Pending";
		double total = 0;
		double total_mrp=0;
//		Biến được gán ngày giờ hiện tại, thể hiện ngày đặt hàng
		Date orderDate = new Date();
		try {
			session = request.getSession(false);
			String customerEmail = (String) session.getAttribute("email");
			if (customerEmail != null) {
//				Một Pageableđối tượng được tạo với số trang 0 và kích thước trang là 1.
//				Điều này sẽ được sử dụng để truy xuất chi tiết đơn hàng cuối cùng.
				Pageable pageable = PageRequest.of(0, 1);

//				Phương thức được gọi để truy xuất chi tiết đơn hàng cuối cùng bằng cách sử dụng Pageableđối tượng được chỉ định
				List<OrderDetail> orderList = orderDetailService.getLastOrderByIdDesc(pageable);
				if(orderList.size() == 0) {
					paymentId = order_count;
					log.info("In List Size 0, Payment Id :: "+paymentId);
				} else {
					paymentId = orderList.get(0).getPaymentId() + 1;
					log.info("In else, Payment Id :: "+paymentId);
				}
				count = (Integer) session.getAttribute("size");
				String paymentMode = request.getParameter("paymentMode");
				int total_qty = 0;
				for (int i = 1; i <= count; i++) {
				// Ghi lại các thông tin khác nhau về mặt hàng trong giỏ hàng
					log.info("Cart Id :: " + request.getParameter("cid_" + i));
					log.info("Product Id :: " + request.getParameter("pid_" + i));
					log.info("Product Name :: " + request.getParameter("pname_" + i));
					log.info("Product code :: " + request.getParameter("code_" + i));
					log.info("Product Quantity :: " + request.getParameter("quantity_" + i));
					log.info("Product MRP :: " + request.getParameter("mrp_" + i));
					log.info("Product Amount :: " + request.getParameter("amount_" + i));
					log.info("Product Total Amount :: " + request.getParameter("total_amount_" + i));
					Long ordersCount = orderService.getOrdersCount();
					log.info("Total Order Count :: "+ordersCount);
					// Lấy số lượng đơn hàng và xác định orderNum
					if(ordersCount == 0) {
						orderNum = order_count;
						log.info("In If :: orderNum :: "+orderNum);
					}
					orderNum = ordersCount.intValue() + order_count;
					log.info("Not In If :: orderNum :: "+orderNum);
					double totalAmount = Double.parseDouble(request.getParameter("total_amount_" + i));
					boolean active = true;
					// Phân tích tham số yêu cầu
					Long productId = Long.parseLong(request.getParameter("pid_" + i));
					Long cartId = Long.parseLong(request.getParameter("cid_" + i));
					int quantity = Integer.parseInt(request.getParameter("quantity_" + i));
					double amount = Double.parseDouble(request.getParameter("amount_" + i));
					double mrpPrice = Double.parseDouble(request.getParameter("mrp_" + i));

					// Tính toán số lượng, tổng số tiền, v.v.
					total_qty += quantity;
					double totalPrice = quantity * amount;
					total += quantity * amount;
					total_mrp += mrpPrice * quantity;
					product.setId(productId);
					log.info("orderNum :: "+orderNum);

					// Tạo và lưu đơn hàng
					order = new Order(orderNum, totalAmount, customerName, customerAddress, customerAddressType, customerEmail, customerPhone, pinCode, active, orderDate);
					List<Order> orders = Arrays.asList(order);
					orderService.saveOrders(orders);
					log.info("======================================");

					// Lưu chi tiết đơn hàng
					Long id = orderService.getOrderIdByNum(orderNum);
					order.setId(id);
					x++;
					log.info("order id =" + id +" x = "+x);
					boolean flag = orderDetailService.saveCartOrderDetail(order, product, quantity, mrpPrice, amount, totalPrice, paymentId, orderStatus, paymentMode);
					log.info("Flag = "+flag);
					if (flag) {
						y++;
						log.info("order detail saved. y = " + y);
					}
					Long customerId = customerService.getCustomerId(customerEmail);
					customer.setId(customerId);

 					// Xoá các mặt hàng trong giỏ hàng
					cartService.removeCartItems(customer, cartId);
				}
				// Tính tổng tiết kiệm
				double totalSavings = total_mrp - total;
				// Thêm thuộc tính flash để chuyển hướng đến trang thanh toán
				rda.addFlashAttribute("orderDate", orderDate);
				rda.addFlashAttribute("totalAmount", total);
				rda.addFlashAttribute("totalMrp", total_mrp);
				rda.addFlashAttribute("paymentId", paymentId);
				rda.addFlashAttribute("pinCode", pinCode);
				rda.addFlashAttribute("totalQty", total_qty);
				rda.addFlashAttribute("totalSavings", totalSavings);
				rda.addFlashAttribute("success", "success");
				return "redirect:/order/payment";
			}
		} catch (Exception e) {
			e.printStackTrace();

//			đại diện cho số lượng đơn đặt hàng được lưu thành công) không bằng count(tổng số mặt hàng trong giỏ hàng)
//			hay không. Nếu chúng không bằng nhau, điều đó có nghĩa là một số đơn đặt hàng đã không được lưu thành công
			if (x != count) {
				log.info("In catch :: "+orderNum);
//				xóa các đơn đặt hàng khỏi cơ sở dữ liệu bằng cách sử dụng phương thức orderNum
				orderService.deleteOrdersByNum(orderNum);
				log.info("deleting from orders table. " + x);
				rda.addFlashAttribute("error", "error");
			}
			if (y != count) {
				log.info("In catch :: "+paymentId);
//				xóa chi tiết đơn hàng khỏi cơ sở dữ liệu bằng cách sử dụng tệp paymentId
			orderDetailService.deleteOrderDetailByNum(paymentId);
			log.info("deleting from order_detail table. " + y);
			rda.addFlashAttribute("error", "error");
			return "redirect:/order/payment";
		}
	}
		return "redirect:/home";
 }
}