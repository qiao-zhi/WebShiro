<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<center>
		<p>欢迎来到xxxxxxx首页</p>
		<c:if test="${user != null }">
			欢迎您:<font style="color:red">${user.name}</font>
			<a href="${pageContext.request.contextPath }/logout.action"
				style="margin-left: 500px; text-decoration: none;" target="_blank">注销</a></font>	
		</c:if>
		<c:if test="${user == null }">
			<font size="7"><a href="JSP/login.jsp"
				style="margin-left: 500px; text-decoration: none;" target="_blank">登录</a></font>
		</c:if>
		
	</center>


	<p></p>

</body>
</html>