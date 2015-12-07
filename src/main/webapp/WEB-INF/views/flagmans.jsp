<%--
  Created by IntelliJ IDEA.
  User: gmineev
  Date: 03.12.2015
  Time: 16:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
  <title></title>
</head>
<body>
<c:if test="${not empty users}">

  <ul>
    <c:forEach var="user" items="${users}">
      <li>${user.username}</li>
    </c:forEach>
  </ul>

</c:if>
</body>
</html>
