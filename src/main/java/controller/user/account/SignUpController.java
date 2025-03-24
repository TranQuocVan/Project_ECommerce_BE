package controller.user.account;

import database.UserDao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import service.user.account.UserService;
import service.util.GmailServices;
import com.google.gson.Gson;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "SignUpController", value = "/SignUpController")
public class SignUpController extends HttpServlet {
    private final GmailServices gmailServices = new GmailServices();
    private final UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String gmail = request.getParameter("gmail");
        String password = request.getParameter("password");

        Map<String, String> result = new HashMap<>();

        try {
            // Kiểm tra nếu Gmail đã tồn tại
            if (gmailServices.isGmailEmpty(gmail)) {
                result.put("status", "error");
                result.put("message", "Gmail is empty");
                response.getWriter().write(new Gson().toJson(result));
                return;
            }

            // Kiểm tra Gmail hợp lệ
            String isValidGmailAndExists = userService.checkValidGmailAndExists(gmail);
            if (!isValidGmailAndExists.equals("Success")) {
                result.put("status", "error");
                result.put("message", isValidGmailAndExists);
                response.getWriter().write(new Gson().toJson(result));
                return;
            }

            // Gửi mã xác thực qua Gmail
            int authCode = gmailServices.sendGmail(gmail);

            // Lưu mã xác thực vào session
            HttpSession session = request.getSession(true);
            session.setAttribute("authCode", String.valueOf(authCode));
            session.setAttribute("gmail", gmail); // Lưu Gmail vào session
            session.setAttribute("password", password);

            result.put("status", "success");
            result.put("redirect", request.getContextPath() + "/gmailAuthentication.jsp");

        } catch (SQLException e) {
            result.put("status", "error");
            result.put("message", "Database error occurred");
        }

        response.getWriter().write(new Gson().toJson(result));
    }
}
