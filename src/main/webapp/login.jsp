<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Blog - Вход</title>
    <link rel="stylesheet" href="<c:url value="/css/bootstrap.min.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/css/style.css"/>"/>
</head>
<body>
<div class="small-container container-fluid my-4 p-4 border shadow-sm">
    <h3 class="h3 mb-3">Вход</h3>
    <form action="<c:url value="/login"/>" method="post" class="m-0">
        <c:if test="${not empty sessionScope.login_error}">
            <div class="alert alert-danger" role="alert"><c:out value="${sessionScope.login_error}"/></div>
            <c:remove var="login_error" scope="session"/>
        </c:if>
        <div class="input-group mb-3">
            <input type="text" name="login" placeholder="Введите логин" aria-label="Логин" class="form-control"/>
        </div>
        <c:if test="${not empty sessionScope.password_error}">
            <div class="alert alert-danger" role="alert"><c:out value="${sessionScope.password_error}"/></div>
            <c:remove var="password_error" scope="session"/>
        </c:if>
        <div class="input-group mb-3">
            <input type="password" name="password" placeholder="Введите пароль" aria-label="Пароль" class="form-control"/>
        </div>
        <input type="submit" value="Войти" class="btn btn-primary"/>
    </form>
</div>
<div class="exit">
    <a href="<c:url value="/main"/>" class="enterA">
        На главную
    </a>
</div>
</body>
</html>
