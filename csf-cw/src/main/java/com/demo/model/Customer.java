package com.demo.model;

public class Customer {
    private String name;
    private String email;
    private String password;
    private Cart cart;

    public Customer() {}
    public Customer(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.cart = new Cart(email);
    }
    public String getName() {return name;}
    public String getEmail() {return email;}
    public String getPassword() {return password;}

    public void setName(String name) {this.name = name;}
    public void setEmail(String email) {this.email = email;}
    public void setPassword(String password) {this.password = password;}

}
