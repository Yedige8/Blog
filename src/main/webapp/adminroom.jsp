<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="<c:url value="/css/bootstrap.min.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/css/style.css"/>"/>
</head>
<body>
    <div class="container my-4 p-4 border shadow-sm">
        <h3 class="h3">Admin room</h3>
    </div>
<div class="container my-4 p-4 border shadow-sm">
    <div class="btn btn-success">
        <a href="<c:url value="/adminUsers"/>" class="text-light">
            Пользователи
        </a>
    </div>
    <div class="btn btn-success">
        <a href="<c:url value="/adminArt?filter=all"/>" class="text-light">
            Публикация статей
        </a>
    </div>
</div>
<div class="exit">
    <a href="<c:url value="/main"/>" class="exitA">
        На главную
    </a>
</div>
</body>
</html>
