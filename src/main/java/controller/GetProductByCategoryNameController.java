package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.PageModel;
import model.ProductModel;
import service.ProductCategoryService;

import java.io.IOException;

@WebServlet(name = "GetProductByCategoryNameController", value = "/GetProductByCategoryNameController")
public class GetProductByCategoryNameController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String keyword = request.getParameter("keyword") != null ? request.getParameter("keyword") : "";
        int pageNo = request.getParameter("pageNo") != null ? Integer.parseInt(request.getParameter("pageNo")) : 1;




        ProductCategoryService productCategoryService = new ProductCategoryService();
        PageModel<ProductModel> page = productCategoryService.getPageNumber(keyword,pageNo,5);

        request.setAttribute("page", page);
        request.getRequestDispatcher("productCategory.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}