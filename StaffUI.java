package main.ui;

import main.exception.OrderNotFoundException;
import main.exception.ProductNotFoundException;
import main.model.*;
import main.service.*;

import java.util.List;
import java.util.Scanner;

/**
 * Handles all Staff interactions via console.
 */
public class StaffUI {
    private Staff          staff;
    private ProductService productService;
    private OrderService   orderService;
    private Scanner        sc;

    public StaffUI(Staff staff, ProductService ps, OrderService os, Scanner sc) {
        this.staff          = staff;
        this.productService = ps;
        this.orderService   = os;
        this.sc             = sc;
    }

    public void run() {
        System.out.println("\nWelcome, Staff " + staff.getUsername() + " [" + staff.getDepartment() + "]!");
        boolean running = true;
        while (running) {
            staff.showMenu();
            System.out.print("Enter choice: ");
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1": viewPendingOrders();  break;
                case "2": processOrder();       break;
                case "3": updateStock();        break;
                case "4": productService.displayAll(); break;
                case "5": viewMyProfile();      break;
                case "6": running = false; System.out.println("Goodbye, " + staff.getUsername() + "!"); break;
                default:  System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void viewPendingOrders() {
        List<Order> pending = orderService.getPendingOrders();
        if (pending.isEmpty()) { System.out.println("No pending orders."); return; }
        System.out.println("\n--- Pending Orders ---");
        for (Order o : pending) o.printDetails();
    }

    private void processOrder() {
        viewPendingOrders();
        System.out.print("Enter Order ID to mark as SHIPPED: ");
        String oid = sc.nextLine().trim().toUpperCase();
        try {
            orderService.updateStatus(oid, Order.Status.SHIPPED);
        } catch (OrderNotFoundException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    private void updateStock() {
        productService.displayAll();
        System.out.print("Enter Product ID to restock: ");
        String pid = sc.nextLine().trim().toUpperCase();
        System.out.print("Quantity to add: ");
        try {
            int qty = Integer.parseInt(sc.nextLine().trim());
            productService.updateStock(pid, qty);
        } catch (NumberFormatException e) {
            System.out.println("[ERROR] Invalid quantity.");
        } catch (ProductNotFoundException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    private void viewMyProfile() {
        System.out.println("\n--- Staff Profile ---");
        System.out.println("ID         : " + staff.getUserId());
        System.out.println("Username   : " + staff.getUsername());
        System.out.println("Email      : " + staff.getEmail());
        System.out.println("Department : " + staff.getDepartment());
        System.out.println("Shift      : " + staff.getShift());
    }
}
