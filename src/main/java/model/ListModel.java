package model;

import java.util.ArrayList;
import java.util.List;

public class ListModel<T> {
    private List<T> shoppingCartItemsList;

    // Khởi tạo danh sách trong constructor
    public ListModel() {
        this.shoppingCartItemsList = new ArrayList<>();
    }

    public List<T> getShoppingCartItemsList() {
        return shoppingCartItemsList;
    }
}
