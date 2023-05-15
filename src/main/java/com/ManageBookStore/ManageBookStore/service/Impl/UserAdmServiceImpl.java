package com.ManageBookStore.ManageBookStore.service.Impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ManageBookStore.ManageBookStore.entity.Customer;
import com.ManageBookStore.ManageBookStore.repository.UserAdmRepository;
import com.ManageBookStore.ManageBookStore.service.UserAdmService;

@Service
@Transactional
public class UserAdmServiceImpl implements UserAdmService
{
//	 Hien thuc cac phuong thuc qua lop UserAdmRepository den bang Customer
	@Autowired
	UserAdmRepository userAdmRepo;

//	truy xuất khách hàng theo ID của họ từ kho lưu trữ userAdmRepo
	@Override
	public Optional<Customer> getCustomerId(Long pid)
	{
		
		return userAdmRepo.findById(pid);
	}

//	truy xuất tất cả khách hàng từ kho lưu trữ userAdmRepo
	@Override
	public List<Customer> getAllCustomer()
	{

		return userAdmRepo.findAll();
	}

//	Xoa 1 khach hang vs id
	@Override
	public void deleteCustomer(Long pid) 
	{
		userAdmRepo.deleteById(pid);
		
	}

//	Xoa danh sach khach hang
	@Override
	public void deleteAll(List<Customer> ids) 
	{
		
		
	}
	
}
