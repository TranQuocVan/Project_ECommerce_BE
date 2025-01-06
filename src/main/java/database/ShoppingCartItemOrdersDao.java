package database;

import model.ShoppingCartItemOrders;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ShoppingCartItemOrdersDao {

    public boolean addShoppingCartItemOrders (ShoppingCartItemOrders order) {
        String insertSql = "INSERT INTO ShoppingCartItemsOrders VALUES(?, ?, ?, ?)";
        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(insertSql)) {
             st.setInt(1, order.paymentId);
             st.setInt(2, order.quantity);
             st.setInt(3, order.orderId);
             st.setInt(4, order.sizeId);
             ResultSet rs = st.executeQuery();
             if (rs.next()) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
