package main.model;

/**
 * Represents a product (laptop or accessory) in the store.
 * Demonstrates: Encapsulation
 */
public class Product {
    private String productId;
    private String name;
    private String category;   // LAPTOP, ACCESSORY
    private String description;
    private double price;
    private int stock;

    public Product(String productId, String name, String category, String description, double price, int stock) {
        this.productId = productId;
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }

    public boolean isAvailable()           { return stock > 0; }
    public void reduceStock(int qty)       { this.stock -= qty; }
    public void increaseStock(int qty)     { this.stock += qty; }

    public String getProductId()           { return productId; }
    public String getName()                { return name; }
    public String getCategory()            { return category; }
    public String getDescription()         { return description; }
    public double getPrice()               { return price; }
    public int getStock()                  { return stock; }
    public void setPrice(double price)     { this.price = price; }
    public void setStock(int stock)        { this.stock = stock; }
    public void setDescription(String d)   { this.description = d; }

    public void display() {
        System.out.printf("%-10s %-25s %-12s $%-10.2f Stock: %d%n",
                productId, name, category, price, stock);
    }

    @Override
    public String toString() {
        return productId + "," + name + "," + category + "," + description + "," + price + "," + stock;
    }
}
