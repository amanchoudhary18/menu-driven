package services;

import serialize.CategorySerializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger; // Import the AtomicInteger class

public class CategoryService {
    private static HashMap<Integer, String> categoryMap = new HashMap<>();

    // Initialize Atomic Integer with the last existing ID
    private static AtomicInteger nextId = new AtomicInteger(4);

    static {
        categoryMap = CategorySerializer.deserializeCategories();
    }

    public HashMap<Integer, String> getCategoryMap() {
        return categoryMap;
    }

    public void addCategory(String name) {
        int newId = nextId.incrementAndGet(); // Get the next available ID
        categoryMap.put(newId, name);
    }
}
