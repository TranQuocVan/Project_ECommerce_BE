package database;

import model.ListModel;
import model.ShoppingCartItems;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCartItemsDao {
    public boolean addProductToShoppingCart(int orderId, int sizeId) {
        String insertSql = "INSERT INTO ShoppingCartItems (quantity, sizeId, orderId) VALUES(?, ?, ?)";
        String updateSql = "UPDATE ShoppingCartItems SET quantity = quantity + 1 WHERE sizeId = ?";

        try (Connection con = JDBCUtil.getConnection()) {
            if (checkProductDetailId(sizeId)) {
                // Nếu sản phẩm đã tồn tại, cập nhật số lượng
                try (PreparedStatement updateSt = con.prepareStatement(updateSql)) {
                    updateSt.setInt(1, sizeId);
                    int rowsAffected = updateSt.executeUpdate();
                    return rowsAffected > 0;
                }
            } else {
                // Nếu sản phẩm chưa tồn tại, thêm mới vào giỏ hàng
                try (PreparedStatement insertSt = con.prepareStatement(insertSql)) {
                    insertSt.setInt(1, 1); // Số lượng mặc định là 1
                    insertSt.setInt(2, sizeId);
                    insertSt.setInt(3, orderId);
                    int rowsAffected = insertSt.executeUpdate();
                    return rowsAffected > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    private boolean checkProductDetailId(int productDetail) {
        String sql = "SELECT 1 FROM ShoppingCartItems WHERE sizeId = ? LIMIT 1";
        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, productDetail);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }
    public List<ShoppingCartItems> getAllShoppingCartItems(int orderId) {
        ListModel<ShoppingCartItems> shoppingCartItemsList = new ListModel<>();
        List<ShoppingCartItems> list = shoppingCartItemsList.getShoppingCartItemsList();
        String sql = "SELECT * FROM ShoppingCartItems WHERE orderId = ?";
        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, orderId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                ShoppingCartItems spci = new ShoppingCartItems(rs.getInt("sizeId"), rs.getInt("quantity"), rs.getInt("orderId"));
                list.add(spci);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

}
