package database;

import model.Order;
import model.OrderModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDao {
    public int addOrder(Order order) {
        String sql = "INSERT INTO orders (paymentId, orderDate, deliveryAddress, totalPrice, userId, deliveryId) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            // Gán giá trị cho các tham số trong câu lệnh SQL
            st.setInt(1, order.getPaymentId());
            st.setTimestamp(2, order.getOrderDate());
            st.setString(3, order.getDeliveryAddress());
            st.setFloat(4, order.getTotalPrice());
            st.setInt(5, order.getUserId());
            st.setInt(6, order.getDeliveryId());

            // Thực thi câu lệnh INSERT
            int affectedRows = st.executeUpdate();
            if (affectedRows > 0) {
                // Lấy giá trị OrderId (khóa chính tự tăng)
                try (ResultSet rs = st.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1); // Trả về giá trị của cột tự động tăng (OrderId)
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Trả về -1 nếu có lỗi hoặc không lấy được OrderId
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
    public List<OrderModel> getAllOrders(int userId) {
        String sql = "select * from orders where userId = ?";
        List<OrderModel> orders = new ArrayList<>();

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, userId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                OrderModel orderModel = new OrderModel(
                        rs.getInt("orderId"),
                        methodPayment(rs.getInt("paymentId")),
                        rs.getTimestamp("orderDate"),
                        rs.getString("deliveryAddress"),
                        rs.getFloat("totalPrice"),
                        deliveryName(rs.getInt("deliveryId"))
                        );

                orders.add(orderModel);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public String methodPayment (int paymentId){
        String sql = "select methodPayment from Payments where paymentId = ?";
        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, paymentId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                return rs.getString("methodPayment");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Trống";
    }
    public String deliveryName (int deliveryId){
        String sql = "select name from deliveries where deliveryId = ?";
        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, deliveryId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                return rs.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Trống";
    }
}
