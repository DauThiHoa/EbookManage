<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%--l?p l?i, ?i?u ki?n, ??nh d?ng--%>
<%--<c:if>, <c:forEach>--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>Ebook General Store | Login </title>
	<jsp:include page="layouts/header.jsp"></jsp:include>
</head>
<body>
<jsp:include page="layouts/navigation-bar.jsp"></jsp:include>
<jsp:include page="layouts/menu.jsp"></jsp:include>

<!-- //banner-2 -->
<!-- page -->
<%--<div class="services-breadcrumb">--%>
<%--	<div class="agile_inner_breadcrumb">--%>
<%--		<div class="container">--%>
<%--			<ul class="w3_short">--%>
<%--				<li>--%>
<%--					<a href="${pageContext.request.contextPath}/home">Home</a>--%>
<%--					<i>|</i>--%>
<%--				</li>--%>
<%--				<li>Login</li>--%>
<%--			</ul>--%>
<%--		</div>--%>
<%--	</div>--%>
<%--</div>--%>
<!-- //page -->
<!-- log in -->
<br>
<div class="container h-100">

	<h3 class="tittle-w3l text-center mb-lg-5 mb-sm-4 mb-3" style="margin-top: 0%;">
		<span>L</span>ogin
	</h3>

	<div class="d-flex justify-content-center h-100">


		<div class="row">
			<div class="col-md-12">

<%--Chuyen trang san pham--%>
				<c:if test="${not empty backUrl}"><center><h4 class="text-center" style="color:red;">Login First</h4><hr></center></c:if>
<%--			modelAttribute="customerLoginForm => Liên k?t d? li?u bi?u m?u v?i ??i t??ng "customerLoginForm" và cung c?p d? li?u ?ó trong ph??ng th?c
c?a b? ?i?u khi?n ?? x? lý --%>
				<form:form action="${pageContext.request.contextPath}/customer/loginCustomer" modelAttribute="customerLoginForm" method="post" class="validatedForm">
					<div class="contact-form1 form-group">
						<label class="col-form-label" style="font-weight: bold">Email</label>
						<form:input type="email" path="email" size="40" class="form-control" id="email" placeholder="Email" name="Email" required="required" />
						<form:errors path="email" cssStyle="color:red;" cssClass="error"/>
					</div>
					<div class="contact-form1 form-group">
						<label class="col-form-label" style="font-weight: bold">${login_Password}</label>
						<form:password path="password" maxlength="64" size="40" minlength="6" class="form-control" placeholder="Password" name="Password" id="password" required="required" />
						<form:errors path="password" cssStyle="color:red;" cssClass="error"/>
					</div>
					<div class="right-w3l">
						<input type="submit" style="background: #28a745; font-weight: bold" class="form-control btn btn-primary" value="${login_Login}">
					</div>
				</form:form>
				<a href="register" style="color:orangered; font-weight: bold">${login_Register}</a>
				<a href="forgot-password" style="color:orangered; font-weight: bold; float:right;">${login_ForgotPassword}</a>
			</div>
		</div>
	</div>
</div>


<div style="margin-top: 3%"></div>
<jsp:include page="layouts/footer1.jsp"></jsp:include>

</body>
</html>