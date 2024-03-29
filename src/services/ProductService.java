package services;

import dto.*;
import serialize.ProductSerializer;

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
            return p1.getName().toLowerCase().compareTo(p2.getName().toLowerCase());
        }
    }

    // product static list
    private static ArrayList<Product> products = new ArrayList<>();

    // initializing atomic integer for auto increment id
    private static AtomicInteger nextId = new AtomicInteger(10);


    // static list dummy data
    static {
        products = ProductSerializer.deserializeProducts();
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

    // using 3 threads to search product by keyword
//    public ArrayList<Product> searchByKeyword(String key) {
//        final int threadCount = 3;
//
//        ArrayList<Product> searchedProducts = new ArrayList<>();
//
//        ArrayList<Product> part1 = new ArrayList<>();
//        ArrayList<Product> part2 = new ArrayList<>();
//        ArrayList<Product> part3 = new ArrayList<>();
//
//        // dividing into 3 parts
//        for (int i = 0; i < products.size(); i++) {
//            if (i < (int) (Math.ceil((double) products.size() / threadCount))) {
//                part1.add(products.get(i));
//            } else if (i >= (int) (Math.ceil((double) products.size() / threadCount)) && i < 2 * (int) (Math.ceil((double) products.size() / threadCount))) {
//                part2.add(products.get(i));
//            } else {
//                part3.add(products.get(i));
//            }
//        }
//
//        // maintain an array of threads
//        ArrayList<Thread> searchThreads = new ArrayList<>();
//
//        for (int i = 0; i < threadCount; i++) {
//            ArrayList<Product> selectedPart = (i==0)?part1:i==1?part2:part3;
//            Thread thread = new Thread(() -> {
//                for (Product product : selectedPart) {
//                    if (product.getName().toLowerCase().contains(key.toLowerCase()) || product.getDescription().toLowerCase().contains(key.toLowerCase())) {
//                        synchronized (searchedProducts) {
//                            searchedProducts.add(product);
//                        }
//                    }
//                }
//            });
//            searchThreads.add(thread);
//            thread.start();
//        }
//
//
//        // Wait for all threads to finish
//        searchThreads.forEach(thread -> {
//            try {
//                thread.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });
//
//
//        return searchedProducts;
//    }



    // filter products based on price
    public ArrayList<Product> filterProducts(double min, double max) throws Exception {

        if (min > max) {
            throw new Exception("Maximum price cannot be less than minimum price.");
        }

        ArrayList<Product> filteredProducts = new ArrayList<>();
        for (Product product : products) {
            if (product.getPrice() >= min && product.getPrice() <= max) {
                filteredProducts.add(product);
            }
        }

        return filteredProducts;
    }


}





