package serialize;
import java.io.*;
import dto.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductSerializer {
    public static void serializeProducts(ArrayList<Product> products) {
        try (FileOutputStream fileOut = new FileOutputStream("products.txt");
             ObjectOutputStream objOut = new ObjectOutputStream(fileOut)) {
            objOut.writeObject(products);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static ArrayList<Product> deserializeProducts() {
        try (FileInputStream fileIn = new FileInputStream("products.txt");
             ObjectInputStream objIn = new ObjectInputStream(fileIn)) {
            Object loadedObject = objIn.readObject();
            if (loadedObject instanceof ArrayList<?>) {
                return (ArrayList<Product>) loadedObject;
            } else {
                LOGGER.log(Level.WARNING, "Unexpected data type found in products.txt");
                return new ArrayList<>(); // Return an empty list if deserialization fails
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>(); // Return an empty list if deserialization fails
        }
    }
}

