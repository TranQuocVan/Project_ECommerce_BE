package controller;

import database.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.UserModel;

import java.io.IOException;

@WebServlet(name = "GmailAuthenticationController", value = "/GmailAuthenticationController")
public class GmailAuthenticationController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String authCode = request.getParameter("authCode");


        HttpSession session = request.getSession(false); // "false" để không tạo mới nếu session không tồn tại
        String authCodeOfSession = (String) session.getAttribute("authCode");
        String gmail = (String) session.getAttribute("gmail");
        String password = (String) session.getAttribute("password");

        if(authCode.equals(authCodeOfSession)){
            session.setAttribute("isLogin", true);
            UserModel userModel = new UserModel(gmail, password, "user");
            UserDao userDao = new UserDao();
            userDao.insert(userModel);

            userModel.setPassword("******");
            session.setAttribute("user", userModel);
            session.setAttribute("isLogin", true);
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
            dispatcher.forward(request, response);
        }else {
            request.setAttribute("res", "Mã xác thực bạn chưa đúng");
            request.setAttribute("password", password);
            RequestDispatcher dispatcher = request.getRequestDispatcher("gmailAuthentication.jsp");
            dispatcher.forward(request, response);

        }

    }
}