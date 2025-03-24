package controller.user.account;

<<<<<<< HEAD
import database.LogDAO;
import database.UserDao;
=======
>>>>>>> 34d11e43d3629fd26334a49acd771d54aa2c3dcb
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.UserModel;
import service.user.account.UserService;

import java.io.IOException;
import java.sql.SQLException;
<<<<<<< HEAD
import java.util.*;
import com.google.gson.Gson;
=======
>>>>>>> 34d11e43d3629fd26334a49acd771d54aa2c3dcb


@WebServlet(name = "SignInController", value = "/SignInController")
public class SignInController extends HttpServlet {
    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Có thể redirect đến trang đăng nhập hoặc đăng ký
        response.sendRedirect("signIn.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
<<<<<<< HEAD
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
=======
        // Lấy thông tin từ request
        String gmail = request.getParameter("gmail");
        String password = request.getParameter("password");

        // Gọi UserService để xử lý
        try {
            // Kiểm tra thông tin tài khoản qua service
            UserModel userModel = userService.authenticateUser(gmail, password);

            if (!userService.isUserModelExistence(userModel)) {
                // Trường hợp thông tin không hợp lệ
                request.setAttribute("res", "This gmail or password is incorrect");
                request.getRequestDispatcher("signIn.jsp").forward(request, response);
                return;
            }

            // Tạo session và xử lý "Remember Me"
            HttpSession session = request.getSession(true);
            UserService.handleRememberMe(userModel, session, response);

            // Chuyển hướng về trang index
            response.sendRedirect(request.getContextPath() + "/IndexController");
        } catch (SQLException e) {
            // Xử lý ngoại lệ từ cơ sở dữ liệu
            throw new RuntimeException("Database error occurred: " + e.getMessage(), e);
        }
>>>>>>> 34d11e43d3629fd26334a49acd771d54aa2c3dcb
    }

}