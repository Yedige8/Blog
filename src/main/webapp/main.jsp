<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="<c:url value="/css/bootstrap.min.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/css/style.css"/>"/>
</head>
<body>

<div class="container my-4 p-4 border shadow-sm">
    <h3 class="h3">Blog</h3>
    <c:if test="${not empty requestScope.user}">
        <c:if test="${requestScope.user.role.roleName == 'Заблокированый'}">
            <br>
            <h3 class="h3">Вы заблокированы</h3>
            <br>
        </c:if>
        <div class="btn btn-primary">
            <a href="<c:url value="/profile"/>" class="text-light">
                Профиль
            </a>
        </div>
        <c:if test="${requestScope.user.role.roleName != 'Заблокированый'}">
            <div class="btn btn-primary">
                <a href="<c:url value="/article"/>" class="text-light">
                   Написать статью
                </a>
            </div>
            <div class="btn btn-primary">
                <a href="<c:url value="/choosen"/>" class="text-light">
                    Избранное
                </a>
            </div>
        </c:if>
        <c:if test="${requestScope.user.role.roleName == 'Администратор'|| requestScope.user.role.roleName == 'Модератор'}">
            <div class="btn btn-primary">
                <a href="<c:url value="/room"/>" class="text-light">
                   Перейти в комнату админов
                </a>
            </div>
        </c:if>
        <div class="btn btn-danger">
            <a href="<c:url value="/exit"/>" class="text-light">
              Выход из профиля
            </a>
        </div>
    </c:if>
    <c:if test="${empty requestScope.user}">
        <div class="container my-4 p-4 border shadow-sm">
            <div class="btn btn-success ">
                <a href="<c:url value="/login.jsp"/>" class="text-light">
                    Войти
                </a>
            </div>
            <div class="btn btn-success ">
                <a href="<c:url value="/register.jsp"/>" class="text-light">
                   Зарегистрировться
                </a>
            </div>
        </div>
    </c:if>
</div>
<div class="container my-4 p-4 border shadow-sm">
    <h3 class="h3">Статьи</h3>
    <div class="art">
        <c:forEach var="article" items="${requestScope.articles}">
            <div class="container my-4 p-4 border shadow-sm ">
            <a href="<%= request.getContextPath() %>/view?id=<c:out value="${article.id}"/>" class="shadow-sm">
                <p class="fw-bold text-muted">Название статьи:
                    <c:out value="${article.name}"/>
                </p>
                <p class="fw-bold text-muted">Текст статьи:
                        <c:out value="${article.text}"/>
                <p>
            </a>
            </div>
        </c:forEach>
    </div>
</div>
</body>
</html>
