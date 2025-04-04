package service.admin.product;

import database.ProductAdminDAO;
import model.*;

import java.sql.SQLException;
import java.util.List;

public class ProductAdminService {
    public List<ProductModel> getAllProduct(int groupProductID, int productCategoryId, String size, String nameColor ) throws SQLException {
        ProductAdminDAO dao = new ProductAdminDAO();
        return dao.getAllProduct(groupProductID, productCategoryId, size, nameColor);
    }
    public List<ProductModel> getAllProduct() throws SQLException {
        ProductAdminDAO dao = new ProductAdminDAO();
        return dao.getAllProduct();
    }
    public List<GroupProductModel> getAllGroupProduct (){
        ProductAdminDAO dao = new ProductAdminDAO();
        return dao.getAllGroupProduct();
    }
    public List<ProductCategoryModel> getAllCatelogyProduct (){
        ProductAdminDAO dao = new ProductAdminDAO();
        return dao.getAllCatelogyProduct();
    }
    public List<SizeModel> getAllSizeProduct (){
        ProductAdminDAO dao = new ProductAdminDAO();
        return dao.getAllSizeProduct();
    }
    public List<ColorModel> getAllColorProduct (){
        ProductAdminDAO dao = new ProductAdminDAO();
        return dao.getAllColorProduct();
    }
    public boolean updateProduct(ProductModel product) throws SQLException {
        ProductAdminDAO dao = new ProductAdminDAO();
        return dao.updateProduct(product);
    }
    public boolean deleteProduct(ProductModel product) throws SQLException {
        ProductAdminDAO dao = new ProductAdminDAO();
        return dao.deleteProduct(product);
    }



}
