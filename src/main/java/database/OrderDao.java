package database;

import jakarta.servlet.http.HttpSession;
import model.OrderModel;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class OrderDao {
    public boolean addOrder(OrderModel order) {

        String sql = "insert into orders (orderDate, deliveryAddress, totalPrice, userId,paymentId,deliveryId) values(?,?,?,?,?,?)";
        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            LocalDate currentDate = LocalDate.now(); // Lấy ngày hiện tại
            st.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            st.setString(2, "Tieu chuan");
            st.setFloat(3, 0);
            st.setInt(4, order.getUserId());
            st.setInt(5, 1);
            st.setInt(6, 1);
            return st.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean checkOrder(int userId) {
        String sql = "select * from orders where userId = ?";
        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, userId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public int getOrderId(int userId) {
        String sql = "select orderId from orders where userId = ?";
        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, userId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
