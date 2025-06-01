package controller.admin.log;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "LogPageController", urlPatterns = "/LogPageController")
public class LogPageController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Forward tới trang log.jsp nằm trong /adminPages/
        request.getRequestDispatcher("/adminPages/log.jsp").forward(request, response);
    }
}
