package services;

import dto.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;

public class ProductService {

    // comparator inner class for price
    static class PriceComparator implements Comparator<Product> {
        public int compare(Product p1, Product p2) {
            return Double.compare(p1.getPrice(), p2.getPrice());
        }
    }

    // comparator inner class for name
    static class AlphabeticComparator implements Comparator<Product> {
        public int compare(Product p1, Product p2) {
            return p1.getName().compareTo(p2.getName());
        }
    }

    // product static list
    private static ArrayList<Product> products = new ArrayList<>();

    // initializing atomic integer for auto increment id
    private static AtomicInteger nextId = new AtomicInteger(10);


    // static list dummy data
    static {
        // Gadgets
        products.add(new Product(1, "Smartphone", 500.0, "A high-end smartphone that is perfect for gaming, streaming, and multitasking.", 1));
        products.add(new Product(2, "Laptop", 1000.0, "A powerful laptop that can handle all your work and entertainment needs.", 1));

        // Grocery
        products.add(new Product(3, "Pizza", 10.0, "A delicious pizza that is perfect for a quick meal or a party.", 2));
        products.add(new Product(4, "Burger", 5.0, "A juicy burger that is perfect for a quick snack or a meal.", 2));
        products.add(new Product(7, "Milk", 2.0, "A gallon of milk that is perfect for your daily needs.", 2));
        products.add(new Product(8, "Bread", 1.0, "A loaf of bread that is perfect for sandwiches or toast.", 2));

        // Stationary
        products.add(new Product(5, "Pen", 1.0, "A ballpoint pen that is perfect for writing notes or signing documents.", 3));
        products.add(new Product(6, "Notebook", 2.0, "A spiral notebook that is perfect for taking notes or keeping a journal.", 3));

        // Furniture
        products.add(new Product(9, "Bed", 500.0, "A sturdy double bed that is perfect for a good night's sleep.", 4));
        products.add(new Product(10, "Table", 200.0, "A wooden study table that is perfect for working or studying.", 4));
    }

    // fetch all products
    public ArrayList<Product> getAllProducts() {
        return products;
    }

    // fetch product by id
    public Product getProductById(int id) {
        Product product = null;
        for (Product productElement : products) {
            if (productElement.getId() == id) {
                product = productElement;
            }
        }

        return product;
    }

    // fetch products by category
    public ArrayList<Product> getProductsByCategory(int categoryId) {
        ArrayList<Product> productsByCategory = new ArrayList<>();

        for (Product product : products) {
            if (product.getCategoryId() == categoryId) {
                productsByCategory.add(product);
            }
        }

        return productsByCategory;
    }

    // show all products - productList is given
    public void showAllProducts(ArrayList<Product> products) {
        printProducts(products);
    }

    // show all products - using static product list
    public void showAllProducts() {
        printProducts(products);
    }

    // simple display of products
    private void printProducts(ArrayList<Product> products) {
        String tableHeader = "| " + String.format("%-10s", "Option ") + "| " + String.format("%-30s", "Product Name") + " | " + String.format("%-30s", "Product Price");
        System.out.println(tableHeader.replaceAll(".", "-"));
        System.out.println(tableHeader);
        System.out.println(tableHeader.replaceAll(".", "-"));
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            System.out.println("| " + String.format("%-10s", (i + 1)) + "| " + String.format("%-30s", product.getName()) + " | " + String.format("%-30s", "$ " + product.getPrice()));

        }
        System.out.println();
        System.out.print("| ");
    }


    // add product - admin
    public void addProduct(String name, double price, String description, int categoryId) {
        Product product = new Product(products.size(), name, price, description, categoryId);
        products.add(product);
        System.out.println(product.getName());
    }

    // remove product - admin
    public void removeProduct(int productId) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId() == productId) {
                products.remove(i);
                break;
            }
        }

    }

    // sort products by name
    public ArrayList<Product> sortByName(boolean asc) {
        Comparator<Product> nameComparator = new AlphabeticComparator();
        ArrayList<Product> sortedProducts = new ArrayList<>(products);
        ;
        if (asc)
            sortedProducts.sort(nameComparator.thenComparing(nameComparator));
        else
            sortedProducts.sort(nameComparator.thenComparing(nameComparator).reversed());

        return sortedProducts;
    }

    // sort products by price
    public ArrayList<Product> sortByPrice(boolean asc) {
        Comparator<Product> priceComparator = new PriceComparator();
        ArrayList<Product> sortedProducts = new ArrayList<>(products);
        if (asc)
            sortedProducts.sort(priceComparator.thenComparing(priceComparator));
        else
            sortedProducts.sort(priceComparator.thenComparing(priceComparator).reversed());

        return sortedProducts;
    }

    // search products using keywords
    public ArrayList<Product> searchByKeyword(String key) {
        ArrayList<Product> searchedProducts = new ArrayList<>();
        for (Product product : products) {
            if (product.getName().toLowerCase().contains(key.toLowerCase()) || product.getDescription().toLowerCase().contains(key.toLowerCase())) {
                searchedProducts.add(product);
            }
        }

        return searchedProducts;
    }

    //filter products based on price
    public ArrayList<Product> filterProducts(double min,double max) throws Exception{

        if(min>max){
            throw new Exception("Maximum price cannot be less than minimum price.");
        }

        ArrayList<Product> filteredProducts = new ArrayList<>();
        for (Product product : products) {
            if (product.getPrice()>=min  && product.getPrice()<=max) {
                filteredProducts.add(product);
            }
        }

        return filteredProducts;
    }


}





