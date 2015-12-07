<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <link rel="apple-touch-icon" sizes="57x57"
          href="${pageContext.request.contextPath}/assets/favicons/apple-icon-57x57.png">
    <link rel="apple-touch-icon" sizes="60x60"
          href="${pageContext.request.contextPath}/assets/favicons/apple-icon-60x60.png">
    <link rel="apple-touch-icon" sizes="72x72"
          href="${pageContext.request.contextPath}/assets/favicons/apple-icon-72x72.png">
    <link rel="apple-touch-icon" sizes="76x76"
          href="${pageContext.request.contextPath}/assets/favicons/apple-icon-76x76.png">
    <link rel="apple-touch-icon" sizes="114x114"
          href="${pageContext.request.contextPath}/assets/favicons/apple-icon-114x114.png">
    <link rel="apple-touch-icon" sizes="120x120"
          href="${pageContext.request.contextPath}/assets/favicons/apple-icon-120x120.png">
    <link rel="apple-touch-icon" sizes="144x144"
          href="${pageContext.request.contextPath}/assets/favicons/apple-icon-144x144.png">
    <link rel="apple-touch-icon" sizes="152x152"
          href="${pageContext.request.contextPath}/assets/favicons/apple-icon-152x152.png">
    <link rel="apple-touch-icon" sizes="180x180"
          href="${pageContext.request.contextPath}/assets/favicons/apple-icon-180x180.png">
    <link rel="icon" type="image/png" sizes="192x192"
          href="${pageContext.request.contextPath}/assets/favicons/android-icon-192x192.png">
    <link rel="icon" type="image/png" sizes="32x32"
          href="${pageContext.request.contextPath}/assets/favicons/favicon-32x32.png">
    <link rel="icon" type="image/png" sizes="96x96"
          href="${pageContext.request.contextPath}/assets/favicons/favicon-96x96.png">
    <link rel="icon" type="image/png" sizes="16x16"
          href="${pageContext.request.contextPath}/assets/favicons/favicon-16x16.png">
    <link rel="manifest" href="${pageContext.request.contextPath}/assets/favicons/manifest.json">
    <meta name="msapplication-TileColor" content="#ffffff">
    <meta name="msapplication-TileImage"
          content="${pageContext.request.contextPath}/assets/favicons/ms-icon-144x144.png">
    <meta name="theme-color" content="#ffffff">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/css/style.css" rel="stylesheet">
    <%--<script src="${pageContext.request.contextPath}/assets/js/lib/jquery.min.js" type="text/javascript"></script>--%>
    <title>${config.commonTitle}</title>
    <script src="${pageContext.request.contextPath}/assets/js/jquery.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/assets/js/jquery.cookie.min.js" type="text/javascript"></script>
</head>
<body style="margin:0; padding-top: 0;">
<security:authorize access="isAnonymous()">
    <jsp:include page="/WEB-INF/views/login.jsp"/>
</security:authorize>
<security:authorize access="isAuthenticated()">
    <nav class="navbar navbar-default">
        <div class="container-fluid">
            <div class="navbar-header">
               <span class="navbar-brand">
                   Текущий пользователь: <security:authentication property="principal.username"/>
               </span>
                <ul class="nav navbar-nav navbar-right">
                    <li>
                        <a id="changeLayout" href="#">Изменить разметку</a>
                    </li>
                </ul>
            </div>
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <a href="${pageContext.request.contextPath}/logout">Выйти</a>
                </li>
            </ul>
        </div>
    </nav>
    <jsp:include page="/WEB-INF/views/voting.jsp"/>
    <security:authorize url="/admin" access="hasRole('ROLE_VOTE_ADMIN')">
        <jsp:include page="/WEB-INF/views/admin.jsp"/>
    </security:authorize>

    <footer class="footer">
        <div class="container">
            <p class="text-muted">${config.mainPageFooterText}</p>
            <!-- These aren't the Droids your looking for -->
        </div>
    </footer>
</security:authorize>

<script src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/assets/js/trianglify.min.js" type="text/javascript"></script>
<script type="text/javascript">
    var mainActive = true;
    var isBlocked = false;
    $(document).ready(function () {

        if ($.cookie('layoutType') == 1 ) {
            changeLayout(0);
        }
        $('#changeLayout').on('click', changeLayout);
    });

    function changeLayout(speed) {
        if (isBlocked)
            return;
        var duration = 250;
        if (speed >= 0) {
            duration = speed;
        }
        isBlocked = true;
        if (mainActive) {
            $('#mainContainer').fadeOut(duration, function () {
                $('#mainContainerFluid').fadeIn(duration);
                isBlocked = false;
                $.cookie('layoutType', '1', {expires: 15, path: '/'});
            })
        } else {
            $('#mainContainerFluid').fadeOut(duration, function () {
                $('#mainContainer').fadeIn(duration);
                isBlocked = false;
                $.cookie('layoutType', '0', {expires: 15, path: '/'});
            })
        }
        mainActive = !mainActive;
    }
</script>
</body>
</html>