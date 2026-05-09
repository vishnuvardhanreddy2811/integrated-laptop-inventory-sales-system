package main.exception;

/** Thrown when a product is out of stock or quantity exceeds available stock. */
public class InsufficientStockException extends Exception {
    public InsufficientStockException(String productName, int requested, int available) {
        super("Insufficient stock for '" + productName + "'. Requested: " + requested + ", Available: " + available);
    }
}
