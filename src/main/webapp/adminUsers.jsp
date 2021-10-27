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
    <table class="table table-striped align-middle">

        <thead>
        <tr>
            <th>Имя</th>
            <th>Фамилия</th>
            <th>Email</th>
            <th>Дата регистрации</th>
            <th>Роль</th>
            <th></th>
        </tr>
        </thead>

        <tbody>
        <c:forEach var="user" items="${requestScope.users}">
            <tr>
                <td><c:out value="${user.name}"/></td>
                <td><c:out value="${user.surname}"/></td>
                <td><c:out value="${user.email}"/></td>
                <td><c:out value="${user.registrationDate}"/></td>
                <td>
                    <select class="form-control" form="user${user.id}-save" name="role_id" >
                        <c:forEach var="role" items="${requestScope.roles}">
                            <c:if test="${user.role.id == role.id}">
                                <option value="${role.id}" selected>${role.roleName}</option>
                            </c:if>
                            <c:if test="${user.role.id != role.id}">
                                <option value="${role.id}">${role.roleName}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                </td>
                <td>
                    <form action="<c:url value="/adminUsers"/>" method="post" class="m-0" id="user${user.id}-save">
                        <input type="hidden" name="userId" value="${user.id}">
                        <input type="submit" class="btn btn-primary" value="сохранить">
                    </form>
                </td>
            </tr>
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
