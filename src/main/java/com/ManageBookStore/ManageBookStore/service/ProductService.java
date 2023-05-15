package com.ManageBookStore.ManageBookStore.service;

import java.util.List;
import org.springframework.data.domain.Pageable;

import com.ManageBookStore.ManageBookStore.entity.Product;

public interface ProductService {

//	Luu san ham
	boolean saveProduct(Product product);

//	lAY TAT CAC CAC SAN PHAM DUOC HIEN THI
	List<Product> getAllActiveProducts();
	List<Product> getProducts(Pageable pageable);

//	xem thong tin chi tiet cua san pham
	public Product getProductByCode(String code);

//	Tim kiem san pham
	List<Product> searchProducts(String name);

//	Chinh sua san pham
	void updateProductByCode(String name, String description, String imageData, double mrpPrice, double price, boolean active, String code);
}
