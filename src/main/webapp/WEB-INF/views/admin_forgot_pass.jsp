<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>Ebook General Store | Forgot Password </title>
	<jsp:include page="layouts/header.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="layouts/navigation-bar.jsp"></jsp:include>

	<!-- //banner-2 -->
	<!-- page -->
	<div class="services-breadcrumb">
		<div class="agile_inner_breadcrumb">
			<div class="container">
				<ul class="w3_short">
					<li>
						<a style="margin-left: -16%;" href="/home">Home</a>
						<i>|</i>
					</li>
					<li>Forgot Password</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- //page -->
<!-- log in -->
	<br>
	<div class="container h-100">
		<c:if test="${not empty success}">
		<div class="alert alert-success alert-dismissible fade show">
        <strong>Forgot Password!</strong> Email has been sent.
        Click  <a href="${pageContext.request.contextPath}/admin/" style="text-decoration: underline;" class="title">Here</a> to login.<button type="button" class="close" data-dismiss="alert">&times;</button>
   		</div>
	</c:if>
		<c:if test="${not empty blank}">
				<div class="alert alert-danger alert-dismissible fade show">
        <strong>Forgot Password!</strong> Email is required.
        <button type="button" class="close" data-dismiss="alert">&times;</button>
   				</div>
		</c:if>
		<c:if test="${not empty error}">
			<div class="alert alert-danger alert-dismissible fade show">
        <strong>Forgot Password!</strong> Oops! Something Went Wrong.
        <button type="button" class="close" data-dismiss="alert">&times;</button>
   			</div>
		</c:if>
		<c:if test="${not empty notFound}">
			<div class="alert alert-danger alert-dismissible fade show">
        <strong>Forgot Password!</strong> Could not found a user with this email.
        <button type="button" class="close" data-dismiss="alert">&times;</button>
   			 </div>
		</c:if>
		<div class="d-flex justify-content-center h-100">
		    <div class="row">
				<div class="col-md-12">
					<form action="/admin/forgotPassword" method="post">
						<div class="form-group">
							<label class="col-form-label">Email</label>
							<input type="email" class="form-control" size="35" placeholder="Enter email" name="email" id="email" required="required">
						</div>	
						<div class="right-w3l">
							<input type="submit" style="font-weight: bold; background: #28a745" class="form-control btn btn-primary" value="Submit">
						</div>
					</form>
					</div>
				</div>
				</div>
			</div>
	<div style="margin-top: 9%"></div>
	<jsp:include page="layouts/footer.jsp"></jsp:include>
</body>
</html>