package com.ManageBookStore.ManageBookStore.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.ManageBookStore.ManageBookStore.entity.Product;
import com.ManageBookStore.ManageBookStore.service.ProductAdmService;
import com.ManageBookStore.ManageBookStore.service.ProductService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LinkController {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private ProductService productService;

	@Autowired
	ProductAdmService productAdmSer;

	@Value("${product_size}")
	private int size;

	private final Logger log = LoggerFactory.getLogger(this.getClass());

//	truy xuất danh sách sản phẩm, đếm tổng số sản phẩm và chuyển dữ liệu cần thiết tới chế độ xem để hiển thị
	@GetMapping(value = { "/", "/index", "/home", "" })
	public String homePage(Model map, HttpServletRequest request, HttpServletResponse response)  throws UnsupportedEncodingException  {
//		tạo một đối tượng mới Pageablebằng PageRequest.of()phương thức chỉ định số trang (0) và
//		kích thước trang (giá trị của sizebiến)
		Pageable pageable = PageRequest.of(0, size);
//		lấy danh sách các sản phẩm từ phương thức productServicebằng cách gọi getProducts()phương thức và truyền pageableđối tượng dưới dạng tham số.
//		Các sản phẩm trả lại được lưu trữ trong productdanh sách
		List<Product> product = productService.getProducts(pageable);

//		truy xuất số lượng của tất cả các sản phẩm bằng cách sử dụng countProduct()
		Long count = productAdmSer.countProduct();
		log.info("count:: " + count);
//		 thêm count biến dưới dạng một thuộc tính có tên "count" vào map đối tượng, làm cho nó có thể truy cập được trong dạng xem.
		map.addAttribute("count", count);
		map.addAttribute("product_size", size);
		map.addAttribute("products", product);
		log.info("Showing Home Page.");

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");


//		Trang index
		String index_QuickView = messageSource.getMessage("index_QuickView", null, LocaleContextHolder.getLocale());
		map.addAttribute("index_QuickView", index_QuickView);
		String index_AddToCart = messageSource.getMessage("index_AddToCart", null, LocaleContextHolder.getLocale());
		map.addAttribute("index_AddToCart", index_AddToCart);
		String index_BuyNow = messageSource.getMessage("index_BuyNow", null, LocaleContextHolder.getLocale());
		map.addAttribute("index_BuyNow", index_BuyNow);
		String index_ThereIsNoItems = messageSource.getMessage("index_ThereIsNoItems", null, LocaleContextHolder.getLocale());
		map.addAttribute("index_ThereIsNoItems", index_ThereIsNoItems);
		String index_LoadMoreItems = messageSource.getMessage("index_LoadMoreItems", null, LocaleContextHolder.getLocale());
		map.addAttribute("index_LoadMoreItems", index_LoadMoreItems);
		String index_GoBack = messageSource.getMessage("index_GoBack", null, LocaleContextHolder.getLocale());
		map.addAttribute("index_GoBack", index_GoBack);


		return "index";
	}

//	hiển thị danh sách sản phẩm được phân trang
	@GetMapping(value = { "/products/{pageNo}" })
	public String homePages(Model model, @PathVariable("pageNo") int pageNo) {
//		truy xuất số lượng của tất cả các sản phẩm bằng cách sử dụng countProduct()
		Long count = productAdmSer.countProduct();
//		ghi nhật ký thông báo thông tin, bao gồm giá trị đếm và giá trị pageNo
		log.info("count:: " + count+" pageNo:: "+pageNo);
//		kiểm tra xem pageNo có nhỏ hơn hoặc bằng 0 hay không hoặc nếu số lượng sản phẩm nhỏ hơn kích thước
		if (pageNo <= 0 || count < size) {
			return "redirect:/home";
		}
//		tạo một đối tượng mới Pageablebằng PageRequest.of()
		Pageable pageable = PageRequest.of(pageNo, size);
//		lấy danh sách các sản phẩm từ phương thức productServicebằng cách gọi getProducts()phương thức và truyền pageableđối tượng dưới dạng tham số.
//		Các sản phẩm trả lại được lưu trữ trong productdanh sách
		List<Product> product = productService.getProducts(pageable);
		if (!product.isEmpty()) {
//			Tính toán số trang tiếp theo bằng cách tăng pageNogiá trị hiện tại lên 1
			int nextNum = pageNo + 1;
//			Tính toán số trang trước đó bằng cách giảm pageNogiá trị hiện tại đi 1
			int backNum = pageNo - 1;
//			tạo URL cho trang tiếp theo bằng cách nối "/products/" với giá nextNumtrị.
			String nextUrl = "/products/" + nextNum;
			String backUrl ="";
			if(pageNo == 0) {
				backUrl = "/products/" + 0;
			} else {
				backUrl = "/products/" + backNum;
			}
			model.addAttribute("pageNo", pageNo);
			model.addAttribute("nextUrl", nextUrl);
			model.addAttribute("backUrl", backUrl);
			model.addAttribute("products", product);
			log.info("Showing Products, Page No. "+pageNo);
		} else {
			log.info("In else ... There is no products.");
//			Nếu productdanh sách trống, nó đặt pageNothuộc tính thành 0
			model.addAttribute("pageNo", 0);
			model.addAttribute("products", product);
		}


		String index_QuickView = messageSource.getMessage("index_QuickView", null, LocaleContextHolder.getLocale());
		model.addAttribute("index_QuickView", index_QuickView);
		String index_AddToCart = messageSource.getMessage("index_AddToCart", null, LocaleContextHolder.getLocale());
		model.addAttribute("index_AddToCart", index_AddToCart);
		String index_BuyNow = messageSource.getMessage("index_BuyNow", null, LocaleContextHolder.getLocale());
		model.addAttribute("index_BuyNow", index_BuyNow);
		String index_ThereIsNoItems = messageSource.getMessage("index_ThereIsNoItems", null, LocaleContextHolder.getLocale());
		model.addAttribute("index_ThereIsNoItems", index_ThereIsNoItems);
		String index_LoadMoreItems = messageSource.getMessage("index_LoadMoreItems", null, LocaleContextHolder.getLocale());
		model.addAttribute("index_LoadMoreItems", index_LoadMoreItems);
		String index_GoBack = messageSource.getMessage("index_GoBack", null, LocaleContextHolder.getLocale());
		model.addAttribute("index_GoBack", index_GoBack);

		return "index";
	}

	@GetMapping("/contact")
	public String contactPage() {
		return "contact-us";
	}

	@PostMapping("/checkout")
	public String servicesPage() {
		return "checkout";
	}

	@GetMapping("/Add_Product")
	public String addProduct() {
		return "new-product";
	}

	@GetMapping("/about")
	public String aboutPage() {
		return "about-us";
	}

	@GetMapping("/page404")
	public String pageNotFound() {
		return "page404";
	}

}
