<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<title>Ebook General Store | Change Password </title>
	<jsp:include page="layouts/header.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="layouts/admin_menu.jsp"></jsp:include>


	<br>

	<h3 class="tittle-w3l text-center mb-lg-5 mb-sm-4 mb-3" style="margin-top: 2%;">
		<span>C</span>hange
		<span>P</span>assword
	</h3>


	<div class="container h-100">
	<c:if test="${not empty success}">
		<div class="alert alert-success alert-dismissible fade show">
        <strong>Change Password!</strong> Password successfully changed.
        Click  <a href="${pageContext.request.contextPath}/admin/" style="text-decoration: underline;" class="title">Here</a> to login.<button type="button" class="close" data-dismiss="alert">&times;</button>
   		</div>
	</c:if>
		<c:if test="${not empty unmatched}">
				<div class="alert alert-danger alert-dismissible fade show">
        <strong>Change Password!</strong> Password &amp; Confirm Password doesn't match.<br>Please try again later.
        <button type="button" class="close" data-dismiss="alert">&times;</button>
   				</div>
		</c:if>
		<c:if test="${not empty error}">
			<div class="alert alert-danger alert-dismissible fade show">
        <strong>Change Password!</strong> Password &amp; Confirm password can't be empty.
        <button type="button" class="close" data-dismiss="alert">&times;</button>
   			</div>
		</c:if>
		<c:if test="${not empty notFound}">
			<div class="alert alert-danger alert-dismissible fade show">
        <strong>Change Password!</strong> Could not found a user with this email.
        <button type="button" class="close" data-dismiss="alert">&times;</button>
   			 </div>
		</c:if>
		<div class="d-flex justify-content-center h-100">
		    <div class="row">
				<div class="col-md-12">
					<form action="${pageContext.request.contextPath}/admin/changeAdminPassword" class="validatedForm" method="post">
						<div class="form-group">
							<input type="password" class="form-control" size="35" placeholder="Password" name="password" id="password" required="required">
						</div>
						<div class="form-group">
							<input type="password" class="form-control" size="35" placeholder="Confirm Password" name="confirm_password" id="confirm_password" required="required">
						</div>
						<div class="right-w3l">
							<input class="form-control btn btn-primary"
								   style="background: #28a745; font-weight: bold" type="button" value="Submit" onClick="validatePassword();" />
						</div>
					</form>
					</div>
				</div>
				</div>
			</div>

	<div style="margin-top: 4%"></div>

	<jsp:include page="layouts/footer.jsp"></jsp:include>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.1/jquery.validate.min.js" type="text/javascript"></script>
	<script>
    function validatePassword() {
        var validator = $(".validatedForm").validate({
            rules: {
                password: "required",
                confirm_password: {
                    equalTo: "#password"
                }
            },
            messages: {
                password: " Enter Password",
                confirm_password: " Enter Confirm Password Same as Password."
            }
        });
        if (validator.form()) {
            $(".validatedForm").submit();
        }
    }
    </script>
</body>
</html>