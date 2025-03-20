package controller.admin.product;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.ColorModel;
import model.ImageModel;
import model.ProductModel;
import service.admin.product.ProductAdminService;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 50, // 50MB
        maxRequestSize = 1024 * 1024 * 100 // 100MB
)

@WebServlet(name = "AdminUpdateProductController", value = "/AdminUpdateProductController")
public class AdminUpdateProductController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductModel product = new ProductModel();
        product.setName(request.getParameter("nameProduct"));
        int productId = Integer.parseInt(request.getParameter("productId"));
        float priceProduct = Float.parseFloat(request.getParameter("priceProduct"));
        float discountProduct = Float.parseFloat(request.getParameter("discountProduct"));
        int stockProduct = Integer.parseInt(request.getParameter("stockProduct"));
        String sizeProduct =request.getParameter("sizeProduct");
        int colorId = Integer.parseInt(request.getParameter("colorId"));
        String colorName = request.getParameter("colorName");
        String hexCode = request.getParameter("hexCode");

        List<ImageModel> imageModels = new ArrayList<>();
        for (Part part : request.getParts()) {
            if (part.getSize() > 0) {
                InputStream imageStream = part.getInputStream();
                imageModels.add(new ImageModel(imageStream));
            }
        }

        ColorModel colorModel = new ColorModel();
        colorModel.setImageModels(imageModels);
        colorModel.setId(colorId);

        List<ColorModel> colorModelList = new ArrayList<>();
        colorModelList.add(colorModel);



        product.setId(productId);
        product.setPrice(priceProduct);
        product.setDiscount(discountProduct);
        product.setStock(stockProduct);
        product.setNameSize(sizeProduct);
        product.setColorModels(colorModelList);
        product.setColorName(colorName);
        product.setHexCode(hexCode);
        product.setColorId(colorId);


        ProductAdminService productAdminService = new ProductAdminService();
        try {
            productAdminService.updateProduct(product);


            request.getRequestDispatcher("adminPages/managerProductWithFilter.jsp").forward(request, response);


        } catch (SQLException e) {
            request.getRequestDispatcher("adminPages/managerProductWithFilter.jsp").forward(request, response);
        }


    }
}
