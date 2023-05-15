package com.ManageBookStore.ManageBookStore.service.Impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ManageBookStore.ManageBookStore.entity.Product;
import com.ManageBookStore.ManageBookStore.repository.ProductAdmRepository;
import com.ManageBookStore.ManageBookStore.service.ProductAdmService;


@Service
@Transactional
public class ProductAdmServiceImpl implements ProductAdmService
{
	@Autowired
	ProductAdmRepository productRepo;

//	Them San Pham
	@Override
	public Product storeFile(Product product) {
		return productRepo.save(product);
	}

//	Lay san pham co ma duoc truyen vao
	@Override
	public Optional<Product> getProductId(Long pid) {
		return productRepo.findById(pid);
	}

//	Lay tat ca cac san pham
	@Override
	public List<Product> getAllProduct() {
		return productRepo.findAll();
	}

//	Xoa san pham
	@Override
	public void deleteProduct(Long pid) {
		productRepo.deleteById(pid);
	}

//	Xoa danh sach san pham
	@Override
	public void deleteAll(List<Product> ids) {

	}

//	Dem so luong san pham
	@Override
	public Long countProduct() {
		return productRepo.count();
	}

}
