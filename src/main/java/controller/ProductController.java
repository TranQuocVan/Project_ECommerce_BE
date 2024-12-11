package controller;

import database.ProductDao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.*;

import java.io.InputStream;
import java.util.*;
import java.io.IOException;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 50, // 50MB
        maxRequestSize = 1024 * 1024 * 100 // 100MB
)
@WebServlet(name = "ProductController", value = "/ProductController")
public class ProductController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String productName = request.getParameter("productName");
            double price = Double.parseDouble(request.getParameter("price"));
            float discount = Float.parseFloat(request.getParameter("discount"));
            int productCategory = Integer.parseInt(request.getParameter("productCategory"));
            int groupProduct =Integer.parseInt(request.getParameter("groupProduct"));

            List<ColorModel> colorModels = new ArrayList<>();

            int colorIndex = 0;
            while (request.getParameter("colorName" + colorIndex) != null) {
                String colorName = request.getParameter("colorName" + colorIndex);
                String hexCode = request.getParameter("hexCode" + colorIndex);

                // Process sizes and stocks
                List<SizeModel> sizeModels = new ArrayList<>();
                String[] sizes = request.getParameterValues("size" + colorIndex + "[]");
                String[] stocks = request.getParameterValues("stock" + colorIndex + "[]");

                if (sizes != null && stocks != null) {
                    for (int i = 0; i < sizes.length; i++) {
                        sizeModels.add(new SizeModel(sizes[i], Integer.parseInt(stocks[i])));
                    }
                }

                // Process images
                List<ImageModel> imageModels = new ArrayList<>();
                for (Part part : request.getParts()) {
                    String fieldName = part.getName();
                    if (fieldName.equals("image" + colorIndex + "[]") && part.getSize() > 0) {
                        InputStream imageStream = part.getInputStream();
                        imageModels.add(new ImageModel(imageStream));
                    }
                }

                // Add color to list
                ColorModel colorModel = new ColorModel(colorName, hexCode);
                colorModel.setSizeModels(sizeModels);
                colorModel.setImageModels(imageModels);
                colorModels.add(colorModel);

                colorIndex++;
            }

            // Create and save the product
            ProductModel product = new ProductModel(productName, price, discount,productCategory,groupProduct);
            product.setColorModels(colorModels);

            ProductDao productDao = new ProductDao();
            productDao.addProduct(product);

            response.getWriter().println("Product added successfully!");

        } catch (Exception e) {
            response.getWriter().println("Failed to add product: " + e.getMessage());
        }
    }
}
