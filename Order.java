package main.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a customer order with items and status tracking.
 * Demonstrates: Composition, Encapsulation, use of Collections
 */
public class Order {
    public enum Status { PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED }

    private String orderId;
    private String customerId;
    private String customerName;
    private List<OrderItem> items;
    private Status status;
    private String orderDate;
    private double totalAmount;

    public Order(String orderId, String customerId, String customerName, List<OrderItem> items, String orderDate) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.items = new ArrayList<>(items);
        this.orderDate = orderDate;
        this.status = Status.PENDING;
        this.totalAmount = items.stream().mapToDouble(OrderItem::getSubtotal).sum();
    }

    // Constructor for loading from file
    public Order(String orderId, String customerId, String customerName,
                 List<OrderItem> items, String orderDate, Status status, double totalAmount) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.items = new ArrayList<>(items);
        this.orderDate = orderDate;
        this.status = status;
        this.totalAmount = totalAmount;
    }

    public String getOrderId()           { return orderId; }
    public String getCustomerId()        { return customerId; }
    public String getCustomerName()      { return customerName; }
    public List<OrderItem> getItems()    { return items; }
    public Status getStatus()            { return status; }
    public String getOrderDate()         { return orderDate; }
    public double getTotalAmount()       { return totalAmount; }
    public void setStatus(Status s)      { this.status = s; }

    public void printDetails() {
        System.out.println("\n--- Order ID: " + orderId + " ---");
        System.out.println("Customer : " + customerName + " (" + customerId + ")");
        System.out.println("Date     : " + orderDate);
        System.out.println("Status   : " + status);
        System.out.println("Items:");
        for (OrderItem item : items) {
            System.out.printf("  %-25s x%d  @ $%.2f  = $%.2f%n",
                    item.getProductName(), item.getQuantity(), item.getUnitPrice(), item.getSubtotal());
        }
        System.out.printf("Total    : $%.2f%n", totalAmount);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(orderId).append(",")
          .append(customerId).append(",")
          .append(customerName).append(",")
          .append(orderDate).append(",")
          .append(status).append(",")
          .append(totalAmount).append(",");
        for (int i = 0; i < items.size(); i++) {
            sb.append(items.get(i).toString());
            if (i < items.size() - 1) sb.append(";");
        }
        return sb.toString();
    }
}
