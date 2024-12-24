package database;

import model.ListModel;
import model.ShoppingCartItems;
import model.ShoppingCartItemsModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCartItemsDao {

    public boolean addProductToShoppingCart( int quantity, int sizeId, int userId) {
        String insertSql = "INSERT INTO ShoppingCartItems (quantity, sizeId, userId) VALUES(?, ?, ?)";
        String updateSql = "UPDATE ShoppingCartItems SET quantity = quantity + 1 WHERE sizeId = ? and userId = ?";

        try (Connection con = JDBCUtil.getConnection()) {
            if (checkProductDetailId(sizeId,userId)) {
                // Nếu sản phẩm đã tồn tại, cập nhật số lượng
                try (PreparedStatement updateSt = con.prepareStatement(updateSql)) {
                    updateSt.setInt(1, sizeId);
                    updateSt.setInt(2, userId);
                    int rowsAffected = updateSt.executeUpdate();
                    return rowsAffected > 0;
                }
            } else {
                // Nếu sản phẩm chưa tồn tại, thêm mới vào giỏ hàng
                try (PreparedStatement insertSt = con.prepareStatement(insertSql)) {
                    insertSt.setInt(1, quantity);
                    insertSt.setInt(2, sizeId);
                    insertSt.setInt(3, userId);
                    int rowsAffected = insertSt.executeUpdate();
                    return rowsAffected > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    private boolean checkProductDetailId(int sizeId, int userId ) {
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
    public List<ShoppingCartItemsModel> getAllShoppingCartItems(int userId) {
        List<ShoppingCartItemsModel> lists = new ArrayList<ShoppingCartItemsModel>();
        String sql = """
        SELECT p.name,p.price, c.name, s.size, s.stock, spc.quantity
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
                       new ShoppingCartItemsModel(rs.getString("name"),rs.getFloat("price"),
                               rs.getString("name"),
                               rs.getString("size"), rs.getInt("stock"), rs.getInt("quantity")) ;
                lists.add(shoppingCartItemsModel);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lists;
    }
    public float totalPrice (int userId){
        float totalPrice = 0;
        List<ShoppingCartItemsModel> lists = getAllShoppingCartItems(userId);
        for (ShoppingCartItemsModel shoppingCartItemsModel : lists) {
            totalPrice += shoppingCartItemsModel.getPrice() * shoppingCartItemsModel.getQuantity();
        }
        return totalPrice;
    }
}
