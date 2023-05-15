package com.ManageBookStore.ManageBookStore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ManageBookStore.ManageBookStore.entity.Customer;
import com.ManageBookStore.ManageBookStore.service.UserAdmService;

@Controller
@RequestMapping("/admin/customer")
public class CustomerAdmController
{
	@Autowired
	UserAdmService userAdmSer;

//	danh sách khách hàng để hiển thị thông tin liên quan cho quản trị viên
	@GetMapping("/view")
	public String customerView(Model map)
	{
//		lấy danh sách khách hàng bằng cách gọi getAllCustomerphương thức trên userAdmSerđối tượng (dịch vụ quản trị người dùng)
		List<Customer> customerList = userAdmSer.getAllCustomer();
		map.addAttribute("customer", customerList);
		return "admin_customer_view";
		
	}

//	xóa một khách hàng có ID đã cho và chuyển hướng quản trị viên đến trang xem khách hàng
	@GetMapping("/delete/{id}")
	public String deleteProduct(@PathVariable("id") Long pid, RedirectAttributes map)
	{
		userAdmSer.deleteCustomer(pid);
		map.addFlashAttribute("delete", "delete");
		return "redirect:/admin/customer/view";
	}
	
}
