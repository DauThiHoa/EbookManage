package com.ManageBookStore.ManageBookStore.service.Impl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ManageBookStore.ManageBookStore.entity.Contact;
import com.ManageBookStore.ManageBookStore.repository.ContactRepository;
import com.ManageBookStore.ManageBookStore.service.ContactService;
@Service
@Transactional
public class ContactServiceImpl implements ContactService {

	private final ContactRepository contactRepository;

	@Autowired
	public ContactServiceImpl(ContactRepository contactRepository) {
		this.contactRepository = contactRepository;
	}

	@Autowired
	private JavaMailSender emailSender;

	@Autowired
	private Environment env; // to get values from property file.
	
	@Override
	public void saveContacts(Contact contact) throws MessagingException {
		System.out.println("contact : " + contact);

		if (contact != null) {
			MimeMessage message = emailSender.createMimeMessage();
			// Nó tạo một MimeMessageđối tượng mới bằng cách sử dụng emailSenderthể hiện.
			// Đối tượng này được sử dụng để xây dựng thông điệp email.
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			String content = "Hi, <b>" + contact.getName()
					+ "</b> has contacted you. Below are the details of his/her message.<br>";
//			đặt chủ đề của email thành giá trị được lấy từ đối contacttượng.
			helper.setSubject(contact.getSubject());
			helper.setText(content + " <b>Name: </b> " + contact.getName() + "<br>" + "" + "<b>Email: </b> "
					+ contact.getEmail() + "<br>"
					+ "<b>Subject: </b> " + contact.getSubject() + "<br>" + "<b>Message:</b> "
					+ contact.getMessage(), true); // set to html
			helper.setFrom(contact.getEmail());
			helper.setTo(env.getProperty("spring.mail.username"));
			// gửi thông điệp email được xây dựng bằng cách sử dụng emailSenderđối tượng
			emailSender.send(message);

			contactRepository.save(contact);
		}

	}

}
