package com.ManageBookStore.ManageBookStore.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ManageBookStore.ManageBookStore.entity.OrderDetail;
import com.ManageBookStore.ManageBookStore.service.OrderDetailService;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;

@Controller
public class PDFController {
	private static final Logger log = LoggerFactory.getLogger(PDFController.class);
	
	@Value("${upoadDir}")
	private String uploadFolder;
	
	@Value("${store_name}")
	private String storeName;
	
	@Value("${store_address}")
	private String storeAddress;
	
	@Value("${store_pin}")
	private String storePin;

//	thêm một số dòng trống được chỉ định vào một đoạn văn nhất định
	void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}

	@Autowired
	private OrderDetailService orderDetailService;

	@GetMapping("/orders/getPdf")
	String getPdf(HttpServletRequest request, HttpSession session, RedirectAttributes rda) {
		try {
			ByteArrayOutputStream outputStream = null;
			int orderNumber = 0;
			session = request.getSession(false);
			String email = (String) session.getAttribute("email");
			if (email != null) {
//				truy xuất giá trị của thuộc tính "paymentId"
				orderNumber = (Integer) session.getAttribute("paymentId");
//				ghi lại giá trị của việc orderNumbersử dụng một logger
				log.info("orderNumber : " + orderNumber);

//				Các thuộc tính này được coi là đã được đặt trước đó trong phiên và được sử dụng để thu thập thông tin về đơn đặt hàng
				List<OrderDetail> list = orderDetailService.getOrderByPayId(orderNumber);
				double totalAmount = (Double) session.getAttribute("totalAmount");
				double totalMrp = (Double) session.getAttribute("totalMrp");
				double totalSavings = (Double) session.getAttribute("totalSavings");
				int totalQty = (Integer) session.getAttribute("totalQty");
				log.info("list size : " + list.size() + " totalAmount " + totalAmount);
				String name = (String) session.getAttribute("name");
				String orderDate = (String) session.getAttribute("orderDate");
				String phone = (String) session.getAttribute("phone");
				String address = (String) session.getAttribute("address");
				String pinCode = (String) session.getAttribute("pinCode");
				log.info("name " + name + " orderDate " + orderDate + " phone " + phone + " address " + address);
//				truy xuất đường dẫn thực của đối tượng được chỉ định uploadFoldertừ ngữ cảnh servlet của tệp HttpServletRequest
				String uploadDirectory = request.getServletContext().getRealPath(uploadFolder);
				File dir = new File(uploadDirectory);
//				kiểm tra xem thư mục được chỉ định bởi dirkhông tồn tại.
				if (!dir.exists()) {
					log.info("Folder Created");
//					tạo thư mục được chỉ định bởi dir
					dir.mkdirs();
				}
//				tạo ra một ByteArrayOutputStreamđối tượng mới và gán nó cho outputStreambiến
				outputStream = new ByteArrayOutputStream();
//				tạo một Documentđối tượng mới từ thư viện iText PDF
				Document document = new Document();
//				nội dung PDF sẽ được ghi vào tệp outputStream
				PdfWriter.getInstance(document, outputStream);
				document.open();
//				 tạo một Fontđối tượng được đặt tên smallBoldvới họ phông chữ, kích thước và kiểu (đậm) đã chỉ định
				Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
//				Dat ten - tieu de - tac gia
				document.addTitle(storeName);
				document.addAuthor(storeName);
				document.addCreator(storeName);
//				 chứa nội dung được thêm vào tài liệu
				Paragraph p = new Paragraph();
				p.add(new Paragraph(storeName, smallBold));

//				đặt căn lề của pđoạn văn được căn giữa
				p.setAlignment(Element.ALIGN_CENTER);
//				thêm một đường chấm chấm vào PDF.
				DottedLineSeparator dottedline = new DottedLineSeparator();
//				đặt độ lệch của đường chấm chấm so với vị trí hiện tại. Một phần bù âm di chuyển dòng lên trên
		        dottedline.setOffset(-2);
//				đặt khoảng cách giữa các dấu chấm trong đường chấm chấm
		        dottedline.setGap(2f);
		        p.add(dottedline);

//				gọi hàm thêm dòng trống vào pđoạn văn
		        addEmptyLine(p, 1);
				document.add(p);
				Paragraph p1 = new Paragraph();
				addEmptyLine(p1, 1);
				p1.add(new Paragraph(storeName, smallBold));
				p1.add(new Paragraph(storeAddress, smallBold));
				p1.add(new Paragraph(storePin, smallBold));
				p1.add(new Paragraph("9920887594", smallBold));
				// Image img = Image.getInstance(imgFIle);
				// img.setAlignment(Element.ALIGN_RIGHT);
				// document.add(img);
				addEmptyLine(p1, 1);
				document.add(p1);
				Paragraph p2 = new Paragraph();
				p2.add(new Paragraph("Order Details", smallBold));
				p2.add(new Paragraph("Order Number: " + orderNumber, smallBold));
				p2.add(new Paragraph("Order Value: Rs. " + totalAmount, smallBold));
				p2.add(new Paragraph("Order Date: " + orderDate, smallBold));
				p2.setAlignment(Element.ALIGN_RIGHT);
				addEmptyLine(p2, 1);
				document.add(p2);
				Paragraph p3 = new Paragraph();
				p3.add(new Paragraph("Customer Details", smallBold));
				p3.add(new Paragraph("Customer Name: " + name, smallBold));
				p3.add(new Paragraph("Address: " + address, smallBold));
				p3.add(new Paragraph("Pin Code: " + pinCode, smallBold));
				p3.add(new Paragraph("Phone: " + phone, smallBold));
				p3.setAlignment(Element.ALIGN_LEFT);
				addEmptyLine(p3, 2);
				document.add(p3);

//				tạo một mảng có tên headerschứa các tiêu đề cột cho bảng PDF
				String[] headers = new String[] { "SR. No.", "Item Name", "Qty.", "MRP (Rs,)", "Selling Price (Rs.)",
						"Total Price (Rs.)", "Total Savings (Rs.)" };
//				tạo ra một đối tượng mới được đặt tên tablevới một số cột được chỉ định bằng với độ dài của headersmảng
				PdfPTable table = new PdfPTable(headers.length);
				Font fontHeader = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.BOLD);
				for (String header : headers) {
//					 tạo ra một đối tượng mới PdfPCellcó tên cell
					PdfPCell cell = new PdfPCell();
					cell.setGrayFill(0.9f);
					cell.setPhrase(new Phrase(header.toUpperCase(), fontHeader));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
				}
				table.completeRow();
				table.setHeaderRows(1);
				int count = 0;
				for (int i = 0; i < list.size(); i++) {
					PdfPCell cell;
					count++;
					cell = new PdfPCell(new Phrase(String.valueOf(count)));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
					cell = new PdfPCell(new Phrase(list.get(i).getProduct().getName()));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
					cell = new PdfPCell(new Phrase(String.valueOf(list.get(i).getQuantity())));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
					cell = new PdfPCell(new Phrase(String.valueOf(list.get(i).getMrpPrice())));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
					cell = new PdfPCell(new Phrase(String.valueOf(list.get(i).getPrice())));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
					cell = new PdfPCell(new Phrase(String.valueOf(list.get(i).getAmount())));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
					double total_mrp = list.get(i).getMrpPrice() * list.get(i).getQuantity();
					double total_price = list.get(i).getPrice() * list.get(i).getQuantity();
					double savings = total_mrp - total_price;
					cell = new PdfPCell(new Phrase(String.valueOf(savings)));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
				}
				PdfPCell cell;
				cell = new PdfPCell(new Phrase("Total"));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				cell = new PdfPCell(new Phrase(""));
				table.addCell(cell);
				cell = new PdfPCell(new Phrase(String.valueOf(totalQty)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				cell = new PdfPCell(new Phrase(String.valueOf(totalMrp)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				cell = new PdfPCell(new Phrase(""));
				table.addCell(cell);
				cell = new PdfPCell(new Phrase(String.valueOf(totalAmount)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				cell = new PdfPCell(new Phrase(String.valueOf(totalSavings)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				document.add(table);
				Paragraph p9 = new Paragraph();
				addEmptyLine(p9, 1);
				p9.add(new Paragraph("Terms", smallBold));
				p9.add(new Paragraph(
						"1. Item price and quantity can vary and Kirana partner will bill as per the prices available for the day.",
						smallBold));
				p9.add(new Paragraph("2. Please collect your bill while collecting your order.", smallBold));
				p9.add(new Paragraph(
						"3. This is not a Performa invoice or bill. This is only a report of order received.",
						smallBold));
				document.add(p9);
				document.close();
				byte[] bytes = outputStream.toByteArray();
				orderDetailService.sendPdfEmail(name, email, orderNumber, bytes);
				log.info("PDF Email Sent.");
				session.removeAttribute("paymentId");
				session.removeAttribute("totalMrp");
				session.removeAttribute("totalQty");
				session.removeAttribute("totalAmount");
				session.removeAttribute("orderDate");
				session.removeAttribute("totalSavings");
				rda.addFlashAttribute("pdf", "pdf");
				return "redirect:/order/payment";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		rda.addFlashAttribute("error_pdf", "error_pdf");
		return "redirect:/order/payment";
	}
}
