package com.ManageBookStore.ManageBookStore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ManageBookStore.ManageBookStore.entity.Order;


public interface OrderAdmRepository extends JpaRepository<Order, Long>
{
//	cập nhật trạng thái đơn hàng của một OrderDetail
//	đối tượng trong cơ sở dữ liệu dựa trên ID của nó
	@Modifying
	@Query(value = "update OrderDetail o set o.orderStatus =:orderStatus where o.id =:id")
	void updateOrderStatusById(@Param("orderStatus") String orderStatus, @Param("id") Long id);

//	xóa một Orderđối tượng khỏi cơ sở dữ liệu dựa trên ID của nó
	@Modifying
	@Query(value = "delete from Order o where o.id =:id")
	void deleteOrdersById(@Param("id") Long id);

//	xóa OrderDetailcác đối tượng khỏi cơ sở dữ liệu dựa trên OrderID
//	được liên kết của chúng
	@Modifying
	@Query(value = "delete from OrderDetail o where o.order =:orderId")
	void deleteOrderDetailById(@Param("orderId") Order orderId);

}
