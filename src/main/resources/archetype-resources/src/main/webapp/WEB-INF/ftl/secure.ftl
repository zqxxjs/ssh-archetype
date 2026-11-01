<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>受保护页面</title>
</head>
<body>
	<h1>受保护页面</h1>
	<p>当前用户：${username?html}</p>
	<p>Spring Session ID：${sessionId?html}</p>
	<p>该页面需要登录后才能访问。</p>
	<p><a href="${rc.contextPath}/logout">退出登录</a></p>
	<p><a href="${rc.contextPath}/">返回首页</a></p>
</body>
</html>
