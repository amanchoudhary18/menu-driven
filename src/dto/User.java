package dto;

import java.util.ArrayList;

public class User {
    private String name;
    private String password;
    private String email;
    private ArrayList<CartElement> cart;
    private double dueBill;
    private Boolean isAdmin;


    public User(String name, String email, String password, Boolean isAdmin) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;

        if (!isAdmin) {
            this.cart = new ArrayList<>();
            this.dueBill = 0.0;
        }

    }

    public void showUserDetails() {
        String tableHeader = "";
        if (this.isAdmin) {
            tableHeader = "| " + String.format("%-30s", "Name") + " | " + String.format("%-30s", "Email") + " | " + String.format("%-10s", "Role") + " |";
            System.out.println(tableHeader.replaceAll(".","-"));
            System.out.println(tableHeader);
            System.out.println(tableHeader.replaceAll(".","-"));
            System.out.println("| " + String.format("%-30s", this.name) + " | " + String.format("%-30s", this.email) + " | " + String.format("%-10s", this.isAdmin ? "Admin" : "Customer") + " |");
        }else{
            tableHeader = "| " + String.format("%-30s", "Name") + " | " + String.format("%-30s", "Email") +  " | " + String.format("%-10s", "Role") + " | " + String.format("%-10s", "Due Bill") + " |";
            System.out.println(tableHeader.replaceAll(".","-"));
            System.out.println(tableHeader);
            System.out.println(tableHeader.replaceAll(".","-"));
            System.out.println("| " + String.format("%-30s", this.name) + " | " + String.format("%-30s", this.email) + " | " + String.format("%-10s", this.isAdmin ? "Admin" : "Customer") + " | " + String.format("%-10s", this.dueBill) + " |");
        }

        System.out.println();
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setCart(ArrayList<CartElement> cart) {
        this.cart = cart;
    }

    public ArrayList<CartElement> getCart() {
        return this.cart;
    }

    public double getDueBill() {
        return dueBill;
    }

    public void setDueBill(double dueBill) {
        this.dueBill = dueBill;
    }

    public Boolean getIsAdmin() {
        return this.isAdmin;
    }


}
