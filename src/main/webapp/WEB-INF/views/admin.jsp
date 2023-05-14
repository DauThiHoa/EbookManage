<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%> 
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<title>Ebook General Store | Login </title>
	<jsp:include page="layouts/header.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="layouts/user-navigation.jsp"></jsp:include>
<!-- log in -->
	<br><br>
	<div class="container h-100">
		<div class="d-flex justify-content-center h-100">
			<div class="row">
				<div class="col-md-12">
					<h3 class="tittle-w3l text-center mb-lg-5 mb-sm-4 mb-3" style="margin-top: -5%;">
						<span>A</span>dmin
						<span>L</span>ogin
					</h3>

			 	<form:form action="${pageContext.request.contextPath}/admin/home" modelAttribute="AdminLoginForm" method="post">
						<div class="form-group">
							<label class="col-form-label" style="font-weight: bold">Email</label>
							<form:input type="email" path="email" class="form-control" size="30" placeholder="Email" name="email" required="required" />
							<form:errors path="email" cssStyle="color:red;" cssClass="error"/>
						</div>	
						<div class="form-group">
							<label class="col-form-label" style="font-weight: bold">Password</label>
							<form:password path="password" size="30" class="form-control" placeholder="Password" name="Password" id="password" required="required" />
							<form:errors path="password" cssStyle="color:red;" cssClass="error"/>
						</div>
						<div class="right-w3l">
							<input type="submit" style="font-weight: bold; background: #117a8b" class="form-control" value="Login">
							<a href="/admin/forgot-password" style="color:orangered; font-weight: bold">Forgot Password?</a>
						</div>
					</form:form>
					</div>
				</div>
				</div>
			</div>
<%--	<div style="margin-top: 14%"></div>--%>
	<jsp:include page="layouts/footer.jsp"></jsp:include>
</body>
</html>