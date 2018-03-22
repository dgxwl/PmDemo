<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<title>用户列表</title>
</head>
<body>
	<h1>用户列表</h1>
	<table border="1">
		<tr>
			<th>用户</th>
			<th>操作</th>
		</tr>
		<c:forEach items="${users}" var="u">
			<tr>
				<td>${u.username}</td>
				<td>
					<c:if test="${u.id != sessionScope.user.id}">
					<a href="${pageContext.request.contextPath}/pm/toPm.do?receiverUid=${u.id}&recvName=${u.username}">发送私信</a>
					</c:if>
				</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>