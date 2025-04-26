<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.basejava.model.ContactType" %>
<%@ page import="com.basejava.model.SectionType" %>
<%@ page import="com.basejava.model.Resume" %>


<%
    Resume resume = (Resume) request.getAttribute("resume");
%>

<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Resume</title>
    <link rel="stylesheet" href="<c:url value='/css/style.css'/>">
</head>
<body>
<jsp:include page="fragments/header.jsp"/>

<section>
    <form method="post" action="resume">
        <% if (resume != null && resume.getUuid() != null && !resume.getUuid().isEmpty()) { %>
        <input type="hidden" name="uuid" value="<%= resume.getUuid() %>"/>
        <% } %>

        <div>
            <label for="fullName">Full Name:</label>
            <input type="text" id="fullName" name="fullName" value="<%= resume.getFullName() %>" size="30" required/>
        </div>

        <h3>Contacts</h3>
        <c:forEach items="${ContactType.values()}" var="type">
            <div>
                <label for="${type.name()}">${type.title}</label>
                <input type="text" id="${type.name()}" name="${type.name()}" size="30" value="${resume.getContact(type)}"/>
            </div>
        </c:forEach>

        <h3>Text Sections</h3>
        <c:forEach items="${SectionType.values()}" var="type">
            <c:if test="${type.isText()}">
                <div>
                    <label for="${type.name()}">${type.title}</label>
                    <textarea id="${type.name()}" name="${type.name()}" rows="3" cols="50">${resume.getSection(type)}</textarea>
                </div>
            </c:if>
        </c:forEach>

        <h3>List Sections</h3>
        <c:forEach items="${SectionType.values()}" var="type">
            <c:if test="${type.isList()}">
                <div>
                    <label for="${type.name()}">${type.title}</label>
                    <textarea id="${type.name()}" name="${type.name()}" rows="5" cols="50">${resume.getSection(type)}</textarea>
                </div>
            </c:if>
        </c:forEach>

        <div>
            <button type="submit">Save</button>
        </div>
    </form>
</section>

<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
