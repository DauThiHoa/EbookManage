
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%--l?p l?i, ?i?u ki?n, ??nh d?ng--%>
<%--<c:if>, <c:forEach>--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html >
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

    <title>Greeting</title>
</head>
<body>

<h1>Language Selection</h1>
<ul>
    <li><a href="/change-language?lang=en">English</a></li>
    <li><a href="/change-language?lang=vi">French</a></li>
</ul>

<h1>HAHAHA</h1>
<h1 th:text="#{greeting}" >${greeting}</h1>

</body>
</html>
