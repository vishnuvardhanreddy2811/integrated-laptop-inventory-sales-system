package main.exception;

/** Thrown when a product ID is not found in the catalog. */
public class ProductNotFoundException extends Exception {
    public ProductNotFoundException(String productId) {
        super("Product with ID '" + productId + "' not found.");
    }
}
