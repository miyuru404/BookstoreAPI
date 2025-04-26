package com.mycompany.bookstoreapi.model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private String email ;
    private List<Book> items  ;
    private double cartValue;

    public Cart(String email) {
        this.email=email;
        this.items = items = new ArrayList<>() ;


    }
    public String getCustomerEmail() {return email;}

    public List<Book> getItems() {return items;}

    public double getValueOfTheCart() { return cartValue(); }


    public void addItem(Book book) {
        items.add(book);
    }
    public void removeItem(Book book) {
        items.remove(book);
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
