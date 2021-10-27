<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Chosens</title>
    <link rel="stylesheet" href="<c:url value="/css/bootstrap.min.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/css/style.css"/>"/>
</head>
<body>
<div class="container my-4 p-4 border shadow-sm">
  <h3 class="h3">Избранные </h3>
    <c:if test="${not empty requestScope.chosen}">
        <c:forEach var="choos" items="${requestScope.chosen}">
            <div class="container my-4 p-4 border shadow-sm">
                <a href="<%= request.getContextPath() %>/view?id=<c:out value="${choos.article.id}"/>">
                <div class="fw-bold">Название статьи: <c:out value="${choos.article.name}"/></div></a>
                <div class="fw-bold">Имя автора: <c:out value="${choos.user.name }"/></div>
            </div>
        </c:forEach>
    </c:if>
</div>
<div class="exit">
    <a href="<c:url value="/main"/>" class="enterA">
        На главную
    </a>
</div>
</body>
</html>
