package database;

import model.Product;
import model.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;

public class ProductDao {


    public void addProduct(ProductModel product) throws SQLException {
        String insertProductQuery = "INSERT INTO products (name, price, discount, productCategoryId,groupProductId) VALUES (?, ?, ?, ? ,?)";
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
                    productStmt.setInt(4, product.getProductCategoryId());
                    productStmt.setInt(5, product.getGroupProductId());
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



    public ProductModel getProductById(int productId) throws SQLException {
        String productQuery = "SELECT * FROM products WHERE productId = ?";
        String colorQuery = "SELECT * FROM colors WHERE productId = ?";
        String sizeQuery = "SELECT * FROM sizes WHERE colorId = ?";
        String imageQuery = "SELECT * FROM images WHERE colorId = ?";

        try (Connection con = JDBCUtil.getConnection()) {
            ProductModel product = null;

            // Fetch Product Details
            try (PreparedStatement productStmt = con.prepareStatement(productQuery)) {
                productStmt.setInt(1, productId);
                try (ResultSet rs = productStmt.executeQuery()) {
                    if (rs.next()) {
                        product = new ProductModel();
                        product.setId(rs.getInt("productId"));
                        product.setName(rs.getString("name"));
                        product.setPrice(rs.getDouble("price"));
                        product.setDiscount(rs.getFloat("discount"));
                        product.setProductCategoryId(rs.getInt("productCategoryId"));
                        product.setGroupProductId(rs.getInt("groupProductId"));
                    } else {
                        throw new SQLException("Product not found with ID: " + productId);
                    }
                }
            }

            if (product != null) {
                // Fetch Colors
                List<ColorModel> colors = new ArrayList<>();
                try (PreparedStatement colorStmt = con.prepareStatement(colorQuery)) {
                    colorStmt.setInt(1, productId);
                    try (ResultSet rs = colorStmt.executeQuery()) {
                        while (rs.next()) {
                            ColorModel color = new ColorModel();
                            color.setId(rs.getInt("colorId"));
                            color.setName(rs.getString("name"));
                            color.setHexCode(rs.getString("hexCode"));

                            // Fetch Sizes for this Color
                            List<SizeModel> sizes = new ArrayList<>();
                            try (PreparedStatement sizeStmt = con.prepareStatement(sizeQuery)) {
                                sizeStmt.setInt(1, color.getId());
                                try (ResultSet sizeRs = sizeStmt.executeQuery()) {
                                    while (sizeRs.next()) {
                                        SizeModel size = new SizeModel();
                                        size.setId(sizeRs.getInt("sizeId"));
                                        size.setSize(sizeRs.getString("size"));
                                        size.setStock(sizeRs.getInt("stock"));
                                        sizes.add(size);
                                    }
                                }
                            }
                            color.setSizeModels(sizes);

                            // Fetch Images for this Color
                            List<ImageModel> images = new ArrayList<>();
                            try (PreparedStatement imageStmt = con.prepareStatement(imageQuery)) {
                                imageStmt.setInt(1, color.getId());
                                try (ResultSet imageRs = imageStmt.executeQuery()) {
                                    while (imageRs.next()) {
                                        ImageModel image = new ImageModel();
                                        image.setId(imageRs.getInt("imageId"));

                                        // Get the binary stream
                                        InputStream imageStream = imageRs.getBinaryStream("image");

                                        // Convert the binary stream to Base64
                                        if (imageStream != null) {
                                            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                                            byte[] buffer = new byte[1024];
                                            int bytesRead;
                                            while ((bytesRead = imageStream.read(buffer)) != -1) {
                                                outputStream.write(buffer, 0, bytesRead);
                                            }
                                            byte[] imageBytes = outputStream.toByteArray();
                                            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                                            image.setImageBase64(base64Image); // Set the Base64 string
                                        }

                                        images.add(image);
                                    }
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                            color.setImageModels(images);

                            colors.add(color);
                        }
                    }
                }
                product.setColorModels(colors);
            }
            return product;
        }
    }

    public ProductModel getProductById(int productId, String colorName, String sizeName) throws SQLException {
        String query = """
        SELECT 
            p.productId, p.name AS productName, p.price, p.discount, p.productCategoryId, p.groupProductId,
            c.colorId, c.name AS colorName, c.hexCode,
            s.sizeId, s.size, s.stock,
            i.imageId, i.image
        FROM products p
        LEFT JOIN colors c ON p.productId = c.productId
        LEFT JOIN sizes s ON c.colorId = s.colorId
        LEFT JOIN images i ON c.colorId = i.colorId
        WHERE p.productId = ?
        """ +
                (colorName != null ? " AND c.name LIKE ? " : "") +
                (sizeName != null ? " AND s.size LIKE ? " : "");

        try (Connection con = JDBCUtil.getConnection()) {
            ProductModel product = null;
            try (PreparedStatement stmt = con.prepareStatement(query)) {
                int paramIndex = 1;
                stmt.setInt(paramIndex++, productId);
                if (colorName != null) {
                    stmt.setString(paramIndex++, "%" + colorName + "%");
                }
                if (sizeName != null) {
                    stmt.setString(paramIndex++, "%" + sizeName + "%");
                }

                try (ResultSet rs = stmt.executeQuery()) {
                    Map<Integer, ColorModel> colorMap = new HashMap<>();

                    while (rs.next()) {
                        // Initialize Product
                        if (product == null) {
                            product = new ProductModel();
                            product.setId(rs.getInt("productId"));
                            product.setName(rs.getString("productName"));
                            product.setPrice(rs.getDouble("price"));
                            product.setDiscount(rs.getFloat("discount"));
                            product.setProductCategoryId(rs.getInt("productCategoryId"));
                            product.setGroupProductId(rs.getInt("groupProductId"));
                        }

                        // Process Color
                        int colorId = rs.getInt("colorId");
                        if (!rs.wasNull()) {
                            ColorModel color = colorMap.getOrDefault(colorId, new ColorModel());
                            color.setId(colorId);
                            color.setName(rs.getString("colorName"));
                            color.setHexCode(rs.getString("hexCode"));

                            // Process Size
                            int sizeId = rs.getInt("sizeId");
                            if (!rs.wasNull()) {
                                SizeModel size = new SizeModel();
                                size.setId(sizeId);
                                size.setSize(rs.getString("size"));
                                size.setStock(rs.getInt("stock"));
                                color.getSizeModels().add(size);
                            }

                            // Process Image
                            int imageId = rs.getInt("imageId");
                            if (!rs.wasNull()) {
                                ImageModel image = new ImageModel();
                                image.setId(imageId);

                                // Convert image to Base64
                                InputStream imageStream = rs.getBinaryStream("image");
                                if (imageStream != null) {
                                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                                    byte[] buffer = new byte[1024];
                                    int bytesRead;
                                    while ((bytesRead = imageStream.read(buffer)) != -1) {
                                        outputStream.write(buffer, 0, bytesRead);
                                    }
                                    byte[] imageBytes = outputStream.toByteArray();
                                    String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                                    image.setImageBase64(base64Image);
                                }

                                color.getImageModels().add(image);
                            }

                            colorMap.put(colorId, color);
                        }
                    }

                    // Set Colors in Product
                    if (product != null) {
                        product.setColorModels(new ArrayList<>(colorMap.values()));
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            return product;
        }
    }

}
