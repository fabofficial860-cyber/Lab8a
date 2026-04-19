package com.cookie;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/CookieServlet")
public class CookieServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("username");

        // Create cookies
        Cookie userCookie = new Cookie("user", name);
        userCookie.setMaxAge(30); // expires in 30 seconds

        Cookie countCookie = new Cookie("count", "1");
        countCookie.setMaxAge(30);

        response.addCookie(userCookie);
        response.addCookie(countCookie);

        response.sendRedirect("CookieServlet");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String user = null;
        int count = 0;

        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("user")) {
                    user = c.getValue();
                }
                if (c.getName().equals("count")) {
                    count = Integer.parseInt(c.getValue());
                }
            }
        }

        out.println("<html><body>");

        if (user != null) {

            count++;

            // Update visit count cookie
            Cookie countCookie = new Cookie("count", String.valueOf(count));
            countCookie.setMaxAge(30);
            response.addCookie(countCookie);

            out.println("<h2 style='color:blue'>Welcome back " + user + "!</h2>");
            out.println("<h3>You have visited this page " + count + " times</h3>");

            // Display all cookies
            out.println("<h3>Cookie List:</h3>");
            if (cookies != null) {
                for (Cookie c : cookies) {
                    out.println("Name: " + c.getName() +
                                " | Value: " + c.getValue() + "<br>");
                }
            }

            out.println("<br><a href='CookieServlet'>Refresh</a>");

        } else {

            out.println("<h2 style='color:red'>Cookie expired or not found</h2>");
            out.println("<a href='index.html'>Enter Name Again</a>");
        }

        out.println("</body></html>");
    }
}