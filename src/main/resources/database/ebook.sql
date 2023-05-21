-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th5 21, 2023 lúc 04:35 AM
-- Phiên bản máy phục vụ: 10.4.27-MariaDB
-- Phiên bản PHP: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `ebook`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `admin`
--

CREATE TABLE `admin` (
  `id` bigint(20) NOT NULL,
  `added_date` datetime NOT NULL,
  `email` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `admin`
--

INSERT INTO `admin` (`id`, `added_date`, `email`, `name`, `password`) VALUES
(1, '2023-05-13 20:29:56', 'admin@gmail.com', 'admin', '123456');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `cart`
--

CREATE TABLE `cart` (
  `id` bigint(20) NOT NULL,
  `mrp_price` double NOT NULL,
  `price` double NOT NULL,
  `quantity` int(11) NOT NULL,
  `total_price` double NOT NULL,
  `customer_id` bigint(20) NOT NULL,
  `product_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `cart`
--

INSERT INTO `cart` (`id`, `mrp_price`, `price`, `quantity`, `total_price`, `customer_id`, `product_id`) VALUES
(13, 100.6, 105.6, 1, 105.6, 3, 4),
(14, 80.6, 83.6, 1, 83.6, 3, 3),
(15, 80.6, 83.6, 1, 83.6, 3, 3);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `contact`
--

CREATE TABLE `contact` (
  `id` bigint(20) NOT NULL,
  `contact_date` datetime NOT NULL,
  `email` varchar(255) NOT NULL,
  `message` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `subject` varchar(255) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `contact`
--

INSERT INTO `contact` (`id`, `contact_date`, `email`, `message`, `name`, `subject`) VALUES
(1, '2023-05-14 22:30:15', '19130075@st.hcmuaf.edu.vn', 'ss', 'Đậu Hoa', 'Content');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `customer`
--

CREATE TABLE `customer` (
  `id` bigint(20) NOT NULL,
  `address` varchar(255) NOT NULL,
  `added_date` datetime NOT NULL,
  `email` varchar(255) NOT NULL,
  `gender` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phone` varchar(255) NOT NULL,
  `pin_code` varchar(255) NOT NULL,
  `valid` bit(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `customer`
--

INSERT INTO `customer` (`id`, `address`, `added_date`, `email`, `gender`, `name`, `password`, `phone`, `pin_code`, `valid`) VALUES
(4, 'tp.hcm', '2023-05-14 11:08:52', 'daudiep2003@gmail.com', 'Female', 'Dau Diep', '$2a$10$BVfDz95HkKzXMwWnI35Yz.fOTa/K//28IPnNveX.T2WbEA7w7iZq.', '0863572876', '123456', b'1'),
(3, 'TP.HCM', '2023-05-14 05:44:40', '19130075@st.hcmuaf.edu.vn', 'Female', 'Dau Hoa', '$2a$10$6K0sg3/6odObOjPF/.9hgOOJPaSxrgnx1I3zXCKj/.3iNipjM1M3q', '0397080549', '124561', b'1');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `orders`
--

CREATE TABLE `orders` (
  `id` bigint(20) NOT NULL,
  `active` bit(1) NOT NULL,
  `amount` double NOT NULL,
  `customer_address` varchar(255) NOT NULL,
  `customer_address_type` varchar(15) NOT NULL,
  `customer_email` varchar(50) NOT NULL,
  `customer_name` varchar(30) NOT NULL,
  `customer_phone` varchar(10) NOT NULL,
  `order_date` datetime NOT NULL,
  `order_num` int(11) NOT NULL,
  `pin_code` varchar(10) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `orders`
--

INSERT INTO `orders` (`id`, `active`, `amount`, `customer_address`, `customer_address_type`, `customer_email`, `customer_name`, `customer_phone`, `order_date`, `order_num`, `pin_code`) VALUES
(1, b'1', 118.15, 'tphcm', 'Home', 'daudiep2003@gmail.com', '&#272;&#7853;u', '0397080549', '2023-05-14 01:25:33', 1000, '111111'),
(2, b'1', 756.5, 'TP.HCM', 'Home', '19130075@st.hcmuaf.edu.vn', 'Dau Hoa', '0397080549', '2023-05-14 05:59:57', 1001, '124561'),
(3, b'1', 104, 'TP.HCM', 'Home', '19130075@st.hcmuaf.edu.vn', 'Dau Hoa', '0397080549', '2023-05-14 06:02:46', 1002, '124561'),
(4, b'1', 260.48, 'TP.HCM', 'Home', '19130075@st.hcmuaf.edu.vn', 'Dau Hoa', '0397080549', '2023-05-14 06:02:46', 1003, '124561'),
(5, b'1', 500, 'TP.HCM', 'Home', '19130075@st.hcmuaf.edu.vn', 'Dau Hoa', '0397080549', '2023-05-14 15:27:15', 1004, '124561'),
(6, b'1', 118.15, 'TP.HCM', 'Home', '19130075@st.hcmuaf.edu.vn', 'Dau Hoa', '0397080549', '2023-05-15 21:48:18', 1005, '124561'),
(7, b'1', 45.76, 'TP.HCM', 'Home', '19130075@st.hcmuaf.edu.vn', 'Dau Hoa', '0397080549', '2023-05-15 21:48:18', 1006, '124561'),
(8, b'1', 104, 'TP.HCM', 'Home', '19130075@st.hcmuaf.edu.vn', 'Dau Hoa', '0397080549', '2023-05-15 21:48:18', 1007, '124561'),
(9, b'1', 118.15, 'TP.HCM', 'Home', '19130075@st.hcmuaf.edu.vn', 'Dau Hoa', '0397080549', '2023-05-15 21:50:00', 1008, '124561'),
(10, b'1', 45.76, 'TP.HCM', 'Home', '19130075@st.hcmuaf.edu.vn', 'Dau Hoa', '0397080549', '2023-05-15 21:50:00', 1009, '124561'),
(11, b'1', 104, 'TP.HCM', 'Home', '19130075@st.hcmuaf.edu.vn', 'Dau Hoa', '0397080549', '2023-05-15 21:50:00', 1010, '124561'),
(12, b'1', 83.6, 'TP.HCM', 'Home', '19130075@st.hcmuaf.edu.vn', 'Dau Hoa', '0397080549', '2023-05-17 12:37:28', 1011, '124561');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `order_detail`
--

CREATE TABLE `order_detail` (
  `id` bigint(20) NOT NULL,
  `amount` double NOT NULL,
  `mrp_price` double NOT NULL,
  `order_status` varchar(25) NOT NULL,
  `payment_id` int(11) NOT NULL,
  `payment_mode` varchar(25) NOT NULL,
  `price` double NOT NULL,
  `quantity` int(11) NOT NULL,
  `order_id` bigint(20) NOT NULL,
  `product_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `order_detail`
--

INSERT INTO `order_detail` (`id`, `amount`, `mrp_price`, `order_status`, `payment_id`, `payment_mode`, `price`, `quantity`, `order_id`, `product_id`) VALUES
(1, 118.15, 117, 'Delivered', 1000, 'COD', 118.15, 1, 1, 17),
(2, 756.5, 140.3, 'Pending', 1001, 'COD', 151.3, 5, 2, 7),
(3, 104, 94, 'Pending', 1002, 'COD', 104, 1, 3, 14),
(4, 260.48, 120.5, 'Pending', 1002, 'COD', 130.24, 2, 4, 21),
(5, 500, 200, 'Pending', 1003, 'COD', 500, 1, 5, 2),
(6, 118.15, 117, 'Pending', 1004, 'COD', 118.15, 1, 6, 17),
(7, 45.76, 45, 'Pending', 1004, 'COD', 45.76, 1, 7, 1),
(8, 104, 94, 'Pending', 1004, 'COD', 104, 1, 8, 14),
(9, 118.15, 117, 'Pending', 1005, 'COD', 118.15, 1, 9, 17),
(10, 45.76, 45, 'Pending', 1005, 'COD', 45.76, 1, 10, 1),
(11, 104, 94, 'Pending', 1005, 'COD', 104, 1, 11, 14),
(12, 83.6, 80.6, 'Delivered', 1006, 'COD', 83.6, 1, 12, 3);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `product`
--

CREATE TABLE `product` (
  `id` bigint(20) NOT NULL,
  `active` bit(1) NOT NULL,
  `code` varchar(255) NOT NULL,
  `create_date` datetime NOT NULL,
  `description` varchar(255) NOT NULL,
  `image` longtext DEFAULT NULL,
  `mrp_price` double NOT NULL,
  `name` varchar(255) NOT NULL,
  `price` double NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `product`
--

INSERT INTO `product` (`id`, `active`, `code`, `create_date`, `description`, `image`, `mrp_price`, `name`, `price`) VALUES
(1, b'1', 'P1', '2023-05-16 12:11:25', 'Saburo Ishikawa', 'https://newshop.vn/public/uploads/products/52646/sach-nguoi-ba-tai-gioi-vung-saga-tap-11.jpg', 45.76, 'The talented grandmother in the saga region', 40.76),
(2, b'1', 'P2', '2023-05-16 12:11:25', 'Quoc Trung', 'https://newshop.vn/public/uploads/products/52634/sach-tu-thu-bia-cung.jpg', 150, 'Tu thu - hard cover', 200),
(3, b'1', 'P3', '2023-05-16 12:11:25', 'Literature', 'https://newshop.vn/public/uploads/products/52635/sach-van-on-thoi-ke-ca-khi-ban-mot-minh.jpg', 80.6, 'It is still fine even when you are alone', 83.6),
(4, b'1', 'P4', '2023-05-16 12:11:25', 'EE SOEUN', 'https://newshop.vn/public/uploads/products/52636/minh-phai-song-that-tron-ven-moi-ngay.jpg', 100.6, 'I have to live fully every day', 105.6),
(5, b'1', 'P5', '2023-05-16 12:11:25', 'Quang Nino', 'https://newshop.vn/public/uploads/products/52625/kham-pha-va-chua-lanh-16-kieu-tinh-cach-qua-mbti.png', 105, 'Family scratching itch - collection when adults …… children', 110),
(6, b'1', 'P6', '2023-05-16 12:11:25', 'Brian A. Primack', 'https://newshop.vn/public/uploads/products/52624/sach-buoc-vao-the-gioi-cam-xuc-be-nho-cua-tre_L.jpg', 120.96, 'You are what you click - click virtual experience', 124.96),
(7, b'1', 'P7', '2023-05-16 12:11:25', 'Pham Quang Hien', 'https://newshop.vn/public/uploads/products/52639/sach-huong-dan-lap-trinh-vba-cho-excel.jpg', 103.84, 'Vba programming guide for excel', 151.3),
(8, b'1', 'P8', '2023-05-16 12:11:25', 'Pham Quang Huy', 'https://newshop.vn/public/uploads/products/52640/sach-huong-dan-su-dung-autocad-bang-hinh-anh.jpg', 103.84, 'Autocad user manual with images', 156.4),
(9, b'1', 'P9', '2023-05-16 12:11:25', 'Chu Nhat Pham', 'https://newshop.vn/public/uploads/products/52641/tre-thong-minh-hoc-tap-nhu-the-nao.jpg', 86.12, 'How smart children learn', 87.12),
(10, b'1', 'P10', '2023-05-16 12:11:25', 'Foreign language books', 'https://newshop.vn/public/uploads/products/52284/sach-200-bo-cau-hoi-va-tra-loi-phong-van-tieng-anh.jpg', 222.72, '200+ sets of questions and interviews in english', 236.72),
(11, b'1', 'P11', '2023-05-16 12:11:25', 'Foreign language books', 'https://newshop.vn/public/uploads/products/41911/sach-tu-hoc-ngu-phap-tieng-anh-bang-mindmap-tap-2.jpg', 200.32, '233 english sentence patterns conquer employers', 210.32),
(12, b'1', 'P12', '2023-05-16 12:11:25', 'Nguyen Kinh Doc', ' https://newshop.vn/public/uploads/products/41910/sach-tu-hoc-ngu-phap-tieng-anh-bang-mindmap-tap-1.jpg', 103.84, 'Literature literature - grammaire francaise (with exercises and corrections', 104),
(13, b'1', 'P13', '2023-05-16 12:11:25', 'Do Nhung', 'https://newshop.vn/public/uploads/products/41071/sach-tieng-anh-genz.jpg', 300.4, 'Combo mindmap english grammar - english grammar with mind map + mind map english vocabulary', 310.4),
(14, b'1', 'P14', '2023-05-16 12:11:25', 'Dong Nai', 'https://newshop.vn/public/uploads/products/51933/sach-bai-tap-bo-tro-toan-dien-ngu-phap-tieng-anh.jpg', 70.65, 'Comprehensive supplementary exercises - english grammar (abb)', 75.65),
(15, b'1', 'P15', '2023-05-16 12:11:25', 'Mizuno Yuka', 'https://newshop.vn/public/uploads/products/51852/sach-sach-tuyet-ky-xoa-mu-tieng-anh.jpg', 113.15, 'Great technique to erase english blindness', 118.15),
(16, b'1', 'P16', '2023-05-16 12:11:25', 'Foreign language books', 'https://newshop.vn/public/uploads/products/51055/sach-cung-ban-truong-thanh_L.jpg', 82.12, '999 letters sent to themselves - the most impressive letters (chinese -vietnamese bilingual version', 87.12),
(17, b'1', 'P17', '2023-05-16 12:11:25', 'Mizuno Yuka', 'https://newshop.vn/public/uploads/products/50776/sach-nghien-ngu-phap-tieng-anh-hinh-que-tap-2-nang-cao_L.jpg', 90.92, 'Everyday english for grown -ups - self -study english for busy people', 95.92),
(18, b'1', 'P18', '2023-05-16 12:11:25', 'Foreign language books', 'https://newshop.vn/public/uploads/products/51639/sach-tu-dien-han-viet-cao-minh.jpg', 22.6, 'Korean dictionary (cm)', 30.6),
(19, b'1', 'P19', '2023-05-16 12:11:25', 'Mizuno Yuka', 'https://newshop.vn/public/uploads/products/50775/sach-nghien-ngu-phap-tieng-anh-hinh-que-tap-1-co-ban_L.jpg', 4.6, 'Grinding english grammar shaped - episode 1: basic', 15.6),
(20, b'1', 'P20', '2023-05-16 12:11:25', 'Mac Bao Phi Bao', 'https://newshop.vn/public/uploads/products/52615/duong-ve-gap-lai-duoi-nang-mai.jpg', 112.24, 'Road to - see again in the morning sun', 130.24),
(21, b'1', 'P21', '2023-05-16 12:11:25', 'Jodi Picoult', 'https://newshop.vn/public/uploads/products/52595/sach-co-phai-anh-noi-nay.jpg', 112.2, 'Is this place', 135.2),
(22, b'1', 'P22', '2023-05-16 12:11:25', 'Alpha Books', 'https://newshop.vn/public/uploads/products/52513/sach-tuoi-tre-lac-loi.jpg', 100.52, 'Youth lost', 113.52),
(23, b'1', 'P23', '2023-05-16 12:11:25', 'Literature', 'https://newshop.vn/public/uploads/products/52533/sach-song-cam-xuc-cung-can-dung-luc.jpg', 103.84, 'Emotional life also needs at the right time', 99.8),
(24, b'1', 'P24', '2023-05-16 12:11:25', 'Dong Nai', 'https://newshop.vn/public/uploads/products/52447/tu-sach-doc-cung-con-van-hoc-dan-gian-tuyen-chon-vuon-co-tich-tuoi-tho-tam-cam.jpg', 30.25, 'Bookcase read with children - folklore selected: childhood fairy garden - tam cam', 38.25),
(25, b'1', 'P25', '2023-05-16 12:11:25', 'Dong Nai', 'https://newshop.vn/public/uploads/products/52445/tu-sach-doc-cung-con-van-hoc-dan-gian-tuyen-chon-vuon-co-tich-tuoi-tho-nang-bach-tuyet-va-bay-chu-lun.png', 34.25, 'Reading bookcase with children - folklore selected: childhood fairy garden - bach tuyet and seven', 38.25),
(26, b'1', 'P26', '2023-05-16 12:11:25', 'Phu Nu Viet Nam', 'https://newshop.vn/public/uploads/products/52443/khuyen-hoc-al.jpg', 159.28, 'Women shu - gender and development: nam phong magazine - women issue in our country', 159.28),
(27, b'1', 'P27', '2023-05-16 12:11:25', 'Summer Kat', 'https://newshop.vn/public/uploads/products/52317/sach-khep-mi-lai-va-yeu.jpg', 71.44, 'Close eyelashes and love', 77.44),
(28, b'1', 'P28', '2023-05-16 12:11:25', 'Summer Kat', 'https://newshop.vn/public/uploads/products/52309/sach-dung-nhin-len-nua-anh-cung-thich-em.jpg', 100.8, 'Don peek anymore, i like you too', 108.8),
(29, b'1', 'P29', '2023-05-16 12:11:25', 'Summer Kat', 'https://newshop.vn/public/uploads/products/52307/sach-bo-cong-anh.jpg', 98.3, '\'Dandelion\'', 100.3),
(30, b'1', 'P30', '2023-05-16 12:11:25', 'Ahra Kim', 'https://newshop.vn/public/uploads/products/52629/phuc-hoi-sau-tram-cam-va-lo-au.jpg', 80.2, 'Recovery after depression and anxiety', 87.2);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_c0r9atamxvbhjjvy5j8da1kam` (`email`) USING HASH;

--
-- Chỉ mục cho bảng `cart`
--
ALTER TABLE `cart`
  ADD PRIMARY KEY (`id`),
  ADD KEY `CART_CUST_FK` (`customer_id`),
  ADD KEY `CART_PROD_FK` (`product_id`);

--
-- Chỉ mục cho bảng `contact`
--
ALTER TABLE `contact`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_dwk6cx0afu8bs9o4t536v1j5v` (`email`) USING HASH,
  ADD UNIQUE KEY `UK_o3uty20c6csmx5y4uk2tc5r4m` (`phone`) USING HASH;

--
-- Chỉ mục cho bảng `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_o6e714ot0hclyvhlcte6vc4mr` (`order_num`),
  ADD UNIQUE KEY `UKo6e714ot0hclyvhlcte6vc4mr` (`order_num`);

--
-- Chỉ mục cho bảng `order_detail`
--
ALTER TABLE `order_detail`
  ADD PRIMARY KEY (`id`),
  ADD KEY `ORDER_DETAIL_ORD_FK` (`order_id`),
  ADD KEY `ORDER_DETAIL_PROD_FK` (`product_id`);

--
-- Chỉ mục cho bảng `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_h3w5r1mx6d0e5c6um32dgyjej` (`code`) USING HASH;

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `admin`
--
ALTER TABLE `admin`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT cho bảng `cart`
--
ALTER TABLE `cart`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT cho bảng `contact`
--
ALTER TABLE `contact`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT cho bảng `customer`
--
ALTER TABLE `customer`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT cho bảng `orders`
--
ALTER TABLE `orders`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT cho bảng `order_detail`
--
ALTER TABLE `order_detail`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT cho bảng `product`
--
ALTER TABLE `product`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
