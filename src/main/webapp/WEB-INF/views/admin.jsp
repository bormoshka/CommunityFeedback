<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container">
    <div class="row admin_header">
        <div class="col-md-4">
            <h2>Управление</h2>
        </div>
    </div>
    <div class="row admin_rows">
        <div class="col-md-6">
            <h3>Добавить Кандидатуру</h3>

            <form class="form" role="form" method="POST" action="${pageContext.request.contextPath}/createCandidate">
                <div class="form-group">
                    <label for="nomination">${adminMessage}</label>
                </div>
                <div class="form-group">
                    <label for="nomination">Номинация:</label>
                    <select class="form-control" id="nomination" name="nomination">
                        <option value="0">Генератор идей</option>
                        <option value="1">Золотые руки</option>
                        <option value="2">Флагман - 2015</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="displayedName">Отображаемое имя:</label>
                    <input type="text" class="form-control" id="displayedName" name="displayedName">
                </div>
                <%--<div class="form-group">
                    <label for="username">Имя учетной записи:</label>
                    <input type="text" class="form-control" id="username" name="username">
                </div>--%>
                <div class="form-group">
                    <label for="about">Описание:</label>
                    <textarea type="text" class="form-control" id="about" name="about"></textarea>
                </div>
                <button type="submit" class="btn btn-default">Создать</button>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>
        </div>
        <div class="col-md-6">
            <h3>Инструкция</h3>

            <p>Все поля обязательны для заполнения. Список кондидатов где-то выше.
                Редактровать и удалять пока нельзя, но ведь и не придется? =)
                Если что это можно сделать в "ручном режиме".</p>

            <p><a class="btn btn-default"
                  href="${pageContext.request.contextPath}/getResults"
                  role="button">Получить результаты</a></p>
            <c:if test="${not empty results}">
                <h4>Результаты <span class="label label-success">Завершили голосование</span> <span
                        class="label label-warning">всего голосов</span></h4>

                <div class="list-group">
                    <c:forEach var="nomination" items="${results}">
                        <a class="list-group-item">
                            <h5 class="list-group-item-heading bigger-group-header">${nomination.nominationHeader}</h5>

                            <p class="list-group-item-text expand-line-height">
                                <c:if test="${not empty nomination.candidates}">
                                    <c:forEach var="candidate" items="${nomination.candidates}">
                                        ${candidate.name}
                                        <span class="label label-success">${candidate.countFinished}</span>
                                        <span class="label label-warning">${candidate.count}</span></br>
                                    </c:forEach>
                                </c:if>
                            </p>
                        </a>
                    </c:forEach>
                </div>
            </c:if>
            <c:if test="${not empty votedUsers}">
                <h4>Статус голосования пользователей</h4>

                <div class="list-group">
                    <a href="#" class="list-group-item">
                        <p class="list-group-item-text expand-line-height">
                            <c:forEach var="user" items="${votedUsers}">
                                ${user.username}
                                <c:if test="${user.isFinished}">
                                    <span class="label label-success">Голосование завершено</span>
                                </c:if>
                                <c:if test="${!user.isFinished}">
                                    <span class="label label-default">В процессе...</span>
                                </c:if>
                                <br>
                            </c:forEach>
                        </p>
                    </a>
                </div>
            </c:if>

        </div>
    </div>
</div>