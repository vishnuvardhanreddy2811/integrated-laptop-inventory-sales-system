package main.service;

import main.exception.InvalidCredentialsException;
import main.model.*;
import main.util.FileHandler;
import main.util.IDGenerator;

import java.util.List;

/**
 * Service for authentication and user management.
 * Demonstrates: Polymorphism (returns User base type), Exception Handling
 */
public class UserService {
    private List<Customer> customers;
    private List<Staff>    staffList;
    private List<Admin>    admins;

    public UserService() {
        customers = FileHandler.loadCustomers();
        staffList = FileHandler.loadStaff();
        admins    = FileHandler.loadAdmins();
    }

    /**
     * Authenticates any user type — demonstrates Polymorphism.
     */
    public User login(String username, String password) throws InvalidCredentialsException {
        for (Admin a : admins)
            if (a.getUsername().equals(username) && a.getPassword().equals(password)) return a;
        for (Staff s : staffList)
            if (s.getUsername().equals(username) && s.getPassword().equals(password)) return s;
        for (Customer c : customers)
            if (c.getUsername().equals(username) && c.getPassword().equals(password)) return c;
        throw new InvalidCredentialsException();
    }

    public Customer registerCustomer(String username, String password, String email, String address)
            throws Exception {
        for (Customer c : customers)
            if (c.getUsername().equalsIgnoreCase(username))
                throw new Exception("Username '" + username + "' is already taken.");
        String id = IDGenerator.nextCustomerId(customers);
        Customer newCust = new Customer(id, username, password, email, address, 1000.00); // default $1000 wallet
        customers.add(newCust);
        FileHandler.saveCustomers(customers);
        System.out.println("[SUCCESS] Account created! Your ID: " + id + " | Starting wallet: $1000.00");
        return newCust;
    }

    public Staff addStaff(String username, String password, String email, String dept, String shift)
            throws Exception {
        for (Staff s : staffList)
            if (s.getUsername().equalsIgnoreCase(username))
                throw new Exception("Staff username '" + username + "' already exists.");
        String id = IDGenerator.nextStaffId(staffList);
        Staff s = new Staff(id, username, password, email, dept, shift);
        staffList.add(s);
        FileHandler.saveStaff(staffList);
        System.out.println("[SUCCESS] Staff added with ID: " + id);
        return s;
    }

    public void removeStaff(String staffId) throws Exception {
        Staff toRemove = null;
        for (Staff s : staffList)
            if (s.getUserId().equals(staffId)) { toRemove = s; break; }
        if (toRemove == null) throw new Exception("Staff ID '" + staffId + "' not found.");
        staffList.remove(toRemove);
        FileHandler.saveStaff(staffList);
        System.out.println("[SUCCESS] Staff member removed.");
    }

    public Customer findCustomerById(String id) throws Exception {
        for (Customer c : customers)
            if (c.getUserId().equals(id)) return c;
        throw new Exception("Customer not found: " + id);
    }

    public void saveCustomers() { FileHandler.saveCustomers(customers); }

    public List<Customer> getAllCustomers() { return customers; }
    public List<Staff>    getAllStaff()     { return staffList; }
    public List<Admin>    getAllAdmins()    { return admins; }
}
