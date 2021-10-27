<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="<c:url value="/css/bootstrap.min.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/css/style.css"/>"/>
    <script src="<c:url value="/js/bootstrap.bundle.min.js"/>"> </script>
</head>
<body>
<div class="container my-4 p-4 border shadow-sm">
    <table class="table table-striped align-middle ">
        <thead>
        <tr>
            <th>Название</th>
            <th>Категория</th>
            <th>Дата публикации</th>
            <th>
                <div class="btn-group" role="group">
                    <button id="btnGroupDrop1" type="button" class="btn btn-primary dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                        Публикация
                    </button>
                    <ul class="dropdown-menu" aria-labelledby="btnGroupDrop1">
                        <li><a class="dropdown-item" href="<c:url value="/adminArt?filter=published"/>">Опубликованные </a></li>
                        <li><a class="dropdown-item" href="<c:url value="/adminArt?filter=unpublished"/>">Не опубликованные</a></li>
                        <li><a class="dropdown-item" href="<c:url value="/adminArt?filter=all"/>">Все</a></li>
                    </ul>
                </div>
            </th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="articles" items="${requestScope.article}">
        <tr>
            <td>
                <a href="<c:url value="/view?id=${articles.id}"/>">
                    <c:out value="${articles.name}"/>
                </a>
            </td>
            <td><c:out value="${articles.category.name}"/></td>
            <td><c:out value="${articles.publishedDate}"/></td>

            <c:if test="${articles.published == true}">
            <td>
                <form action="<c:url value="/adminArt?filter=all"/>" method="post" class="m-0">
                    <input type="hidden" name="articleId" value="${articles.id}">
                    <input type="submit" value="Удалить публикацию" class="btn btn-danger">
                    <input type="hidden" value="remove" name="action">
                </form>
            </td>
            </c:if>
            <c:if test="${articles.published == false}">
            <td>
                <form action="<c:url value="/adminArt?filter=all"/>" method="post" class="m-0">
                    <input type="hidden" name="articleId" value="${articles.id}">
                    <input type="submit" value="Опубликовать" class="btn btn-success">
                    <input type="hidden" value="add" name="action">
                </form>
            </td>
            </c:if>
            </c:forEach>
        </tbody>
    </table>
</div>
<div class="exit">
    <a href="<c:url value="/main"/>" class="exitA">
        На главную
    </a>
</div>
</body>
</html>
