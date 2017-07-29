<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ tagliburi ="http://shiro.apache.org/tags" prefix="shiro"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查询商品列表</title>
</head>
<body>
	<form action="${pageContext.request.contextPath }/itemlist.action"
		method="post">
		查询条件：
		<table width="100%" border=1>
			<tr>
				<td><input type="submit" value="查询" /></td>
			</tr>
		</table>
	</form>
	<form action="${pageContext.request.contextPath }/updates.action">
		商品列表：
		<table width="100%" border=1>
			<tr>

				<td><input type="checkbox" name="ids"></td>
				<td>商品名称</td>
				<td>商品价格</td>
				<td>生产日期</td>
				<td>商品描述</td>
				<td>操作</td>
			</tr>
			<c:forEach items="${itemsList }" var="item" varStatus="status">
				<tr>

					<td><input type="checkbox" name="ids" value="${item.id }"></td>
					<td><input type="text" name="name" value="${item.name }"></td>
					<td><input type="text" name="price" value="${item.price }"></td>
					<td><fmt:formatDate value="${item.createtime}"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td>${item.detail }</td>
					<shiro:hasPermission name="item:create">

						<td><a
							href="${pageContext.request.contextPath }/itemEdit.action?id=${item.id}">修改</a></td>
					</shiro:hasPermission>
				</tr>
			</c:forEach>
		</table>
		<input type="submit" value="提交">
	</form>

	<shiro:hasPermission name="item:create">
		<input type="button" value="有创建权限" />
	</shiro:hasPermission>
	<shiro:hasPermission name="item:create1">
		<input type="button" value="有创建权限1" />
	</shiro:hasPermission>



</body>

</html>