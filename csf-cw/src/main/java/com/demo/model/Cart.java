package com.demo.model;

import java.util.List;

public class Cart {
    private Customer customer ;
    private List<Book> items;
    private double cartValue;

    public Cart(Customer customer, List<Book> items) {
        this.customer = customer;
        this.items = items;
    }
    public Customer getCustomer() {return customer;}
    public List<Book> getItems() {return items;}
    public double getValueOfTheCart() { return cartValue; }

    public double cartValue() {
        for (Book book : items) {
            cartValue += book.getPrice();
        }
        return cartValue;
    }
    public void clearCart(){items.clear();}


}
