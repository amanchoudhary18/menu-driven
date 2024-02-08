package serialize;
import java.io.*;
import java.util.*;


public class CategorySerializer {
    public static void serializeCategories(HashMap<Integer, String> categoryMap) {
        try (FileOutputStream fileOut = new FileOutputStream("categories.txt");
             ObjectOutputStream objOut = new ObjectOutputStream(fileOut)) {
            objOut.writeObject(categoryMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static HashMap<Integer, String> deserializeCategories() {
        try (FileInputStream fileIn = new FileInputStream("categories.txt");
             ObjectInputStream objIn = new ObjectInputStream(fileIn)) {
            Object loadedObject = objIn.readObject();

            return (HashMap<Integer, String>) loadedObject;

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new HashMap<>(); // Return an empty list if deserialization fails
        }
    }
}

