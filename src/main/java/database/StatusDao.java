package database;

import model.StatusModel;

import java.sql.*;
import java.util.*;

public class StatusDao {
    public boolean addStatus(StatusModel status) {
        String sql = "INSERT INTO Statuses ( orderId, statusTypeId) VALUES (?, ?)";

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            // Thiết lập các tham số cho câu lệnh SQL
//            st.setString(1, status.getName());
            st.setInt(1, status.getOrderId());
//            st.setString(3, status.getDescription());
            st.setInt(2, status.getStatusTypeId());

            // Thực thi câu lệnh và kiểm tra kết quả
            int rowsAffected = st.executeUpdate();
            return rowsAffected > 0; // Trả về true nếu thêm thành công
        } catch (SQLException e) {
            e.printStackTrace(); // In lỗi ra console
        }

        return false; // Trả về false nếu có lỗi
    }


    //check
    public List<StatusModel> getStatusesByOrderId(int orderId) {
        String sql = "SELECT * FROM Statuses s LEFT JOIN statusestype st on s.statusTypeId = st.id WHERE orderId = ?";
        List<StatusModel> statuses = new ArrayList<>();

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            // Thiết lập tham số cho câu lệnh SQL
            st.setInt(1, orderId);

            try (ResultSet rs = st.executeQuery()) {
                // Duyệt qua từng bản ghi trong kết quả truy vấn
                while (rs.next()) {
                    StatusModel status = new StatusModel();
                    status.setId(rs.getInt("statusId"));
                    status.setName(rs.getString("nameType"));
                    status.setOrderId(rs.getInt("orderId"));
                    status.setDescription(rs.getString("description"));

                    // Lấy `startDate` và `endDate`
                    status.setStartDate(rs.getTimestamp("startDate")); // Sử dụng Timestamp
                    Timestamp endDate = rs.getTimestamp("endDate");
                    if (endDate != null) {
                        status.setEndDate(endDate);
                    }

                    statuses.add(status); // Thêm đối tượng StatusModel vào danh sách
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // In lỗi ra console
        }

        return statuses; // Trả về danh sách các StatusModel
    }



}
