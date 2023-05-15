package com.ManageBookStore.ManageBookStore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ManageBookStore.ManageBookStore.entity.Cart;
import com.ManageBookStore.ManageBookStore.entity.Customer;

public interface CartRepository extends JpaRepository<Cart, Long> {

//	truy xuất danh sách các mặt hàng trong giỏ hàng được liên kết với
//	một khách hàng cụ thể bằng cách sử dụng truy vấn tùy chỉnh
	@Query(value = "select c from Cart c where c.customer=?1")
	List<Cart> findCartItemsByCustomer(Customer customer);

//	xóa các mặt hàng cụ thể trong giỏ hàng được liên kết với
//	một khách hàng dựa trên ID mặt hàng và khách hàng
	@Modifying(clearAutomatically = true)
	@Query(value = "delete from Cart c where c.customer=?1 and c.id=?2")
	void deleteCartItems(Customer customer, Long id);

//	xóa một mặt hàng cụ thể trong giỏ hàng dựa trên ID của mặt hàng đó
	@Modifying(clearAutomatically = true)
	@Query(value = "delete from Cart c where c.id=?1")
	void deleteCartItem(Long id);
	
}
