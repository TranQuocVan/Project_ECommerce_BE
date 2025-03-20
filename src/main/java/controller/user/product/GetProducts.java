package controller.user.product;

import database.JDBCUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.Product;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

@WebServlet(name = "getProducts", value = "/getProducts")
public class GetProducts extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM Products";

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                Product product = new Product();
                product.setQuantity(rs.getInt("quantity"));
                product.setNameProduct(rs.getString("nameProduct"));
                product.setPrice(rs.getFloat("price"));
                product.setIdProductCategory(rs.getInt("idProductCategory"));

                // Convert InputStream image to Base64 String
                InputStream imageStream = rs.getBinaryStream("image");
                if (imageStream != null) {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = imageStream.read(buffer)) != -1) {
                        byteArrayOutputStream.write(buffer, 0, bytesRead);
                    }
                    String base64Image = Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
                    product.setImageBase64(base64Image);  // Add a new setter for base64 image in Product
                }

                products.add(product);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        // Convert the list of products to JSON
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");

        request.setAttribute("products", products);
        RequestDispatcher dispatcher = request.getRequestDispatcher("main.jsp");
        dispatcher.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}