package service;

import database.ShoppingCartItemsDao;

public class ShoppingCartService {
    public boolean addProductToShoppingCart(int orderId, int sizeId){
        ShoppingCartItemsDao shoppingCartItemsDao = new ShoppingCartItemsDao();
        return shoppingCartItemsDao.addProductToShoppingCart(orderId, sizeId);
    }

}
