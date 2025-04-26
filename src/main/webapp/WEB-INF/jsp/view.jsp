<%@ page import="com.basejava.model.ListSection" %>
<%@ page import="com.basejava.model.OrganizationSection" %>
<%@ page import="com.basejava.model.TextSection" %>
<%@ page import="com.basejava.util.HtmlUtil" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="<c:url value='/css/style.css'/>">
    <jsp:useBean id="resume" type="com.basejava.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <h1>
        ${resume.fullName}&nbsp;
        <a href="resume?uuid=${resume.uuid}&action=edit">
            <img src="<c:url value='/img/pencil.png'/>" alt="Edit">
        </a>
    </h1>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<com.basejava.model.ContactType, java.lang.String>"/>
                <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    <p>
    <hr>
    <table style="border-collapse: collapse; margin: auto;">
        <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry" type="java.util.Map.Entry<com.basejava.model.SectionType, com.basejava.model.Section>"/>
            <c:set var="type" value="${sectionEntry.key}"/>
            <c:set var="section" value="${sectionEntry.value}"/>
            <jsp:useBean id="section" type="com.basejava.model.Section"/>
            <tr>
                <td colspan="2" style="padding: 2px;">
                    <h2><a id="${type}">${type.title}</a></h2>
                </td>
            </tr>
            <c:choose>
                <c:when test="${type=='OBJECTIVE'}">
                    <tr>
                        <td colspan="2" style="padding: 2px;">
                            <h3><%=((TextSection) section).getContent()%></h3>
                        </td>
                    </tr>
                </c:when>
                <c:when test="${type=='PERSONAL'}">
                    <tr>
                        <td colspan="2" style="padding: 2px;">
                            <%=((TextSection) section).getContent()%>
                        </td>
                    </tr>
                </c:when>
                <c:when test="${type=='QUALIFICATIONS' || type=='ACHIEVEMENT'}">
                    <tr>
                        <td colspan="2" style="padding: 2px;">
                            <ul>
                                <c:forEach var="item" items="<%=((ListSection) section).getItems()%>">
                                    <li>${item}</li>
                                </c:forEach>
                            </ul>
                        </td>
                    </tr>
                </c:when>
                <c:when test="${type=='EXPERIENCE' || type=='EDUCATION'}">
                    <c:forEach var="org" items="<%=((OrganizationSection) section).getOrganizations()%>">
                        <tr>
                            <td colspan="2" style="padding: 2px;">
                                <c:choose>
                                    <c:when test="${empty org.homePage.url}">
                                        <h3>${org.homePage.name}</h3>
                                    </c:when>
                                    <c:otherwise>
                                        <h3><a href="${org.homePage.url}">${org.homePage.name}</a></h3>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                        <c:forEach var="position" items="${org.positions}">
                            <jsp:useBean id="position" type="com.basejava.model.Organization.Position"/>
                            <tr>
                                <td style="width: 15%; vertical-align: top; padding: 2px;">
                                    <%=HtmlUtil.formatDates(position)%>
                                </td>
                                <td style="padding: 2px;">
                                    <b>${position.title}</b><br>${position.description}
                                </td>
                            </tr>
                        </c:forEach>
                    </c:forEach>
                </c:when>
            </c:choose>
        </c:forEach>
    </table>
    <br/>
    <button onclick="window.history.back()">ОК</button>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
