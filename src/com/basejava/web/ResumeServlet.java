package com.basejava.web;

import com.basejava.Config;
import com.basejava.model.*;
import com.basejava.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");

        if (fullName == null || fullName.trim().isEmpty()) {
            response.sendRedirect("resume?uuid=" + uuid + "&action=edit&error=emptyName");
            return;
        }

        Resume r = storage.get(uuid);
        r.setFullName(fullName.trim());

        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && !value.trim().isEmpty()) {
                r.addContact(type, value.trim());
            } else {
                r.getContacts().remove(type);
            }
        }

        for (SectionType type : SectionType.values()) {
            String value = request.getParameter(type.name());
            if (value == null || value.trim().isEmpty()) {
                r.getSections().remove(type);
                continue;
            }

            switch (type) {
                case OBJECTIVE:
                case PERSONAL:
                    r.addSection(type, new TextSection(value.trim()));
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    List<String> items = Arrays.stream(value.split("\n"))
                            .map(String::trim)
                            .filter(s -> !s.isEmpty())
                            .collect(Collectors.toList());
                    r.addSection(type, new ListSection(items));
                    break;
                case EXPERIENCE:
                case EDUCATION:
                    List<Organization> organizations = new ArrayList<>();
                    int orgIndex = 0;
                    while (true) {
                        String name = request.getParameter(type.name() + "_name_" + orgIndex);
                        if (name == null) break;
                        name = name.trim();
                        if (name.isEmpty()) {
                            orgIndex++;
                            continue;
                        }
                        String url = request.getParameter(type.name() + "_url_" + orgIndex);
                        List<Organization.Position> positions = new ArrayList<>();
                        int posIndex = 0;
                        while (true) {
                            String title = request.getParameter(type.name() + "_title_" + orgIndex + "_" + posIndex);
                            if (title == null || title.trim().isEmpty()) break;
                            String start = request.getParameter(type.name() + "_startDate_" + orgIndex + "_" + posIndex);
                            String end = request.getParameter(type.name() + "_endDate_" + orgIndex + "_" + posIndex);
                            String desc = request.getParameter(type.name() + "_description_" + orgIndex + "_" + posIndex);
                            positions.add(new Organization.Position(
                                    LocalDate.parse(start),
                                    LocalDate.parse(end),
                                    title.trim(),
                                    desc != null ? desc.trim() : null
                            ));
                            posIndex++;
                        }
                        organizations.add(new Organization(new Link(name, url), positions));
                        orgIndex++;
                    }
                    r.addSection(type, new OrganizationSection(organizations));
                    break;
            }
        }

        if (r.getContacts().isEmpty() && r.getSections().isEmpty()) {
            response.sendRedirect("resume");
            return;
        }

        storage.update(r);
        response.sendRedirect("resume?uuid=" + r.getUuid() + "&action=edit");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");

        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }

        Resume r;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
            case "edit":
                r = storage.get(uuid);
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }

        request.setAttribute("resume", r);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }
}
