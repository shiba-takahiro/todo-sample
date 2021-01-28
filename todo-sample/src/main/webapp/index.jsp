<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>ログイン</title>
<link rel="STYLESHEET" href="css/normalize.css" type="text/css">
<link rel="STYLESHEET" href="css/main.css" type="text/css">

</head>
<body>
<div class="container">
	<header>
		<h1>ログイン</h1>
	</header>

	<main>
		<c:if test="${not empty message}">
			<p class="error"><c:out value="${message}"/></p>
		</c:if>
		<form action="login" method="post">
			<table class="login">
				<tr>
					<th>ユーザ名</th>
					<td>
						<input type="text" name="user_name" id="user_name" value="">
					</td>
				</tr>
				<tr>
					<th>パスワード</th>
					<td>
						<input type="password" name="password" id="password" value="" >
					</td>
				</tr>
			</table>
			<input type="submit" value="ログイン" id="login">
		</form>
	</main>

	<footer>

	</footer>
</div>
</body>
</html>