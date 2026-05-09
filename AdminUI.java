package main.ui;

import main.exception.OrderNotFoundException;
import main.exception.ProductNotFoundException;
import main.model.*;
import main.service.*;

import java.util.List;
import java.util.Scanner;

/**
 * Handles all Admin interactions via console.
 */
public class AdminUI {
    private Admin          admin;
    private ProductService productService;
    private OrderService   orderService;
    private UserService    userService;
    private Scanner        sc;

    public AdminUI(Admin admin, ProductService ps, OrderService os, UserService us, Scanner sc) {
        this.admin          = admin;
        this.productService = ps;
        this.orderService   = os;
        this.userService    = us;
        this.sc             = sc;
    }

    public void run() {
        System.out.println("\nWelcome, Admin " + admin.getUsername() + "!");
        boolean running = true;
        while (running) {
            admin.showMenu();
            System.out.print("Enter choice: ");
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1":  addProduct();        break;
                case "2":  updateProduct();     break;
                case "3":  removeProduct();     break;
                case "4":  productService.displayAll(); break;
                case "5":  viewAllOrders();     break;
                case "6":  updateOrderStatus(); break;
                case "7":  viewAllCustomers();  break;
                case "8":  viewAllStaff();      break;
                case "9":  addStaff();          break;
                case "10": removeStaff();       break;
                case "11": orderService.printSalesReport(); break;
                case "12": running = false; System.out.println("Goodbye, Admin!"); break;
                default:   System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void addProduct() {
        System.out.print("Product Name: ");     String name = sc.nextLine().trim();
        System.out.print("Category (LAPTOP/ACCESSORY): "); String cat = sc.nextLine().trim().toUpperCase();
        System.out.print("Description: ");      String desc = sc.nextLine().trim();
        try {
            System.out.print("Price: $");       double price = Double.parseDouble(sc.nextLine().trim());
            System.out.print("Stock quantity: "); int stock = Integer.parseInt(sc.nextLine().trim());
            productService.addProduct(name, cat, desc, price, stock);
        } catch (NumberFormatException e) {
            System.out.println("[ERROR] Invalid number input.");
        }
    }

    private void updateProduct() {
        productService.displayAll();
        System.out.print("Enter Product ID to update: ");
        String pid = sc.nextLine().trim().toUpperCase();
        try {
            System.out.print("New Price: $");    double price = Double.parseDouble(sc.nextLine().trim());
            System.out.print("New Stock: ");      int stock   = Integer.parseInt(sc.nextLine().trim());
            System.out.print("New Description: ");String desc = sc.nextLine().trim();
            productService.updateProduct(pid, price, stock, desc);
        } catch (NumberFormatException e) {
            System.out.println("[ERROR] Invalid input.");
        } catch (ProductNotFoundException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    private void removeProduct() {
        productService.displayAll();
        System.out.print("Enter Product ID to remove: ");
        String pid = sc.nextLine().trim().toUpperCase();
        try { productService.removeProduct(pid); }
        catch (ProductNotFoundException e) { System.out.println("[ERROR] " + e.getMessage()); }
    }

    private void viewAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        if (orders.isEmpty()) { System.out.println("No orders in system."); return; }
        for (Order o : orders) o.printDetails();
    }

    private void updateOrderStatus() {
        viewAllOrders();
        System.out.print("Enter Order ID: ");
        String oid = sc.nextLine().trim().toUpperCase();
        System.out.println("Statuses: PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED");
        System.out.print("New Status: ");
        String statusStr = sc.nextLine().trim().toUpperCase();
        try {
            Order.Status newStatus = Order.Status.valueOf(statusStr);
            orderService.updateStatus(oid, newStatus);
        } catch (IllegalArgumentException e) {
            System.out.println("[ERROR] Invalid status value.");
        } catch (OrderNotFoundException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    private void viewAllCustomers() {
        List<Customer> customers = userService.getAllCustomers();
        System.out.printf("%n%-8s %-15s %-25s %-10s%n", "ID", "Username", "Email", "Wallet");
        System.out.println("-".repeat(60));
        for (Customer c : customers)
            System.out.printf("%-8s %-15s %-25s $%-10.2f%n",
                    c.getUserId(), c.getUsername(), c.getEmail(), c.getWalletBalance());
    }

    private void viewAllStaff() {
        List<Staff> staffList = userService.getAllStaff();
        System.out.printf("%n%-8s %-15s %-25s %-12s %s%n", "ID", "Username", "Email", "Department", "Shift");
        System.out.println("-".repeat(70));
        for (Staff s : staffList)
            System.out.printf("%-8s %-15s %-25s %-12s %s%n",
                    s.getUserId(), s.getUsername(), s.getEmail(), s.getDepartment(), s.getShift());
    }

    private void addStaff() {
        System.out.print("Username: ");     String uname = sc.nextLine().trim();
        System.out.print("Password: ");     String pass  = sc.nextLine().trim();
        System.out.print("Email: ");        String email = sc.nextLine().trim();
        System.out.print("Department: ");   String dept  = sc.nextLine().trim();
        System.out.print("Shift (MORNING/EVENING/NIGHT): "); String shift = sc.nextLine().trim().toUpperCase();
        try { userService.addStaff(uname, pass, email, dept, shift); }
        catch (Exception e) { System.out.println("[ERROR] " + e.getMessage()); }
    }

    private void removeStaff() {
        viewAllStaff();
        System.out.print("Enter Staff ID to remove: ");
        String sid = sc.nextLine().trim().toUpperCase();
        try { userService.removeStaff(sid); }
        catch (Exception e) { System.out.println("[ERROR] " + e.getMessage()); }
    }
}
