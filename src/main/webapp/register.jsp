<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register_page</title>
    <link rel="stylesheet" href="<c:url value="/css/bootstrap.min.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/css/style.css"/>"/>
</head>
<body>
<div class="small-container container-fluid my-4 p-4 border shadow-sm">
    <h1 class="h3 mb-3">Регистрация</h1>
    <form action="<c:url value="/create"/>" method="get" class="m-0">
        <div class="input-group mb-3">
        <input type="text" name="login" placeholder="Введите логин.." aria-label="Логин" class="form-control">
        </div>
        <c:if test="${not empty sessionScope.login_error}">
            <div class="alert alert-danger" role="alert"><c:out value="${sessionScope.login_error}"/></div>
            <c:remove var="login_error" scope="session"/>

        </c:if>
        <div class="input-group mb-3">
            <input type="text" name="email" placeholder="Введите емайл.." aria-label="Email" class="form-control">
        </div>
        <c:if test="${not empty sessionScope.email_error}">
            <div class="alert alert-danger" role="alert">
                <c:out value="${sessionScope.email_error}"/>
            </div>
            <c:remove var="email_error" scope="session"/>

        </c:if>
        <div class="input-group mb-3">
            <input type="text" name="surname" placeholder="Введите фамилия.." aria-label="surname" class="form-control">
        </div>
        <div class="input-group mb-3">
            <input type="text" name="name" placeholder="Введите имя.." aria-label="name" class="form-control">
        </div>
        <div class="input-group mb-3">
            <input type="text" name="password" placeholder="Введите пароль.." aria-label="password" class="form-control">
        </div>
        <input type="submit" value="Зарегестрироваться" class="btn btn-primary">
    </form>
</div>

<div class="exit">

    <a href="<c:url value="/main"/>" class="exitA">
        На главную
    </a>
</div>
</body>
</html>
