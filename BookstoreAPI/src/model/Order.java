package model;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private int orderId;
    private Customer customer;
    private List<Cart> items;
    private LocalDateTime orderDate;
    private double totalAmount;

    public Order(int orderId, Customer customer, List<Cart> items, LocalDateTime orderDate) {
        this.orderId = orderId;
        this.customer = customer;
        this.items = items;
        this.orderDate = orderDate;
        this.totalAmount = 0;
    }
    public int getOrderId() {return orderId;}
    public Customer getCustomerId() {return customer;}
    public List<Cart> getItems() {return items;}
    public LocalDateTime getOrderDate() {return orderDate;}
    public double getTotalAmount() {return totalAmount;}

    public void setOrderId(int orderId) {this.orderId = orderId;}
    public void setCustomerId(Customer customer) {this.customer = customer;}
    public void setItems(List<Cart> items) {this.items = items;}
    //public void setOrderDate(LocalDateTime orderDate) {this.orderDate = orderDate;}
    public void setTotalAmount(double totalAmount) {this.totalAmount = totalAmount;}
}
