package main.exception;

/** Thrown when an order ID does not exist in the system. */
public class OrderNotFoundException extends Exception {
    public OrderNotFoundException(String orderId) {
        super("Order with ID '" + orderId + "' not found.");
    }
}
