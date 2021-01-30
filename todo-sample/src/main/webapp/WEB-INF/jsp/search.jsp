<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>検索結果</title>
<link rel="STYLESHEET" href="css/normalize.css" type="text/css">
<link rel="STYLESHEET" href="css/main.css" type="text/css">
</head>
<body>
<div class="container">
	<header>
		<div class="title">
			<h1>検索結果</h1>
		</div>
		<div class="login_info">
			<ul>
				<li>ようこそ<c:out value="${currentUser.name}"/>さん</li>
				<li>
					<a href="logout">ログアウト</a>
				</li>
			</ul>
		</div>
	</header>

	<main>
		<div class="main-header">
			<div class="goback">
				<form action="list" method="get">
					<input type="submit" value="戻る">
				</form>
			</div>
		</div>

		<table class="list">
			<tr>
				<th>項目名</th>
				<th>担当者</th>
				<th>期限</th>
				<th>完了</th>
				<th colspan="3">操作</th>
			</tr>

			<c:forEach var="item" items="${items}" varStatus="s">
				<c:set var="className"></c:set>
				<c:choose>
					<c:when test="${not empty item.finishedDate}">
						<c:set var="className">finished</c:set>
					</c:when>
					<c:otherwise>
						<c:if test="${item.expired}">
							<c:set var="className">warning</c:set>
						</c:if>
					</c:otherwise>
				</c:choose>

				<tr class="${className}">
					<td class="align-left">
						<c:out value="${item.name}"/>
					</td>
					<td class="align-left">
						<c:out value="${item.user.name}"/>
					</td>
					<td>
						<fmt:formatDate value="${item.expireDate}" pattern="yyyy/MM/dd"/>
					</td>
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
					<td>
						<form action="finish" method="post">
							<input type="hidden" name="item_id" value="<c:out value="${item.id}"/>">
							<input type="hidden" name="keyword" value="<c:out value="${param.keyword}"/>">
							<c:choose>
								<c:when test="${item.finished}">
									<input type="submit" value="未完了">
								</c:when>
								<c:otherwise>
									<input type="submit" value="完了">
								</c:otherwise>
							</c:choose>
						</form>
					</td>
					<td>
						<form action="edit" method="post">
							<input type="hidden" name="item_id" value="<c:out value="${item.id}"/>">
							<input type="submit" value="更新">
						</form>
					</td>
					<td>
						<form action="delete" method="post">
							<input type="hidden" name="item_id" value="<c:out value="${item.id}"/>">
							<input type="submit" value="削除">
						</form>
					</td>
				</tr>
			</c:forEach>
		</table>

		<div class="main-footer">
			<div class="goback">
				<form action="list" method="get">
					<input type="submit" value="戻る">
				</form>
			</div>
        </div>
	</main>

	<footer>

	</footer>
</div>
</body>
</html>