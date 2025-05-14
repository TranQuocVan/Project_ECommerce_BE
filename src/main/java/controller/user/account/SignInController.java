package controller.user.account;

import database.LogDAO;
import database.UserDao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.UserModel;
import service.log.LogService;
import service.user.account.UserService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import com.google.gson.Gson;

@WebServlet(name = "SignInController", value = "/SignInController")
public class SignInController extends HttpServlet {
    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("FACEBOOK_APP_ID", System.getenv("FACEBOOK_APP_ID"));
        request.setAttribute("GOOGLE_CLIENT_ID", System.getenv("GOOGLE_CLIENT_ID"));
        request.getRequestDispatcher("/signIn.jsp").forward(request, response);
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
                LogService.logLogin(0,gmail,false,ipAddress);
                result.put("status", "error");
                result.put("message", "This gmail or password is incorrect");
            } else {
                HttpSession session = request.getSession(true);
                UserService.handleRememberMe(userModel, session, response);

                UserDao userDao = new UserDao();
                UserModel userModelCheck = userDao.selectByGmail(gmail);

                LogService.logLogin(userModelCheck.getId(),gmail,true,ipAddress);
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