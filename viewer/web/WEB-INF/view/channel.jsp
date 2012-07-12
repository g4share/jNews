<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="/css/home.css" type="text/css"/>
    <title>Rss news aggregator (Channel Manager)</title>
</head>
<body>
<div>
    <table border="1">
        <tr>
            <th>Name</th>
            <th>Rss Link</th>
            <th>Enabled</th>
            <th>&nbsp;</th>
        </tr>
        <form:form action='/channel/add'
              method="post"
              commandName="channel"
              modelAttribute="channel">
            <tr>
                <td><form:input path="name" /></td>
                <td><form:input path="url" /></td>
                <td><form:checkbox path="enabled" /></td>

                <td><input type="submit" value="New "/></td>
            </tr>
        </form:form>

        <c:forEach var="channel" items="${channels}">
            <form name='${urlEncoder.encode(channel.getName())}'
                  action='/channel/${urlEncoder.encode(channel.getName())}'
                  method="post">

                <tr>
                    <td><c:out value="${channel.getName()}" /></td>
                    <td><c:out value="${channel.getUrl()}" /></td>

                    <td>
                       <input type="checkbox"
                           name="cb_enabled"
                           id="cb_enabled"
                           value='true' <c:if test="${channel.isEnabled()}">checked</c:if>/>
                    </td>
                    <td><input type="submit" value="Ssve"/></td>
                </tr>
            </form>
        </c:forEach>
    </table>
</div>

</body>
</html>