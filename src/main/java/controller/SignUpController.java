package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import service.UserService;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "SignUpController", value = "/SignUpController")
public class SignUpController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("doGet");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy thông tin từ request
        String gmail = request.getParameter("gmail");
        String password = request.getParameter("password");
        String res = "";

        // Tạo session
        HttpSession session = request.getSession();

        // Kiểm tra nếu không nhập Gmail
        if (gmail.isEmpty()) {
            request.setAttribute("res", "Must enter gmail");
            session.setAttribute("password", password); // Lưu mật khẩu vào session (nếu cần)

            // Chuyển hướng về trang đăng nhập
            RequestDispatcher login = getServletContext().getRequestDispatcher("/signIn.jsp");
            login.forward(request, response);
            return; // Kết thúc xử lý tại đây
        }

        // Nếu Gmail không rỗng, xử lý tiếp
        try {
            // Kiểm tra Gmail hợp lệ và tồn tại
            res = UserService.checkValidGmailAndExists(gmail);

            // Lưu Gmail vào session
            session.setAttribute("gmail", gmail);

            if (res.equals("Success")) {
                // Tạo mã xác thực (ở đây đặt tạm là 1)
                int authCode = 1;

                // Gửi mã xác thực qua email (bỏ qua phần gửi thực tế để test)
                // Email.sendEmail(gmail, "Auth code", authCode + "");

                // Lưu mã xác thực vào session
                session.setAttribute("authCode", String.valueOf(authCode));
                session.setAttribute("password", password);

                // Chuyển hướng sang trang xác thực Gmail
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/gmailAuthentication.jsp");
                dispatcher.forward(request, response);
            } else {
                // Nếu Gmail không hợp lệ hoặc không tồn tại
                request.setAttribute("res", res);

                // Quay lại trang đăng nhập với thông báo lỗi
                RequestDispatcher login = getServletContext().getRequestDispatcher("/signUp.jsp");
                login.forward(request, response);
            }
        } catch (SQLException e) {
            // Xử lý ngoại lệ SQL
            throw new RuntimeException("Database error occurred: " + e.getMessage(), e);
        }
    }

}