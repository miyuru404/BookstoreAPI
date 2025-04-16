package model;

import java.util.List;

public class Cart {
    private Customer customer ;
    private List<Book> items;

    public Cart(Customer customer, List<Book> items) {
        this.customer = customer;
        this.items = items;
    }
}
