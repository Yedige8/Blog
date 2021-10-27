<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="<c:url value="/css/bootstrap.min.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/css/style.css"/>"/>
</head>
<body>
<div class="small-container container-fluid my-4 p-4 border shadow-sm">
    <h1 class="h3 mb-3">Статья</h1>
    <form action="<c:url value="/article"/>" method="post" class="m-0">
        <div class="input-group mb-3">
            <input type="text" name="name" placeholder="Введите название" aria-label="Логин" class="form-control">
        </div>
        <div class="input-group mb-3">
            <textarea name="text" placeholder="Введите текст" aria-label="Логин" class="form-control"></textarea>
        </div>
        <div class="input-group mb-3">
            <select name="category_id" aria-label="Логин" class="form-control">
                <c:forEach var="category" items="${requestScope.categories}">
                    <option value="<c:out value="${category.id}"/>">
                        <c:out value="${category.name}"/>
                    </option>
                </c:forEach>
            </select>
        </div>
        <input type="submit" value="сохранить" class="btn btn-primary">
    </form>
</div>
<div class="exit">
    <a href="<c:url value="/main"/>" class="exitA">
        На главную
    </a>
</div>
</body>
</html>
