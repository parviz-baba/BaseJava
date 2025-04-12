package com.basejava.web;

import com.basejava.config.Config;
import com.basejava.model.Resume;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ResumeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<link rel='stylesheet' href='css/style.css'>");
        out.println("<title>Resume List</title>");
        out.println("</head>");
        out.println("<body>");

        out.println("<header>Resume Table</header>");
        out.println("<h2 style='text-align:center;'>Resume List</h2>");
        out.println("<table>");
        out.println("<tr><th>UUID</th><th>Full Name</th></tr>");

        for (Resume resume : Config.getInstance().getStorage().getAllSorted()) {
            out.println("<tr>");
            out.println("<td>" + resume.getUuid() + "</td>");
            out.println("<td>" + resume.getFullName() + "</td>");
            out.println("</tr>");
        }

        out.println("</table>");
        out.println("<footer style='text-align:center;'>End of Resume List</footer>");
        out.println("</body>");
        out.println("</html>");
    }
}