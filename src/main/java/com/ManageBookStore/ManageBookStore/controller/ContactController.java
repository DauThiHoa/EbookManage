package com.ManageBookStore.ManageBookStore.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ManageBookStore.ManageBookStore.entity.Contact;
import com.ManageBookStore.ManageBookStore.service.ContactService;


@Controller
public class ContactController {

	private static Logger log = LoggerFactory.getLogger(ContactController.class);

	private final ContactService contactService;

	@Autowired
	public ContactController(ContactService contactService) {
		this.contactService = contactService;
	}

	@PostMapping(value = "/saveContact", produces = MediaType.APPLICATION_JSON_VALUE) // headers =
																						// "Accept=application/json",
																						// produces =
	//				đại diện cho một thực thể phản hồi HTTP có kiểu chung.
//				Nó cho phép bạn kiểm soát trạng thái phản hồi, tiêu đề và nội dung.																				// "application/json")
	@ResponseBody
	ResponseEntity<?> addContact(@RequestBody Contact contact, RedirectAttributes redirectAttributes) {
		try {
			log.info("In try....");
			HttpHeaders headers = new HttpHeaders();
			if (contact == null) {
//				Nếu contactđối tượng là null, nó sẽ trả về một ResponseEntity có trạng thái HTTP là BAD_REQUEST,
//				cho biết một yêu cầu không hợp lệ
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
//			các thuộc tính email, chủ đề, tin nhắn và contactDate được truy xuất và đặt tương ứng
			String name = contact.getName();
			String email = contact.getEmail();
			String subject = contact.getSubject();
			String message = contact.getMessage();
			Date contactDate = new Date();
			log.info("name :: " + name);
			contact.setName(name);
			contact.setEmail(email);
			contact.setSubject(subject);
			contact.setMessage(message);
			contact.setContactDate(contactDate);

//			Gọi saveContactsphương thức của contactServiceđối tượng lưu liên hệ
			contactService.saveContacts(contact);

//			Thêm tiêu đề tùy chỉnh với khóa "Người dùng đã lưu với tên -" và giá trị của biến name
			headers.add("User Saved With name - ", name);
//			Tạo phản hồi chuỗi JSON chứa thuộc tính tên.
			String response = "{\"name\": \"" + name + "\"}";

//			Trả về một ResponseEntity với phản hồi JSON, tiêu đề tùy chỉnh và trạng thái HTTP là CREATED
			return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
//			Nếu xảy ra ngoại lệ, nó sẽ trả về ResponseEntity có trạng thái HTTP là BAD_REQUEST, cho biết có lỗi
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

}
