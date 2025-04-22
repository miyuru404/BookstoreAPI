package com.demo.dataModel;



import com.demo.model.Cart;
import com.demo.model.Customer;

import java.util.ArrayList;
import java.util.List;

public class CartDataStore {

    private static List<Cart> carts;

    public  CartDataStore() {
        carts = new ArrayList<>();
    }

    public static void addCart(Cart cart) {
        carts.add(cart);
    }

    public static Cart getCartByCustomer(Customer customer) {
        for (Cart cart : carts) {
            if (cart.getCustomer().equals(customer)) {
                return cart;
            }
        }
        return null;
    }

    public static void removeCartByCustomer(Customer customer) {
        carts.removeIf(cart -> cart.getCustomer().equals(customer));
    }

    public static List<Cart> getAllCarts() {
        return carts;
    }

    public static void clearAllCarts() {
        carts.clear();
    }
}

