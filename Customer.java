package main.model;

/**
 * Customer user — can browse products, place orders, view order history.
 * Demonstrates: Inheritance, Method Overriding
 */
public class Customer extends User {
    private String address;
    private double walletBalance;

    public Customer(String userId, String username, String password, String email, String address, double walletBalance) {
        super(userId, username, password, email, "CUSTOMER");
        this.address = address;
        this.walletBalance = walletBalance;
    }

    @Override
    public void showMenu() {
        System.out.println("\n========== CUSTOMER MENU ==========");
        System.out.println("1. Browse Products");
        System.out.println("2. Add to Cart");
        System.out.println("3. View Cart");
        System.out.println("4. Place Order");
        System.out.println("5. View My Orders");
        System.out.println("6. Cancel Order");
        System.out.println("7. View Wallet Balance");
        System.out.println("8. Update Profile");
        System.out.println("9. Logout");
        System.out.println("===================================");
    }

    public String getAddress()               { return address; }
    public double getWalletBalance()         { return walletBalance; }
    public void setAddress(String a)         { this.address = a; }
    public void setWalletBalance(double b)   { this.walletBalance = b; }
    public void deductBalance(double amount) { this.walletBalance -= amount; }
    public void addBalance(double amount)    { this.walletBalance += amount; }

    @Override
    public String toString() {
        return super.toString() + "," + address + "," + walletBalance;
    }
}
