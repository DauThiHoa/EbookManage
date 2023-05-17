package com.ManageBookStore.ManageBookStore;

import com.ManageBookStore.ManageBookStore.config.DataSourceConfig;
import com.ManageBookStore.ManageBookStore.config.ProductTableChecker;
import com.ManageBookStore.ManageBookStore.entity.Product;
import com.ManageBookStore.ManageBookStore.repository.ProductRepository;
import com.ManageBookStore.ManageBookStore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.sql.DataSource;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@SpringBootApplication
@ComponentScan("com.ManageBookStore.ManageBookStore.service")
@ComponentScan("com.ManageBookStore.ManageBookStore.config")
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
public class ManageBookStoreApplication extends SpringBootServletInitializer  implements CommandLineRunner {

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductTableChecker productTableChecker;

	@Autowired
	private DataSourceConfig dataSourceConfig;


	@Override
	public void run(String... args) throws Exception {

		DataSource dataSource = dataSourceConfig.dataSource();

		ProductTableChecker checker = new ProductTableChecker(dataSource);
		boolean tableExists = checker.doesTableExist();

		if (!tableExists) {


		Product p1 = new Product("P1","The talented grandmother in the saga region", "Saburo Ishikawa" ,
				40.760 , 45.760 , "https://newshop.vn/public/uploads/products/52646/sach-nguoi-ba-tai-gioi-vung-saga-tap-11.jpg" ,
				true , new Date());
		Product p2 = new Product("P2","Tu thu - hard cover", "Quoc Trung" ,
				150.000 , 200.000 , "https://newshop.vn/public/uploads/products/52634/sach-tu-thu-bia-cung.jpg" ,
				true , new Date());
		Product p3 = new Product("P3","It is still fine even when you are alone", "Literature" ,
				80.600 , 83.600 , "https://newshop.vn/public/uploads/products/52635/sach-van-on-thoi-ke-ca-khi-ban-mot-minh.jpg" ,
				true , new Date());
		Product p4 = new Product("P4","I have to live fully every day", "EE SOEUN" ,
				100.600 , 105.600 , "https://newshop.vn/public/uploads/products/52636/minh-phai-song-that-tron-ven-moi-ngay.jpg" ,
				true , new Date());
		Product p5 = new Product("P5","Family scratching itch - collection when adults …… children",
				"Quang Nino" ,
				105.000 , 110.000 ,
				"https://newshop.vn/public/uploads/products/52625/kham-pha-va-chua-lanh-16-kieu-tinh-cach-qua-mbti.png" ,
				true , new Date());
		Product p6 = new Product("P6",
				"You are what you click - click virtual experience",
				"Brian A. Primack" ,
				120.960 , 124.960 ,
				"https://newshop.vn/public/uploads/products/52624/sach-buoc-vao-the-gioi-cam-xuc-be-nho-cua-tre_L.jpg" ,
				true , new Date());
		Product p7 = new Product("P7",
				"Vba programming guide for excel",
				"Pham Quang Hien" ,
				103.840 , 151.300 ,
				"https://newshop.vn/public/uploads/products/52639/sach-huong-dan-lap-trinh-vba-cho-excel.jpg" ,
				true , new Date());

		Product p8 = new Product("P8",
				"Autocad user manual with images",
				"Pham Quang Huy" ,
				103.840 , 156.400 ,
				"https://newshop.vn/public/uploads/products/52640/sach-huong-dan-su-dung-autocad-bang-hinh-anh.jpg" ,
				true , new Date());
		Product p9 = new Product("P9",
				"How smart children learn",
				"Chu Nhat Pham" ,
				86.120 , 87.120 ,
				"https://newshop.vn/public/uploads/products/52641/tre-thong-minh-hoc-tap-nhu-the-nao.jpg" ,
				true , new Date());
		Product p10 = new Product("P10",
				"200+ sets of questions and interviews in english",
				"Foreign language books" ,
				222.720 , 236.720 ,
				"https://newshop.vn/public/uploads/products/52284/sach-200-bo-cau-hoi-va-tra-loi-phong-van-tieng-anh.jpg" ,
				true , new Date());
		Product p11 = new Product("P11",
				"233 english sentence patterns conquer employers",
				"Foreign language books" ,
				200.320 , 210.320 ,
				"https://newshop.vn/public/uploads/products/41911/sach-tu-hoc-ngu-phap-tieng-anh-bang-mindmap-tap-2.jpg" ,
				true , new Date());
		Product p12 = new Product("P12",
				"Literature literature - grammaire francaise (with exercises and corrections",
				"Nguyen Kinh Doc" ,
				103.840 , 104.000 ,
				" https://newshop.vn/public/uploads/products/41910/sach-tu-hoc-ngu-phap-tieng-anh-bang-mindmap-tap-1.jpg" ,
				true , new Date());
		Product p13 = new Product("P13",
				"Combo mindmap english grammar - english grammar with mind map + mind map english vocabulary",
				"Do Nhung" ,
				300.400 , 310.400 ,
				"https://newshop.vn/public/uploads/products/41071/sach-tieng-anh-genz.jpg" ,
				true , new Date());
		Product p14 = new Product("P14",
				"Comprehensive supplementary exercises - english grammar (abb)",
				"Dong Nai" ,
				70.650 ,  75.650,
				"https://newshop.vn/public/uploads/products/51933/sach-bai-tap-bo-tro-toan-dien-ngu-phap-tieng-anh.jpg" ,
				true , new Date());
		Product p15 = new Product("P15",
				"Great technique to erase english blindness",
				"Mizuno Yuka" ,
				113.150,  118.150,
				"https://newshop.vn/public/uploads/products/51852/sach-sach-tuyet-ky-xoa-mu-tieng-anh.jpg" ,
				true , new Date());
		Product p16 = new Product("P16",
				"999 letters sent to themselves - the most impressive letters (chinese -vietnamese bilingual version",
				"Foreign language books" ,
				82.120,  87.120,
				"https://newshop.vn/public/uploads/products/51055/sach-cung-ban-truong-thanh_L.jpg" ,
				true , new Date());
		Product p17 = new Product("P17",
				"Everyday english for grown -ups - self -study english for busy people",
				"Mizuno Yuka" ,
				90.920,  95.920,
				"https://newshop.vn/public/uploads/products/50776/sach-nghien-ngu-phap-tieng-anh-hinh-que-tap-2-nang-cao_L.jpg" ,
				true , new Date());
		Product p18 = new Product("P18",
				"Korean dictionary (cm)",
				"Foreign language books" ,
				22.600,  30.600,
				"https://newshop.vn/public/uploads/products/51639/sach-tu-dien-han-viet-cao-minh.jpg" ,
				true , new Date());
		Product p19 = new Product("P19",
				"Grinding english grammar shaped - episode 1: basic",
				"Mizuno Yuka" ,
				4.600,  15.600,
				"https://newshop.vn/public/uploads/products/50775/sach-nghien-ngu-phap-tieng-anh-hinh-que-tap-1-co-ban_L.jpg" ,
				true , new Date());

		Product p20 = new Product("P20",
				"Road to - see again in the morning sun",
				"Mac Bao Phi Bao" ,
				112.240, 130.240 ,
				"https://newshop.vn/public/uploads/products/52615/duong-ve-gap-lai-duoi-nang-mai.jpg" ,
				true , new Date());
		Product p21 = new Product("P21",
				"Is this place",
				"Jodi Picoult" ,
				112.200,  135.200,
				"https://newshop.vn/public/uploads/products/52595/sach-co-phai-anh-noi-nay.jpg" ,
				true , new Date());
		Product p22 = new Product("P22",
				"Youth lost",
				"Alpha Books" ,
				100.520,  113.520,
				"https://newshop.vn/public/uploads/products/52513/sach-tuoi-tre-lac-loi.jpg" ,
				true , new Date());

		Product p23 = new Product("P23","Emotional life also needs at the right time", "Literature" ,
				103.840 , 99.8 , "https://newshop.vn/public/uploads/products/52533/sach-song-cam-xuc-cung-can-dung-luc.jpg" ,
				true , new Date());

		Product p24 = new Product("P24",
				"Bookcase read with children - folklore selected: childhood fairy garden - tam cam",
				"Dong Nai" ,
				30.250,  38.250,
				"https://newshop.vn/public/uploads/products/52447/tu-sach-doc-cung-con-van-hoc-dan-gian-tuyen-chon-vuon-co-tich-tuoi-tho-tam-cam.jpg" ,
				true , new Date());
		Product p25 = new Product("P25",
				"Reading bookcase with children - folklore selected: childhood fairy garden - bach tuyet and seven",
				"Dong Nai" ,
				34.250,  38.250,
				"https://newshop.vn/public/uploads/products/52445/tu-sach-doc-cung-con-van-hoc-dan-gian-tuyen-chon-vuon-co-tich-tuoi-tho-nang-bach-tuyet-va-bay-chu-lun.png" ,
				true , new Date());
		Product p26 = new Product("P26",
				"Women shu - gender and development: nam phong magazine - women issue in our country",
				"Phu Nu Viet Nam" ,
				159.280, 159.280 ,
				"https://newshop.vn/public/uploads/products/52443/khuyen-hoc-al.jpg" ,
				true , new Date());
		Product p27 = new Product("P27",
				"Close eyelashes and love",
				"Summer Kat" ,
				71.440,  77.440,
				"https://newshop.vn/public/uploads/products/52317/sach-khep-mi-lai-va-yeu.jpg" ,
				true , new Date());
		Product p28 = new Product("P28",
				"Don peek anymore, i like you too",
				"Summer Kat" ,
				100.800	,  108.800,
				"https://newshop.vn/public/uploads/products/52309/sach-dung-nhin-len-nua-anh-cung-thich-em.jpg" ,
				true , new Date());
		Product p29 = new Product("P29",
				"'Dandelion'",
				"Summer Kat" ,
				98.300,  100.300,
				"https://newshop.vn/public/uploads/products/52307/sach-bo-cong-anh.jpg" ,
				true , new Date());
		Product p30 = new Product("P30",
				"Recovery after depression and anxiety",
				"Ahra Kim" ,
				80.200,  87.200,
				"https://newshop.vn/public/uploads/products/52629/phuc-hoi-sau-tram-cam-va-lo-au.jpg" ,
				true , new Date());

		productService.saveProduct(p1);
		productService.saveProduct(p2);
		productService.saveProduct(p3);
		productService.saveProduct(p4);
		productService.saveProduct(p5);
		productService.saveProduct(p6);
		productService.saveProduct(p7);
		productService.saveProduct(p8);
		productService.saveProduct(p9);
		productService.saveProduct(p10);

		productService.saveProduct(p11);
		productService.saveProduct(p12);
		productService.saveProduct(p13);
		productService.saveProduct(p14);
		productService.saveProduct(p15);
		productService.saveProduct(p16);
		productService.saveProduct(p17);
		productService.saveProduct(p18);
		productService.saveProduct(p19);
		productService.saveProduct(p20);


		productService.saveProduct(p21);
		productService.saveProduct(p22);
		productService.saveProduct(p23);
		productService.saveProduct(p24);
		productService.saveProduct(p25);
		productService.saveProduct(p26);
		productService.saveProduct(p27);
		productService.saveProduct(p28);
		productService.saveProduct(p29);
		productService.saveProduct(p30);


		} else {
			return;
		}

	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ManageBookStoreApplication.class);
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(ManageBookStoreApplication.class, args);
	}


}
