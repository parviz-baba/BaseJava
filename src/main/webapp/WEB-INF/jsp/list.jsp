<%@ page import="com.basejava.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="<c:url value='/css/style.css'/>">
    <title>Список всех резюме</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <a href="resume?action=add">
        <img src="<c:url value='/img/add.png'/>" alt="Add">
    </a>
    <br>
    <table style="margin: auto; border: 1px solid black; border-collapse: collapse;">
        <tr>
            <th style="padding: 8px;">Имя</th>
            <th style="padding: 8px;">Email</th>
            <th></th>
            <th></th>
        </tr>
        <jsp:useBean id="resumes" scope="request" type="java.util.List"/>
        <c:forEach items="${resumes}" var="resume">
            <jsp:useBean id="resume" type="com.basejava.model.Resume"/>
            <tr>
                <td><a href="resume?uuid=${resume.uuid}&action=view">${resume.fullName}</a></td>
                <td><%=ContactType.EMAIL.toHtml(resume.getContact(ContactType.EMAIL))%>
                </td>
                <td>
                    <a href="resume?uuid=${resume.uuid}&action=delete">
                        <img src="<c:url value='/img/delete.png'/>" alt="Delete">
                    </a>
                </td>
                <td>
                    <a href="resume?uuid=${resume.uuid}&action=edit">
                        <img src="<c:url value='/img/pencil.png'/>" alt="Edit">
                    </a>
                </td>
            </tr>
        </c:forEach>
    </table>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>