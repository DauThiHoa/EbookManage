package com.ManageBookStore.ManageBookStore.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ManageBookStore.ManageBookStore.entity.Order;
import com.ManageBookStore.ManageBookStore.entity.OrderDetail;
import com.ManageBookStore.ManageBookStore.entity.Product;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

//	truy xuất danh sách chi tiết đơn hàng ( OrderDetail) dựa trên các tiêu chí cụ thể
	@Query("select od from OrderDetail od join Order o on od.order = o.id where o.id in :id and o.customerEmail =:customerEmail order by o.id desc")
	List<OrderDetail> findOrdersByOrderId(@Param("id") Set<Long> id, @Param("customerEmail") String customerEmail);

//	chèn một bản ghi mới vào order_detailbảng với các giá trị được cung cấp
	@Modifying
	@Query(value = "insert into order_detail (order_id, product_id, quantity, mrp_price, price, amount, payment_id, order_status, payment_mode) values (:order, :product, :quantity, :mrpPrice, :amount, :totalPrice, :paymentId, :orderStatus, :paymentMode)", nativeQuery = true)
	void saveOrderDetails(@Param("order") Order orders, @Param("product") Product products, @Param("quantity") int quantity,
			@Param("mrpPrice") double mrpPrice, @Param("amount") double amount, @Param("totalPrice") double totalPrice, @Param("paymentId") double paymentId, @Param("orderStatus") String orderStatus, @Param("paymentMode") String paymentMode);

//	xóa các bản ghi khỏi OrderDetailbảng dựa trên ID thanh toán cụ thể
	@Modifying(clearAutomatically = true)
	@Query(value = "delete from OrderDetail od where od.paymentId=?1")
	void deleteOrderDetailByNum(int paymentId);

//	truy xuất danh sách OrderDetailcác đối tượng dựa trên ID thanh toán cụ thể
	@Query(value= "select od from OrderDetail od where od.paymentId=?1")
	List<OrderDetail> findOrderByPayId(int paymentId);

//	tìm và trả về danh sách OrderDetailcác đối tượng dựa trên trạng thái đơn hàng của chúng, được sắp xếp theo ngày của
//	đơn hàng tương ứng theo thứ tự giảm dần
	@Query("select od from OrderDetail od join Order o on od.order = o.id where od.orderStatus= :orderStatus order by o.orderDate desc")
	List<OrderDetail> findOrdersByStatus(@Param("orderStatus") String orderStatus);

//	 tìm và trả về 10 OrderDetailđối tượng hàng đầu dựa trên trạng thái đơn hàng của chúng, được sắp xếp theo ngày của
//	 đơn hàng tương ứng theo thứ tự giảm dần và được phân trang theo đối tượng được cung cấp Pageable
	@Query("select od from OrderDetail od join Order o on od.order = o.id where od.orderStatus= :orderStatus order by o.orderDate desc")
	List<OrderDetail> findTop10OrdersByStatus(@Param("orderStatus") String orderStatus, Pageable pageable);

//	truy xuất tất cả OrderDetailcác đối tượng cùng với Ordercác đối tượng được liên kết của chúng, được sắp xếp theo
//	ID thứ tự giảm dần.
	@Query("select od from OrderDetail od join Order o on od.order = o.id order by o.id desc")
	List<OrderDetail> findAllOrders();

//	truy xuất danh sách OrderDetailđối tượng được phân trang, được sắp xếp theo thứ tự ngày giảm dần, trang cụ thể và
//	kích thước của phân trang được xác định bởi đối Pageabletượng được cung cấp
	@Query(value = "select o from OrderDetail o order by o.order desc")
	List<OrderDetail> getLastOrder(Pageable pageable);
} 