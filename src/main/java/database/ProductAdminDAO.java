package database;

import model.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

public class ProductAdminDAO {


    public List<ProductModel>getAllProduct(int groupProductID, int productCategoryId, String size, String nameColor ) throws SQLException {
        StringBuilder query = new StringBuilder("""
        WITH RankedProducts AS (
                                  SELECT
                                        p.productId,
                                        p.name AS productName,
                                        p.price,
                                        p.discount,
                                        p.productCategoryId,
                                        pc.name AS productCategoryName,
                                        p.groupProductId,
                                        g.name AS productGroupName,
                                        c.colorId,
                                        c.name AS colorName,
                                        c.hexCode,
                                        s.sizeId,
                                        s.size,
                                        s.stock,
                                        i.imageId,
                                        i.image,
                                        ROW_NUMBER() OVER (PARTITION BY p.productId) AS rn
                                    FROM sizes s
                                    INNER JOIN colors c ON s.colorId = c.colorId
                                    INNER JOIN products p ON c.productId = p.productId
                                    LEFT JOIN images i ON c.colorId = i.colorId
                                    LEFT JOIN groupproducts g ON p.groupProductID = g.groupProductID
                                    LEFT JOIN productcategory pc ON p.productCategoryId = pc.productCategoryId
                                )
                                SELECT *
                                FROM RankedProducts r
                                WHERE rn = 1
                
    """);
        List<Object> parameters = new ArrayList<>();
        List<ProductModel> products = new ArrayList<>();

        if (groupProductID!=0)  {
            query.append(" AND r.groupProductID = ?");
            parameters.add(groupProductID);
        }
        if (productCategoryId != 0) {
            query.append(" AND r.productCategoryId = ?");
            parameters.add(productCategoryId);
        }
        if (!"".equalsIgnoreCase(size)) {
            query.append(" AND r.size = ?");
            parameters.add(size);
        }
        if (!"".equalsIgnoreCase(nameColor)) {
            query.append(" AND r.colorName = ?");
            parameters.add(nameColor);
        }


        try (Connection con = JDBCUtil.getConnection()) {

            try (PreparedStatement stmt = con.prepareStatement(query.toString())) {
                for (int i = 0; i < parameters.size(); i++) {
                    stmt.setObject(i + 1, parameters.get(i));
                }

                try (ResultSet rs = stmt.executeQuery()) {

                    while (rs.next()) {
                        // Khởi tạo Product nếu chưa được khởi tạo
                           ProductModel product = new ProductModel();
                            product.setId(rs.getInt("productId"));
                            product.setName(rs.getString("productName"));
                            product.setPrice(rs.getFloat("price"));
                            product.setDiscount(rs.getFloat("discount"));
                            product.setProductCategoryId(rs.getInt("productCategoryId"));
                            product.setGroupProductId(rs.getInt("groupProductId"));
                            product.setProductCategoryName(rs.getString("productCategoryName"));
                            product.setProductGroupName(rs.getString("productGroupName"));
                            product.setNameSize(rs.getString("size"));
                            product.setColorName(rs.getString("colorName"));
                            product.setStock(rs.getInt("stock"));
                            product.setHexCode(rs.getString("hexCode"));


                        // Xử lý Color (chỉ có một color tương ứng với sizeId)
                        ColorModel color = new ColorModel();
                        color.setId(rs.getInt("colorId"));
                        color.setName(rs.getString("colorName"));
                        color.setHexCode(rs.getString("hexCode"));

                        // Xử lý Size (size cụ thể của sizeId)
                        SizeModel size2 = new SizeModel();
                        size2.setId(rs.getInt("sizeId"));
                        size2.setSize(rs.getString("size"));
                        size2.setStock(rs.getInt("stock"));
                        color.getSizeModels().add(size2);

                        // Xử lý Image (nếu tồn tại)
                        int imageId = rs.getInt("imageId");
                        if (!rs.wasNull()) {
                            ImageModel image = new ImageModel();
                            image.setId(imageId);

                            // Chuyển đổi image sang Base64
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

                        // Gán Color cho Product
                        product.setColorModels(Collections.singletonList(color));

                        products.add(product);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            return products;
        }
    }
    public List<GroupProductModel> getAllGroupProduct (){
        List<GroupProductModel> groupProductModels = new ArrayList<>();
        String sql = "select * from groupproducts" ;
        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                GroupProductModel groupProductModel = new GroupProductModel();
                groupProductModel.setId(rs.getInt("groupProductId"));
                groupProductModel.setName(rs.getString("name"));

                groupProductModels.add(groupProductModel);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groupProductModels;
    }


    public List<ProductCategoryModel> getAllCatelogyProduct (){
        List<ProductCategoryModel> groupProductModels = new ArrayList<>();
        String sql = "select * from productcategory" ;
        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                ProductCategoryModel  productCategoryModel = new ProductCategoryModel();
                productCategoryModel.setId(rs.getInt("productCategoryId"));
                productCategoryModel.setName(rs.getString("name"));

                groupProductModels.add(productCategoryModel);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groupProductModels;
    }
    public List<SizeModel> getAllSizeProduct (){
        List<SizeModel>  sizeModels = new ArrayList<>();
        String sql = """
    WITH RankedSize AS ( 
    Select *,
             ROW_NUMBER() OVER (PARTITION BY size) AS rn
    FROM sizes
 )
    SELECT sizeId,size FROM RankedSize WHERE rn = 1
""" ;
        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                SizeModel sizeModel = new SizeModel();
                sizeModel.setId(rs.getInt("sizeId"));
                sizeModel.setSize(rs.getString("size"));

                sizeModels.add(sizeModel);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sizeModels;
    }

    public List<ColorModel> getAllColorProduct (){
        List<ColorModel> colorModels = new ArrayList<>();
        String sql = """
        WITH RankedColors AS (
        SELECT *,
               ROW_NUMBER() OVER (PARTITION BY name) AS rn
        FROM colors 
    )
    SELECT colorId, name, hexCode FROM RankedColors WHERE rn = 1
""";
        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                ColorModel sizeModel = new ColorModel();
                sizeModel.setId(rs.getInt("colorId"));
                sizeModel.setName(rs.getString("name"));
                sizeModel.setHexCode(rs.getString("hexCode"));

                colorModels.add(sizeModel);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return colorModels;
    }

    public boolean updateProduct(ProductModel product) throws SQLException {
        String sqlUpdateNameProduct = "UPDATE products SET name = ? WHERE productId = ?";
        String sqlUpdatePriceProduct = "UPDATE products SET price = ? WHERE productId = ?";
        String sqlUpdateDiscountProduct = "UPDATE products SET discount = ? WHERE productId = ?";
        String sqlUpdateStockProduct = "UPDATE sizes s " +
                "JOIN colors c ON s.colorId = c.colorId " +
                "JOIN products p ON p.productId = c.productId " +
                "SET s.stock = ? " +
                "WHERE p.productId = ?";
        String sqlUpdateSizeProduct = "UPDATE sizes s " +
                "JOIN colors c ON s.colorId = c.colorId " +
                "JOIN products p ON p.productId = c.productId " +
                "SET s.size = ? " +
                "WHERE p.productId = ?";

        String sqlUpdateImage = "UPDATE images SET image = ? WHERE colorId = ?"; // Câu lệnh cập nhật ảnh
        String sqlUpdateColorNameAndHexCode = "UPDATE colors SET hexCode = ?, name = ? WHERE colorId = ?";


        try (Connection con = JDBCUtil.getConnection()) {
            // Bắt đầu transaction
            con.setAutoCommit(false);

            try {
                // Cập nhật tên sản phẩm
                if (product.getName() != null && !"".equalsIgnoreCase(product.getName())) {
                    try (PreparedStatement stUpdateProduct = con.prepareStatement(sqlUpdateNameProduct)) {
                        stUpdateProduct.setString(1, product.getName());
                        stUpdateProduct.setInt(2, product.getId());
                        stUpdateProduct.executeUpdate();
                    }
                }

                // Cập nhật giá sản phẩm
                if (product.getPrice() != 0) {
                    try (PreparedStatement stUpdatePriceProduct = con.prepareStatement(sqlUpdatePriceProduct)) {
                        stUpdatePriceProduct.setFloat(1, product.getPrice());
                        stUpdatePriceProduct.setInt(2, product.getId());
                        stUpdatePriceProduct.executeUpdate();
                    }
                }

                // Cập nhật giảm giá
                if (product.getDiscount() != 0) {
                    try (PreparedStatement stUpdateDiscountProduct = con.prepareStatement(sqlUpdateDiscountProduct)) {
                        stUpdateDiscountProduct.setFloat(1, product.getDiscount());
                        stUpdateDiscountProduct.setInt(2, product.getId());
                        stUpdateDiscountProduct.executeUpdate();
                    }
                }

                // Cập nhật số lượng sản phẩm
                if (product.getStock() != 0) {
                    try (PreparedStatement stUpdateStockProduct = con.prepareStatement(sqlUpdateStockProduct)) {
                        stUpdateStockProduct.setInt(1, product.getStock());
                        stUpdateStockProduct.setInt(2, product.getId());
                        stUpdateStockProduct.executeUpdate();
                    }
                }

                // Cập nhật kích thước sản phẩm
                if (product.getNameSize() != null && !"".equalsIgnoreCase(product.getNameSize())) {
                    try (PreparedStatement stUpdateSizeProduct = con.prepareStatement(sqlUpdateSizeProduct)) {
                        stUpdateSizeProduct.setString(1, product.getNameSize());
                        stUpdateSizeProduct.setInt(2, product.getId());
                        stUpdateSizeProduct.executeUpdate();
                    }
                }

                if ( !"".equalsIgnoreCase(product.getColorName()) && !"".equalsIgnoreCase(product.getHexCode())) {
                    try (PreparedStatement stUpdateColorNameAndHexCode = con.prepareStatement(sqlUpdateColorNameAndHexCode)) {
                        stUpdateColorNameAndHexCode.setString(1, product.getHexCode());
                        stUpdateColorNameAndHexCode.setString(2, product.getColorName());
                        stUpdateColorNameAndHexCode.setInt(3, product.getColorId());

                        stUpdateColorNameAndHexCode.executeUpdate();
                    }
                }
                for (ColorModel color : product.getColorModels()) {
                    for (ImageModel image : color.getImageModels()) {
                        try (PreparedStatement imageStmt = con.prepareStatement(sqlUpdateImage)) {
                            InputStream imageStream = image.getImage();
                            if (imageStream != null) {
                                imageStmt.setBinaryStream(1, imageStream);
                                imageStmt.setInt(2, color.getId());
                                imageStmt.executeUpdate();
                            }
                        }
                    }
                }


                // Commit transaction nếu tất cả các câu lệnh thành công
                con.commit();
                return true;
            } catch (SQLException e) {
                // Rollback nếu có lỗi xảy ra
                con.rollback();
                throw new SQLException("Lỗi khi cập nhật sản phẩm: " + e.getMessage(), e);
            } finally {
                // Khôi phục trạng thái tự động commit
                con.setAutoCommit(true);
            }
        }
    }

    public boolean deleteProduct(ProductModel product) throws SQLException {
        String sqlDeleteImages = "DELETE FROM images WHERE colorId = ?;";
        String sqlDeleteShoppingCartItemsOrder = "DELETE FROM shoppingcartitemsorder WHERE sizeId IN (SELECT sizeId FROM sizes WHERE colorId = ?);";
        String sqlDeleteShoppingCartItems = "DELETE FROM shoppingcartitems WHERE sizeId IN (SELECT sizeId FROM sizes WHERE colorId = ?);";
        String sqlDeleteSize = "DELETE FROM sizes WHERE colorId = ?;";
        String sqlDeleteColor = "DELETE FROM colors WHERE productId = ?;";
        String sqlDeleteProduct = "DELETE FROM products WHERE productId = ?;";

        try (Connection con = JDBCUtil.getConnection()) {



            // Xóa các bản ghi trong bảng shoppingcartitemsorder
            try (PreparedStatement stDeleteShoppingCartItemsOrder = con.prepareStatement(sqlDeleteShoppingCartItemsOrder)) {
                stDeleteShoppingCartItemsOrder.setInt(1, product.getColorId());
                stDeleteShoppingCartItemsOrder.executeUpdate();
            }

            // Xóa các bản ghi trong bảng shoppingcartitems
            try (PreparedStatement stDeleteShoppingCartItems = con.prepareStatement(sqlDeleteShoppingCartItems)) {
                stDeleteShoppingCartItems.setInt(1, product.getColorId());
                stDeleteShoppingCartItems.executeUpdate();
            }

            // Xóa các bản ghi trong bảng sizes
            try (PreparedStatement stDeleteSize = con.prepareStatement(sqlDeleteSize)) {
                stDeleteSize.setInt(1, product.getColorId());
                stDeleteSize.executeUpdate();
            }
            // Xóa các bản ghi trong bảng images
            try (PreparedStatement stDeleteImages = con.prepareStatement(sqlDeleteImages)) {
                stDeleteImages.setInt(1, product.getColorId());
                stDeleteImages.executeUpdate();
            }

            // Xóa các bản ghi trong bảng colors
            try (PreparedStatement stDeleteColor = con.prepareStatement(sqlDeleteColor)) {
                stDeleteColor.setInt(1, product.getId());
                stDeleteColor.executeUpdate();
            }

            // Xóa các bản ghi trong bảng products
            try (PreparedStatement stDeleteProduct = con.prepareStatement(sqlDeleteProduct)) {
                stDeleteProduct.setInt(1, product.getId());
                stDeleteProduct.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Log the error
            return false;
        }

        return true;
    }

}
