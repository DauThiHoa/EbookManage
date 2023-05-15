package com.ManageBookStore.ManageBookStore.service.Impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ManageBookStore.ManageBookStore.entity.Cart;
import com.ManageBookStore.ManageBookStore.entity.Customer;
import com.ManageBookStore.ManageBookStore.repository.CartRepository;
import com.ManageBookStore.ManageBookStore.service.CartService;

@Service
@Transactional
public class CartServiceImpl implements CartService {
	
	@Autowired
	private CartRepository cartRepository;

//	truy xuất danh sách các mặt hàng trong giỏ hàng được liên kết với một khách hàng cụ thể
	@Override
	public List<Cart> getCartItemsByCustomerId(Customer customer) {
		return cartRepository.findCartItemsByCustomer(customer);
	}

//	xóa các mặt hàng cụ thể trong giỏ hàng được liên kết với khách hàng
	@Override
	public void removeCartItems(Customer customer, Long id) {
		cartRepository.deleteCartItems(customer, id);
	}

//	xóa một mặt hàng cụ thể trong giỏ hàng được xác định bằng ID của mặt hàng đó
	@Override
	public void removeCartItem(Long id) {
		cartRepository.deleteCartItem(id);
	}

//	lưu danh sách các mặt hàng trong giỏ hàng
	@Override
	public void saveCartItems(List<Cart> cartsItems) {
		cartRepository.saveAll(cartsItems);
	}

//	lưu một mặt hàng trong giỏ hàng
	@Override
	public void saveCart(Cart cart) {
		cartRepository.save(cart);
	}

}
