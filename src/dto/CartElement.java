package dto;

public class CartElement {
    private int productId;
    private int quantity;

    public CartElement(int productId,int quantity){
        this.productId = productId;
        this.quantity = quantity;
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }


    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }
}
