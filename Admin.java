package main.model;

/**
 * Admin/Manager user — full system control: manage users, products, orders, reports.
 * Demonstrates: Inheritance, Method Overriding
 */
public class Admin extends User {

    public Admin(String userId, String username, String password, String email) {
        super(userId, username, password, email, "ADMIN");
    }

    @Override
    public void showMenu() {
        System.out.println("\n=========== ADMIN MENU ===========");
        System.out.println("--- Product Management ---");
        System.out.println("1. Add Product");
        System.out.println("2. Update Product");
        System.out.println("3. Remove Product");
        System.out.println("4. View All Products");
        System.out.println("--- Order Management ---");
        System.out.println("5. View All Orders");
        System.out.println("6. Update Order Status");
        System.out.println("--- User Management ---");
        System.out.println("7. View All Customers");
        System.out.println("8. View All Staff");
        System.out.println("9. Add Staff Member");
        System.out.println("10. Remove Staff Member");
        System.out.println("--- Reports ---");
        System.out.println("11. Sales Report");
        System.out.println("12. Logout");
        System.out.println("===================================");
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
