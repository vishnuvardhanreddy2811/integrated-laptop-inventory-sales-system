# Before Lightning - Laptop & Accessories Store
### OOP Case Study | Java Console Application | Semester 2 B.Tech CSE

---

## Project Overview
A Java console-based enterprise system simulating an online laptop and accessories store.
Three distinct user roles — **Customer**, **Staff**, and **Admin** — each with their own
use cases. Built using core OOP principles, custom exception handling, and file-based persistence.

---

## Project Structure

```
BeforeLightning/
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

## Default Login Credentials

| Role     | Username     | Password   |
|----------|-------------|------------|
| Admin    | `admin`     | `admin123` |
| Staff    | `john_staff`| `staff123` |
| Staff    | `sara_staff`| `staff456` |
| Customer | `alice`     | `alice123` |
| Customer | `bob`       | `bob123`   |

> New customers can register from the main menu. They receive a $1000 wallet by default.

---

## How to Run in VS Code

### Step 1 — Install Required Software
1. Install [Java JDK 17+](https://adoptium.net/) — download and run the installer
2. Install [VS Code](https://code.visualstudio.com/)
3. Open VS Code → Go to **Extensions** (Ctrl+Shift+X) → Search and install:
   - **"Extension Pack for Java"** by Microsoft (installs everything needed)

### Step 2 — Open the Project
1. Open VS Code
2. Click **File → Open Folder**
3. Select the `BeforeLightning` folder

### Step 3 — Run the Project
**Method A — Using VS Code Run Button (Easiest)**
1. Open `src/main/Main.java`
2. You'll see a **▷ Run** button above the `main` method — click it
3. The program runs in the integrated terminal

**Method B — Using the Terminal**
1. Open terminal in VS Code: **Terminal → New Terminal**
2. Run these commands:
```bash
# From inside the BeforeLightning folder:

# Compile all files
javac -d out src/main/Main.java src/main/model/*.java src/main/exception/*.java src/main/service/*.java src/main/ui/*.java src/main/util/*.java

# Run the application
java -cp out main.Main
```

### Step 4 — VS Code Settings (if needed)
If VS Code doesn't recognize the packages, add a file `.vscode/settings.json`:
```json
{
  "java.project.sourcePaths": ["src"],
  "java.project.outputPath": "out"
}
```

---

## User Use Cases

### Customer
- Register / Login
- Browse all products or filter by Laptops / Accessories
- Add products to cart, view and clear cart
- Place order (deducted from wallet balance)
- View order history
- Cancel pending/confirmed orders (automatic refund)
- Update profile (email, address, password)

### Staff
- Login
- View all CONFIRMED (pending) orders
- Mark orders as SHIPPED
- Restock product inventory
- View product catalog
- View their own profile

### Admin / Manager
- Login
- **Product Management**: Add, update, remove, view all products
- **Order Management**: View all orders, update any order status
- **User Management**: View all customers and staff, add/remove staff members
- **Reports**: Sales report (total revenue, order counts)

---

## File Format Reference

**products.txt** — `productId,name,category,description,price,stock`
**customers.txt** — `userId,username,password,email,CUSTOMER,address,walletBalance`
**staff.txt** — `userId,username,password,email,STAFF,department,shift`
**admins.txt** — `userId,username,password,email,ADMIN`
**orders.txt** — `orderId,customerId,customerName,date,status,total,items`

> All data files are auto-created with sample data on the first run.

---

## Evaluation Components Mapping

1. **OOP Concepts (2 marks)** — See table above; Abstraction, Inheritance, Polymorphism,
   Encapsulation, Composition all clearly demonstrated

2. **Class Diagram & Sequence Diagrams (2 marks)** — Key classes: `User → Customer/Staff/Admin`,
   `Product`, `Order → OrderItem`, `Cart`. Sequence flows: Login, Place Order, Cancel Order,
   Process Shipment (Staff), Add Product (Admin)

3. **Execution & Output (2 marks)** — Run using VS Code steps above; all 3 user types fully
   functional with persistent file storage

4. **Test Cases (2 marks)** — See test scenarios below

---

## 5 Test Case Scenarios

| # | Scenario | Steps | Expected Output |
|---|----------|-------|----------------|
| 1 | **Login with wrong password** | Enter username `alice`, password `wrongpass` | `InvalidCredentialsException`: "Invalid username or password" |
| 2 | **Add out-of-stock item to cart** | Admin sets product stock to 0; Customer tries to add it | "Product is out of stock" error message |
| 3 | **Place order with insufficient wallet** | Customer with $10 wallet tries to buy $1299 laptop | `InsufficientBalanceException`: shows required vs available amount |
| 4 | **Cancel a shipped order** | Staff marks order as SHIPPED; Customer tries to cancel it | "Cannot cancel an order that is already SHIPPED" |
| 5 | **Cancel valid order — verify refund** | Customer places order, then cancels it | Status → CANCELLED; wallet balance restored; stock restored |
