package controller.admin.product;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.*;
import service.admin.product.ProductAdminService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ProductAdminController", value = "/ProductAdminController")
public class ProductAdminController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductAdminService productAdminService = new ProductAdminService();


        List<GroupProductModel> groupProductModels = productAdminService.getAllGroupProduct();

        List<ProductCategoryModel>  productCategoryModels = productAdminService.getAllCatelogyProduct();

        List<SizeModel> sizeModels = productAdminService.getAllSizeProduct();

        List<ColorModel> colorModels = productAdminService.getAllColorProduct();

        request.setAttribute("groupProductModels", groupProductModels);
        request.setAttribute("productCategoryModels", productCategoryModels);
        request.setAttribute("sizeModels", sizeModels);
        request.setAttribute("colorModels", colorModels);

        request.getRequestDispatcher("adminPages/managerProductWithFilter.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductAdminService productAdminService = new ProductAdminService();

        int groupId = Integer.parseInt(request.getParameter("groupId"));
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));
        String sizeId = request.getParameter("nameSize");
        String colorId = request.getParameter("nameColor");

        List<ProductModel> productModels = null ;
        try {
            productModels = productAdminService.getAllProduct(groupId,categoryId,sizeId,colorId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        List<GroupProductModel> groupProductModels = productAdminService.getAllGroupProduct();

        List<ProductCategoryModel>  productCategoryModels = productAdminService.getAllCatelogyProduct();

        List<SizeModel> sizeModels = productAdminService.getAllSizeProduct();

        List<ColorModel> colorModels = productAdminService.getAllColorProduct();

        request.setAttribute("groupProductModels", groupProductModels);
        request.setAttribute("productCategoryModels", productCategoryModels);
        request.setAttribute("sizeModels", sizeModels);
        request.setAttribute("colorModels", colorModels);
        request.setAttribute("productModels", productModels);



        request.getRequestDispatcher("adminPages/managerProductWithFilter.jsp").forward(request, response);

    }
}
