package com.ManageBookStore.ManageBookStore.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ManageBookStore.ManageBookStore.entity.Product;
import com.ManageBookStore.ManageBookStore.exception.MyResourceNotFoundException;
import com.ManageBookStore.ManageBookStore.service.ProductService;

@Controller
@RequestMapping("/product")
public class ProductController {
	
	@Value("${upoadDir}")
	private String uploadFolder;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private ProductService productService;

	private static final Logger log = LoggerFactory.getLogger(ProductController.class);

//	code => Ma san pham => Lay SP => Theo ma code ( P1, P2,.. )
	@GetMapping("/productdetails")
	String showProductDetails(@RequestParam("code") String code, Product product,
			Model model) {
		try {
			log.info("Code :: " + code);
//			Neu Code != null & Bat dau bang chu cai "P"
			if (code != null && code.startsWith("P")) {
//				Lay SP theo ma code
				product = productService.getProductByCode(code);
				log.info("products :: " + product);
//				Neu co SP => Dua thong tin SP => Den trang chi tiet san pham voi ma code
				if (product != null) {
					model.addAttribute("product", product);
					return "productdetails";
				}
//				Neu khong co thi chuyen sang trang Home
				return "redirect:/home";
			}
		return "redirect:/home";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/home";
		}	
	}

//	Hien thi tat ca cac SP voi status = Active ( Hien thi )
//	=> Chuyen sang trang product_all
	@GetMapping("/all")
	String show(Model map) {
		List<Product> product = productService.getAllActiveProducts();
		map.addAttribute("products", product);

		String index_QuickView = messageSource.getMessage("index_QuickView", null, LocaleContextHolder.getLocale());
		map.addAttribute("index_QuickView", index_QuickView);
		String index_AddToCart = messageSource.getMessage("index_AddToCart", null, LocaleContextHolder.getLocale());
		map.addAttribute("index_AddToCart", index_AddToCart);
		String index_BuyNow = messageSource.getMessage("index_BuyNow", null, LocaleContextHolder.getLocale());
		map.addAttribute("index_BuyNow", index_BuyNow);

//		allPriceDesc allPriceAsc allNameDesc allNameAsc
		String allPriceDesc = messageSource.getMessage("allPriceDesc", null, LocaleContextHolder.getLocale());
		map.addAttribute("allPriceDesc", allPriceDesc);
		String allPriceAsc = messageSource.getMessage("allPriceAsc", null, LocaleContextHolder.getLocale());
		map.addAttribute("allPriceAsc", allPriceAsc);
		String allNameDesc = messageSource.getMessage("allNameDesc", null, LocaleContextHolder.getLocale());
		map.addAttribute("allNameDesc", allNameDesc);
		String allNameAsc = messageSource.getMessage("allNameAsc", null, LocaleContextHolder.getLocale());
		map.addAttribute("allNameAsc", allNameAsc);

		return "product_all";
	}

	@GetMapping("/allPriceDesc")
	String showProductPriceDesc (Model map) {
		//	findAllActiveProductsOrderByPriceDesc findAllActiveProductsOrderByPriceAsc
		// findAllActiveProductsOrderByNameDesc findAllActiveProductsOrderByNameAsc

		List<Product> product = productService.findAllActiveProductsOrderByPriceDesc();
		map.addAttribute("products", product);

		String index_QuickView = messageSource.getMessage("index_QuickView", null, LocaleContextHolder.getLocale());
		map.addAttribute("index_QuickView", index_QuickView);
		String index_AddToCart = messageSource.getMessage("index_AddToCart", null, LocaleContextHolder.getLocale());
		map.addAttribute("index_AddToCart", index_AddToCart);
		String index_BuyNow = messageSource.getMessage("index_BuyNow", null, LocaleContextHolder.getLocale());
		map.addAttribute("index_BuyNow", index_BuyNow);


//		allPriceDesc allPriceAsc allNameDesc allNameAsc
		String allPriceDesc = messageSource.getMessage("allPriceDesc", null, LocaleContextHolder.getLocale());
		map.addAttribute("allPriceDesc", allPriceDesc);
		String allPriceAsc = messageSource.getMessage("allPriceAsc", null, LocaleContextHolder.getLocale());
		map.addAttribute("allPriceAsc", allPriceAsc);
		String allNameDesc = messageSource.getMessage("allNameDesc", null, LocaleContextHolder.getLocale());
		map.addAttribute("allNameDesc", allNameDesc);
		String allNameAsc = messageSource.getMessage("allNameAsc", null, LocaleContextHolder.getLocale());
		map.addAttribute("allNameAsc", allNameAsc);

		return "product_all";
	}

	@GetMapping("/allPriceAsc")
	String showProductPriceAsc (Model map) {
		//	findAllActiveProductsOrderByPriceDesc findAllActiveProductsOrderByPriceAsc
		// findAllActiveProductsOrderByNameDesc findAllActiveProductsOrderByNameAsc

		List<Product> product = productService.findAllActiveProductsOrderByPriceAsc();
		map.addAttribute("products", product);

		String index_QuickView = messageSource.getMessage("index_QuickView", null, LocaleContextHolder.getLocale());
		map.addAttribute("index_QuickView", index_QuickView);
		String index_AddToCart = messageSource.getMessage("index_AddToCart", null, LocaleContextHolder.getLocale());
		map.addAttribute("index_AddToCart", index_AddToCart);
		String index_BuyNow = messageSource.getMessage("index_BuyNow", null, LocaleContextHolder.getLocale());
		map.addAttribute("index_BuyNow", index_BuyNow);



//		allPriceDesc allPriceAsc allNameDesc allNameAsc
		String allPriceDesc = messageSource.getMessage("allPriceDesc", null, LocaleContextHolder.getLocale());
		map.addAttribute("allPriceDesc", allPriceDesc);
		String allPriceAsc = messageSource.getMessage("allPriceAsc", null, LocaleContextHolder.getLocale());
		map.addAttribute("allPriceAsc", allPriceAsc);
		String allNameDesc = messageSource.getMessage("allNameDesc", null, LocaleContextHolder.getLocale());
		map.addAttribute("allNameDesc", allNameDesc);
		String allNameAsc = messageSource.getMessage("allNameAsc", null, LocaleContextHolder.getLocale());
		map.addAttribute("allNameAsc", allNameAsc);

		return "product_all";
	}

	@GetMapping("/allNameDesc")
	String showProductNameDesc (Model map) {
		//	findAllActiveProductsOrderByPriceDesc findAllActiveProductsOrderByPriceAsc
		// findAllActiveProductsOrderByNameDesc findAllActiveProductsOrderByNameAsc

		List<Product> product = productService.findAllActiveProductsOrderByNameDesc();
		map.addAttribute("products", product);

		String index_QuickView = messageSource.getMessage("index_QuickView", null, LocaleContextHolder.getLocale());
		map.addAttribute("index_QuickView", index_QuickView);
		String index_AddToCart = messageSource.getMessage("index_AddToCart", null, LocaleContextHolder.getLocale());
		map.addAttribute("index_AddToCart", index_AddToCart);
		String index_BuyNow = messageSource.getMessage("index_BuyNow", null, LocaleContextHolder.getLocale());
		map.addAttribute("index_BuyNow", index_BuyNow);


//		allPriceDesc allPriceAsc allNameDesc allNameAsc
		String allPriceDesc = messageSource.getMessage("allPriceDesc", null, LocaleContextHolder.getLocale());
		map.addAttribute("allPriceDesc", allPriceDesc);
		String allPriceAsc = messageSource.getMessage("allPriceAsc", null, LocaleContextHolder.getLocale());
		map.addAttribute("allPriceAsc", allPriceAsc);
		String allNameDesc = messageSource.getMessage("allNameDesc", null, LocaleContextHolder.getLocale());
		map.addAttribute("allNameDesc", allNameDesc);
		String allNameAsc = messageSource.getMessage("allNameAsc", null, LocaleContextHolder.getLocale());
		map.addAttribute("allNameAsc", allNameAsc);

		return "product_all";
	}

	@GetMapping("/allNameAsc")
	String showProductNameAsc (Model map) {
		//	findAllActiveProductsOrderByPriceDesc findAllActiveProductsOrderByPriceAsc
		// findAllActiveProductsOrderByNameDesc findAllActiveProductsOrderByNameAsc

		List<Product> product = productService.findAllActiveProductsOrderByNameAsc();
		map.addAttribute("products", product);

		String index_QuickView = messageSource.getMessage("index_QuickView", null, LocaleContextHolder.getLocale());
		map.addAttribute("index_QuickView", index_QuickView);
		String index_AddToCart = messageSource.getMessage("index_AddToCart", null, LocaleContextHolder.getLocale());
		map.addAttribute("index_AddToCart", index_AddToCart);
		String index_BuyNow = messageSource.getMessage("index_BuyNow", null, LocaleContextHolder.getLocale());
		map.addAttribute("index_BuyNow", index_BuyNow);

//		allPriceDesc allPriceAsc allNameDesc allNameAsc
		String allPriceDesc = messageSource.getMessage("allPriceDesc", null, LocaleContextHolder.getLocale());
		map.addAttribute("allPriceDesc", allPriceDesc);
		String allPriceAsc = messageSource.getMessage("allPriceAsc", null, LocaleContextHolder.getLocale());
		map.addAttribute("allPriceAsc", allPriceAsc);
		String allNameDesc = messageSource.getMessage("allNameDesc", null, LocaleContextHolder.getLocale());
		map.addAttribute("allNameDesc", allNameDesc);
		String allNameAsc = messageSource.getMessage("allNameAsc", null, LocaleContextHolder.getLocale());
		map.addAttribute("allNameAsc", allNameAsc);

		return "product_all";
	}

//	Tim kiem san pham theo ten hoac SP co gia nho hon hoac bang gia nhap vao
	@PostMapping("/search")
	String searchProducts(@RequestParam(value = "keyword", required = true) String keyword, Model model) {
		try {
			if (keyword != null) {
				List<Product> products = productService.searchProducts(keyword.trim());
				log.info("products :: "+products);
//				Them san pham vao Model => Chuyen trang search

				model.addAttribute("products", products);

				String index_QuickView = messageSource.getMessage("index_QuickView", null, LocaleContextHolder.getLocale());
				model.addAttribute("index_QuickView", index_QuickView);
				String index_AddToCart = messageSource.getMessage("index_AddToCart", null, LocaleContextHolder.getLocale());
				model.addAttribute("index_AddToCart", index_AddToCart);
				String index_BuyNow = messageSource.getMessage("index_BuyNow", null, LocaleContextHolder.getLocale());
				model.addAttribute("index_BuyNow", index_BuyNow);


//		allPriceDesc allPriceAsc allNameDesc allNameAsc
				String allPriceDesc = messageSource.getMessage("allPriceDesc", null, LocaleContextHolder.getLocale());
				model.addAttribute("allPriceDesc", allPriceDesc);
				String allPriceAsc = messageSource.getMessage("allPriceAsc", null, LocaleContextHolder.getLocale());
				model.addAttribute("allPriceAsc", allPriceAsc);
				String allNameDesc = messageSource.getMessage("allNameDesc", null, LocaleContextHolder.getLocale());
				model.addAttribute("allNameDesc", allNameDesc);
				String allNameAsc = messageSource.getMessage("allNameAsc", null, LocaleContextHolder.getLocale());
				model.addAttribute("allNameAsc", allNameAsc);


				return "search";
			}
//			Neu khong co kq phu hop chuyen sang trang home
			return "redirect:/home";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/home";
		}
	}

//	Dat hang
	@PostMapping("/checkout")
	String productCheckout(@RequestParam("code") String code,HttpServletRequest request, HttpSession session,
						   Product product, Model model) {
		try {
			session = request.getSession(false);
			String email = (String) session.getAttribute("email");

//			Neu chua dang nhap => Ve trang Login => Dang nhap de xem cac san pham trong gio hang
//			=> Thuc hien dang nhap
			if(email == null) {
				String backUrl = request.getHeader("referer");
				log.info("backUrl :: "+backUrl);
				session.setAttribute("backUrl", backUrl);
				return "redirect:/customer/login";
			}
			log.info("Code :: "+code);
//			Lay ma code cua san pham
			if (code != null && code.startsWith("P")) {
//				Lay san pham trung voi ma code duoc truyen vao
				product = productService.getProductByCode(code);
				log.info("product :: " + product.getName());
				if (product != null) {
//					Chuyen du lieu cac san pham vao trang checkout => Gio hang
					model.addAttribute("code", code);
					model.addAttribute("image", product.getImage());
					model.addAttribute("itemName", product.getName());
					model.addAttribute("quantity", "1");
					model.addAttribute("price", product.getPrice());
					model.addAttribute("totalPrice", product.getPrice());
					session.setAttribute("code", code);
					session.setAttribute("itemId", product.getId());
					session.setAttribute("quantity", 1);
					session.setAttribute("price", product.getPrice());
					session.setAttribute("totalPrice", product.getPrice());
					return "checkout";
				}
			}
			return "redirect:/page404";
		}  catch (Exception e) {
			e.printStackTrace();
			return "redirect:/home";
		}
	}

	@ExceptionHandler(MyResourceNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(MyResourceNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}
}
