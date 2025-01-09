package database;

import model.GroupProductModel;
import model.ProductCategoryModel;
import model.ProductModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class GroupProductDao {
    public List<GroupProductModel> getAllGroupProduct() {
        List<GroupProductModel> list = new ArrayList<>();

        String sql = "SELECT * FROM GroupProducts";
        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                // Tạo đối tượng GroupProductModel từ kết quả truy vấn
                GroupProductModel groupProductModel = new GroupProductModel(rs.getString("name"));
                groupProductModel.setId(rs.getInt("groupProductId")); // Sửa nếu cột chính không phải "productCategoryId"
                list.add(groupProductModel); // Thêm vào danh sách
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    public boolean addGroupProduct(GroupProductModel groupProductModel) {
        String sql = "INSERT INTO GroupProducts (name, image) VALUES (?, ?)";

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, groupProductModel.getName());
            st.setBinaryStream(2, groupProductModel.getImage());

            // Kiểm tra nếu số dòng bị ảnh hưởng là 1
            return st.executeUpdate() == 1;

        } catch (SQLException e) {
            System.err.println("Error while adding group product: " + e.getMessage());
            return false;
        }
    }

    public List<ProductModel> getProductByGroupName(String groupName, int pageNumber, int pageSize, String name) {
        List<ProductModel> productList = new ArrayList<>();
        String getProductsByCategoryQuery = """
        SELECT p.* 
        FROM Products p 
        JOIN GroupProducts gp ON p.groupProductId = gp.groupProductId 
        WHERE gp.name LIKE ? AND p.name LIKE ? 
        LIMIT ? OFFSET ?
    """;

        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(getProductsByCategoryQuery)) {

            // Set parameters for the query
            statement.setString(1, "%" + groupName + "%"); // Filter by group name
            statement.setString(2, "%" + name + "%"); // Filter by product name
            statement.setInt(3, pageSize); // Limit to the specified number of products
            statement.setInt(4, (pageNumber - 1) * pageSize); // Calculate offset

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    ProductModel product = new ProductModel();
                    product.setId(resultSet.getInt("productId"));

                    // Assuming ProductDao.getProductById populates full details of the product
                    ProductDao productDao = new ProductDao();
                    ProductModel detailedProduct = productDao.getProductById(product.getId());

                    if (detailedProduct != null) {
                        productList.add(detailedProduct);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log the exception (consider using a logger)
        }

        return productList;
    }



    public int countProductsByGroupName(String groupName, String name) {
        String countProductsQuery = """
        SELECT COUNT(*) AS totalProducts 
        FROM Products p 
        JOIN GroupProducts gp ON p.groupProductId = gp.groupProductId 
        WHERE gp.name LIKE ? AND p.name LIKE ?
    """;
        int totalProducts = 0;

        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(countProductsQuery)) {

            // Set parameters for group name and product name
            statement.setString(1, "%" + groupName + "%"); // Filter by group name
            statement.setString(2, "%" + name + "%"); // Filter by product name

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    totalProducts = resultSet.getInt("totalProducts");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log the exception (use a logger in production)
        }

        return totalProducts;
    }


    public List<GroupProductModel> getAllGroupProducts() {
        String sql = "SELECT * FROM GroupProducts";
        List<GroupProductModel> groupProducts = new ArrayList<>();

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                GroupProductModel groupProduct = new GroupProductModel();
                groupProduct.setId(rs.getInt("groupProductId"));
                groupProduct.setName(rs.getString("name"));

                // Chuyển đổi ảnh sang Base64
                byte[] imageBytes = rs.getBytes("image");
                if (imageBytes != null) {
                    groupProduct.setImageBase64(Base64.getEncoder().encodeToString(imageBytes));
                }

                groupProducts.add(groupProduct);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log lỗi nếu có
        }

        return groupProducts;
    }




}
