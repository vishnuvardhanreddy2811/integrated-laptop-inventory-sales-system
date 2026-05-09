# Integrated Laptop Inventory & Sales System
### Java Console-Based Inventory & Sales Management System

---

## Project Overview
A Java console-based application developed to manage laptop inventory, customer orders, and sales operations.

The project demonstrates core Object-Oriented Programming concepts, modular architecture, file handling, and custom exception management.
---

## Project Structure

```
IntegratedLaptopInventorySystem/
├── src/
│   └── main/
│       ├── Main.java                          ← Entry point
│       ├── model/
│       │   ├── User.java                      ← Abstract base class
│       │   ├── Customer.java                  ← Extends User
│       │   ├── Staff.java                     ← Extends User
│       │   ├── Admin.java                     ← Extends User
│       │   ├── Product.java
│       │   ├── Order.java                     ← Contains OrderItems (Composition)
│       │   ├── OrderItem.java
│       │   └── Cart.java
│       ├── exception/
│       │   ├── InvalidCredentialsException.java
│       │   ├── InsufficientStockException.java
│       │   ├── InsufficientBalanceException.java
│       │   ├── ProductNotFoundException.java
│       │   └── OrderNotFoundException.java
│       ├── service/
│       │   ├── ProductService.java
│       │   ├── OrderService.java
│       │   └── UserService.java
│       ├── ui/
│       │   ├── CustomerUI.java
│       │   ├── StaffUI.java
│       │   └── AdminUI.java
│       └── util/
│           ├── FileHandler.java               ← All file I/O
│           └── IDGenerator.java
└── data/                                      ← Auto-created on first run
    ├── products.txt
    ├── customers.txt
    ├── staff.txt
    ├── admins.txt
    └── orders.txt
```

---

## OOP Concepts Demonstrated

| Concept | Where Used |
|---|---|
| **Abstraction** | `User` is abstract with `showMenu()` abstract method |
| **Inheritance** | `Customer`, `Staff`, `Admin` all extend `User` |
| **Polymorphism** | `userService.login()` returns `User`; routed by `instanceof` in `Main` |
| **Encapsulation** | All fields private with getters/setters in every model class |
| **Composition** | `Order` contains a `List<OrderItem>`; `Cart` contains `List<OrderItem>` |
| **Exception Handling** | 5 custom exceptions across order/auth/product operations |
| **File Handling** | `FileHandler.java` — BufferedReader/Writer for all persistence |
| **Enums** | `Order.Status` enum (PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED) |
| **Collections** | `ArrayList`, `List`, `Stream` used throughout |
| **Packages** | 5 packages: model, exception, service, ui, util |

---

## Technologies Used

- Java
- Object-Oriented Programming (OOP)
- File Handling
- Collections Framework
- Exception Handling
- Eclipse IDE / VS Code

## How to Run

1. Open the project in Eclipse or VS Code
2. Compile all Java source files
3. Run `Main.java`
4. Use the console menu to access customer, staff, and admin operations

### Compile Command

```bash
javac -d out src/main/Main.java src/main/model/*.java src/main/exception/*.java src/main/service/*.java src/main/ui/*.java src/main/util/*.java
```



- ### Run Command

```bash
java -cp out main.Main
```
