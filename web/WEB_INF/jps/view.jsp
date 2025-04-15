<%@ page import="com.basejava.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.basejava.model.Resume" scope="request"/>
    <title>Просмотр резюме</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <h2>${resume.fullName}</h2>
    <h3>Контакты</h3>
    <ul>
        <c:forEach var="contact" items="${resume.contacts}">
            <li><b>${contact.key.title}</b>: ${contact.value}</li>
        </c:forEach>
    </ul>

    <h3>Секции</h3>
    <c:if test="${resume.sections.OBJECTIVE != null}">
        <h4>Objective</h4>
        <p>${resume.sections.OBJECTIVE.content}</p>
    </c:if>
    <c:if test="${resume.sections.PERSONAL != null}">
        <h4>Personal</h4>
        <p>${resume.sections.PERSONAL.content}</p>
    </c:if>

    <c:if test="${resume.sections.ACHIEVEMENT != null}">
        <h4>Achievements</h4>
        <ul>
            <c:forEach var="item" items="${resume.sections.ACHIEVEMENT.items}">
                <li>${item}</li>
            </c:forEach>
        </ul>
    </c:if>
    <c:if test="${resume.sections.QUALIFICATIONS != null}">
        <h4>Qualifications</h4>
        <ul>
            <c:forEach var="item" items="${resume.sections.QUALIFICATIONS.items}">
                <li>${item}</li>
            </c:forEach>
        </ul>
    </c:if>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>


<c:if test="${resume.sections.EXPERIENCE != null}">
    <h4>Experience</h4>
    <c:forEach var="org" items="${resume.sections.EXPERIENCE.organizations}">
        <div>
            <strong>${org.homePage.name}</strong>
            <c:if test="${org.homePage.url != null && !org.homePage.url.isEmpty()}">
                (<a href="${org.homePage.url}" target="_blank">${org.homePage.url}</a>)
            </c:if>
            <ul>
                <c:forEach var="pos" items="${org.positions}">
                    <li>
                            ${pos.startDate} - ${pos.endDate}: <strong>${pos.title}</strong>
                        <c:if test="${pos.description != null && !pos.description.isEmpty()}">
                            <br/>${pos.description}
                        </c:if>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </c:forEach>
</c:if>

<c:if test="${resume.sections.EDUCATION != null}">
    <h4>Education</h4>
    <c:forEach var="org" items="${resume.sections.EDUCATION.organizations}">
        <div>
            <strong>${org.homePage.name}</strong>
            <c:if test="${org.homePage.url != null && !org.homePage.url.isEmpty()}">
                (<a href="${org.homePage.url}" target="_blank">${org.homePage.url}</a>)
            </c:if>
            <ul>
                <c:forEach var="pos" items="${org.positions}">
                    <li>
                            ${pos.startDate} - ${pos.endDate}: <strong>${pos.title}</strong>
                        <c:if test="${pos.description != null && !pos.description.isEmpty()}">
                            <br/>${pos.description}
                        </c:if>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </c:forEach>
</c:if>