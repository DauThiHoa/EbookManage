package com.ManageBookStore.ManageBookStore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ManageBookStore.ManageBookStore.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {

//	kiểm tra thông tin đăng nhập của người dùng quản trị viên bằng cách
//	xác minh email và mật khẩu được cung cấp
	@Query(value = "select a from Admin a where a.email=?1 and a.password=?2")
	Admin checkAdminLogin(String email, String password);

//	lấy một Adminđối tượng dựa trên email được cung cấp
	@Query(value = "select a from Admin a where a.email=?1")
	Admin findAdminByEmail(String email);

//	cập nhật mật khẩu cho người dùng quản trị bằng email được cung cấp
	@Modifying
    @Query("UPDATE Admin c SET c.password = :password WHERE c.email = :email")
    int changePassword(@Param("password") String password, @Param("email") String email);

//	cập nhật tên và email của người dùng quản trị viên dựa trên ID quản trị viên được cung cấp
	@Modifying
    @Query("UPDATE Admin a SET a.name = :name,a.email =:email WHERE a.id = :id")
	void updateAdmin(@Param("name") String name, @Param("email") String email, @Param("id") Long id);
}
