<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
		<form action="${pageContext.request.contextPath}/guestbook/delete" method="get">
			비밀번호 : <input type ="password" name="password">
			<br>
			<c:if test="${param.result eq 0}">   <!-- param.result -->
				<p>비밀번호를 잘못 입력하셨습니다.</p>
			</c:if>
			<button type="submit">확인</button>
			
			<input type="hidden" name="no" value="${param.no}">		
		</form>
		<br>
		<a href="${pageContext.request.contextPath}/guestbook/list">메인으로 돌아가기</a>
</body>
</html>