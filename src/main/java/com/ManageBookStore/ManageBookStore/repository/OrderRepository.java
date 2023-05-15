package com.ManageBookStore.ManageBookStore.repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ManageBookStore.ManageBookStore.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

//	 truy xuất danh sách các đơn đặt hàng được liên kết với một email khách hàng cụ thể
	@Query(value = "select o from Order o where o.customerEmail=?1")
	List<Order> findAllOrdersByCustomer(String customerEmail);

//	nhận Stringtham số đại customerEmaildiện cho địa chỉ email của khách hàng. Nó trả về một Settrong Longcác đối
//	tượng chứa ID đơn hàng được liên kết với email đó
	@Query(value = "select o.id from Order o where o.customerEmail=?1")
	Set<Long> findOrderIdByEmail(String customerEmail);

//	xóa các đơn đặt hàng khỏi cơ sở dữ liệu có số thứ tự được chỉ định
	@Modifying(clearAutomatically = true)
	@Query(value = "delete from Order o where o.orderNum =:orderNum")
	void removeOrdersByNum(@Param("orderNum") int orderNum);

//	chèn một hàng mới vào bảng "orders"
	@Modifying
	@Query(value = "insert into orders (order_num, customer_name, customer_email, customer_phone, customer_address, customer_address_type, amount, active, order_date) VALUES (:orderNum, :customerName, :customerEmail, :customerPhone, :customerAddress, :customerAddressType, :amount, :active, :orderDate)", nativeQuery = true)
	void saveOrders(@Param("orderNum") int orderNum, @Param("customerName") String customerName,@Param("customerEmail") String customerEmail,@Param("customerPhone") String customerPhone,@Param("customerAddress") String customerAddress,@Param("customerAddressType") String customerAddressType, @Param("amount") double amount,
	@Param("active") boolean active, @Param("orderDate") Date orderDate);

//	Sap xep sắp xếp kết quả theo trường "id" theo thứ tự giảm dần
	@Query(value = "select o from Order o order by o.id desc")
	List<Order> getLastOrder(Pageable pageable);

//	Nó lấy ID của đơn đặt hàng khớp với số thứ tự đã cho
	@Query(value = "select o.id from Order o where o.orderNum=?1")
	Long findOrderIdByNum(int orderNum);
}
