import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import dto.*;
import services.CategoryService;
import services.ProductService;
import services.UserService;


public class Main {

    // Logger
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    // Scanner 
    private final static Scanner in = new Scanner(System.in);

    // Services Import
    private final static UserService userService = new UserService();
    private final static ProductService productService = new ProductService();
    private final static CategoryService categoryService = new CategoryService();

    // Current User
    private static User user = null;

    // Main function
    public static void main(String[] args) {
        // Register/Login Menu
        boolean loginMenu = true;
        while (loginMenu) {
            try {
                System.out.println("\n## LOGIN PAGE ##\n");
                System.out.println("Welcome to E-Commerce Platform - ");
                System.out.println("1. New to the platform? Register");
                System.out.println("2. Already registered? Login");
                System.out.println("3. Explore Products");
                System.out.println("4. Exit");
                String loginChoice = in.nextLine();

                switch (loginChoice) {
                    // Register Case
                    case "1": {
                        registerMenu();
                        break;
                    }

                    // Login Case
                    case "2": {
                        loginMenu();
                        break;
                    }

                    // Explore Products
                    case "3": {
                        viewProductsPage();
                        break;
                    }

                    // Exit Case
                    case "4": {
                        System.out.println("Please come again :)");
                        loginMenu = false;
                        break;
                    }

                    // default
                    default: {
                        System.out.println("Choose a correct option !!");
                        LOGGER.log(Level.INFO, "Invalid input for switch");
                    }
                }
            } catch (Exception error) {
                LOGGER.log(Level.SEVERE, "Exception : " + error);
            }
        }
    }


    // Register account menu
    private static void registerMenu() {

        boolean accountMenu = true;
        while (accountMenu) {
            try {
                System.out.println("\n## CHOOSE ACCOUNT TYPE ##\n");
                System.out.println("1. Create a admin account");
                System.out.println("2. Create a customer account");
                System.out.println("3. Return to login page");
                String accountChoice = in.nextLine();
                switch (accountChoice) {

                    // Admin Register
                    case "1": {
                        System.out.println("\n## ENTER YOUR REGISTRATION DETAILS ##\n");

                        System.out.print("Enter your name : ");
                        String enteredName = in.nextLine();

                        boolean emailInput = true;

                        String enteredEmail = null;
                        System.out.print("Enter your email : ");
                        while (emailInput) {
                            enteredEmail = in.nextLine();

                            if (!userService.validateEmail(enteredEmail)) {
                                LOGGER.log(Level.WARNING, "Input email was invalid");
                                System.out.print("Please enter a valid email : ");
                                continue;
                            }

                            if (userService.emailAlreadyExists(enteredEmail)) {
                                LOGGER.log(Level.WARNING, "User already exists with this email");
                                System.out.println("This email is already in use !!");
                                System.out.print("Please enter a valid email : ");
                                continue;
                            }

                            emailInput = false;

                        }

                        boolean passwordInput = true;

                        String enteredPassword = null;
                        System.out.print("Enter your password : ");
                        while (passwordInput) {

                            enteredPassword = in.nextLine();

                            if (!userService.validatePassword(enteredPassword)) {
                                System.out.print("Please re-enter your password : ");
                                continue;
                            }

                            passwordInput = false;

                        }
                        System.out.print("Enter company key to initiate account creation : ");
                        String secretKey = in.nextLine();

                        user = userService.register(enteredName, enteredEmail, enteredPassword, secretKey);

                        LOGGER.log(Level.INFO, "Registration Successful !!");
                        System.out.println("Successfully registered. !!");
                        homeMenu();

                        accountMenu=false;

                        break;
                    }

                    // Customer Register
                    case "2": {
                        System.out.println("\n## ENTER YOUR REGISTRATION DETAILS ##\n");

                        System.out.print("Enter your name : ");
                        String enteredName = in.nextLine();

                        boolean emailInput = true;

                        String enteredEmail = null;
                        System.out.print("Enter your email : ");
                        while (emailInput) {
                            enteredEmail = in.nextLine();

                            if (!userService.validateEmail(enteredEmail)) {
                                LOGGER.log(Level.WARNING, "Input email was invalid");
                                System.out.print("Please enter a valid email : ");
                                continue;
                            }

                            if (userService.emailAlreadyExists(enteredEmail)) {
                                LOGGER.log(Level.WARNING, "User already exists with this email");
                                System.out.println("This email is already in use !!");
                                System.out.print("Please enter a valid email : ");
                                continue;
                            }

                            emailInput = false;
                            accountMenu = false;

                        }

                        boolean passwordInput = true;

                        String enteredPassword = null;
                        System.out.print("Enter your password : ");
                        while (passwordInput) {

                            enteredPassword = in.nextLine();

                            if (!userService.validatePassword(enteredPassword)) {
                                System.out.print("Please re-enter your password : ");
                                continue;
                            }

                            passwordInput = false;

                        }


                        user = userService.register(enteredName, enteredEmail, enteredPassword);

                        LOGGER.log(Level.INFO, "Registration Successful !!");
                        System.out.println("Successfully registered. Enjoy your shopping !!");
                        homeMenu();
                        break;
                    }

                    // Return to login page
                    case "3": {
                        accountMenu = false;
                        break;
                    }

                    // default
                    default: {
                        System.out.println("Choose a correct option");
                        LOGGER.log(Level.WARNING, "Invalid input for switch case");


                    }
                }

            } catch (Exception error) {
                System.out.println("Some error occurred. Try Again !!");
                LOGGER.log(Level.SEVERE, "Exception : " + error);

            }
        }
    }


    // Login menu
    private static void loginMenu() {
        System.out.println("\n## ENTER YOUR LOGIN DETAILS ##\n");

        try {
            System.out.print("Enter your email : ");
            String enteredEmail = in.nextLine();

            System.out.print("Enter your password : ");
            String enteredPassword = in.nextLine();
            user = userService.login(enteredEmail, enteredPassword);
            System.out.println("Congratulations! You have successfully logged in");
            LOGGER.log(Level.INFO, "Login Successful");

            user.showUserDetails();
            // Home Menu
            homeMenu();

        } catch (Exception error) {
            System.out.println(error.getLocalizedMessage());
            LOGGER.log(Level.WARNING, "Exception : " + error);
        }
    }


    // home menu
    private static void homeMenu() {
        System.out.println("\n## HOME PAGE ##\n");

        boolean homeMenu = true;
        while (homeMenu) {
            try {
                if (!user.getIsAdmin()) {

                    // User Menu
                    System.out.println("What do you want to do?");
                    System.out.println("1. Let's do some shopping");
                    System.out.println("2. View my cart");
                    System.out.println("3. View my profile");
                    System.out.println("4. Return to Login Page");

                    String homeChoice = in.nextLine();
                    switch (homeChoice) {
                        // View Products Page
                        case "1": {
                            viewProductsPage();
                            break;
                        }

                        // Cart menu
                        case "2": {
                            cartMenu();
                            break;
                        }

                        // View Profile
                        case "3": {
                            System.out.println("\n## MY PROFILE ##\n");
                            user.showUserDetails();
                            break;
                        }

                        // Return back to login page
                        case "4": {
                            user = null;
                            homeMenu = false;
                            break;
                        }

                        // default
                        default: {
                            System.out.println("Choose a correct option !!");
                            LOGGER.log(Level.WARNING, "Invalid option selected for switch");
                        }
                    }

                } else {

                    // Admin Menu

                    System.out.println("What do you want to do?");
                    System.out.println("1. View all products");
                    System.out.println("2. View all users");
                    System.out.println("3. Add a product");
                    System.out.println("4. Remove a product");
                    System.out.println("5. Return to Login Page");


                    String homeChoice = in.nextLine();

                    switch (homeChoice) {
                        // View all products
                        case "1": {
                            viewProductsPage();
                            break;
                        }

                        // View all users
                        case "2": {
                            userService.viewAllUsers(user);
                            break;
                        }

                        // Add a product
                        case "3": {
                            addProductMenu();
                            break;
                        }

                        // Remove a product
                        case "4": {
                            removeProductMenu();
                            break;
                        }

                        // Return back to home
                        case "5": {
                            homeMenu = false;
                            break;
                        }

                        // default
                        default: {
                            System.out.println("Choose a correct option !!");
                            LOGGER.log(Level.WARNING, "Invalid option selected for switch");
                        }
                    }

                }


            } catch (Exception error) {
                System.out.println(error.getLocalizedMessage());
                LOGGER.log(Level.SEVERE, "Error : " + error);
            }
        }
    }


    // cart menu
    private static void cartMenu() {
        userService.showCartDetails(user);
        boolean cartMenu = true;
        while (cartMenu) {
            try {
                System.out.println("\n## MY CART ##\n");
                System.out.println("What do you want to do with your cart?");

                System.out.println("1. Checkout");
                System.out.println("2. Remove Item");
                System.out.println("3. Return to home");

                String cartChoice = in.nextLine();

                switch (cartChoice) {
                    // Checkout Cart
                    case "1": {
                        ArrayList<CartElement> cart = user.getCart();
                        if (cart.isEmpty()) {
                            System.out.println("Cart is empty!!");
                            break;
                        }
                        userService.checkoutCart(user);
                        System.out.println("Due bill : $" + user.getDueBill());
                        cartMenu = false;
                        break;
                    }

                    // Remove item from cart
                    case "2": {
                        ArrayList<CartElement> cart = user.getCart();
                        if (cart.isEmpty()) {
                            System.out.println("Cart is empty!!");
                            cartMenu = false;
                            break;
                        }

                        // Dynamic rendering cart items
                        System.out.println("Enter the choice you want to remove from cart: ");
                        for (int i = 0; i < cart.size(); i++) {
                            Product p = productService.getProductById(cart.get(i).getProductId());
                            System.out.println((i + 1) + ". " + p.getName());
                        }
                        System.out.println(cart.size() + 1 + ". Return to Cart Menu");

                        int removeChoice = Integer.parseInt(in.nextLine());

                        if (removeChoice >= 1 && removeChoice <= cart.size()) {
                            int productId = cart.get(removeChoice - 1).getProductId();
                            userService.removeFromCart(productId, user);
                        } else if (removeChoice == cart.size() + 1) {
                            break;
                        } else {
                            System.out.println("Choose a correct option");
                        }

                        userService.showCartDetails(user);

                        break;
                    }

                    // Return to Home
                    case "3": {
                        cartMenu = false;
                        break;
                    }

                    // default
                    default: {
                        System.out.println("Choose a correct option !!");
                        LOGGER.log(Level.WARNING, "Invalid option selected for switch");
                    }
                }

            } catch (Exception error) {
                LOGGER.log(Level.SEVERE, "Exception : " + error);
            }
        }
    }


    // add product menu
    private static void addProductMenu() {
        System.out.println("## ADD A PRODUCT FORM ##\n");
        System.out.println("Enter product name :");
        String name = in.nextLine();


        boolean priceInput = true;
        System.out.println("Enter price (in dollars) :");
        double price = 0;
        while (priceInput) {
            try {
                price = Double.parseDouble(in.nextLine());
                priceInput = false;
            } catch (Exception error) {
                LOGGER.log(Level.WARNING, "Invalid input for price");
                System.out.println("Re-enter price (in dollars) :");
            }

        }

        System.out.println("Enter product description :");
        String description = in.nextLine();

        System.out.println("Select the category :");
        HashMap<Integer, String> categories = categoryService.getCategoryMap();

        boolean categoryInput = true;
        while (categoryInput) {
            // Dynamic displaying of Categories
            for (Map.Entry<Integer, String> entry : categories.entrySet()) {
                System.out.println(entry.getKey() + ". " + entry.getValue());
            }

            System.out.println((categories.size() + 1) + ". Add a new Category");

            try {
                int categoryChoice = Integer.parseInt(in.nextLine());
                if (categoryChoice >= 1 && categoryChoice <= categories.size()) {
                    productService.addProduct(name, price, description.trim(), categoryChoice);
                    productService.showAllProducts();
                } else if (categoryChoice == categories.size() + 1) {
                    System.out.println("Enter new category name");
                    String newCategoryName = in.nextLine();
                    categoryService.addCategory(newCategoryName);

                    categories = categoryService.getCategoryMap();
                    productService.addProduct(name, price, description.trim(), categories.size());
                    productService.showAllProducts();
                } else {

                    System.out.println("Choose a correct option !!");
                    LOGGER.log(Level.WARNING, "Invalid option selected for switch");

                }

                categoryInput = false;
            } catch (Exception e) {
                LOGGER.log(Level.WARNING, "Invalid input for category");
                System.out.println("Choose from the given options:");
            }

        }
    }

    // remove product menu
    private static void removeProductMenu() {
        System.out.println("\n## REMOVE A PRODUCT FORM ##\n");
        ArrayList<Product> products = productService.getAllProducts();

        boolean removeProductMenu = true;
        while (removeProductMenu) {
            try {
                System.out.println("Choose the product you want to remove:");

                productService.showAllProducts(products);

                System.out.println((products.size() + 1) + " Return");
                int productChoice = Integer.parseInt(in.nextLine());


                if (productChoice >= 1 && productChoice <= products.size()) {
                    productService.removeProduct(products.get(productChoice - 1).getId());
                    productService.showAllProducts();
                    removeProductMenu = false;


                } else if (productChoice == products.size() + 1) {
                    removeProductMenu = false;
                } else {

                    System.out.println("Choose a correct option !!");
                    LOGGER.log(Level.WARNING, "Invalid option selected");

                }
            } catch (Exception e) {
                LOGGER.log(Level.WARNING, "Invalid input");
                System.out.println("Choose from the following products:");
            }

        }
    }


    // view products page
    private static void viewProductsPage() {
        LOGGER.log(Level.INFO, "Successfully entered view product page");

        boolean viewProductMenu = true;

        while (viewProductMenu) {

            System.out.println("\n## VIEW PRODUCTS PAGE ##\n");
            System.out.println("Choose from the following options : ");

            System.out.println("1. View by category");
            System.out.println("2. Sort and view products");
            System.out.println("3. Search products by keyword");
            System.out.println(user != null ? "4. Return to home page" : "4. Go back");
            String viewProductChoice = in.nextLine();

            try {
                switch (viewProductChoice) {
                    // Category Menu
                    case "1": {
                        productCategoryMenu();
                        break;
                    }

                    // Sort Products Menu
                    case "2": {
                        productSortMenu();
                        break;
                    }

                    // Search Products by keyword
                    case "3": {
                        productSearchMenu();
                        break;
                    }

                    // Return to Home
                    case "4": {
                        viewProductMenu = false;
                        break;
                    }

                    // default
                    default: {
                        System.out.println("Choose a correct option !!");
                        LOGGER.log(Level.WARNING, "Invalid option selected for switch");
                    }
                }

            } catch (Exception error) {
                LOGGER.log(Level.WARNING, "Invalid input for category");
                System.out.println("Choose from the given options:");
            }
        }
    }

    // search product by category menu
    private static void productCategoryMenu() {
        LOGGER.log(Level.INFO, "Successfully entered product category page");

        boolean categoryMenu = true;
        while (categoryMenu) {
            System.out.println("\n## CATEGORY PAGE ##\n");
            System.out.println("Choose from the following categories : ");

            HashMap<Integer, String> categories = categoryService.getCategoryMap();
            for (Map.Entry<Integer, String> entry : categories.entrySet()) {
                System.out.println(entry.getKey() + ". " + entry.getValue());
            }

            System.out.println((categories.size() + 1) + ". Return to View Products page");


            try {
                int categoryChoice = Integer.parseInt(in.nextLine());
                if (categoryChoice >= 1 && categoryChoice <= categories.size()) {
                    ArrayList<Product> products = productService.getProductsByCategory(categoryChoice);
                    showProductMenus(products);
                } else if (categoryChoice == categories.size() + 1) {
                    categoryMenu = false;
                } else {
                    System.out.println("Choose a correct option !!");
                    LOGGER.log(Level.WARNING, "Invalid option selected");
                }


            } catch (Exception error) {

                System.out.println("Choose a correct option !!");
                LOGGER.log(Level.WARNING, "Invalid option selected for switch");

            }
        }
    }

    // Search product by keyword menu
    private static void productSearchMenu() {
        LOGGER.log(Level.INFO, "Successfully entered search product page");

        System.out.println("Enter keyword to search :");

        try {
            String keyword = in.nextLine();
            if (keyword.isEmpty())
                throw new Exception("Keyword cannot be empty");
            ArrayList<Product> searchedProducts = productService.searchByKeyword(keyword);
            if (searchedProducts.isEmpty()) {
                System.out.println();
                System.out.println("No results found.");
                System.out.println();
            } else {
                showProductMenus(searchedProducts);
            }

        } catch (Exception error) {
            System.out.println(error.getLocalizedMessage());
            LOGGER.log(Level.WARNING, "Empty input for keyword");
        }

    }


    // sort product menu
    private static void productSortMenu() {
        LOGGER.log(Level.INFO, "Successfully entered sort product menu");

        boolean sortMenu = true;
        while (sortMenu) {
            System.out.println("Sort by : ");

            System.out.println("1. Name - (A-Z)");
            System.out.println("2. Name - (Z-A)");
            System.out.println("3. Price - Lowest to Highest");
            System.out.println("4. Price - Highest to Lowest");
            System.out.println("5. Return to View Product page");

            try {
                String sortChoice = in.nextLine();
                switch (sortChoice) {
                    case "1": {
                        ArrayList<Product> sortedProducts = productService.sortByName(true);
                        LOGGER.log(Level.INFO, "Successfully sorted A-Z");

                        showProductMenus(sortedProducts);
                        break;
                    }

                    case "2": {
                        ArrayList<Product> sortedProducts = productService.sortByName(false);
                        LOGGER.log(Level.INFO, "Successfully sorted Z-A");

                        showProductMenus(sortedProducts);
                        break;
                    }

                    case "3": {
                        ArrayList<Product> sortedProducts = productService.sortByPrice(true);
                        LOGGER.log(Level.INFO, "Successfully sorted by ascending price");

                        showProductMenus(sortedProducts);
                        break;
                    }

                    case "4": {
                        ArrayList<Product> sortedProducts = productService.sortByPrice(false);
                        LOGGER.log(Level.INFO, "Successfully sorted by descending price");
                        showProductMenus(sortedProducts);
                        break;
                    }

                    case "5": {
                        sortMenu = false;
                        break;
                    }

                    default: {
                        System.out.println("Choose a correct option !!");
                        LOGGER.log(Level.WARNING, "Invalid option selected for switch");
                    }

                }


            } catch (Exception error) {
                System.out.println("Choose a correct option !!");
                LOGGER.log(Level.WARNING, "Invalid option selected for switch");
            }
        }
    }

    // dynamic rendering menu for products
    private static void showProductMenus(ArrayList<Product> products) {
        LOGGER.log(Level.INFO, "Entered product menu page.");
        ProductService productService = new ProductService();

        // Products Menu
        boolean productsMenu = true;
        while (productsMenu) {

            try {
                System.out.println("\n## PRODUCTS PAGE ##\n");
                System.out.println("Choose from the following products : ");
                productService.showAllProducts(products);
                System.out.println(products.size() + 1 + ". Go back");
                int productsChoice = Integer.parseInt(in.nextLine());

                // Dynamic case rendering
                if (productsChoice <= products.size() && productsChoice >= 1) {
                    LOGGER.log(Level.INFO, "Entered product details page");
                    Product product = products.get(productsChoice - 1);

                    System.out.println("----------------------------------------");
                    System.out.println("|           PRODUCT DETAILS            |");
                    System.out.println("----------------------------------------");

                    System.out.println("| " + String.format("%-36s", "Name : " + product.getName()) + " |");
                    System.out.println("|                                      |");

                    int maxWidth = 36;
                    String[] words = product.getDescription().split("\\s+");
                    StringBuilder currentLine = new StringBuilder();

                    for (String word : words) {
                        if (currentLine.length() + word.length() + 1 <= maxWidth) {
                            // Add the word and a space
                            if (currentLine.length() == 0) {
                                currentLine.append(word);
                            } else
                                currentLine.append(" ").append(word);
                        } else {

                            // Print the current line and start a new one
                            System.out.println("| " + String.format("%-36s", currentLine) + " |");
                            currentLine = new StringBuilder(word);
                        }
                    }

                    System.out.println("| " + String.format("%-36s", currentLine) + " |");

                    System.out.println("|                                      |");

                    System.out.println("| " + String.format("%-36s", "Price : $" + product.getPrice()) + " |");
                    System.out.println("----------------------------------------");


                    if (user != null && !user.getIsAdmin()) {
                        addToCartMenu(product);

                    } else {
                        System.out.println("Press enter key to go back");
                        in.nextLine();

                    }


                } else if (productsChoice == products.size() + 1) {
                    // Return to category page
                    productsMenu = false;
                } else {
                    // Default
                    System.out.println("Choose a correct option !!");
                    LOGGER.log(Level.WARNING, "Invalid option selected");
                }

            } catch (Exception error) {
                System.out.println("Choose a correct option !!");
                LOGGER.log(Level.WARNING, "Invalid option selected");
            }
        }
    }

    // add to cart menu
    private static void addToCartMenu(Product product) {
        boolean addToCartMenu = true;
        while (addToCartMenu) {

            try {
                System.out.println("Choose from the following options - ");
                System.out.println("1. Add to cart");
                System.out.println("2. Return to product page");

                String addToCartChoice = in.nextLine();

                switch (addToCartChoice) {
                    // Add to cart
                    case "1": {
                        System.out.println("Enter quantity");
                        int quantity = Integer.parseInt(in.nextLine());

                        int productId = product.getId();
                        userService.addToCart(productId, quantity, user);

                        System.out.println("Added to cart! Wohooo");
                        userService.showCartDetails(user);
                        LOGGER.log(Level.INFO, "Successfully added to cart");
                        addToCartMenu = false;
                        break;
                    }

                    // Return to products page
                    case "2": {
                        addToCartMenu = false;
                        break;
                    }

                    // default
                    default: {
                        System.out.println("Choose a correct option !!");
                        LOGGER.log(Level.WARNING, "Invalid option selected for switch");
                    }
                }
            } catch (Exception error) {
                System.out.println("Some error occurred. Try again !!");
                LOGGER.log(Level.SEVERE, "Exception : " + error);
            }

        }
    }
}