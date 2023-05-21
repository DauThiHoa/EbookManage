package com.ManageBookStore.ManageBookStore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ManageBookStore.ManageBookStore.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

	Customer findByEmail(String email);

//	tìm một khách hàng trong cơ sở dữ liệu dựa trên số điện thoại của họ
	@Query(value = "select c from Customer c where c.phone=?1")
	Customer checkCustomerPhone(String phone);

//	tìm một khách hàng trong cơ sở dữ liệu dựa trên email của họ
	@Query(value = "select c from Customer c where c.email=?1")
	Customer checkCustomerByEmail(String email);

//	lấy lại mật khẩu của khách hàng dựa trên email của họ
	@Query(value = "select c.password from Customer c where c.email=?1")
	String getCustomerPasswordByEmail(String email);

//	kiểm tra xem khách hàng có email và mật khẩu
//	đã cho có tồn tại và hợp lệ trong cơ sở dữ liệu hay không
	@Query(value = "select c.valid from Customer c where c.email=?1 and c.password=?2")
	boolean findValidCustomer(String email, String password);

//	 cập nhật mật khẩu của khách hàng trong cơ sở dữ liệu dựa trên email của họ
	@Modifying(clearAutomatically = true)
	@Query("update Customer c set c.password =:password where c.email =:email")
	void changePassword(@Param("email") String email, @Param("password") String password);

//	lấy ID của khách hàng dựa trên email của họ
	@Query(value = "select c.id from Customer c where c.email=?1")
	Long findCustomerId(String customerEmail);

//	 cập nhật thông tin của khách hàng trong cơ sở dữ liệu dựa trên ID của họ
	@Modifying(clearAutomatically = true)
	@Query("update Customer c set c.name =:name,c.address =:address,c.gender = :gender,c.phone =:phone,c.pinCode =:pinCode where c.id =:id")
	void updateMyCustomer(@Param("id") Long id,@Param("name") String name, @Param("address") String address,@Param("gender") String gender, 
			@Param("phone") String phone, @Param("pinCode") String pinCode);
	
	
	
}
