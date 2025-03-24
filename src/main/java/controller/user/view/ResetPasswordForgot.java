package controller.user.view; import jakarta.servlet.*; 
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException; 
@WebServlet(name = "ResetPasswordForgot", value = "/ResetPasswordForgot") 
public class ResetPasswordForgot extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = request.getParameter("token");

        if (token == null || token.trim().isEmpty()) {
            response.sendRedirect("error.jsp?message=Token không hợp lệ hoặc đã hết hạn!");
            return;
        }

//        PasswordResetTokensDao tokenDao = new PasswordResetTokensDao();
//        boolean isValid = tokenDao.isTokenValid(token);

//        if (!isValid) {
//            response.sendRedirect("error.jsp?message=Token không hợp lệ hoặc đã hết hạn!");
//            return;
//        }

        // Redirect to reset password page with token
        response.sendRedirect("resetPasswordForgot.jsp?token=" + token);
    }
@Override protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { } 
}