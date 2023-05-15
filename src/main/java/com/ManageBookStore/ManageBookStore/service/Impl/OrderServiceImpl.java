package com.ManageBookStore.ManageBookStore.service.Impl;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ManageBookStore.ManageBookStore.entity.Order;
import com.ManageBookStore.ManageBookStore.repository.OrderRepository;
import com.ManageBookStore.ManageBookStore.service.OrderService;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

//	Them san pham
	@Override
	public boolean saveProductOrder(Order order) {
		boolean status = false;
		if (order != null) {
			status = true;
			orderRepository.save(order);
		}
		return status;
	}

//	 truy xuất tất cả các đơn đặt hàng được liên kết với một khách hàng cụ thể
	@Override
	public List<Order> getAllOrdersByCustomer(String customerEmail) {
		return orderRepository.findAllOrdersByCustomer(customerEmail);
	}

//	xóa các đơn đặt hàng dựa trên số thứ tự
	@Override
	public void deleteOrdersByNum(int orderNum) {
		orderRepository.removeOrdersByNum(orderNum);	
	}

//	truy xuất ID đơn đặt hàng được liên kết với email của khách hàng
	@Override
	public Set<Long> getOrderIdByEmail(String customerEmail) {
		// TODO Auto-generated method stub
		return orderRepository.findOrderIdByEmail(customerEmail);
	}

//	lưu đơn đặt hàng vao trong cơ sở dữ liệu
	@Override
	public boolean saveCartOrder(int orderNum, String customerName, String customerEmail, String customerPhone,
			String customerAddress, String customerAddressType, double amount, boolean active, Date orderDate) {
		boolean status = false;
		if (orderNum != 0) {
			status = true;
			orderRepository.saveOrders(orderNum, customerName, customerEmail, customerPhone, customerAddress, customerAddressType, amount, active, orderDate);		}
		return status;
	}

//	truy xuất ID đơn đặt hàng được liên kết với một số đơn đặt hàng cụ thể
	@Override
	public Long getOrderIdByNum(int orderNum) {
		return orderRepository.findOrderIdByNum(orderNum);
	}

//	lưu nhiều đơn đặt hàng cùng một lúc
	@Override
	public void saveOrders(List<Order> orders) {
		orderRepository.saveAll(orders);
		
	}

//	lấy các đơn đặt hàng cuối cùng từ cơ sở dữ liệu, được phân trang và sắp xếp theo chỉ định của đối tượng pageable
	@Override
	public List<Order> getLastOrderByIdDesc(Pageable pageable) {
		return orderRepository.getLastOrder(pageable);
	}

//	dễ dàng truy xuất số lượng đơn đặt hàng
	@Override
	public Long getOrdersCount() {
		return orderRepository.count();
	}

}
