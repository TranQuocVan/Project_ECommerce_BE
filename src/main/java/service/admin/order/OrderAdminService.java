package service.admin.order;

import database.OrderAdminDAO;
import model.OrderAdminModel;
import model.StatusAdminModel;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class OrderAdminService {
    public List<OrderAdminModel> getAllOrders(Integer paymentId, Integer deliveryId, Date orderDate, Integer statusTypeId) throws SQLException, SQLException {
        OrderAdminDAO dao = new OrderAdminDAO();
        return dao.getAllOrders(paymentId, deliveryId, orderDate, statusTypeId);
    }
//    public List<StatusAdminModel> getAllStatus () {
//        OrderAdminDAO dao = new OrderAdminDAO();
//        return dao.getAllStatus();
//    }
//    public boolean insertStatus(int status, int orderId) throws SQLException {
//        OrderAdminDAO dao = new OrderAdminDAO();
//        return dao.insertStatus(status,orderId);
//    }


    }
