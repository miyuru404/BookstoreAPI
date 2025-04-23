package com.demo.model;

import com.demo.dataModel.CartDataStore;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {

    private String orderId;
    private String customerEmail;
    private Cart cart;
    private Date orderDate;
    private double shippingCost;
    private String shippingAddress;
    private double totalAmount;
    private String status; // "Pending", "Confirmed", "Shipped", "Delivered"
    //private List<Book> books = new ArrayList<Book>();

    public Order() {}

    public Order(String customerEmail,  String shippingAddress) {
        this.orderId = generateUniqueOrderId();
        this.customerEmail = customerEmail;
        this.cart = CartDataStore.getCart(customerEmail);
        this.shippingCost = calculateShippingCost();
        this.totalAmount = calculateTotalAmount();
        this.shippingAddress = shippingAddress;
        this.status = "pending";
    }

    private static String generateUniqueOrderId() {
        int randomId = 100000 + (int)(Math.random() * 900000); // ensures 6 digits
        return String.valueOf(randomId);
    }
    private double calculateShippingCost() {
        return cart.getValueOfTheCart() * 0.05; // 5% shipping cost
    }
    private  double calculateTotalAmount() {
        return cart.getValueOfTheCart() + calculateShippingCost();
    }



    public String getOrderId() {
        return orderId;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public Cart getCart() {
        return cart;
    }



    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public double getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(double shippingCost) {
        this.shippingCost = shippingCost;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Optional: toString() for logging or debugging
    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", customerEmail='" + customerEmail + '\'' +
                ", cart=" + cart +
                ", orderDate=" + orderDate +
                ", shippingCost=" + shippingCost +
                ", shippingAddress='" + shippingAddress + '\'' +
                ", totalAmount=" + totalAmount +
                ", status='" + status + '\'' +
                '}';
    }
}
