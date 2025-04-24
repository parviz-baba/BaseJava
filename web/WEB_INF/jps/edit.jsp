<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="com.basejava.model.*" %>
<html>
<head>
    <title>Edit Resume</title>
</head>
<body>
<h1>Edit Resume</h1>
<form method="post">
    <input type="hidden" name="uuid" value="${resume.uuid}"/>
    <c:if test="${param.error == 'emptyName'}">
        <p style="color:red;">Full Name sahəsi boş ola bilməz!</p>
    </c:if>
    Full Name: <input type="text" name="fullName" value="${resume.fullName}"/><br/>

    <h3>Contacts</h3>
    <c:forEach var="type" items="${ContactType.values()}">
        ${type}: <input type="text" name="${type}" value="${resume.getContact(type)}"/><br/>
    </c:forEach>

    <h3>Text Sections</h3>
    OBJECTIVE: <textarea name="OBJECTIVE">${resume.getSections().get(SectionType.OBJECTIVE)}</textarea><br/>
    PERSONAL: <textarea name="PERSONAL">${resume.getSections().get(SectionType.PERSONAL)}</textarea><br/>

    <h3>List Sections</h3>
    ACHIEVEMENT: <textarea name="ACHIEVEMENT">
<c:forEach var="item" items="${resume.getSections().get(SectionType.ACHIEVEMENT).items}">
    ${item}
</c:forEach></textarea><br/>

    QUALIFICATIONS: <textarea name="QUALIFICATIONS">
<c:forEach var="item" items="${resume.getSections().get(SectionType.QUALIFICATIONS).items}">
    ${item}
</c:forEach></textarea><br/>

    <h3>Experience</h3>
    <% OrganizationSection experienceSection = (OrganizationSection) resume.getSections().get(SectionType.EXPERIENCE); %>
    <div id="experience-section">
        <c:forEach var="org" items="${experienceSection.organizations}" varStatus="orgStatus">
            <div class="organization-block">
                <input type="text" name="EXPERIENCE_name_${orgStatus.index}" value="${org.homePage.name}" placeholder="Company Name"/><br/>
                <input type="text" name="EXPERIENCE_url_${orgStatus.index}" value="${org.homePage.url}" placeholder="Company URL"/><br/>
                <c:forEach var="pos" items="${org.positions}" varStatus="posStatus">
                    <div class="position-block">
                        <input type="text" name="EXPERIENCE_title_${orgStatus.index}_${posStatus.index}" value="${pos.title}" placeholder="Title"/><br/>
                        <input type="date" name="EXPERIENCE_startDate_${orgStatus.index}_${posStatus.index}" value="${pos.startDate}"/><br/>
                        <input type="date" name="EXPERIENCE_endDate_${orgStatus.index}_${posStatus.index}" value="${pos.endDate}"/><br/>
                        <textarea name="EXPERIENCE_description_${orgStatus.index}_${posStatus.index}" placeholder="Description">${pos.description}</textarea><br/>
                    </div>
                </c:forEach>
            </div>
        </c:forEach>
    </div>

    <h3>Education</h3>
    <% OrganizationSection educationSection = (OrganizationSection) resume.getSections().get(SectionType.EDUCATION); %>
    <div id="education-section">
        <c:forEach var="org" items="${educationSection.organizations}" varStatus="orgStatus">
            <div class="organization-block">
                <input type="text" name="EDUCATION_name_${orgStatus.index}" value="${org.homePage.name}" placeholder="School Name"/><br/>
                <input type="text" name="EDUCATION_url_${orgStatus.index}" value="${org.homePage.url}" placeholder="School URL"/><br/>
                <c:forEach var="pos" items="${org.positions}" varStatus="posStatus">
                    <div class="position-block">
                        <input type="text" name="EDUCATION_title_${orgStatus.index}_${posStatus.index}" value="${pos.title}" placeholder="Title"/><br/>
                        <input type="date" name="EDUCATION_startDate_${orgStatus.index}_${posStatus.index}" value="${pos.startDate}"/><br/>
                        <input type="date" name="EDUCATION_endDate_${orgStatus.index}_${posStatus.index}" value="${pos.endDate}"/><br/>
                        <textarea name="EDUCATION_description_${orgStatus.index}_${posStatus.index}" placeholder="Description">${pos.description}</textarea><br/>
                    </div>
                </c:forEach>
            </div>
        </c:forEach>
    </div>

    <br/>
    <input type="submit" value="Save"/>
</form>
</body>
</html>