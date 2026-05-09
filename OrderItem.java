package main.model;

/**
 * Represents a single line item inside an order or cart.
 * Demonstrates: Composition (used inside Order)
 */
public class OrderItem {
    private String productId;
    private String productName;
    private int quantity;
    private double unitPrice;

    public OrderItem(String productId, String productName, int quantity, double unitPrice) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public double getSubtotal()       { return unitPrice * quantity; }
    public String getProductId()      { return productId; }
    public String getProductName()    { return productName; }
    public int getQuantity()          { return quantity; }
    public double getUnitPrice()      { return unitPrice; }

    @Override
    public String toString() {
        return productId + "|" + productName + "|" + quantity + "|" + unitPrice;
    }

    public static OrderItem fromString(String s) {
        String[] parts = s.split("\\|");
        return new OrderItem(parts[0], parts[1], Integer.parseInt(parts[2]), Double.parseDouble(parts[3]));
    }
}
