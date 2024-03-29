package servlets;

import model.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;

@WebServlet (name = "login", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("JSP/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String userType = req.getParameter("user_type");

        if (userType.equals("student")) {
            LinkedList<String[]> userData = QuerySelector.logInStudent(username, password);

            // If user exist, change UserBean and redirect
            if (!userData.isEmpty()) {
                int id = Integer.parseInt(userData.get(0)[0]);
                String fname = userData.get(0)[1];

                req.getSession().setMaxInactiveInterval(300);
                UserBean userBean = new UserBean(StateType.confirmed, UserType.student, PrivilegeType.user, fname, id);
                req.getSession().setAttribute("userBean", userBean);
                req.getRequestDispatcher("/index.jsp").forward(req, resp);

            // If user doesn't exist, show error message
            } else {
                req.setAttribute("errorMessage", "User not found, try again!");
                req.getRequestDispatcher("JSP/login.jsp").forward(req, resp);
            }
        } else {
            LinkedList<String[]> userData = QuerySelector.logInTeacher(username, password);

            // If user exist, change UserBean and redirect
            if (!userData.isEmpty()) {
                int id = Integer.parseInt(userData.get(0)[0]);
                String fname = userData.get(0)[1];
                PrivilegeType privilegeType = PrivilegeType.valueOf(userData.get(0)[2]);

                req.getSession().setMaxInactiveInterval(300);
                UserBean userBean = new UserBean(StateType.confirmed, UserType.teacher, privilegeType, fname, id);
                req.getSession().setAttribute("userBean", userBean);
                req.getRequestDispatcher("/index.jsp").forward(req, resp);

            // If user doesn't exist, show error message
            } else {
                req.getSession().setAttribute("errorMessage", "No user found");
                req.getRequestDispatcher("JSP/login.jsp").forward(req, resp);
            }
        }
    }
}