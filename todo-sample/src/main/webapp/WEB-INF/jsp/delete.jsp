<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>削除確認</title>
<link rel="STYLESHEET" href="css/normalize.css" type="text/css">
<link rel="STYLESHEET" href="css/main.css" type="text/css">
</head>
<body>
	<div class="container">
		<header>
			<div class="title">
				<h1>削除確認</h1>
			</div>
			<div class="login_info">
				<ul>
					<li>ようこそ<c:out value="${currentUser.name}" />さん
					</li>
					<li><a href="logout">ログアウト</a></li>
				</ul>
			</div>
		</header>

		<main>
			<c:if test="${not empty message}">
				<p class="error">${message}</p>
			</c:if>

			<p>下記の項目を削除します。よろしいですか？</p>
			<form action="delete_action" method="post">
				<input type="hidden" name="item_id" value="${item.id}">
				<table class="item">
					<tr>
						<th>項目名</th>
						<td>
							<c:out value="${item.name}" />
						</td>
					</tr>
					<tr>
						<th>担当者</th>
						<td>
							<c:out value="${item.user.name}" />
						</td>
					</tr>
					<tr>
						<th>期限</th>
						<td>
							<fmt:formatDate value="${item.expireDate}" pattern="yyyy/MM/dd"/>
						</td>
					</tr>
					<tr>
						<th>完了</th>
						<td>
							<c:choose>
								<c:when test="${item.finished}">
									<fmt:formatDate value="${item.finishedDate}" pattern="yyyy/MM/dd"/>
								</c:when>
								<c:otherwise>
									未
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</table>
			</form>
			<button type="button" onclick="document.forms[0].submit()">削除</button>
			<button type="button" onclick="location.href='list'">キャンセル</button>
		</main>

		<footer> </footer>
	</div>
</body>
</html>
