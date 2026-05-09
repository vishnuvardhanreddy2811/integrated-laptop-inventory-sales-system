package main.service;

import main.exception.InsufficientStockException;
import main.exception.ProductNotFoundException;
import main.model.Product;
import main.util.FileHandler;
import main.util.IDGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class for product operations.
 * Demonstrates: Service layer, Exception Handling
 */
public class ProductService {
    private List<Product> products;

    public ProductService() {
        this.products = FileHandler.loadProducts();
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }

    public List<Product> getByCategory(String category) {
        List<Product> result = new ArrayList<>();
        for (Product p : products) {
            if (p.getCategory().equalsIgnoreCase(category)) result.add(p);
        }
        return result;
    }

    public Product findById(String productId) throws ProductNotFoundException {
        for (Product p : products) {
            if (p.getProductId().equals(productId)) return p;
        }
        throw new ProductNotFoundException(productId);
    }

    public void addProduct(String name, String category, String desc, double price, int stock) {
        String id = IDGenerator.nextProductId(products);
        products.add(new Product(id, name, category, desc, price, stock));
        FileHandler.saveProducts(products);
        System.out.println("[SUCCESS] Product added with ID: " + id);
    }

    public void updateProduct(String productId, double newPrice, int newStock, String newDesc)
            throws ProductNotFoundException {
        Product p = findById(productId);
        p.setPrice(newPrice);
        p.setStock(newStock);
        p.setDescription(newDesc);
        FileHandler.saveProducts(products);
        System.out.println("[SUCCESS] Product updated.");
    }

    public void removeProduct(String productId) throws ProductNotFoundException {
        Product p = findById(productId);
        products.remove(p);
        FileHandler.saveProducts(products);
        System.out.println("[SUCCESS] Product removed.");
    }

    public void updateStock(String productId, int qty) throws ProductNotFoundException {
        Product p = findById(productId);
        p.increaseStock(qty);
        FileHandler.saveProducts(products);
        System.out.println("[SUCCESS] Stock updated. New stock: " + p.getStock());
    }

    public void reserveStock(String productId, int qty)
            throws ProductNotFoundException, InsufficientStockException {
        Product p = findById(productId);
        if (p.getStock() < qty) {
            throw new InsufficientStockException(p.getName(), qty, p.getStock());
        }
        p.reduceStock(qty);
        FileHandler.saveProducts(products);
    }

    public void releaseStock(String productId, int qty) throws ProductNotFoundException {
        Product p = findById(productId);
        p.increaseStock(qty);
        FileHandler.saveProducts(products);
    }

    public void displayAll() {
        if (products.isEmpty()) {
            System.out.println("No products available.");
            return;
        }
        System.out.printf("%n%-10s %-25s %-12s %-12s %s%n", "ID", "Name", "Category", "Price", "Stock");
        System.out.println("-".repeat(70));
        for (Product p : products) p.display();
    }
}
