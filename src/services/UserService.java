package services;

import dto.*;

import java.util.*;
import java.util.logging.*;
import java.util.regex.*;

public class UserService {

    // logger init
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    // static user database
    static HashMap<String, User> userMap = new HashMap<>();
    private final static String EMAIL_REGEX = "^[a-zA-Z0-9][a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
    private final static String SECRET_KEY = "E-Comm#Fastenal";

    // dummy user data


    static {
        userMap.put("aman.choudhary9785@gmail.com", new User("Aman Choudhary", "aman.choudhary9785@gmail.com", "Aman123", false));
    }


    // validate email using regex
    public boolean validateEmail(String email) {
        // initializing regex
        Pattern pattern = Pattern.compile(EMAIL_REGEX);

        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean emailAlreadyExists(String email) throws Exception{
        if(email.isEmpty()){
            throw new Exception("Email field cannot be empty");
        }
        return userMap.containsKey(email.toLowerCase());
    }

    public boolean validatePassword(String enteredPassword) {
        if (!enteredPassword.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")) {
            if (enteredPassword.length() < 8) {
                System.out.println("The length of the password must be at least 8");
                return false;
            }

            if (!enteredPassword.matches("^(?=.*[0-9]).*$")) {
                System.out.println("The password must contain at least one digit.");
                return false;
            }
            if (!enteredPassword.matches("^(?=.*[a-z]).*$")) {
                System.out.println("The password must contain at least one lowercase letter.");
                return false;

            }
            if (!enteredPassword.matches("^(?=.*[A-Z]).*$")) {
                System.out.println("The password must contain at least one uppercase letter.");
                return false;

            }
            if (!enteredPassword.matches("^(?=.*[@#$%^&+=]).*$")) {
                System.out.println("The password must contain at least one special character.");
                return false;

            }
            if (enteredPassword.matches("^\\S*$")) {
                System.out.println("The password must not contain any whitespace.");
                return false;

            }
        }
        return true;
    }

    public User register(String name, String email, String password) {
        User user = new User(name, email.toLowerCase(), password, false);
        userMap.put(email, user);
        System.out.println(userMap.size());
        user.showUserDetails();
        return user;
    }

    public User register(String name, String email, String password, String secretKey) throws Exception {

        if (!secretKey.equals(SECRET_KEY)) {
            throw new Exception("The company key is invalid");
        }
        User user = new User(name, email.toLowerCase(), password, true);
        userMap.put(email, user);
        user.showUserDetails();
        return user;
    }


    public User login(String email, String password) throws Exception {
        User user = userMap.get(email);
        if (null == user) {
            throw new Exception("Email does not exist !!");
        }
        if (user.getPassword().equals(password)) {
            return user;
        } else {
            throw new Exception("Incorrect password !!");
        }
    }


    public void addToCart(int id, int quantity, User user) throws Exception {
        ProductService productService = new ProductService();
        Product product = productService.getProductById(id);

        ArrayList<CartElement> cart = user.getCart();

        if(quantity<1){
            throw new Error("Quantity cannot be less than 1");
        }

        for (CartElement cartElement : cart) {
            if (cartElement.getProductId() == id) {
                cartElement.addQuantity(quantity);
                return;
            }
        }

        if (product != null) {
            CartElement cartElement = new CartElement(id, quantity);
            cart.add(cartElement);
            user.setCart(cart);
        } else {
            throw new Exception("Incorrect Product ID");
        }

    }

    public void removeFromCart(int productId, User user) throws Exception{

        ArrayList<CartElement> cart = user.getCart();

        if(null == cart){
            throw new Error("Error while fetching the cart.");
        }

        for (int i = 0; i < cart.size(); i++) {
            if(null == cart.get(i)){
                throw new Error("Cart element does not exist");
            }


            if (cart.get(i).getProductId() == productId) {
                cart.remove(i);
                break;
            }
        }

        user.setCart(cart);

    }

    public void showCartDetails(User user) throws Exception{
        ArrayList<CartElement> cart = user.getCart();
        ProductService productService = new ProductService();

        if (cart.isEmpty()) {
            System.out.println("Enter some items in the cart !!");
            System.out.println();
            throw new Exception("Cart is Empty");
        } else {
            double totalAmt = 0;

            String tableHeader = "| " + String.format("%-30s", "Item Name") + " | " + String.format("%-10s", "Quantity") + " | " + String.format("%-30s", "Price") + " |" + String.format("%-30s", "Total Price") + " |";
            System.out.println(tableHeader.replaceAll(".", "-"));
            System.out.println(tableHeader);
            System.out.println(tableHeader.replaceAll(".", "-"));

            for (CartElement cartElement : cart) {
                Product product = productService.getProductById(cartElement.getProductId());

                if(null==product){
                    throw new Exception("Error occurred while fetching products from cart. Try again");
                }

                int quantity = cartElement.getQuantity();
                System.out.println("| " + String.format("%-30s", product.getName()) + " | " + String.format("%-10s", quantity) + " | " + String.format("%-30s", product.getPrice()) + " |" + String.format("%-30s", product.getPrice() * quantity) + " |");
                totalAmt += product.getPrice() * quantity;
            }

            System.out.println(tableHeader.replaceAll(".", "-"));
            System.out.println("Total Amount - " + "$" + totalAmt);
            System.out.println(tableHeader.replaceAll(".", "-"));
            System.out.println();
        }
    }

    public void checkoutCart(User user) throws Exception{

        ArrayList<CartElement> cart = user.getCart();
        ProductService productService = new ProductService();

        double totalAmt = 0;
        for (CartElement cartElement : cart) {
            Product product = productService.getProductById(cartElement.getProductId());
            if(null==product){
                throw new Exception("Error occurred while fetching products from cart. Try again");
            }
            int quantity = cartElement.getQuantity();
            totalAmt += product.getPrice() * quantity;
        }

        user.setCart(new ArrayList<>());
        user.setDueBill(totalAmt);

    }


    public void viewAllUsers(User currentUser) {
        if (!currentUser.getIsAdmin()) {
            LOGGER.log(Level.WARNING, "This action requires 'ADMIN' privileges ");
            return;
        }

        System.out.println("\n## ALL USERS ##\n");

        String tableHeader = "| " + String.format("%-30s", "Name") + " | " + String.format("%-30s", "Email") + " | " + String.format("%-10s", "Role") + " |";

        System.out.println(tableHeader.replaceAll(".", "-"));
        System.out.println(tableHeader);
        System.out.println(tableHeader.replaceAll(".", "-"));


        for (Map.Entry<String, User> entry : userMap.entrySet()) {
            User user = entry.getValue();
            System.out.println("| " + String.format("%-30s", user.getName()) + " | " + String.format("%-30s", user.getEmail()) + " | " + String.format("%-10s", user.getIsAdmin() ? "Admin" : "Customer") + " |");
        }


    }

}

