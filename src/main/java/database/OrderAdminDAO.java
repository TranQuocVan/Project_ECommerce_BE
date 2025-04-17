package database;

import model.DeliveriesModel;
import model.OrderAdminModel;
import model.StatusAdminModel;
import model.StatusModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderAdminDAO {

    public List<OrderAdminModel> getAllOrders(int paymentId, int deliveryId, Date orderDate ) throws SQLException, SQLException {
        // Khởi tạo câu truy vấn cơ bản
        StringBuilder sql = new StringBuilder("SELECT o.orderId, p.methodPayment, o.orderDate, o.deliveryAddress, \n" +
                "       o.totalPrice, d.name AS deliveryName, \n" +
                "       scio.quantity, scio.sizeId" +
                " FROM orders o " +
                "LEFT JOIN statuses s ON o.orderId = s.orderId " +
                "LEFT JOIN payments p ON o.paymentId = p.paymentId " +
                "LEFT JOIN deliveries d ON o.deliveryId = d.deliveryId " +
                "LEFT JOIN shoppingcartitemsorder scio ON o.orderId = scio.orderId " +
                "where 1=1");

        // Danh sách tham số để truyền vào PreparedStatement
        List<Object> parameters = new ArrayList<>();

        if (paymentId != 0) {
            sql.append(" AND o.paymentId = ?");
            parameters.add(paymentId);
        }
        if (deliveryId != 0) {
            sql.append(" AND o.deliveryId = ?");
            parameters.add(deliveryId);
        }
        if (orderDate != null) {
            sql.append(" AND o.orderDate = ?");
            parameters.add(new java.sql.Date(orderDate.getTime()));
        }


        // Chuẩn bị câu lệnh truy vấn
        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql.toString())) {
            for (int i = 0; i < parameters.size(); i++) {
                st.setObject(i + 1, parameters.get(i));
            }
            ResultSet rs = st.executeQuery();
            List<OrderAdminModel> orders = new ArrayList<>();
            while (rs.next()) {
                OrderAdminModel order = new OrderAdminModel(
                        rs.getInt("orderId"), rs.getString("methodPayment"),
                        rs.getDate("orderDate"), rs.getString("deliveryAddress"),
                        rs.getFloat("totalPrice"), rs.getString("deliveryName"),
                        rs.getInt("quantity"), rs.getInt("sizeId")

                );
                orders.add(order);

            }
            return orders;

        }

    }
//    public List<StatusAdminModel> getAllStatus () {
//        List<StatusAdminModel> statusAdminModels = new ArrayList<>();
//        String sql = "select * from statusestype" ;
//        try (Connection con = JDBCUtil.getConnection();
//             PreparedStatement st = con.prepareStatement(sql)) {
//            ResultSet rs = st.executeQuery();
//            while (rs.next()) {
//                StatusAdminModel statusAdminModel = new StatusAdminModel(rs.getInt(1), rs.getString(2),rs.getString(3));
//                statusAdminModels.add(statusAdminModel);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return statusAdminModels;
//    }
//    public boolean insertStatus(int status, int orderId) throws SQLException {
//        String sql = "INSERT INTO Statuses (statusTypeId, orderId) VALUES (?, ?)";
//
//        try (Connection con = JDBCUtil.getConnection();
//             PreparedStatement st = con.prepareStatement(sql)) {
//
//            // Thiết lập các tham số cho câu lệnh SQL
//            st.setInt(1, status);
//            st.setInt(2, orderId);
//
//            // Thực thi câu lệnh và kiểm tra kết quả
//            int rowsAffected = st.executeUpdate();
//            return rowsAffected > 0; // Trả về true nếu thêm thành công
//        } catch (SQLException e) {
//            e.printStackTrace(); // In lỗi ra console
//        }
//
//        return false; // Trả về false nếu có lỗi
//    }

}



