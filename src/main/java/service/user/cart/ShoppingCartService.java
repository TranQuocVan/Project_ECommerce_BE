package service.user.cart;

import database.ShoppingCartItemsDao;
import model.DeliveriesModel;
import model.PaymentModel;
import model.ShoppingCartItemsModel;

import java.util.List;

public class ShoppingCartService {
    public boolean addProductToShoppingCart( int quantity, int sizeId, int userId){
        ShoppingCartItemsDao shoppingCartItemsDao = new ShoppingCartItemsDao();
        return shoppingCartItemsDao.addProductToShoppingCart(quantity, sizeId, userId);
    }
    public List<ShoppingCartItemsModel> getAllShoppingCartItems(int userId){
        ShoppingCartItemsDao shoppingCartItemsDao = new ShoppingCartItemsDao();
        return shoppingCartItemsDao.getAllShoppingCartItems(userId);
    }

    public boolean deleteProductToShoppingCart( int sizeId, int userId) {
        ShoppingCartItemsDao shoppingCartItemsDao = new ShoppingCartItemsDao();
        return shoppingCartItemsDao.deleteProductToShoppingCart(sizeId,userId);
    }
    public boolean cleanShoppingCartItems(List<Integer> listSizeId) {
        ShoppingCartItemsDao shoppingCartItemsDao = new ShoppingCartItemsDao();
        return shoppingCartItemsDao.cleanShoppingCartItems(listSizeId);
    }
    public boolean updateStockProduct(int userId) {
        ShoppingCartItemsDao shoppingCartItemsDao = new ShoppingCartItemsDao();
        return shoppingCartItemsDao.updateStockProduct(userId);
    }
    public List<PaymentModel> getAllPayments() {
        ShoppingCartItemsDao shoppingCartItemsDao = new ShoppingCartItemsDao();
        return shoppingCartItemsDao.getAllPayments();
    }
    public List<DeliveriesModel> getAllDeliveries() {
        ShoppingCartItemsDao shoppingCartItemsDao = new ShoppingCartItemsDao();
        return shoppingCartItemsDao.getAllDeliveries();
    }
    public boolean updateProductToShoppingCart(int quantity, int sizeId, int userId, boolean isDecreaseQuantity) {

        ShoppingCartItemsDao shoppingCartItemsDao = new ShoppingCartItemsDao();
        if(isDecreaseQuantity){
            return shoppingCartItemsDao.updateProductToShoppingCart(quantity, sizeId, userId);
        }
        if(shoppingCartItemsDao.checkStockProduct(sizeId, quantity)){
            return shoppingCartItemsDao.updateProductToShoppingCart(quantity, sizeId, userId);
        }


        return false;
    }
}






