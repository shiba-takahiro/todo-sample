<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>作業更新</title>
<link rel="STYLESHEET" href="css/normalize.css" type="text/css">
<link rel="STYLESHEET" href="css/main.css" type="text/css">
</head>
<body>
<div class="container">
	<header>
		<div class="title">
			<h1>作業更新</h1>
		</div>
		<div class="login_info">
			<ul>
				<li>ようこそ<c:out value="${currentUser.name}"/>さん</li>
				<li><a href="logout">ログアウト</a></li>
			</ul>
		</div>
	</header>

	<main>
		<c:if test="${not empty message}">
			<p class="error">${message}</p>
		</c:if>
		<form action="edit_action" method="post">
			<input type="hidden" name="item_id" value="${item.id}">
			<table class="item">
				<tr>
					<th>項目名</th>
					<td>
						<input type="text" name="name" value="${item.name}">
					</td>
				</tr>
				<tr>
					<th>担当者</th>
					<td>
						<select name="user_id">
							<c:forEach var="user" items="${users}">
								<c:set var="selected" value=""/>
								<c:if test="${user.id == item.user.id}">
									<c:set var="selected" value="selected"/>
								</c:if>
								<option value="${user.id}" ${selected} >
									<c:out value="${user.name}"/>
								</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th>期限</th>
					<td>
						<fmt:formatDate value="${item.expireDate}" pattern="yyyy-MM-dd" var="formattedDate"/>
						<input type="date" name="expire_date" value="${formattedDate}">
					</td>
				</tr>
				<tr>
					<th>完了</th>
					<td>
						<c:set var="checked" value=""/>
						<c:if test="${item.finished}">
							<c:set var="checked" value="checked"/>
						</c:if>
						<input type="checkbox" name="finished" value="true" ${checked}> 完了した
					</td>
				</tr>
			</table>
		</form>
		<button type="button" onclick="document.forms[0].submit()">更新</button>
		<button type="button" onclick="location.href='list'">キャンセル</button>
	</main>

	<footer> </footer>
</div>
</body>
</html>