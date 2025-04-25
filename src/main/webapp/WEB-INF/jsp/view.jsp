<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.basejava.model.*" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <title>View Resume</title>
</head>
<body>
<h1>Resume: ${resume.fullName}</h1>

<h3>Contacts</h3>
<ul>
    <c:forEach var="entry" items="${resume.contacts}">
        <li>${entry.key}: ${entry.value}</li>
    </c:forEach>
</ul>

<h3>Sections</h3>
<c:forEach var="entry" items="${resume.sections}">
    <h4>${entry.key}</h4>
    <c:choose>
        <!-- TextSection -->
        <c:when test="${entry.value.class.simpleName == 'TextSection'}">
            <p>${entry.value.content}</p>
        </c:when>

        <!-- ListSection -->
        <c:when test="${entry.value.class.simpleName == 'ListSection'}">
            <ul>
                <c:forEach var="item" items="${entry.value.items}">
                    <li>${item}</li>
                </c:forEach>
            </ul>
        </c:when>

        <!-- OrganizationSection -->
        <c:when test="${entry.value.class.simpleName == 'OrganizationSection'}">
            <c:forEach var="org" items="${entry.value.organizations}">
                <div>
                    <strong>${org.homePage.name}</strong> <a href="${org.homePage.url}" target="_blank">${org.homePage.url}</a><br/>
                    <ul>
                        <c:forEach var="pos" items="${org.positions}">
                            <li>
                                <strong>${pos.startDate} - ${pos.endDate}</strong>: ${pos.title}<br/>
                                <c:if test="${not empty pos.description}">
                                    <em>${pos.description}</em>
                                </c:if>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </c:forEach>
        </c:when>
    </c:choose>
</c:forEach>
</body>
</html>