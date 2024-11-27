package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import service.UserService;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "SignInController", value = "/SignInController")
public class SignInController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("doGet");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String gmail = request.getParameter("gmail");
        String password = request.getParameter("password");
        String res = "";
        HttpSession session = request.getSession();
        if(gmail.isEmpty()) {
            request.setAttribute("res", " Must enter gmail");
            session.setAttribute("password", password);
            RequestDispatcher login = getServletContext().getRequestDispatcher("/signin.jsp");
            login.forward(request, response);
        }
        if(!gmail.isEmpty()) {
            try {
                res = UserService.checkValidGmailAndExists(gmail);
                session.setAttribute("gmail", gmail);

                if(res.equals("Success")) {
//                 int authCode = (int) (Math.random() * 999999);
                   int authCode = 1;
//                 Email.sendEmail(gmail,"Auth code", authCode+"");

                    session.setAttribute("authCode", ""+authCode);

                   RequestDispatcher login = getServletContext().getRequestDispatcher("/gmailAuthentication.jsp");
                   login.forward(request, response);
                   // response.sendRedirect("index.jsp");
                }
                else {
                    request.setAttribute("res",res);
                    RequestDispatcher login = getServletContext().getRequestDispatcher("/signin.jsp");
                    login.forward(request, response);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }
}