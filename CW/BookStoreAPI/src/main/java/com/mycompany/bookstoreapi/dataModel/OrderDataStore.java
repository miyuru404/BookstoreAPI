package com.mycompany.bookstoreapi.dataModel;



import com.mycompany.bookstoreapi.model.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderDataStore {

    private static final List<Order> orders = new ArrayList<>();

    // Add a new order
    public static void addOrder(Order order) {
        orders.add(order);

    }

    // Get all orders
    public static List<Order> getAllOrders() {
        return new ArrayList<>(orders);
    }


    // Get orders by customer email
    public static Order getOrdersByCustomerEmail(String email) {

        for (Order order : orders) {
            if (order.getCustomerEmail().equals(email)) {
                return order;
            }
        }

        return null;
    }

    // Get a specific order by ID
    public static Order getOrderById(String orderId) {
        for (Order order : orders) {
            if (order.getOrderId().equalsIgnoreCase(orderId)) {
                return order;
            }
        }
        return null;
    }

    // Update the status of an order
    public static boolean updateOrderStatus(String orderId, String newStatus) {
        Order order = getOrderById(orderId);
        if (order != null) {
            order.setStatus(newStatus);
            return true;
        }
        return false;
    }

    // Optional: Remove an order
    public static boolean removeOrder(String orderId) {
        return orders.removeIf(order -> order.getOrderId().equalsIgnoreCase(orderId));
    }

    // Optional: Clear all orders (for testing)
    public static void clearAllOrders() {
        orders.clear();
    }
}
