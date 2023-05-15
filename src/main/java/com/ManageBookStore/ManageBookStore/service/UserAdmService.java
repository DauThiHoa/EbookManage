package com.ManageBookStore.ManageBookStore.service;

import java.util.List;
import java.util.Optional;

import com.ManageBookStore.ManageBookStore.entity.Customer;

public interface UserAdmService
{
//	trả về một Optional<Customer>giá trị có thể chứa khách hàng nếu tìm thấy hoặc để trống nếu
//	không tồn tại khách hàng nào có ID đã chỉ định.
	public Optional<Customer> getCustomerId(Long pid);

//	Lay danh sach tat ca cac khach hang
	public List<Customer> getAllCustomer();

//	xóa một khách hàng có ID được chỉ định
	public void deleteCustomer(Long pid);

//	xóa tất cả các khách hàng được chỉ định trong danh sách
	public void deleteAll(List<Customer> ids);
}
