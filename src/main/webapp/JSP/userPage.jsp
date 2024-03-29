<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User page</title>
    <link rel="stylesheet" href="CSS/style.css">
</head>
<body>
    <%@ include file="fragments/navbar.jsp" %>
    <c:choose>
        <c:when test="${userBean.userType == 'student'}">
            <%@ include file="fragments/student/studentUserPage.jsp" %>
        </c:when>
        <c:when test="${userBean.userType == 'teacher'}">
            <%@ include file="fragments/teacher/teacherUserPage.jsp" %>
        </c:when>
    </c:choose>
</body>
</html>