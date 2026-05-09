package main.ui;

import main.exception.*;
import main.model.*;
import main.service.*;

import java.util.*;

/**
 * Handles all Customer interactions via console.
 * Demonstrates: Use of OOP services, Exception Handling in UI
 */
public class CustomerUI {
    private Customer customer;
    private ProductService productService;
    private OrderService   orderService;
    private UserService    userService;
    private Scanner        sc;
    private Cart           cart;

    public CustomerUI(Customer customer, ProductService ps, OrderService os, UserService us, Scanner sc) {
        this.customer       = customer;
        this.productService = ps;
        this.orderService   = os;
        this.userService    = us;
        this.sc             = sc;
        this.cart           = new Cart(customer.getUserId());
    }

    public void run() {
        System.out.println("\nWelcome, " + customer.getUsername() + "!");
        boolean running = true;
        while (running) {
            customer.showMenu();
            System.out.print("Enter choice: ");
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1": browseProducts();   break;
                case "2": addToCart();        break;
                case "3": cart.display();     break;
                case "4": placeOrder();       break;
                case "5": viewMyOrders();     break;
                case "6": cancelOrder();      break;
                case "7": viewWallet();       break;
                case "8": updateProfile();    break;
                case "9": running = false; System.out.println("Goodbye, " + customer.getUsername() + "!"); break;
                default:  System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void browseProducts() {
        System.out.println("\nFilter by: 1. All  2. Laptops  3. Accessories");
        System.out.print("Choice: ");
        String f = sc.nextLine().trim();
        switch (f) {
            case "2": printProducts(productService.getByCategory("LAPTOP"));      break;
            case "3": printProducts(productService.getByCategory("ACCESSORY"));  break;
            default:  productService.displayAll();
        }
    }

    private void printProducts(List<Product> list) {
        if (list.isEmpty()) { System.out.println("No products found."); return; }
        System.out.printf("%n%-10s %-25s %-12s %-12s %s%n","ID","Name","Category","Price","Stock");
        System.out.println("-".repeat(70));
        for (Product p : list) p.display();
    }

    private void addToCart() {
        productService.displayAll();
        System.out.print("Enter Product ID to add: ");
        String pid = sc.nextLine().trim().toUpperCase();
        System.out.print("Quantity: ");
        int qty;
        try { qty = Integer.parseInt(sc.nextLine().trim()); }
        catch (NumberFormatException e) { System.out.println("[ERROR] Invalid quantity."); return; }

        try {
            Product p = productService.findById(pid);
            if (!p.isAvailable()) { System.out.println("[ERROR] Product is out of stock."); return; }
            if (qty > p.getStock()) {
                System.out.println("[ERROR] Only " + p.getStock() + " units available.");
                return;
            }
            cart.addItem(new OrderItem(p.getProductId(), p.getName(), qty, p.getPrice()));
            System.out.println("[SUCCESS] Added to cart: " + p.getName() + " x" + qty);
        } catch (ProductNotFoundException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    private void placeOrder() {
        if (cart.isEmpty()) { System.out.println("[ERROR] Your cart is empty."); return; }
        cart.display();
        System.out.printf("Total: $%.2f | Wallet: $%.2f%n", cart.getTotal(), customer.getWalletBalance());
        System.out.print("Confirm order? (yes/no): ");
        if (!sc.nextLine().trim().equalsIgnoreCase("yes")) { System.out.println("Order cancelled."); return; }

        try {
            Order order = orderService.placeOrder(customer, cart.getItems());
            cart.clear();
            userService.saveCustomers();
            System.out.println("[SUCCESS] Order placed! Order ID: " + order.getOrderId());
            System.out.printf("Remaining wallet balance: $%.2f%n", customer.getWalletBalance());
        } catch (InsufficientStockException | InsufficientBalanceException | ProductNotFoundException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    private void viewMyOrders() {
        List<Order> myOrders = orderService.getOrdersByCustomer(customer.getUserId());
        if (myOrders.isEmpty()) { System.out.println("You have no orders yet."); return; }
        for (Order o : myOrders) o.printDetails();
    }

    private void cancelOrder() {
        viewMyOrders();
        System.out.print("Enter Order ID to cancel: ");
        String oid = sc.nextLine().trim().toUpperCase();
        try {
            orderService.cancelOrder(oid, customer);
            userService.saveCustomers();
        } catch (OrderNotFoundException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    private void viewWallet() {
        System.out.printf("Your wallet balance: $%.2f%n", customer.getWalletBalance());
    }

    private void updateProfile() {
        System.out.println("1. Change Email  2. Change Address  3. Change Password");
        System.out.print("Choice: ");
        String c = sc.nextLine().trim();
        switch (c) {
            case "1":
                System.out.print("New email: ");
                customer.setEmail(sc.nextLine().trim());
                userService.saveCustomers();
                System.out.println("[SUCCESS] Email updated.");
                break;
            case "2":
                System.out.print("New address: ");
                customer.setAddress(sc.nextLine().trim());
                userService.saveCustomers();
                System.out.println("[SUCCESS] Address updated.");
                break;
            case "3":
                System.out.print("New password: ");
                customer.setPassword(sc.nextLine().trim());
                userService.saveCustomers();
                System.out.println("[SUCCESS] Password updated.");
                break;
            default: System.out.println("Invalid option.");
        }
    }
}
