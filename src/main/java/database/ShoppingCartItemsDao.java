package database;

import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCartItemsDao {

    public boolean addProductToShoppingCart(int quantity, int sizeId, int userId) {
        String insertSql = "INSERT INTO ShoppingCartItems (quantity, sizeId, userId) VALUES(?, ?, ?)";
        String updateSql = "UPDATE ShoppingCartItems SET quantity = quantity + ? WHERE sizeId = ? and userId = ?";

        try (Connection con = JDBCUtil.getConnection()) {
            if (checkProductDetailId(sizeId, userId)) {
                // Kiểm tra xem có đủ số lượng sản phẩm trong kho không trước khi cập nhật
                if (checkStockProduct(sizeId, userId, 1)) { // Kiểm tra nếu thêm 1 sản phẩm có vượt quá số lượng kho không
                    // Nếu sản phẩm đã tồn tại, cập nhật số lượng
                    try (PreparedStatement updateSt = con.prepareStatement(updateSql)) {
                        updateSt.setInt(1, quantity); // Số lượng tăng thêm
                        updateSt.setInt(2, sizeId);
                        updateSt.setInt(3, userId);
                        int rowsAffected = updateSt.executeUpdate();
                        return rowsAffected > 0;
                    }
                }
            } else {
                // Nếu sản phẩm chưa tồn tại, thêm mới vào giỏ hàng
                if (checkStockProduct(sizeId, userId, quantity)) {
                    try (PreparedStatement insertSt = con.prepareStatement(insertSql)) {
                        insertSt.setInt(1, quantity);
                        insertSt.setInt(2, sizeId);
                        insertSt.setInt(3, userId);
                        int rowsAffected = insertSt.executeUpdate();
                        return rowsAffected > 0;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateProductToShoppingCart(int quantity, int sizeId, int userId) {
        String updateSql = "UPDATE ShoppingCartItems SET quantity =  ? WHERE sizeId = ? and userId = ?";

        try (Connection con = JDBCUtil.getConnection()) {
                if (checkStockUpdateProduct(sizeId, userId, quantity)) {
                    try (PreparedStatement updateSt = con.prepareStatement(updateSql)) {
                        updateSt.setInt(1, quantity);
                        updateSt.setInt(2, sizeId);
                        updateSt.setInt(3, userId);
                        int rowsAffected = updateSt.executeUpdate();
                        return rowsAffected > 0;
                    }
                }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    private boolean checkStockUpdateProduct(int sizeId, int userId, int quantity) {
        String sql = """
    SELECT s.stock, IFNULL(SUM(spc.quantity), 0) AS cartQuantity
    FROM Sizes s
    LEFT JOIN ShoppingCartItems spc ON spc.sizeId = s.sizeId AND spc.userId = ?
    WHERE s.sizeId = ?
    GROUP BY s.sizeId
    """;
        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, userId);
            st.setInt(2, sizeId);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                int stock = rs.getInt("stock");
                // Kiểm tra xem tổng số lượng sản phẩm trong giỏ hàng (cộng với số lượng sắp thêm) có vượt quá số lượng kho hay không
                return (quantity) <= stock;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    private boolean checkProductDetailId(int sizeId, int userId) {
        String sql = "SELECT 1 FROM ShoppingCartItems WHERE sizeId = ? and userId = ? LIMIT 1";
        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, sizeId);
            st.setInt(2, userId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean checkStockProduct(int sizeId, int userId, int quantityToAdd) {
        String sql = """
            SELECT s.stock, IFNULL(SUM(spc.quantity), 0) AS cartQuantity
            FROM Sizes s
            LEFT JOIN ShoppingCartItems spc ON spc.sizeId = s.sizeId AND spc.userId = ?
            WHERE s.sizeId = ?
            GROUP BY s.sizeId
        """;
        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, userId);
            st.setInt(2, sizeId);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                int stock = rs.getInt("stock");
                int cartQuantity = rs.getInt("cartQuantity");
                // Kiểm tra xem tổng số lượng sản phẩm trong giỏ hàng (cộng với số lượng sắp thêm) có vượt quá số lượng kho hay không
                return (cartQuantity + quantityToAdd) <= stock;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkStockProduct(int sizeId, int quantityToAdd) {
        String sql = """
            SELECT stock
            FROM Sizes
            WHERE sizeId = ?
            """;
        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, sizeId);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                int stock = rs.getInt("stock");
                // Kiểm tra xem số lượng sản phẩm sắp thêm có vượt quá số lượng kho hay không
                return (quantityToAdd <= stock);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<ShoppingCartItemsModel> getAllShoppingCartItems(int userId) {
        List<ShoppingCartItemsModel> lists = new ArrayList<ShoppingCartItemsModel>();
        String sql = """
        SELECT s.sizeId, p.name,p.price, c.name, s.size, s.stock, spc.quantity
        FROM Sizes s 
        LEFT JOIN  ShoppingCartItems spc ON spc.sizeId = s.sizeId 
        LEFT JOIN  Colors c ON c.colorId = s.colorId 
        LEFT JOIN  Products p ON c.productId = p.productId 
        WHERE  spc.userId = ?
    """;

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, userId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
               ShoppingCartItemsModel shoppingCartItemsModel =
                       new ShoppingCartItemsModel( rs.getInt("sizeId"), rs.getString("name"),rs.getFloat("price"),
                               rs.getString("name"),
                               rs.getString("size"), rs.getInt("stock"), rs.getInt("quantity")) ;
                lists.add(shoppingCartItemsModel);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lists;
    }
    public List<PaymentModel> getAllPayments() {
        List<PaymentModel> paymentModelList = new ArrayList<>();
        String sql = "select * from payments" ;
        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
             ResultSet rs = st.executeQuery();
            while (rs.next()) {
                PaymentModel paymentModel = new PaymentModel(rs.getInt(1), rs.getString(2));
                paymentModelList.add(paymentModel);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return paymentModelList;

    }

    public List<DeliveriesModel> getAllDeliveries() {
        List<DeliveriesModel> paymentModelList = new ArrayList<>();
        String sql = "select * from deliveries" ;
        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                DeliveriesModel deliveriesModel = new DeliveriesModel(rs.getInt(1), rs.getInt(2),rs.getString(3));
                paymentModelList.add(deliveriesModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return paymentModelList;

    }

        public boolean deleteProductToShoppingCart( int sizeId, int userId) {

         String sql = "delete from shoppingCartItems where sizeId = ? and userId = ?";

         try (Connection con = JDBCUtil.getConnection()) {

                 try (PreparedStatement ps = con.prepareStatement(sql)) {
                     ps.setInt(1, sizeId);
                     ps.setInt(2, userId);
                     return ps.executeUpdate() > 0;
                 }


         } catch (SQLException e) {
             e.printStackTrace();
         }
         return false;
     }
    public boolean updateStockProduct(int userId) {
        String sql = """
        SELECT spc.quantity, s.sizeId
        FROM shoppingCartItems spc
        JOIN sizes s ON spc.sizeId = s.sizeId 
        WHERE spc.userId = ?
    """;

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            con.setAutoCommit(false); // Bắt đầu transaction
            st.setInt(1, userId);
            ResultSet rs = st.executeQuery();

            int totalCount = 0; // Tổng số hàng cần cập nhật
            int updatedCount = 0; // Số hàng đã cập nhật thành công

            while (rs.next()) {
                totalCount++;
                String sql2 = "UPDATE sizes SET stock = stock - ? WHERE sizeId = ?";
                try (PreparedStatement ps2 = con.prepareStatement(sql2)) {
                    ps2.setInt(1, rs.getInt("quantity"));
                    ps2.setInt(2, rs.getInt("sizeId"));

                    int result = ps2.executeUpdate();
                    if (result > 0) {
                        updatedCount++;
                    }
                }
            }

            // Kiểm tra nếu tất cả hàng đều được cập nhật thành công
            if (totalCount == updatedCount) {
                con.commit(); // Hoàn thành transaction
                return true;
            } else {
                con.rollback(); // Hủy transaction nếu không cập nhật đủ
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    public boolean cleanShoppingCartItems(List<Integer> listSizeId) {
        String sql = "DELETE FROM shoppingCartItems WHERE sizeId = ?";
        try (Connection con = JDBCUtil.getConnection()) {
            // Tắt auto-commit để bắt đầu transaction
            con.setAutoCommit(false);
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                for (Integer sizeId : listSizeId) {
                    ps.setInt(1, sizeId);
                    int rowsAffected = ps.executeUpdate();
                    if (rowsAffected == 0) {
                        // Nếu không xóa được sizeId nào, rollback và trả về false
                        con.rollback();
                        return false;
                    }
                }
                // Nếu tất cả sizeId đều xóa thành công, commit transaction
                con.commit();
                return true;
            } catch (SQLException e) {
                // Rollback nếu có lỗi
                con.rollback();
                e.printStackTrace();
                return false;
            } finally {
                // Bật lại auto-commit
                con.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


//    public float totalPrice (int sizeId){
//            String sql = """
//        SELECT p.price, spc.quantity
//        FROM Sizes s
//        LEFT JOIN  ShoppingCartItems spc ON spc.sizeId = s.sizeId
//        LEFT JOIN  Colors c ON c.colorId = s.colorId
//        LEFT JOIN  Products p ON c.productId = p.productId
//        WHERE  spc.sizeId = ?
//    """;
//            try (Connection con = JDBCUtil.getConnection()) {
//                try (PreparedStatement ps = con.prepareStatement(sql)) {
//                    ps.setInt(1, sizeId);
//                    ResultSet rs = ps.executeQuery();
//                    if (rs.next()) {
//                        return rs.getFloat("price") * rs.getInt("quantity");
//                    }
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//            return 0;
//    }
}
