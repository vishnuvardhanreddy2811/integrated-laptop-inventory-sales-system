package main.model;

/**
 * Staff user — handles orders, updates product stock, views assigned tasks.
 * Demonstrates: Inheritance, Method Overriding
 */
public class Staff extends User {
    private String department;
    private String shift;  // MORNING / EVENING / NIGHT

    public Staff(String userId, String username, String password, String email, String department, String shift) {
        super(userId, username, password, email, "STAFF");
        this.department = department;
        this.shift = shift;
    }

    @Override
    public void showMenu() {
        System.out.println("\n=========== STAFF MENU ===========");
        System.out.println("1. View All Pending Orders");
        System.out.println("2. Process Order (Mark as Shipped)");
        System.out.println("3. Update Product Stock");
        System.out.println("4. View Product List");
        System.out.println("5. View My Profile");
        System.out.println("6. Logout");
        System.out.println("===================================");
    }

    public String getDepartment() { return department; }
    public String getShift()      { return shift; }
    public void setShift(String s){ this.shift = s; }

    @Override
    public String toString() {
        return super.toString() + "," + department + "," + shift;
    }
}
