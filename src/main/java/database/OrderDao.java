package database;

import model.Order;
import model.OrderModel;
import model.StatusModel;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderDao {
    public int addOrder(Order order) {
        String orderSql = "INSERT INTO orders (paymentId, orderDate, deliveryAddress, totalPrice, userId, deliveryId, statusPayment) VALUES (?, ?, ?, ?, ?, ?, ?)";
        int orderId = -1;

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement orderSt = con.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS)) {

            // Không cần transaction trừ khi có nhiều thao tác quan trọng cần rollback cùng nhau
            orderSt.setInt(1, order.getPaymentId());
            orderSt.setTimestamp(2, order.getOrderDate());
            orderSt.setString(3, order.getDeliveryAddress());
            orderSt.setFloat(4, order.getTotalPrice());
            orderSt.setInt(5, order.getUserId());
            orderSt.setInt(6, order.getDeliveryId());
            orderSt.setInt(7, 0);

            int affectedRows = orderSt.executeUpdate();
            if (affectedRows == 0) {
                System.out.println("Creating order failed, no rows affected.");
                return -1;
            }

            try (ResultSet rs = orderSt.getGeneratedKeys()) {
                if (rs.next()) {
                    orderId = rs.getInt(1);
                    System.out.println("Generated Order ID: " + orderId);
                } else {
                    System.out.println("Creating order failed, no generated ID.");
                    return -1;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }

        // Thêm trạng thái đơn hàng sau khi có Order ID, nhưng không chặn phương thức
        int finalOrderId = orderId;
        new Thread(() -> {
            try (Connection con = JDBCUtil.getConnection()) {
                StatusModel status = new StatusModel();
                status.setName("Đặt hàng thành công");
                status.setOrderId(finalOrderId);
                status.setDescription("Chờ người bán xác nhận");

                LocalDateTime localDateTime = LocalDateTime.now(); // Lấy ngày và giờ hiện tại
                Timestamp sqlTimestamp = Timestamp.valueOf(localDateTime); // Chuyển đổi thành Timestamp (cả ngày và giờ)
                status.setTimeline(sqlTimestamp); // Lưu vào status

                StatusDao statusDao = new StatusDao();
                statusDao.addStatus(status);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }).start(); // Chạy thêm trạng thái trong thread khác để không làm chậm hàm chính

        return orderId;
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

    public List<OrderModel> getAllOrders(int userId) {
        String sql = "select * from orders where userId = ?";
        List<OrderModel> orders = new ArrayList<>();

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, userId);
            ResultSet rs = st.executeQuery();

            // Định dạng ngày
            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

            while (rs.next()) {
                // Lấy ngày từ ResultSet
                Timestamp orderDateTimestamp = rs.getTimestamp("orderDate");
                String formattedDate = orderDateTimestamp != null ?
                        dateFormatter.format(orderDateTimestamp) : "N/A"; // Nếu null, trả về "N/A"

                OrderModel orderModel = new OrderModel(
                        rs.getInt("orderId"),
                        methodPayment(rs.getInt("paymentId")),
                        formattedDate, // Ngày đã được định dạng
                        rs.getString("deliveryAddress"),
                        rs.getFloat("totalPrice"),
                        deliveryName(rs.getInt("deliveryId")),
                        deliveryFee(rs.getInt("deliveryId")),
                        nameStatusPayment(rs.getInt("statusPayment"))
                );

                orders.add(orderModel);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public String methodPayment(int paymentId) {
        String sql = "select methodPayment from payments where paymentId = ?";
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


    public String deliveryName(int deliveryId) {
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

    public String nameStatusPayment(int statusPaymentId){
        switch (statusPaymentId) {
            case 0: return "Chưa thanh toán";
            case 1: return "Đã thanh toán";
            default: return "Trạng thái không hợp lệ";
        }
    }

    public boolean updateStatusPayment(int orderId, int status) {
        String sql = "UPDATE orders SET statusPayment = ? WHERE orderId = ?";
        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, status);
            st.setInt(2, orderId);
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int convertStatusPaymentToInt(String statusPayment) {
        if ("Chưa thanh toán".equals(statusPayment)) {
            return 0;
        } else if ("Đã thanh toán".equals(statusPayment)) {
            return 1;
        } else {
            return -1; // Giá trị không hợp lệ
        }
    }

    public void updateStatusPayment(OrderModel order) {
        String sql = "UPDATE orders SET statusPayment = ? WHERE orderId = ?";

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            // Chuyển đổi từ String -> int trước khi lưu vào CSDL
            int statusPaymentInt = convertStatusPaymentToInt(order.getStatusPayment());

            if (statusPaymentInt == -1) {
                System.out.println("Lỗi: Trạng thái thanh toán không hợp lệ!");
                return; // Dừng nếu trạng thái không hợp lệ
            }

            ps.setInt(1, statusPaymentInt); // Cập nhật với kiểu int
            ps.setInt(2, order.getId()); // Điều kiện WHERE để cập nhật đúng đơn hàng

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Cập nhật trạng thái thanh toán thành công cho đơn hàng ID: " + order.getId());
            } else {
                System.out.println("Không tìm thấy đơn hàng để cập nhật.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public float deliveryFee(int deliveryId) {
        String sql = "select fee from deliveries where deliveryId = ?";
        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, deliveryId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                return rs.getFloat("fee");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public float calculateTotalPrice(List<Integer> listSizeId, int userId, int paymentId) throws SQLException {
        float totalPrice = 0;
        for (Integer sizeId : listSizeId) {
            String sql = """
        SELECT p.price, p.discount, spc.quantity
        FROM sizes s 
        LEFT JOIN shoppingcartitems spc ON spc.sizeId = s.sizeId 
        LEFT JOIN colors c ON c.colorId = s.colorId 
        LEFT JOIN products p ON c.productId = p.productId 
        WHERE spc.sizeId = ? AND spc.userId = ?
        """;

            try (Connection con = JDBCUtil.getConnection();
                 PreparedStatement st = con.prepareStatement(sql)) {
                st.setInt(1, sizeId);
                st.setInt(2, userId);
                ResultSet rs = st.executeQuery();
                if (rs.next()) {
                    float price = rs.getFloat("price");
                    int discount = rs.getInt("discount");
                    int quantity = rs.getInt("quantity");

                    // Tính giá sau khi giảm
                    float discountedPrice = price - (price * discount / 100);

                    // Tính tổng tiền
                    totalPrice += discountedPrice * quantity;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        totalPrice += deliveryFee(paymentId);
        return totalPrice;
    }

    public OrderModel getSignAndPublishKeyById(int orderId) {
        String sql = "SELECT publishKey, sign FROM orders WHERE orderId = ?";
        OrderModel orderModel = null;

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, orderId);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                orderModel = new OrderModel(
                        rs.getString("publishKey"),
                        rs.getString("sign")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderModel;
    }

    public OrderModel getOrderById(int orderId) {
        String sql = "select * from orders where orderId = ?";
        OrderModel orderModel = null;

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, orderId);
            ResultSet rs = st.executeQuery();

            // Định dạng ngày
            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

            if (rs.next()) {
                // Lấy ngày từ ResultSet
                Timestamp orderDateTimestamp = rs.getTimestamp("orderDate");
                String formattedDate = orderDateTimestamp != null ?
                        dateFormatter.format(orderDateTimestamp) : "N/A"; // Nếu null, trả về "N/A"

                // Tạo đối tượng OrderModel
                orderModel = new OrderModel(
                        rs.getInt("orderId"),
                        methodPayment(rs.getInt("paymentId")),
                        formattedDate, // Ngày đã được định dạng
                        rs.getString("deliveryAddress"),
                        rs.getFloat("totalPrice"),
                        deliveryName(rs.getInt("deliveryId")),
                        deliveryFee(rs.getInt("deliveryId")),
                        nameStatusPayment(rs.getInt("statusPayment"))
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderModel;
    }

    public List<OrderModel> findByIdOrder(int userId) {
        String sql = "select * from orders where userId = ?";
        List<OrderModel> orders = new ArrayList<>();

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, userId);
            ResultSet rs = st.executeQuery();


            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

            while (rs.next()) {

                Timestamp orderDateTimestamp = rs.getTimestamp("orderDate");
                String formattedDate = orderDateTimestamp != null ?
                        dateFormatter.format(orderDateTimestamp) : "N/A"; // Nếu null, trả về "N/A"

                OrderModel orderModel = new OrderModel(
                        rs.getInt("orderId"),
                        methodPayment(rs.getInt("paymentId")),
                        formattedDate, // Ngày đã được định dạng
                        rs.getString("deliveryAddress"),
                        rs.getFloat("totalPrice"),
                        deliveryName(rs.getInt("deliveryId")),
//                        deliveryFee(rs.getInt("deliveryId")),
                        rs.getString("nameStatus")
                );

                orders.add(orderModel);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }
// bug
    public List<OrderModel> getOrderByDate(Timestamp orderDate) {
        List<OrderModel> orders = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE orderDate BETWEEN ? AND ?";

        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            // Tính khoảng thời gian trong ngày
            LocalDateTime orderDateTime = orderDate.toLocalDateTime();
            LocalDateTime startOfDay = orderDateTime.toLocalDate().atStartOfDay();
            LocalDateTime endOfDay = orderDateTime.toLocalDate().atTime(23, 59, 59);

            Timestamp startTimestamp = Timestamp.valueOf(startOfDay);
            Timestamp endTimestamp = Timestamp.valueOf(endOfDay);

            // Gán tham số cho PreparedStatement
            ps.setTimestamp(1, startTimestamp);
            ps.setTimestamp(2, endTimestamp);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                // Lấy ngày từ ResultSet
                Timestamp orderDateTimestamp = rs.getTimestamp("orderDate");
                String formattedDate = (orderDateTimestamp != null) ?
                        orderDate.toGMTString() : "N/A"; // Nếu null, trả về "N/A"

                OrderModel orderModel = new OrderModel(
                        rs.getInt("orderId"),
                        methodPayment(rs.getInt("paymentId")),
                        formattedDate, // Ngày đã được định dạng
                        rs.getString("deliveryAddress"),
                        rs.getFloat("totalPrice"),
                        deliveryName(rs.getInt("deliveryId")),
//                        deliveryFee(rs.getInt("deliveryId")),
                        rs.getString("nameStatus")
                );

                orders.add(orderModel);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public int getUserIdByOrderId(int id) {
        String sql = "SELECT userId FROM orders WHERE orderId = ?";
        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt("userId");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Trả về -1 nếu không tìm thấy
    }

    public boolean updateOrderSignature(int orderId, String base64Signature, String base64PublicKey) throws SQLException {
        String sql = "UPDATE orders SET sign = ?, publishKey = ? WHERE orderId = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, base64Signature);
            stmt.setString(2, base64PublicKey);
            stmt.setInt(3, orderId);
            int rows = stmt.executeUpdate();
            if (rows == 0) {
                return true;
            }
            return false;
        }
    }
}


