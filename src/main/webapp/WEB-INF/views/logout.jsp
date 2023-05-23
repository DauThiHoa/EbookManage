<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>Ebook General Store | Logout </title>
	<jsp:include page="layouts/header.jsp"></jsp:include>
	<script src="${pageContext.request.contextPath}/js/backNoWork.js"></script>
</head>
<body>
	<jsp:include page="layouts/navigation-bar.jsp"></jsp:include>
	<jsp:include page="layouts/menu.jsp"></jsp:include>

	</div>
	<!-- //banner-2 -->
	<!-- page -->
	<div class="services-breadcrumb">
		<div class="agile_inner_breadcrumb">
			<div class="container">
				<ul class="w3_short">
					<li>
						<a href="${pageContext.request.contextPath}/home">Home</a>
						<i>|</i>
					</li>
					<li>Logout</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- //page -->
<br>
	<div class="col-12 text-center">
			<div>
				<h5 class="subtitle">You have been logged-out successfully.</h5>
			</div>
		</div>
	<div style="margin-top: 20%"></div>
	<jsp:include page="layouts/footer1.jsp"></jsp:include>
</body>
</html>