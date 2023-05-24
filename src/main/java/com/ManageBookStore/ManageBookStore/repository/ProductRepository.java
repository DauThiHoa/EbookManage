package com.ManageBookStore.ManageBookStore.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ManageBookStore.ManageBookStore.entity.Product;
public interface ProductRepository extends JpaRepository<Product, Long> {

//	truy xuất một Product thực thể dựa trên thuộc tính mã
	@Query(value = "select p from Product p where p.code=?1")
	Product findProductByCode(String code);

//	tìm kiếm các sản phẩm dựa trên tên hoặc giá
	@Query(value = "select distinct p from Product p where locate(?1, p.name)>0 or locate(?1, p.price)>0 or p.price<=?1")
	List<Product> findSearchedProducts(String name);

//	truy xuất tất cả các sản phẩm đang hoạt động từ cơ sở dữ liệu, được sắp xếp theo ngày tạo
	@Query(value = "select p from Product p where p.active=true order by p.createDate desc")
	List<Product> findAllActiveProducts();

//	sắp xếp sản phẩm giảm dần theo giá
	@Query(value = "SELECT p FROM Product p WHERE p.active = true ORDER BY p.price DESC")
	List<Product> findAllActiveProductsOrderByPriceDesc();

	//	sắp xếp sản phẩm tăng dần theo giá
	@Query(value = "SELECT p FROM Product p WHERE p.active = true ORDER BY p.price ASC")
	List<Product> findAllActiveProductsOrderByPriceAsc();

	//	sắp xếp sản phẩm giảm dần theo tên
	@Query(value = "SELECT p FROM Product p WHERE p.active = true ORDER BY p.name DESC")
	List<Product> findAllActiveProductsOrderByNameDesc();

	//	sắp xếp sản phẩm tăng dần theo tên
	@Query(value = "SELECT p FROM Product p WHERE p.active = true ORDER BY p.name ASC")
	List<Product> findAllActiveProductsOrderByNameAsc();
//	findAllActiveProductsOrderByPriceAsc findAllActiveProductsOrderByNameDesc findAllActiveProductsOrderByNameAsc

	//	trả về một danh sách các sản phẩm được sắp xếp theo ngày tạo theo thứ tự giảm dần, giới hạn ở kích thước được chỉ
//	định trong đối tượng Pageable.
	@Query(value = "select p from Product p order by p.createDate desc")
	List<Product> findProducts(Pageable pageable);

//	Cap nhat thong tin khach hang
	@Modifying
	@Query(value = "update Product p set p.name =:name,p.description =:description,p.image =:image,p.mrpPrice =:mrpPrice,p.price =:price,p.active =:active where p.code =:code")
	void updateProduct(@Param("name") String name, @Param("description") String description, @Param("image") String imageData, 
	@Param("price") double price, @Param("mrpPrice") double mrpPrice, @Param("active") boolean active, String code);
}
