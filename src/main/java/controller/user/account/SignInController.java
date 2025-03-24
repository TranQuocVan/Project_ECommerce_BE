package controller.user.account;

import database.LogDAO;
import database.UserDao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.UserModel;
import service.user.account.UserService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import com.google.gson.Gson;

@WebServlet(name = "SignInController", value = "/SignInController")
public class SignInController extends HttpServlet {
    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Có thể redirect đến trang đăng nhập hoặc đăng ký
        response.sendRedirect("signIn.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String gmail = request.getParameter("gmail");
        String password = request.getParameter("password");
        String ipAddress = request.getRemoteAddr();

        Map<String, String> result = new HashMap<>();

        try {
            UserModel userModel = userService.authenticateUser(gmail, password);

            if (!userService.isUserModelExistence(userModel)) {
                LogDAO.insertLog(0, "LOGIN_FAILED", "users", gmail, "INVALID_PASSWORD", ipAddress);
                result.put("status", "error");
                result.put("message", "This gmail or password is incorrect");
            } else {
                HttpSession session = request.getSession(true);
                UserService.handleRememberMe(userModel, session, response);

                UserDao userDao = new UserDao();
                UserModel userModelCheck = userDao.selectByGmail(gmail);

                LogDAO.insertLog(userModelCheck.getId(), "LOGIN_SUCCESS", "users", gmail, "SUCCESS", ipAddress);

                result.put("status", "success");
                result.put("redirect", request.getContextPath() + "/IndexController");
            }
        } catch (SQLException e) {
            result.put("status", "error");
            result.put("message", "Database error occurred");
        }

        response.getWriter().write(new Gson().toJson(result));
    }

}