package com.demo.dataModel;



import com.demo.model.Cart;
import com.demo.model.Customer;

import java.util.ArrayList;
import java.util.List;

public class CartDataStore {

    private static final List<Cart> carts = new ArrayList<>();

    public  CartDataStore() {

    }

    public static void addCart(Cart cart) {
        carts.add(cart);
    }

    public static Cart getCart(String email) {
        for (Cart cart : carts) {
            if (cart.getCustomerEmail().equals(email)) {
                return cart;
            }
        }
        return null;
    }

    public static void removeCart(String email) {
        for (Cart cart : carts) {
            if (cart.getCustomerEmail().equals(email)) {
                carts.remove(cart);
            }
        }
    }

    public static List<Cart> getAllCarts() {
        return carts;
    }

    public static void clearAllCarts() {
        carts.clear();
    }
}

