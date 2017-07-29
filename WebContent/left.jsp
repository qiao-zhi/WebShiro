<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首页</title>
</head>
<body>

	<p></p>
	<p></p>
	<%--items用于迭代的变量，var代表每次迭代之后存放名字 --%>
	<center>
		<c:if test="${user != null }">
			<c:forEach items="${userPri }" var="user">
				<c:if test="${user.menuClass == '1'}">
					<br />
					<a href="javascript:void(0)">${user.menuName}</a>
					<br /><br />
				</c:if>
				<c:if test="${user.menuClass == '2'}">
					<br />
					<a href="${pageContext.request.contextPath }${user.menuUrl }"
						target="right">${user.menuName}</a>
					<br />
				</c:if>
			</c:forEach>
		</c:if>
	</center>

</body>
</html>