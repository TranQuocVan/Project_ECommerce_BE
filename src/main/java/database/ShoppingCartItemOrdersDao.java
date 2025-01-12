package database;


import model.ShoppingCartItemOrderModel;


import java.sql.*;
import java.util.*;

public class ShoppingCartItemOrdersDao {

    public boolean addShoppingCartItemOrders(List<Integer> sizes, int userId, int orderId) {
        String selectSql = "SELECT quantity FROM shoppingcartitems WHERE sizeId = ? AND userId = ?";
        String insertSql = "INSERT INTO shoppingcartitemsorder (quantity, orderId, sizeId) VALUES (?, ?, ?)";

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement selectStmt = con.prepareStatement(selectSql);
             PreparedStatement insertStmt = con.prepareStatement(insertSql)) {

            for (Integer sizeId : sizes) {
                // Lấy quantity từ bảng shoppingcartitems
                selectStmt.setInt(1, sizeId);
                selectStmt.setInt(2, userId);

                try (ResultSet rs = selectStmt.executeQuery()) {
                    if (rs.next()) {
                        int quantity = rs.getInt("quantity");

                        // Thêm vào bảng shoppingcartitemsorder
                        insertStmt.setInt(1, quantity);
                        insertStmt.setInt(2, orderId);
                        insertStmt.setInt(3, sizeId);

                        insertStmt.executeUpdate();
                    }
                }
            }

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    public List<ShoppingCartItemOrderModel> getShoppingCartItemsByOrderId(int orderId) {
        String query = "SELECT quantity, orderId, sizeId FROM shoppingcartitemsorder WHERE orderId = ?";
        List<ShoppingCartItemOrderModel> items = new ArrayList<>();

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setInt(1, orderId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    // Tạo một đối tượng ShoppingCartItemOrder
                    ShoppingCartItemOrderModel item = new ShoppingCartItemOrderModel();
                    item.setQuantity(rs.getInt("quantity"));
                    item.setOrderId(rs.getInt("orderId"));
                    item.setSizeId(rs.getInt("sizeId"));

                    // Thêm vào danh sách
                    items.add(item);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }

}
