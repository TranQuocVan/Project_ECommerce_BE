package database;

import model.ShoppingCartItemOrders;

import java.sql.*;

public class ShoppingCartItemOrdersDao {

    public boolean addShoppingCartItemOrders(ShoppingCartItemOrders order, int userId) {
        // Dùng transaction để đảm bảo tính nhất quán của dữ liệu
        String insertSql = "INSERT INTO shoppingcartitemsorder (paymentId, quantity, orderId, sizeId) VALUES(?, ?, ?, ?)";

        try (Connection con = JDBCUtil.getConnection()) {
            // Bắt đầu giao dịch
            con.setAutoCommit(false);  // Tắt auto commit để bắt đầu giao dịch

            try (PreparedStatement st = con.prepareStatement(insertSql)) {
                for (Integer sizeId : order.getListSizeId()) {
                    String sql = "select quantity from shoppingcartitems where sizeId = ? and userId = ?";
                    try (PreparedStatement ps = con.prepareStatement(sql)) {
                        ps.setInt(1, sizeId);
                        ps.setInt(2, userId);
                        ResultSet rs = ps.executeQuery();
                        if (rs.next()) {
                            // Thiết lập các tham số cho mỗi dòng chèn
                            st.setInt(1, order.getPaymentId());        // paymentId
                            st.setInt(2, rs.getInt(1));   // quantity
                            st.setInt(3, order.getOrderId());       // orderId
                            st.setInt(4, sizeId);                  // sizeId
                            // Thực hiện insert
                            st.addBatch();  // Thêm câu lệnh vào batch
                        }
                    }

                }

                // Thực thi batch insert
                int[] updateCounts = st.executeBatch();

                // Kiểm tra kết quả batch insert, nếu có lỗi, throw exception
                for (int count : updateCounts) {
                    if (count == PreparedStatement.EXECUTE_FAILED) {
                        // Nếu bất kỳ câu lệnh nào thất bại, rollback toàn bộ giao dịch
                        con.rollback();
                        return false;  // Trả về false nếu có lỗi
                    }
                }

                // Nếu tất cả insert thành công, commit giao dịch
                con.commit();
                return true;

            } catch (SQLException e) {
                // Nếu có lỗi trong quá trình thêm dữ liệu, rollback giao dịch
                con.rollback();
                e.printStackTrace();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }


}
