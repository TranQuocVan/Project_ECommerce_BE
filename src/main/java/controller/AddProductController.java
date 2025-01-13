package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.GroupProductModel;
import model.ProductCategoryModel;
import service.GroupProductService;
import service.ProductCategoryService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "AddProductController", value = "/AddProductController")
public class AddProductController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductCategoryService pcs = new ProductCategoryService();
        GroupProductService gps = new GroupProductService();

        List<ProductCategoryModel> listCate = pcs.getAllProductCategory();
        request.setAttribute("ListProductCategory", listCate);

        List<GroupProductModel> listGr = gps.getAllProductCategory();
        request.setAttribute("ListGroupProduct", listGr);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/adminPages/addProducts.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductCategoryService pcs = new ProductCategoryService();
        request.setAttribute("ListProductCategory", pcs.getAllProductCategory());
        GroupProductService gps = new GroupProductService();
        request.setAttribute("ListGroupProduct", gps.getAllProductCategory());

        RequestDispatcher dispatcher = request.getRequestDispatcher("/adminPages/addProducts.jsp");
        dispatcher.forward(request, response);

    }
}








