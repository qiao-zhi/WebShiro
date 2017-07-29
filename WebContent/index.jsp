<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首页</title>
</head>
<frameset rows="20%,*">
		<frame src="${pageContext.request.contextPath }/top.jsp" />
		<frameset cols="20%,80%">
			<frame src="left.jsp" />
			<!--frame不能放在body标签内。指定name属性，为这一个框架指定名字，在html的a的target属性可以
			设为target="right"在该框架显示跳转的页面-->
			<frame name="right" />          
		</frameset>
	</frameset>
</html>