package service;

import database.ShoppingCartItemOrdersDao;
import database.StatusDao;


import java.sql.Timestamp;
import java.util.List;

public class ShoppingCartItemOrderService {
    public boolean addShoppingCartItemOrders(List<Integer> sizes, int userId ,int orderId){
        ShoppingCartItemOrdersDao dao = new ShoppingCartItemOrdersDao();
        return dao.addShoppingCartItemOrders(sizes,userId,orderId);
    }


    }
