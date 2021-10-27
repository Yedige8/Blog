<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Blog - Профиль (<c:out value="${requestScope.user.login}"/>)</title>
    <link rel="stylesheet" href="<c:url value="/css/bootstrap.min.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/css/style.css"/>"/>
</head>
<body>
<div class="small-container container-fluid my-4 p-4 border shadow-sm">
    <h3 class="h3 mb-3"><c:out value="${requestScope.user.login}"/> - Профиль</h3>
    <form action="<c:url value="/profile"/>" method="post" class="m-0">
        <div class="input-group mb-3">
            <input type="text" name="login" placeholder="Введите логин" aria-label="Логин" value="<c:out value="${requestScope.user.login}"/>" class="form-control"/>
        </div>
        <div class="input-group mb-3">
            <input type="password" name="password" placeholder="Введите пароль" aria-label="Пароль" value="<c:out value="${requestScope.user.password}"/>" class="form-control"/>
        </div>
        <div class="input-group mb-3">
            <input type="text" name="email" placeholder="Введите Email" aria-label="Email" value="<c:out value="${requestScope.user.email}"/>" class="form-control"/>
        </div>
        <div class="input-group mb-3">
            <input type="text" name="name" placeholder="Введите имя" aria-label="Имя" value="<c:out value="${requestScope.user.name}"/>" class="form-control"/>
        </div>
        <div class="input-group mb-3">
            <input type="text" name="surname" placeholder="Введите фамилия" aria-label="Фамилия" value="<c:out value="${requestScope.user.surname}"/>" class="form-control"/>
        </div>
        <input type="hidden" name="role" value="<c:out value="${requestScope.user.role.id}"/>"/>
        <input type="submit" value="Изменить" class="btn btn-primary">
    </form>
</div>
<div class="exit">
    <a href="<c:url value="/main"/>" class="enterA">
        На главную
    </a>
</div>
</body>
</html>
