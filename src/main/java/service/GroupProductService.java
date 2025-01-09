package service;

import database.GroupProductDao;
import database.ProductCategoryDao;
import model.GroupProductModel;
import model.PageModel;
import model.ProductCategoryModel;
import model.ProductModel;

import java.util.List;

public class GroupProductService {
    public List<GroupProductModel> getAllProductCategory(){
        GroupProductDao dao = new GroupProductDao();
        return dao.getAllGroupProduct();
    }

    public boolean addGroupProduct(GroupProductModel groupProductModel){
        GroupProductDao dao = new GroupProductDao();
        return dao.addGroupProduct(groupProductModel);
    }

    public PageModel<ProductModel> getPageNumber(String groupName, int pageNo, int pageSize, String name){
        GroupProductDao dao = new GroupProductDao();

        int totalRecords = dao.countProductsByGroupName(groupName,name);
        List<ProductModel> products = dao.getProductByGroupName(groupName, pageNo, pageSize,name);

        PageModel<ProductModel> pageModel = new PageModel<>();
        pageModel.setPageNo(pageNo);
        pageModel.setPageSize(pageSize);
        pageModel.setTotalRecords(totalRecords);
        pageModel.setItems(products);

        return pageModel;

    }


}
