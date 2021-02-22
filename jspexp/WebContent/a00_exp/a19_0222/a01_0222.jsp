<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.util.*"
    import="jspexp.z01_vo.*"
    import="jspexp.a03_database.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<% request.setCharacterEncoding("UTF-8");
   String path = request.getContextPath();
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="<%=path%>/a00_com/a00_com.css">
<style>

</style>
<script type="text/javascript" src="<%=path%>/a00_com/jquery-3.5.1.js"></script>
<script type="text/javascript">
/* 
 
 
*/
//
	$(document).ready(function(){
		$("h3").text("시작");
	});
</script>
</head>
<body>
<%--
[JSP]
[하] 1. jstl의 환경 설정 방법에 대하여 기술하세요
		1) WebContent\WEB-INF\lib에 jstl-1.2.jar 추가
		2) tag lib를 상단에 선언
			<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
			<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
			
[하] 2. jstl의 일반 변수 선언, 객체할당 선언을 구분하여 처리한 기본 예제를 기술하세요.
		> 일반 변수 선언
			<c:set var="name" value="홍길동" scope="request"/>
			<h4>${name}</h4>
			==> 홍길동 출력
		> 객체할당 선언
			<jsp:useBean id="prod" class="jspexp.z01_vo.Product"/>
			<c:set target="${prod}" property="name" value="apple"/>
			<c:set target="${prod}" property="price" value="2000"/>
			<c:set target="${prod}" property="cnt" value="2"/>
			<h3></h3>
			<table>
				<tr><th>물건명</th><td>${prod.name}</td></tr>
				<tr><th>가격</th><td>${prod.price}</td></tr>
				<tr><th>개수</th><td>${prod.cnt}</td></tr>
			</table>
			==> 표: 물건명	apple
					가격 2000
					개수 2
				출력
				
[하] 3. 기존 조건문과 else if 조건문에 해당하는 jstl의 조건 형식을 기술하세요.
		> 단일조건문(if)
			<c:if test="boolean">boolean이 true일 때 수행할 내용</c:if>
		> 다중조건문(else if)
			<c:choose>
				<c:when test="조건1인경우">조건1이 true일 때, </c:when>
				<c:when test="조건2인경우">조건2이 true일 때, </c:when>
				<c:when test="조건3인경우">조건3이 true일 때, </c:when>
				<c:otherwise>위의 조건을 제외한 나머지..</c:otherwise>
			</c:choose>
			
[하] 4. 임의의 구구단 문제를 form객체에 의해 정답값 입력에 따라, jstl에 의해 정답/오답을 구분하여 처리하세요.
--%>
	<h2>2월 22일 과제 4번</h2>
	<% request.setAttribute("random1", (int)(Math.random()*8+2));
	   request.setAttribute("random2", (int)(Math.random()*9+1)); %>
	<form method="post">
	<table>
		<tr><td>
			<input name="random1" value="${random1}"/> X 
			<input name="random2" value="${random2}"/> =
			<input size="2" name="answer" value=""/>
			<input type="submit" value="정답 확인"/>
		</td></tr>
		<c:if test="${not empty param.answer}">
		<tr><td>
			입력데이터: ${param.random1} X ${param.random2} = ${param.answer}<br>
			정답여부: ${param.random1*param.random2 == param.answer ? "정답입니다":"틀렸습니다"}
		</td></tr>
		</c:if>
	</table>
	</form>
<%--
[하] 5. jstl 반복문으로 개당 물건값을 입력받아 10개까지 계산된 물건총계을 출력하세요
        물건명:[   ] 단가:[   ] [10개까지 출력]
            @@@  1개  @@@ 원
            @@@  2개  @@@ 원
            @@@  3개  @@@ 원
            ...
            @@@  10개  @@@ 원
--%>
	<form>
	<table>
		<tr><th>물건명</th><td><input size="5" name="name"/></td><td rowspan="2"><input type="submit" value="10개까지 출력"/></td></tr>
		<tr><th>단가</th><td><input size="5" name="price"/></td></tr>
	</table>
	</form>
	<c:forEach var="cnt" begin="1" end="10">
		<c:if test="${not empty param.name && not empty param.price}">
			<h4>${param.name}  ${cnt}개  ${param.price*cnt}원</h4>
		</c:if>
	</c:forEach>
</body>
</html>