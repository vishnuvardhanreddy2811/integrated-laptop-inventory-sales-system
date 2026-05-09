package main.service;

import main.exception.*;
import main.model.*;
import main.util.FileHandler;
import main.util.IDGenerator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for order management.
 * Demonstrates: Exception Handling (multiple custom exceptions), File Handling
 */
public class OrderService {
    private List<Order> orders;
    private ProductService productService;

    public OrderService(ProductService productService) {
        this.orders = FileHandler.loadOrders();
        this.productService = productService;
    }

    public Order placeOrder(Customer customer, List<OrderItem> items)
            throws InsufficientStockException, InsufficientBalanceException, ProductNotFoundException {

        double total = items.stream().mapToDouble(OrderItem::getSubtotal).sum();

        // Check wallet balance
        if (customer.getWalletBalance() < total) {
            throw new InsufficientBalanceException(total, customer.getWalletBalance());
        }

        // Reserve stock for each item
        for (OrderItem item : items) {
            productService.reserveStock(item.getProductId(), item.getQuantity());
        }

        // Deduct wallet
        customer.deductBalance(total);

        // Create order
        String orderId = IDGenerator.nextOrderId(orders);
        String date = LocalDate.now().toString();
        Order order = new Order(orderId, customer.getUserId(), customer.getUsername(), items, date);
        order.setStatus(Order.Status.CONFIRMED);
        orders.add(order);

        FileHandler.saveOrders(orders);
        return order;
    }

    public void cancelOrder(String orderId, Customer customer)
            throws OrderNotFoundException {
        Order order = findById(orderId);

        if (!order.getCustomerId().equals(customer.getUserId())) {
            System.out.println("[ERROR] You can only cancel your own orders.");
            return;
        }
        if (order.getStatus() == Order.Status.SHIPPED || order.getStatus() == Order.Status.DELIVERED) {
            System.out.println("[ERROR] Cannot cancel an order that is already " + order.getStatus());
            return;
        }
        if (order.getStatus() == Order.Status.CANCELLED) {
            System.out.println("[ERROR] Order is already cancelled.");
            return;
        }

        // Restore stock
        for (OrderItem item : order.getItems()) {
            try { productService.releaseStock(item.getProductId(), item.getQuantity()); }
            catch (ProductNotFoundException e) { /* Product may have been deleted */ }
        }

        // Refund wallet
        customer.addBalance(order.getTotalAmount());
        order.setStatus(Order.Status.CANCELLED);
        FileHandler.saveOrders(orders);
        System.out.println("[SUCCESS] Order " + orderId + " cancelled. $" + order.getTotalAmount() + " refunded.");
    }

    public void updateStatus(String orderId, Order.Status newStatus) throws OrderNotFoundException {
        Order order = findById(orderId);
        order.setStatus(newStatus);
        FileHandler.saveOrders(orders);
        System.out.println("[SUCCESS] Order " + orderId + " status updated to " + newStatus);
    }

    public List<Order> getOrdersByCustomer(String customerId) {
        List<Order> result = new ArrayList<>();
        for (Order o : orders) {
            if (o.getCustomerId().equals(customerId)) result.add(o);
        }
        return result;
    }

    public List<Order> getPendingOrders() {
        List<Order> result = new ArrayList<>();
        for (Order o : orders) {
            if (o.getStatus() == Order.Status.CONFIRMED) result.add(o);
        }
        return result;
    }

    public List<Order> getAllOrders() { return new ArrayList<>(orders); }

    public Order findById(String orderId) throws OrderNotFoundException {
        for (Order o : orders) {
            if (o.getOrderId().equals(orderId)) return o;
        }
        throw new OrderNotFoundException(orderId);
    }

    public void printSalesReport() {
        System.out.println("\n========== SALES REPORT ==========");
        double totalRevenue = 0;
        int totalOrders = 0;
        for (Order o : orders) {
            if (o.getStatus() != Order.Status.CANCELLED) {
                totalRevenue += o.getTotalAmount();
                totalOrders++;
            }
        }
        System.out.println("Total Orders    : " + totalOrders);
        System.out.printf("Total Revenue   : $%.2f%n", totalRevenue);
        System.out.println("Cancelled Orders: " + (orders.size() - totalOrders));
        System.out.println("===================================");
    }
}
