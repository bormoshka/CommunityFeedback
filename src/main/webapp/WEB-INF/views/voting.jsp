<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<div class="container">
    <div class="row">
        <div class="col-md-12">
            <c:if test="${not empty voteMessage}">
                <div class="alert alert-success" role="alert">${voteMessage}</div>
            </c:if>
            <c:if test="${!canVote}">
                <div class="container">
                    <div class="row">
                        <div class="col-md-12">
                            <h2>Вы завершили голосование
                                <a class="revote-link" href="${pageContext.request.contextPath}/IWantVoteAgain">Ой, а
                                    можно
                                    переголосовать?</a></h2>
                        </div>
                    </div>
                </div>
            </c:if>
        </div>
    </div>
</div>
<!-- pre-alpha -->
<div id="mainContainerFluid" class="container-fluid" style="display: none;">
    <c:if test="${not empty nominations}">
        <div class="row">
            <c:forEach var="nomination" items="${nominations}">
                <div class="col-md-4">
                    <div class="container-fluid">
                        <div class="row nomination nomination_header_${nomination.id}">
                            <div class="col-md-12">
                                <h2>${nomination.nominationHeader}</h2>
                            </div>
                        </div>
                        <c:forEach var="candidate" items="${nomination.candidates}">
                            <div class="row candidate_row nomination_${nomination.id}">
                                <div class="col-md-12 profile-card">
                                    <h3>${candidate.name}
                                        <c:if test="${candidate.isChosen}">
                                            <span class="label label-success">Вы проголосавали за этого кандидата!</span>
                                        </c:if>
                                    </h3>

                                    <div id="candidate_photo_alt_${candidate.id}" class="candidate-photo media-object">
                                            <%--<img src="${pageContext.request.contextPath}/assets/img/profile.jpg"
                                                 class="img-circle" alt="${candidate.name}" width="120" height="120">--%>
                                    </div>

                                    <p class="candidate-about">${candidate.about}</p>
                                    <c:if test="${canVote}">
                                        <c:if test="${!candidate.isChosen}">
                                            <a class="btn btn-primary"
                                               href="${pageContext.request.contextPath}/vote/${candidate.id}/${nomination.id}"
                                               role="button">Выбрать</a>
                                        </c:if>
                                        <c:if test="${candidate.isChosen}">
                                            <a class="btn btn-default"
                                               href="${pageContext.request.contextPath}/cancel/${candidate.id}/${nomination.id}"
                                               role="button">Отменить</a>
                                        </c:if>
                                    </c:if>
                                    <security:authorize url="/admin" access="hasRole('ROLE_VOTE_ADMIN')">
                                        <div class="btn-group">
                                            <button type="button" class="btn btn-warning dropdown-toggle"
                                                    data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                                Удалить <span class="caret"></span>
                                            </button>
                                            <ul class="dropdown-menu">
                                                <li>
                                                    <a href="${pageContext.request.contextPath}/delete/${candidate.id}/${nomination.id}">Удалить</a>
                                                </li>
                                            </ul>
                                        </div>
                                    </security:authorize>
                                    </p>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </c:forEach>
        </div>
    </c:if>
</div>

<div id="mainContainer" class="container">

<c:if test="${not empty nominations}">
    <c:forEach var="nomination" items="${nominations}">
        <div class="row nomination nomination_header_${nomination.id}">
            <div class="col-md-12">
                <h2>${nomination.nominationHeader}</h2>
            </div>
        </div>
        <c:if test="${not empty nomination.candidates}">
            <% boolean closed = false;
                int iteration = 0;%>

            <c:forEach var="candidate" items="${nomination.candidates}">
                <%
                    if (iteration == 0) {
                        closed = false;
                %>
                <div class="row nomination_${nomination.id}">
                    <%}%>

                    <div class="col-md-6 profile-card">
                        <h3>${candidate.name}
                            <c:if test="${candidate.isChosen}">
                                <span class="label label-success">Вы проголосавали за этого кандидата!</span>
                            </c:if>
                        </h3>

                        <div id="candidate_photo_${candidate.id}" class="candidate-photo media-object">
                                <%--<img src="${pageContext.request.contextPath}/assets/img/profile.jpg"
                                     class="img-circle" alt="${candidate.name}" width="120" height="120">--%>
                        </div>

                        <script type="application/javascript">
                            $(document).ready(function () {
                                var pattern = Trianglify({
                                    width: 120,
                                    height: 120,
                                    variance: "0.98",
                                    cell_size: 20,
                                    seed: '${candidate.name}',
                                    x_colors: 'random'
                                });

                                $('#candidate_photo_${candidate.id}').append(pattern.canvas());
                                $('#candidate_photo_${candidate.id} canvas').addClass("img-circle");

                                $('#candidate_photo_alt_${candidate.id}').append(pattern.canvas());
                                $('#candidate_photo_alt_${candidate.id} canvas').addClass("img-circle");
                            });

                        </script>
                        <p class="candidate-about">${candidate.about}</p>
                        <c:if test="${canVote}">
                            <c:if test="${!candidate.isChosen}">
                                <a class="btn btn-primary"
                                   href="${pageContext.request.contextPath}/vote/${candidate.id}/${nomination.id}"
                                   role="button">Выбрать</a>
                            </c:if>
                            <c:if test="${candidate.isChosen}">
                                <a class="btn btn-default"
                                   href="${pageContext.request.contextPath}/cancel/${candidate.id}/${nomination.id}"
                                   role="button">Отменить</a>
                            </c:if>
                        </c:if>
                        <security:authorize url="/admin" access="hasRole('ROLE_VOTE_ADMIN')">
                            <div class="btn-group">
                                <button type="button" class="btn btn-warning dropdown-toggle"
                                        data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    Удалить <span class="caret"></span>
                                </button>
                                <ul class="dropdown-menu">
                                    <li>
                                        <a href="${pageContext.request.contextPath}/delete/${candidate.id}/${nomination.id}">Удалить</a>
                                    </li>
                                </ul>
                            </div>
                        </security:authorize>
                        </p>
                    </div>
                    <%
                        if (++iteration == 2) {
                            closed = true;
                            iteration = 0;
                    %>
                </div>
                <%}%>
            </c:forEach>
            <%if (!closed) {%>
            </div>
            <%}%>
        </c:if>
    </c:forEach>
</c:if>
</div>
<c:if test="${canVote}">
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <a class="btn btn-primary finish-vote-btn" href="${pageContext.request.contextPath}/finish">ЗАКОНЧИТЬ
                    ГОЛОСОВАНИЕ!</a>
            </div>
        </div>
    </div>
</c:if>