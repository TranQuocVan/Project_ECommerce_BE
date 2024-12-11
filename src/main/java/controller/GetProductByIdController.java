package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.Product;
import model.ProductModel;
import service.ProductService;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "GetProductByIdController", value = "/GetProductByIdController")
public class GetProductByIdController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //int id = Integer.parseInt(request.getParameter("id"));
        ProductService service = new ProductService();
        try {
            ProductModel product = service.getProductById(10);
            request.setAttribute("product", product);
            request.getRequestDispatcher("productDetails.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}