package database;

import model.Product;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductDao {
    public  int getCategoryIDByName(String categoryName) {
        String query = "SELECT idProductCategory FROM ProductCategory WHERE nameCategory = ?";


        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(query)) {

            // Check if category exists
            st.setString(1, categoryName);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return rs.getInt("idProductCategory"); // Return existing category ID
            } else {
                // If category does not exist, insert it and retrieve the new ID
                String insertQuery = "INSERT INTO ProductCategory (nameCategory) VALUES (?)";
                try (PreparedStatement insertSt = con.prepareStatement(insertQuery)) {
                    insertSt.setString(1, categoryName);
                    insertSt.executeUpdate();

                    return getCategoryIDByName(categoryName);
                }
            }

        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
        return -1;
    }

    public  int addNewProduct(Product product) {
        int row = 0;
        String sql = "INSERT INTO products (nameProduct, quantity, image, price, idProductCategory) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, product.getNameProduct());
            st.setInt(2, product.getQuantity());

            if (product.getImage() != null) {
                st.setBlob(3, product.getImage());
            }
            st.setFloat(4, product.getPrice());
            st.setInt(5, product.getIdProductCategory());

            row = st.executeUpdate();

        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }

        return row;
    }


}
