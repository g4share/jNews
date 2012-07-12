<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="/css/home.css" type="text/css"/>
    <meta http-equiv="refresh" content="20">
    <title>Rss news aggregator</title>
</head>
<body>
<div>
    <span class="blockFloatLeft categories">
        <c:forEach var="category" items="${newsCategories}">
            <div>
                <c:choose>
                    <c:when test="${empty category.getName()}">
                        <a class="src menu" href="/home">Toate (${category.getCount()})</a>
                        <br/><br/>
                    </c:when>
                    <c:otherwise>
                        <spring:url value="/home/category/{categoryName}" var="categoryUrl" htmlEscape="true">
                            <spring:param name="categoryName" value="${urlEncoder.encode(category.getName())}" />
                        </spring:url>
                        <a class="src menu" href="${categoryUrl}" /><c:out value="${category.getName()}" /> (${category.getCount()})</a>
                    </c:otherwise>
                </c:choose>
            </div>
        </c:forEach>
    </span>
    <span class="blockFloatLeft">
        <c:forEach var="news" items="${newsList}">
            <div class="block">
                <span  id="newsDate" class="blockFloatLeft">${jspFormatter.format(news.getPubDate())}</span>
                <span class="blockAlignLeft">
                    <spring:url value="{newsLinkName}" var="newsLinkUrl" htmlEscape="true">
                        <spring:param name="newsLinkName" value="${news.getLink()}" />
                    </spring:url>
                    <a class="src link" href="${newsLinkUrl}" target="_blank"><c:out value="${news.getTitle()}" /> </a>

                    <span class="blockAlignLeft">
                        <spring:url value="/home/site/{newsChannelName}" var="newsChannelUrl" htmlEscape="true">
                            <spring:param name="newsChannelName" value="${urlEncoder.encode(news.getChannelId())}" />
                        </spring:url>
                        <a class="src site" href="${newsChannelUrl}"><c:out value="${news.getChannelId()}" /> </a>

                            /

                        <spring:url value="/home/category/{newsCategoryName}" var="newsCategoryUrl" htmlEscape="true">
                            <spring:param name="newsCategoryName" value="${urlEncoder.encode(news.getCategory())}" />
                        </spring:url>
                        <a class="src site" href="${newsCategoryUrl}"><c:out value="${news.getCategory()}" /> </a>
                    </span>
                </span>
            </div>
        </c:forEach>
    </span>

    <span class="blockFloatLeft blockLogin">
        <form name='login' action='/login' method="post">
            <table border="0">
                <tr>
                    <td>Email</td>
                    <td><input type="text" name="usr" id="usr"/></td>
                </tr>
                <tr>
                    <td>Parola</td>
                    <td><input type="password" name="pwd" id="pwd"/></td>
                </tr>
                <tr>
                    <td colspan="2" align="right">
                        <input type="submit">Login</input>
                    </td>
                </tr>
            </table>
            </form>
    </span>
</div>
</body>
</html>