package services;

import java.util.ArrayList;
import java.util.HashMap;

public class CategoryService {
    private static HashMap<Integer, String> categoryMap = new HashMap<>();

    static {
        categoryMap.put(1, "Gadgets");
        categoryMap.put(2, "Food");
        categoryMap.put(3, "Stationary");
        categoryMap.put(4, "Furniture");
    }

    public HashMap<Integer, String> getCategoryMap() {
        return categoryMap;
    }

    public String getCategoryNameById(int id) {
        return categoryMap.get(id);
    }

    public void addCategory(String name) {
        categoryMap.put(categoryMap.size() + 1, name);
    }


}
