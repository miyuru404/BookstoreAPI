package com.demo.model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private Customer customer ;
    private List<Book> items;
    private double cartValue;

    public Cart(Customer customer) {
        this.customer = customer;
        this.items = new ArrayList<>();
    }
    public Customer getCustomer() {return customer;}

    public List<Book> getItems() {return items;}

    public double getValueOfTheCart() { return cartValue(); }

    public void setCustomer(Customer customer) { this.customer = customer; }

    public void addItem(Book book) {
        items.add(book);
    }

    public double cartValue() {
        cartValue = 0; // reset before calculating
        for (Book book : items) {
            cartValue += book.getPrice();
        }
        return cartValue;
    }

    public void clearCart(){items.clear();}


}
