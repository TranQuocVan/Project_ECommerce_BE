package service;


import database.ProductDao;
import model.Product;
import model.ProductModel;

import java.io.InputStream;
import java.sql.SQLException;

public class ProductService {
    public static int saveProduct(String nameProduct, int quantity, InputStream file, float price, String category) {
//        ProductDao productDao = new ProductDao();
//        int idProductCategory = productDao.getCategoryIDByName(category);
//
//        Product product = new Product(nameProduct,price,quantity,idProductCategory,file);
//
//        return productDao.addNewProduct(product);
        return 0;
    }


    public ProductModel getProductById(int id) throws SQLException {
        ProductDao dao = new ProductDao();
        return dao.getProductById(id);
    }

}
