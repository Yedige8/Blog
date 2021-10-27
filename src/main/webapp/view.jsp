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
    <h2 class="h2"><c:out value="${requestScope.article.name}"/>
    </h2>
    <div class="fw-bold">Категория: <c:out value="${requestScope.article.category.name}"/>
    </div>
    <div class="fw-bold">Дата публикации: <c:out value="${requestScope.article.publishedDate}"/>
    </div>
    <c:out value="${requestScope.article.text}"/>

    <div class="fw-bold">Автор: <c:out value="${requestScope.article.user.name} ${requestScope.article.user.surname}"/>
    </div>
    <br>
    <c:if test="${not empty requestScope.user && requestScope.user.role.id != 4}">
        <div class="choosen">
            <c:if test="${not requestScope.chosen}">
                <form action="<%= request.getContextPath() %>/choosen" method="post">
                    <input type="hidden" name="article_id" value="<c:out value="${requestScope.article.id}"/>">
                    <input type="submit" value="В избранные" class="btn btn-success">
                    <input type="hidden" value="add" name="action">
                </form>
            </c:if>
            <c:if test="${requestScope.chosen}">
                <form action="<%= request.getContextPath() %>/choosen" method="post">
                    <input type="hidden" name="article_id" value="<c:out value="${requestScope.article.id}"/>">
                    <input type="submit" value="Удалить" class="btn btn-danger">
                    <input type="hidden" value="remove" name="action">
                </form>
            </c:if>

            <a href="<c:url value="/choosen"/>" class="btn btn-primary">
                Перейти в избранные
            </a>
        </div>
    </c:if>
    <br>
    <div class="alert alert-success">
        <p class="mb-3">Средний рейтинг: <c:out value="${requestScope.average_rating}"/></p>
        <c:if test="${not empty requestScope.user && requestScope.user.role.id != 4}">
            <c:if test="${requestScope.is_rated}">
                Вы проголосовали: <c:out value="${requestScope.set_rating}"/>
            </c:if>
            <c:if test="${not requestScope.is_rated}">
                <form action="<%= request.getContextPath() %>/rating" method="post" class="m-0 d-flex">
                    <select class="form-control" name="rating">
                        <option value="1">1
                        </option>
                        <option value="2">2
                        </option>
                        <option value="3">3
                        </option>
                        <option value="4">4
                        </option>
                        <option value="5">5
                        </option>
                    </select>
                    <input type="hidden" name="articleId" value="<c:out value="${requestScope.article.id}"/>">
                    <input type="submit" value="Голосовать" class="btn btn-success">
                </form>
            </c:if>
        </c:if>
    </div>
    <h3 class="h3 col-8 offset-2">Комментарии</h3>
    <c:if test="${not empty requestScope.user && requestScope.user.role.id != 4}">
        <div class="col-8 offset-2 me-2">
            <form class="d-flex flex-column" action="<%= request.getContextPath() %>/comment" method="post">
                    <%--<input type="text" name="text" placeholder="Введите текст">--%>
                <textarea name="text" placeholder="Введите текст комментария" class="form-control"></textarea>
                <input type="hidden" name="articleId" value="<c:out value="${requestScope.article.id}"/>">
                <input type="hidden" value="add" name="action">
                <input class="align-self-center mt-2" type="submit" value="сохранить">
                <c:if test="${not empty sessionScope.comment_error}">
                    <div class="alert alert-danger" role="alert">
                        <c:out value="${sessionScope.comment_error}"/></div>
                    <c:remove var="comment_error" scope="session"/>
                </c:if>
            </form>
        </div>
    </c:if>
    <c:forEach var="comment" items="${requestScope.article.comments}">
        <div class="col-8 offset-2 p-3 border shadow-sm mt-2">
            <div class="fw-bold ">Текст: <c:out value="${comment.text}"/>
            </div>
            <div class="fw-bold">Автор комментария: <c:out value="${comment.user.name} ${comment.user.surname}"/>
            </div>
            <c:if test="${requestScope.user.role.id ==1 || requestScope.user.role.id == 2}">
                <div class="parent">
                    <form action="<c:url value="/comment"/>" method="post" class="mt-4 mb-0">
                        <input type="hidden" name="commentId" value="<c:out value="${comment.id}"/>">
                        <input type="hidden" name="articleId" value="<c:out value="${requestScope.article.id}"/>">
                        <input type="submit" value="Удалить" class="btn btn-danger">
                        <input type="hidden" value="remove" name="action">
                    </form>
                </div>
            </c:if>
        </div>
    </c:forEach>
</div>
<div class="exit">
    <a href="<c:url value="/main"/>" class="enterA">
        На главную
    </a>
</div>
</body>
</html>
