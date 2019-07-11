<%@ page contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>用户登录</title>
</head>
<body>
	<h1>欢迎登录!</h1>
	<form action="login.do" method="post">
		用户名<p><input name="username"></p>
		密码<p><input name="password" type="password"></p>
		<input type="submit">
	</form>
</body>
</html>