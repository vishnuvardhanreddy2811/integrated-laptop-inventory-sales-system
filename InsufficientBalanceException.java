package main.exception;

/** Thrown when a customer's wallet balance is insufficient to place an order. */
public class InsufficientBalanceException extends Exception {
    public InsufficientBalanceException(double required, double available) {
        super(String.format("Insufficient wallet balance. Required: $%.2f, Available: $%.2f", required, available));
    }
}
