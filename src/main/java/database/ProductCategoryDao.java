package database;

import model.ProductCategoryModel;
import model.ProductModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductCategoryDao {

    public List<ProductCategoryModel> getAllProductCategory() {
        String sql = "SELECT * FROM ProductCategory";
        List<ProductCategoryModel> list = new ArrayList<>();

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql);
             ResultSet rs = st.executeQuery()){

                while (rs.next()) {
                    // Tạo đối tượng ProductCategoryModel từ kết quả truy vấn
                    ProductCategoryModel productCategoryModel = new ProductCategoryModel(rs.getString("name"));
                    productCategoryModel.setId(rs.getInt("productCategoryId")); // Gán ID từ cột productCategoryId
                    list.add(productCategoryModel); // Thêm vào danh sách
                }

        } catch (SQLException e) {
            e.printStackTrace(); // Log lỗi nếu xảy ra
        }

        return list;

    }

    public boolean addProductCategory(ProductCategoryModel productCategoryModel) {
        String sql = "INSERT INTO ProductCategory (name, description) VALUES (?, ?)";
        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Set parameters for the query
            st.setString(1, productCategoryModel.getName());
            st.setString(2,"This is description" );

            // Execute the update
            int rowsAffected = st.executeUpdate();

            // Check if the row was inserted successfully
            if (rowsAffected > 0) {
               return true;
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log the exception
        }


        return false;
    }



    public List<ProductModel> getProductByCategoryName(String categoryName) {
        List<ProductModel> productList = new ArrayList<>();
        String getProductCategoryQuery = "SELECT * FROM ProductCategory WHERE name LIKE ?";
        String getProductsByCategoryIdQuery = "SELECT * FROM Products WHERE productCategoryId = ?";

        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement categoryStatement = connection.prepareStatement(getProductCategoryQuery)) {

            // Set parameter for category query
            categoryStatement.setString(1, "%" + categoryName + "%");
            try (ResultSet categoryResultSet = categoryStatement.executeQuery()) {
                while (categoryResultSet.next()) {
                    int categoryId = categoryResultSet.getInt("productCategoryId");

                    // Fetch products for the category
                    try (PreparedStatement productStatement = connection.prepareStatement(getProductsByCategoryIdQuery)) {
                        productStatement.setInt(1, categoryId);
                        try (ResultSet productResultSet = productStatement.executeQuery()) {
                            while (productResultSet.next()) {
                                ProductModel product = new ProductModel();
                                product.setId(productResultSet.getInt("productId"));

                                // Assuming ProductDao.getProductById populates full details of the product
                                ProductDao productDao = new ProductDao();
                                ProductModel detailedProduct = productDao.getProductById(product.getId());

                                if (detailedProduct != null) {
                                    productList.add(detailedProduct);
                                }
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log the exception (consider using a logger)
        }

        return productList;
    }

    public List<ProductModel> getProductByCategoryName(String categoryName, int pageNumber, int pageSize) {
        List<ProductModel> productList = new ArrayList<>();
        String getProductsByCategoryQuery = """
        SELECT p.* 
        FROM Products p 
        JOIN ProductCategory pc ON p.productCategoryId = pc.productCategoryId 
        WHERE pc.name LIKE ? 
        LIMIT ? OFFSET ?
    """;

        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(getProductsByCategoryQuery)) {

            // Set parameters for the query
            statement.setString(1, "%" + categoryName + "%");
            statement.setInt(2, pageSize); // Limit to the specified number of products
            statement.setInt(3, (pageNumber - 1) * pageSize); // Calculate offset

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



    public int countProductsByCategoryName(String categoryName) {
        String countProductsQuery =
                "SELECT COUNT(*) AS totalProducts " +
                        "FROM Products p " +
                        "JOIN ProductCategory pc ON p.productCategoryId = pc.productCategoryId " +
                        "WHERE pc.name LIKE ?";
        int totalProducts = 0;

        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(countProductsQuery)) {

            // Set the parameter for category name with wildcard for partial matching
            statement.setString(1, "%" + categoryName + "%");

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












}
