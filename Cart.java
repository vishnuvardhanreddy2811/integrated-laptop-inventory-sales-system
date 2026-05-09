package main.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Shopping cart — holds OrderItems before an order is placed.
 * Demonstrates: Encapsulation, Composition
 */
public class Cart {
    private String customerId;
    private List<OrderItem> items;

    public Cart(String customerId) {
        this.customerId = customerId;
        this.items = new ArrayList<>();
    }

    public void addItem(OrderItem item) {
        // If product already in cart, increase quantity
        for (OrderItem existing : items) {
            if (existing.getProductId().equals(item.getProductId())) {
                items.remove(existing);
                items.add(new OrderItem(item.getProductId(), item.getProductName(),
                        existing.getQuantity() + item.getQuantity(), item.getUnitPrice()));
                return;
            }
        }
        items.add(item);
    }

    public boolean removeItem(String productId) {
        return items.removeIf(i -> i.getProductId().equals(productId));
    }

    public void clear() { items.clear(); }
    public boolean isEmpty() { return items.isEmpty(); }

    public double getTotal() {
        return items.stream().mapToDouble(OrderItem::getSubtotal).sum();
    }

    public List<OrderItem> getItems() { return items; }
    public String getCustomerId()     { return customerId; }

    public void display() {
        if (items.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }
        System.out.println("\n--- Your Cart ---");
        System.out.printf("%-10s %-25s %-6s %-10s %-10s%n", "Prod ID", "Name", "Qty", "Unit $", "Subtotal");
        System.out.println("-".repeat(65));
        for (OrderItem item : items) {
            System.out.printf("%-10s %-25s %-6d $%-9.2f $%-9.2f%n",
                    item.getProductId(), item.getProductName(),
                    item.getQuantity(), item.getUnitPrice(), item.getSubtotal());
        }
        System.out.println("-".repeat(65));
        System.out.printf("%-43s $%.2f%n", "TOTAL:", getTotal());
    }
}
