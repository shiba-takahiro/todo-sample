<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>エラー</title>
<link rel="STYLESHEET" href="css/normalize.css" type="text/css">
<link rel="STYLESHEET" href="css/main.css" type="text/css">
</head>
<body>
<div class="container">
	<header>
		<div class="title">
			<h1>エラー</h1>
		</div>
	</header>

	<main>
		<p class="error">エラーが発生しました<br>
		<c:out value="${message}"/></p>
		<form action="index.jsp" method="get">
			<input type="submit" value="戻る">
		</form>
	</main>

	<footer>

	</footer>
</div>
</body>
</html>
