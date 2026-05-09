package main.util;

import java.util.List;

/**
 * Utility class for generating unique IDs.
 * Demonstrates: Static utility methods
 */
public class IDGenerator {

    public static String nextOrderId(List<?> orders) {
        return String.format("ORD%04d", orders.size() + 1);
    }

    public static String nextCustomerId(List<?> customers) {
        return String.format("C%03d", customers.size() + 1);
    }

    public static String nextStaffId(List<?> staff) {
        return String.format("S%03d", staff.size() + 1);
    }

    public static String nextProductId(List<?> products) {
        return String.format("P%03d", products.size() + 1);
    }
}
