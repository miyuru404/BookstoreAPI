package model;

import java.util.List;

public class Cart {
    private Customer customer ;
    private List<Book> items;
    private int valueOfTheCart;

    public Cart(Customer customer, List<Book> items) {
        this.customer = customer;
        this.items = items;
    }
    public Customer getCustomer() {return customer;}
    public List<Book> getItems() {return items;}
    public int getValueOfTheCart() { return valueOfTheCart; }

    public void setValueOfTheCart(int valueOfTheCart) { this.valueOfTheCart = valueOfTheCart; }
    public double calculateTotal() {
        double total = 0.0;
        for (Book book : items) {
            total += book.getPrice();
        }
        return total;
    }
    public void clearCart(){items.clear();}


}
