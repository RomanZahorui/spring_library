<%--
  Created by IntelliJ IDEA.
  User: Marian
  Date: 22.07.2019
  Time: 22:00
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<link rel="stylesheet" href="<c:url value="/resources/tableCss.css"/>" type="text/css"><head>

    <title>Title</title>
</head>
<body>
<%@include file="/WEB-INF/pages/menu.jsp" %>
<table class="simple-little-table" cellspacing='0' style="text-align: center; margin: auto; margin-top: 50px">
    <thead>
    <tr>
        <th>${name} зареєстрований з такого часу: ${readerDto.localDate}</th>
    </tr><!-- Table Header -->
    </thead>
</table>

</body>
</html>
