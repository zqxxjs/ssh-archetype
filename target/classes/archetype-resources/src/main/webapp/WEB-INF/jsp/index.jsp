<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>JSP 页面</title>
</head>
<body>
	<h1>JSP 渲染成功</h1>
	<p>${message}</p>
	<p>Context Path：${pageContext.request.contextPath}</p>
	<p><a href="${pageContext.request.contextPath}/">返回首页</a></p>
</body>
</html>
