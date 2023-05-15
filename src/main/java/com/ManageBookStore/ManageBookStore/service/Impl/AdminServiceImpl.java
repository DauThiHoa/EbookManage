package com.ManageBookStore.ManageBookStore.service.Impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ManageBookStore.ManageBookStore.entity.Admin;
import com.ManageBookStore.ManageBookStore.repository.AdminRepository;
import com.ManageBookStore.ManageBookStore.service.AdminService;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminRepository adminRepository;

//	xác thực thông tin đăng nhập của người dùng quản trị viên
	@Override
	public Admin validateAdmin(String email, String password) {
		if (email != null && password != null) {
			return adminRepository.checkAdminLogin(email, password);
		}
		return null;
	}

//	lấy một Adminđối tượng dựa trên email được cung cấp
	@Override
	public Admin getAdminByEmail(String email) {
		return adminRepository.findAdminByEmail(email);
	}

//	truy xuất một Adminđối tượng dựa trên ID quản trị viên được cung cấp
	@Override
	public Optional<Admin> getByAdminId(Long id) 
	{
		return adminRepository.findById(id);
	}

//	cập nhật mật khẩu cho người dùng quản trị bằng email được cung cấp
	@Override
	public int changePassword(String password, String email)
	{
		return adminRepository.changePassword(password, email);
	}

//	cập nhật tên và email của người dùng quản trị viên dựa trên ID quản trị viên được cung cấp
	@Override
	public void updateAdminByEmail(String name, String email, Long id) {
		adminRepository.updateAdmin(name, email, id);	
	}
}
