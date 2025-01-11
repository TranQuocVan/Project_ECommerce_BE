package service;

import database.OrderAdminDAO;
import model.OrderAdminModel;
import model.StatusAdminModel;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class OrderAdminService {
    public List<OrderAdminModel> getAllOrders(Integer paymentId, Integer deliveryId, Date orderDate, String nameStatus) throws SQLException, SQLException {
        OrderAdminDAO dao = new OrderAdminDAO();
        return dao.getAllOrders(paymentId, deliveryId, orderDate, nameStatus);
    }
    public List<StatusAdminModel> getAllStatus () {
        OrderAdminDAO dao = new OrderAdminDAO();
        return dao.getAllStatus();
    }

    }
