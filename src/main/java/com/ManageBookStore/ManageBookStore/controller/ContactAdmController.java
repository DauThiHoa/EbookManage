package com.ManageBookStore.ManageBookStore.controller;

import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ManageBookStore.ManageBookStore.entity.Contact;
import com.ManageBookStore.ManageBookStore.service.ContactAdmService;

@Controller
@RequestMapping("/admin/contact")
public class ContactAdmController 
{
	private Logger log = Logger.getLogger(ContactAdmController.class);
	
	@Autowired
	ContactAdmService contactAdmSer;

//	tìm nạp danh sách các liên hệ và thêm nó vào mô hình để hiển thị trong chế độ xem "admin_contact_view"
	@GetMapping("/view")
	public String getContactList(Model map)
	{
		List<Contact> contactList = contactAdmSer.getAllContact();
		map.addAttribute("contact", contactList);
		return "admin_contact_view";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteContact(@PathVariable("id") Long id, RedirectAttributes map) {
//		ghi lại ID của liên hệ sẽ bị xóa
		log.info("Contact id :: "+id);
//		gọi deleteContactphương thức trên contactAdmSerđối tượng (dịch vụ quản trị liên hệ) để xóa liên hệ có ID đã chỉ định
			contactAdmSer.deleteContact(id);
//			thêm một thuộc tính flash với tên "delete" và giá trị "d". Thuộc tính flash này có thể
//			được truy cập trong chế độ xem được chuyển hướng
			map.addFlashAttribute("delete", "d");
			return "redirect:/admin/contact/view";
	}
}
