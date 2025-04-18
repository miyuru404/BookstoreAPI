package com.demo.model;

public class Customer {
    private String name;
    private String email;
    private int password;

    public Customer() {}
    public Customer(String name, String email, int password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
    public String getName() {return name;}
    public String getEmail() {return email;}
    public int getPassword() {return password;}

    public void setName(String name) {this.name = name;}
    public void setEmail(String email) {this.email = email;}
    public void setPassword(int password) {this.password = password;}

}
