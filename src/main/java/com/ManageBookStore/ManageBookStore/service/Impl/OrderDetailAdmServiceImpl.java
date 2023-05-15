package com.ManageBookStore.ManageBookStore.service.Impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ManageBookStore.ManageBookStore.entity.OrderDetail;
import com.ManageBookStore.ManageBookStore.repository.OrderDetailRepository;
import com.ManageBookStore.ManageBookStore.service.OrderDetailAdmService;
@Service
@Transactional
public class OrderDetailAdmServiceImpl implements OrderDetailAdmService 
{
	@Autowired
	OrderDetailRepository orderDetRepo;

//	tìm nạp và trả về danh sách tất cả OrderDetailcác đối tượng từ kho lưu trữ dữ liệu cơ bản
	@Override
	public List<OrderDetail> getAllOrderDetail()
	{
		return orderDetRepo.findAll();
	}

//	lấy một OrderDetailđối tượng từ cơ sở dữ liệu dựa trên ID của nó
	@Override
	public Optional<OrderDetail> getOrderDetailId(Long pid)
	{
		return orderDetRepo.findById(pid);
	}

//	xóa một OrderDetailđối tượng khỏi cơ sở dữ liệu dựa trên ID của nó
	@Override
	public void deleteOrderDetail(Long pid) 
	{
		orderDetRepo.deleteById(pid);
	}

//	ruy xuất danh sách OrderDetailcác đối tượng từ cơ sở dữ liệu
//	dựa trên trạng thái đơn hàng đã cho.
	@Override
	public List<OrderDetail> getOrdersByStatus(String orderStatus) {
		return orderDetRepo.findOrdersByStatus(orderStatus);
	}

//	lấy danh sách tất cả OrderDetailcác đối tượng từ cơ sở dữ liệu.
	@Override
	public List<OrderDetail> getAllOrders() {
		return orderDetRepo.findAllOrders();
	}

//	truy xuất danh sách được phân trang của 10 OrderDetailđối tượng hàng đầu
//	dựa trên trạng thái đơn hàng đã cho
	@Override
	public List<OrderDetail> getTop10Orders(String orderStatus, Pageable pageable) {
		return orderDetRepo.findTop10OrdersByStatus(orderStatus, pageable);
	}

}
