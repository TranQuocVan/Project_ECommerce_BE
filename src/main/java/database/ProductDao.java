package database;

import model.Product;
import model.*;

import java.io.InputStream;
import java.sql.*;

public class ProductDao {
//    public  int getCategoryIDByName(String categoryName) {
//        String query = "SELECT idProductCategory FROM ProductCategory WHERE nameCategory = ?";
//
//
//        try (Connection con = JDBCUtil.getConnection();
//             PreparedStatement st = con.prepareStatement(query)) {
//
//            // Check if category exists
//            st.setString(1, categoryName);
//            ResultSet rs = st.executeQuery();
//
//            if (rs.next()) {
//                return rs.getInt("idProductCategory"); // Return existing category ID
//            } else {
//                // If category does not exist, insert it and retrieve the new ID
//                String insertQuery = "INSERT INTO ProductCategory (nameCategory) VALUES (?)";
//                try (PreparedStatement insertSt = con.prepareStatement(insertQuery)) {
//                    insertSt.setString(1, categoryName);
//                    insertSt.executeUpdate();
//
//                    return getCategoryIDByName(categoryName);
//                }
//            }
//
//        } catch (SQLException e) {
//            System.err.println("SQL Error: " + e.getMessage());
//        }
//        return -1;
//    }

//    public  int addNewProduct(Product product) {
//        int row = 0;
//        String sql = "INSERT INTO products (nameProduct, quantity, image, price, idProductCategory) VALUES (?, ?, ?, ?, ?)";
//
//        try (Connection con = JDBCUtil.getConnection();
//             PreparedStatement st = con.prepareStatement(sql)) {
//
//            st.setString(1, product.getNameProduct());
//            st.setInt(2, product.getQuantity());
//
//            if (product.getImage() != null) {
//                st.setBlob(3, product.getImage());
//            }
//            st.setFloat(4, product.getPrice());
//            st.setInt(5, product.getIdProductCategory());
//
//            row = st.executeUpdate();
//
//        } catch (SQLException e) {
//            System.err.println("SQL Error: " + e.getMessage());
//        }
//
//        return row;
//    }

    public void addProduct(ProductModel product) throws SQLException {
        String insertProductQuery = "INSERT INTO products (name, price, discount, groupProductId,productCategoryId) VALUES (?, ?, ?, ? ,?)";
        String insertColorQuery = "INSERT INTO colors (name, hexCode, productId) VALUES (?, ?, ?)";
        String insertSizeQuery = "INSERT INTO sizes (size, stock, colorId) VALUES (?, ?, ?)";
        String insertImageQuery = "INSERT INTO images (image, colorId) VALUES (?, ?)";

        try (Connection con = JDBCUtil.getConnection()) {
            // Start transaction
            con.setAutoCommit(false);

            try {
                // 1. Insert Product
                int productId;
                try (PreparedStatement productStmt = con.prepareStatement(insertProductQuery, Statement.RETURN_GENERATED_KEYS)) {
                    productStmt.setString(1, product.getName());
                    productStmt.setDouble(2, product.getPrice());
                    productStmt.setFloat(3, product.getDiscount());
                    productStmt.setInt(4, 1);
                    productStmt.setInt(5, 1);
                    productStmt.executeUpdate();

                    try (ResultSet rs = productStmt.getGeneratedKeys()) {
                        if (rs.next()) {
                            productId = rs.getInt(1);
                        } else {
                            throw new SQLException("Failed to insert product, no ID obtained.");
                        }
                    }
                }

                // 2. Insert Colors
                for (ColorModel color : product.getColorModels()) {
                    int colorId;
                    try (PreparedStatement colorStmt = con.prepareStatement(insertColorQuery, Statement.RETURN_GENERATED_KEYS)) {
                        colorStmt.setString(1, color.getName());
                        colorStmt.setString(2, color.getHexCode());
                        colorStmt.setInt(3, productId);
                        colorStmt.executeUpdate();

                        try (ResultSet rs = colorStmt.getGeneratedKeys()) {
                            if (rs.next()) {
                                colorId = rs.getInt(1);
                            } else {
                                throw new SQLException("Failed to insert color, no ID obtained.");
                            }
                        }
                    }

                    // 3. Insert Sizes
                    for (SizeModel size : color.getSizeModels()) {
                        try (PreparedStatement sizeStmt = con.prepareStatement(insertSizeQuery)) {
                            sizeStmt.setString(1, size.getSize());
                            sizeStmt.setInt(2, size.getStock());
                            sizeStmt.setInt(3, colorId);
                            sizeStmt.executeUpdate();
                        }
                    }

                    // 4. Insert Images
                    for (ImageModel image : color.getImageModels()) {
                        try (PreparedStatement imageStmt = con.prepareStatement(insertImageQuery)) {
                            InputStream imageStream = image.getImage();
                            if (imageStream != null) {
                                imageStmt.setBinaryStream(1, imageStream);
                                imageStmt.setInt(2, colorId);
                                imageStmt.executeUpdate();
                            }
                        }
                    }
                }

                // Commit transaction
                con.commit();
            } catch (SQLException e) {
                con.rollback();
                throw e; // Rethrow exception for caller to handle
            }
        }
    }


}
