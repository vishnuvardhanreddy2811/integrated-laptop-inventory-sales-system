package main.util;

import main.model.*;

import java.io.*;
import java.util.*;

/**
 * Handles all file I/O for the application.
 * Demonstrates: File Handling, Exception Handling (IOException)
 */
public class FileHandler {

    // File paths
    private static final String DATA_DIR      = "data/";
    private static final String PRODUCTS_FILE = DATA_DIR + "products.txt";
    private static final String CUSTOMERS_FILE= DATA_DIR + "customers.txt";
    private static final String STAFF_FILE    = DATA_DIR + "staff.txt";
    private static final String ADMINS_FILE   = DATA_DIR + "admins.txt";
    private static final String ORDERS_FILE   = DATA_DIR + "orders.txt";

    // ===================== PRODUCT I/O =====================

    public static List<Product> loadProducts() {
        List<Product> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(PRODUCTS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] p = line.split(",");
                list.add(new Product(p[0], p[1], p[2], p[3], Double.parseDouble(p[4]), Integer.parseInt(p[5])));
            }
        } catch (FileNotFoundException e) {
            System.out.println("[INFO] products.txt not found. Starting fresh.");
        } catch (IOException e) {
            System.out.println("[ERROR] Reading products: " + e.getMessage());
        }
        return list;
    }

    public static void saveProducts(List<Product> products) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PRODUCTS_FILE))) {
            for (Product p : products) {
                bw.write(p.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("[ERROR] Saving products: " + e.getMessage());
        }
    }

    // ===================== CUSTOMER I/O =====================

    public static List<Customer> loadCustomers() {
        List<Customer> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(CUSTOMERS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] p = line.split(",");
                // userId,username,password,email,CUSTOMER,address,walletBalance
                list.add(new Customer(p[0], p[1], p[2], p[3], p[5], Double.parseDouble(p[6])));
            }
        } catch (FileNotFoundException e) {
            System.out.println("[INFO] customers.txt not found. Starting fresh.");
        } catch (IOException e) {
            System.out.println("[ERROR] Reading customers: " + e.getMessage());
        }
        return list;
    }

    public static void saveCustomers(List<Customer> customers) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CUSTOMERS_FILE))) {
            for (Customer c : customers) {
                bw.write(c.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("[ERROR] Saving customers: " + e.getMessage());
        }
    }

    // ===================== STAFF I/O =====================

    public static List<Staff> loadStaff() {
        List<Staff> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(STAFF_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] p = line.split(",");
                // userId,username,password,email,STAFF,department,shift
                list.add(new Staff(p[0], p[1], p[2], p[3], p[5], p[6]));
            }
        } catch (FileNotFoundException e) {
            System.out.println("[INFO] staff.txt not found. Starting fresh.");
        } catch (IOException e) {
            System.out.println("[ERROR] Reading staff: " + e.getMessage());
        }
        return list;
    }

    public static void saveStaff(List<Staff> staffList) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(STAFF_FILE))) {
            for (Staff s : staffList) {
                bw.write(s.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("[ERROR] Saving staff: " + e.getMessage());
        }
    }

    // ===================== ADMIN I/O =====================

    public static List<Admin> loadAdmins() {
        List<Admin> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ADMINS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] p = line.split(",");
                list.add(new Admin(p[0], p[1], p[2], p[3]));
            }
        } catch (FileNotFoundException e) {
            System.out.println("[INFO] admins.txt not found. Starting fresh.");
        } catch (IOException e) {
            System.out.println("[ERROR] Reading admins: " + e.getMessage());
        }
        return list;
    }

    public static void saveAdmins(List<Admin> admins) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ADMINS_FILE))) {
            for (Admin a : admins) {
                bw.write(a.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("[ERROR] Saving admins: " + e.getMessage());
        }
    }

    // ===================== ORDER I/O =====================

    public static List<Order> loadOrders() {
        List<Order> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ORDERS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] p = line.split(",", 7);
                // orderId,customerId,customerName,orderDate,status,totalAmount,items
                String orderId      = p[0];
                String customerId   = p[1];
                String customerName = p[2];
                String orderDate    = p[3];
                Order.Status status = Order.Status.valueOf(p[4]);
                double total        = Double.parseDouble(p[5]);
                List<OrderItem> items = new ArrayList<>();
                if (p.length > 6 && !p[6].trim().isEmpty()) {
                    for (String itemStr : p[6].split(";")) {
                        items.add(OrderItem.fromString(itemStr));
                    }
                }
                list.add(new Order(orderId, customerId, customerName, items, orderDate, status, total));
            }
        } catch (FileNotFoundException e) {
            System.out.println("[INFO] orders.txt not found. Starting fresh.");
        } catch (IOException e) {
            System.out.println("[ERROR] Reading orders: " + e.getMessage());
        }
        return list;
    }

    public static void saveOrders(List<Order> orders) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ORDERS_FILE))) {
            for (Order o : orders) {
                bw.write(o.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("[ERROR] Saving orders: " + e.getMessage());
        }
    }

    // ===================== DATA SEEDING =====================

    /**
     * Seeds initial data files if they don't exist.
     */
    public static void seedDataIfNeeded() {
        File dataDir = new File(DATA_DIR);
        if (!dataDir.exists()) dataDir.mkdirs();

        seedIfMissing(PRODUCTS_FILE,
            "P001,Dell XPS 13,LAPTOP,Intel i7 16GB RAM 512GB SSD,1299.99,10",
            "P002,HP Spectre x360,LAPTOP,Intel i5 8GB RAM 256GB SSD,999.99,8",
            "P003,Logitech MX Master 3,ACCESSORY,Wireless ergonomic mouse,89.99,25",
            "P004,Apple Magic Keyboard,ACCESSORY,Wireless keyboard for Mac,129.99,15",
            "P005,Samsung 27in Monitor,ACCESSORY,4K UHD IPS Display,399.99,12",
            "P006,ASUS ROG Zephyrus,LAPTOP,AMD Ryzen 9 32GB RAM 1TB SSD,1799.99,5",
            "P007,USB-C Hub 7-in-1,ACCESSORY,Multi-port USB-C hub,49.99,30",
            "P008,Lenovo ThinkPad E15,LAPTOP,Intel i5 16GB RAM 512GB SSD,849.99,7"
        );

        seedIfMissing(ADMINS_FILE,
            "A001,admin,admin123,admin@beforelightning.com,ADMIN"
        );

        seedIfMissing(STAFF_FILE,
            "S001,john_staff,staff123,john@beforelightning.com,STAFF,Warehouse,MORNING",
            "S002,sara_staff,staff456,sara@beforelightning.com,STAFF,Dispatch,EVENING"
        );

        seedIfMissing(CUSTOMERS_FILE,
            "C001,alice,alice123,alice@email.com,CUSTOMER,123 Main St,500.00",
            "C002,bob,bob123,bob@email.com,CUSTOMER,456 Oak Ave,1200.00"
        );

        seedIfMissing(ORDERS_FILE); // Empty initially
    }

    private static void seedIfMissing(String filePath, String... lines) {
        File f = new File(filePath);
        if (!f.exists()) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
                for (String line : lines) {
                    bw.write(line);
                    bw.newLine();
                }
            } catch (IOException e) {
                System.out.println("[ERROR] Seeding " + filePath + ": " + e.getMessage());
            }
        }
    }
}
