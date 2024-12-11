package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import service.GroupProductService;
import service.ProductCategoryService;

import java.io.IOException;

@WebServlet(name = "AddProductController", value = "/AddProductController")
public class AddProductController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductCategoryService pcs = new ProductCategoryService();
        request.setAttribute("ListProductCategory", pcs.getAllProductCategory());
        GroupProductService gps = new GroupProductService();
        request.setAttribute("ListGroupProduct", gps.getAllProductCategory());

        RequestDispatcher dispatcher = request.getRequestDispatcher("/adminPages/addProduct.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}