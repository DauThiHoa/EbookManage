<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>Ebook General Store | About </title>
	<jsp:include page="layouts/header.jsp"></jsp:include>
</head>
<body>
<c:choose>
	<c:when test="${not empty email}">
		<jsp:include page="layouts/user-navigation.jsp"></jsp:include>
		<jsp:include page="layouts/user-menu.jsp"></jsp:include>
	</c:when>
	<c:otherwise>
		<jsp:include page="layouts/navigation-bar.jsp"></jsp:include>
		<jsp:include page="layouts/menu.jsp"></jsp:include>
	</c:otherwise>
</c:choose>

<!-- //page -->
<!-- log in -->
<br>
<!-- about -->
<div class="welcome py-sm-5 py-4">
	<div class="container py-xl-4 py-lg-2">
		<!-- tittle heading -->
		<h3 class="tittle-w3l text-center mb-lg-5 mb-sm-4 mb-3" style="   margin-top: -5%;">
			<span>A</span>bout
			<span>U</span>s
		</h3>
		<!-- //tittle heading -->
		<div class="row">
			<div class="col-lg-6 welcome-left">
				<h3 style="color: #117a8b; font-weight: bold">About Store</h3>
				<h4 class="my-sm-3 my-2">GrocSMART(Ebook General Store) is a low-price online Grocery store, which has many vital ranging products consumed in our everyday Breakfast, Meals & Dinners. We have Fresh Vegetables, Staples, Beverages, Personal care products & many other products needed daily. All the products on our
					website are distinguished based on their categories, which gives you the hassle-free shopping experience ever with minimum time spent for your shopping.</h4>
			</div>

			<div class="col-lg-6 welcome-right-top mt-lg-0 mt-sm-5 mt-4">
				<img src="${pageContext.request.contextPath}/images/img_1.png" class="img-fluid" alt=" ">
			</div>
			<div class="welcome-left">
				<br><br><h3 class="text-left" style="color: #117a8b; font-weight: bold">&ensp;What We Do</h3>
				<h4 class="my-sm-3" style="margin-left:15px;">We guarantee you the best quality products with the lowest price. Right now we only serve you in Thane. To have a more convenient shopping experience, for you we deliver your selected products at your doorstep at your selected time slot. As in today's era every person sense short in time & also its hard to find if the consumable products are healthy/natural. So to tackle this issue we serve you with the finest & hand-picked products from all the categories,
					to save your time from the conventional shopping as well as
					from the problems like getting stuck in long queues, the parking fees to be paid, carrying you heavy shopping bags & etc.</h4>
			</div>
			<div class="welcome-left">
				<br><br><h3 class="text-left" style="color: #117a8b; font-weight: bold">&ensp;Our Vision</h3>
				<h4 class="my-sm-3" style="margin-left:15px;">
					<li>Redefining India's freshest and finest food experience by 2020.</li>
					<li>To provide a brighter customer experience with an assortment of finest local and international foods and highest level of service. </li>
					<li>To spread the joy of food.</li>
				</h4>
			</div>
		</div>
	</div>
</div>
<!-- //about -->
<jsp:include page="layouts/footer.jsp"></jsp:include>
</body>
</html>