<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="holder" class="jumbotron vertical-center">
    <%--<script type="application/javascript">
        $(document).ready(function () {
            var pattern = Trianglify({
                width: window.innerWidth,
                height: window.innerHeight,
                variance: "0.88",
                cell_size: 70,
                seed: '44',
                x_colors: 'Spectral'
            }).png();

            $('#holder').css('background-image', 'url(' + pattern +')');
        });

    </script>--%>
    <div class="container">
        <div class="row">
            <div class="col-md-4 col-md-offset-4">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        ${config.loginFormHeader}
                    </div>
                    <div class="panel-body">
                        <c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">×</button>
                                <strong>Ошибка авторизации!</strong> <br>
                                Неверный логин или пароль! <br>
                                <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>
                            </div>
                        </c:if>

                        <form name='loginForm' action="${pageContext.request.contextPath}/login" method='POST'
                              style="margin:0">
                            <fieldset>
                                <div class="form-group input-group">
                                    <input id="login" class="form-control" placeholder="Логин" name="login" type="text">
                                    <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                                </div>
                                <div class="form-group input-group">
                                    <input class="form-control" placeholder="Пароль" name="password" type="password"
                                           value="">
                                    <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                                </div>

                                <input class="btn btn-lg btn-primary btn-block" type="submit" value="Войти">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            </fieldset>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
