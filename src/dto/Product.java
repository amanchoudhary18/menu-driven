package dto;

public class Product {
    private int id;
    private String name;
    private double price;
    private String description;
    private int categoryId;

    public Product(int id, String name, double price, String description, int categoryId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.categoryId = categoryId;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public Product getProductDetails(){
        return this;
    }

}

