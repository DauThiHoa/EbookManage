package com.ManageBookStore.ManageBookStore.service;

import java.util.List;
import java.util.Optional;

import com.ManageBookStore.ManageBookStore.entity.Product;

public interface ProductAdmService
{
	public Product storeFile(Product product);

//	Lay San pham voi Id
	public Optional<Product> getProductId(Long pid);

//	Lay toan bo Sp
	public List<Product> getAllProduct();

//	Xoa SP
	public void deleteProduct(Long pid);

//	Xoa danh sach san pham
	public void deleteAll(List<Product> ids);

//	Dem so luoc San Pham
	public Long countProduct();
}
