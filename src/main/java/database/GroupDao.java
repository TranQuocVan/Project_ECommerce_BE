package database;

import model.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class GroupDao {

    public ProductModel getGroupByName(String name) throws SQLException {
        String query = """
                SELECT
                    gp.name AS groupName,
                    p.name AS productName,
                    p.price,
                    p.discount,
                    c.colorId,
                    c.name AS colorName,
                    c.hexCode,
                    s.size,
                    s.stock,
                    i.image
                FROM groupProducts gp
                LEFT JOIN products p ON gp.groupProductID = p.groupProductID
                LEFT JOIN colors c ON p.productId = c.productId
                LEFT JOIN images i ON c.colorId = i.colorId
                LEFT JOIN sizes s ON c.colorId = s.colorId
                WHERE gp.name = ?;
                """;

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(query)) {

            st.setString(1, name);
            try (ResultSet rs = st.executeQuery()) {
                // Map lưu trữ để tránh trùng lặp `ColorModel`
                Map<Integer, ColorModel> colorMap = new HashMap<>();
                ProductModel productModel = null;

                while (rs.next()) {
                    // Lấy thông tin sản phẩm
                    if (productModel == null) {
                        String productName = rs.getString("productName");
                        float price = rs.getFloat("price");
                        float discount = rs.getFloat("discount");
                        productModel = new ProductModel(productName, price, discount);
                    }

                    // Lấy thông tin màu sắc
                    int colorId = rs.getInt("colorId");
                    String colorName = rs.getString("colorName");
                    String hexCode = rs.getString("hexCode");

                    ColorModel colorModel = colorMap.getOrDefault(colorId, new ColorModel(colorId, colorName, hexCode));

                    // Lấy thông tin size
                    String size = rs.getString("size");
                    int stock = rs.getInt("stock");
                    if (size != null) {
                        SizeModel sizeModel = new SizeModel(size, stock);
                        if (!colorModel.getSizeModels().contains(sizeModel)) {
                            colorModel.getSizeModels().add(sizeModel);
                        }
                    }

                    // Lấy thông tin hình ảnh
                    try (InputStream image = rs.getBinaryStream("image")) {
                        if (image != null) {
                            String base64 = convertBase64(image);
                            ImageModel imageModel = new ImageModel();
                            imageModel.setImageBase64(base64);
                            if (!colorModel.getImageModels().contains(imageModel)) {
                                colorModel.getImageModels().add(imageModel);
                            }
                        }
                    }

                    // Thêm `ColorModel` vào danh sách nếu chưa tồn tại
                    if (!colorMap.containsKey(colorId)) {
                        productModel.getColorModels().add(colorModel);
                        colorMap.put(colorId, colorModel);
                    }
                }
                return productModel;
            } catch (IOException e) {
                throw new RuntimeException("Error processing image", e);
            }
        }
    }

    /**
     * Chuyển đổi InputStream thành chuỗi Base64.
     */
    private String convertBase64(InputStream imageStream) throws IOException {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = imageStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
            return Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
        }
    }


}
