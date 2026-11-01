<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>登录</title>
</head>
<body>
	<h1>登录</h1>

	<#if error>
		<p style="color: red;">用户名或密码错误。</p>
	</#if>
	<#if logout>
		<p style="color: green;">已退出登录。</p>
	</#if>

	<form method="post" action="${rc.contextPath}/login">
		<p>
			<label>用户名：<input type="text" name="username" autocomplete="username"></label>
		</p>
		<p>
			<label>密码：<input type="password" name="password" autocomplete="current-password"></label>
		</p>
		<p>
			<button type="submit">登录</button>
		</p>
	</form>

	<p>演示账号：admin / admin123</p>
	<p><a href="${rc.contextPath}/">返回首页</a></p>
</body>
</html>
