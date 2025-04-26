package com.mycompany.bookstoreapi.dataModel;





import com.mycompany.bookstoreapi.model.Cart;
import com.mycompany.bookstoreapi.model.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerDataStore {
    private static final List<Customer> customers = new ArrayList<Customer>();

    public static List<Customer> getCustomerList() {
        if (customers.isEmpty()) {
            System.out.println("Customers list is empty");
            return null;
        }
        return customers;
    }

    public static Customer getCustomer(String name,String email){
        if(customers.isEmpty()){
            System.out.println("no customer been added yet");
            return null;
        }
        for (Customer customer : customers) {
            if (customer.getName().equals(name) && customer.getEmail().equals(email)) {
                System.out.println("Customer found");
                return customer;
            }
        }
        System.out.println("Customer not found");
        return null;
    }

    public static Customer getCustomerByName(String name) {
        if(name == null || name.isEmpty()){
            System.out.println(" validate name is not provided");
            return null;
        }

        if(customers.isEmpty()){
            System.out.println("no customer been added yet");
            return null;
        }
        for(Customer customer: customers){
            if(customer.getName().equalsIgnoreCase(name)){
                System.out.println("customer found");
                return customer;
            }
        }

        System.out.println("customer not found");
        return null;
    }
    public static Customer getCustomerByEmail(String email) {
        if( email == null || email.isEmpty()){
            System.out.println(" validate email is not provided");
            return null;
        }

        if(customers.isEmpty()){
            System.out.println("no customer been added yet");
            return null;
        }
        for(Customer customer: customers){
            if( customer.getEmail().equalsIgnoreCase(email)){
                System.out.println("customer found");
                return customer;
            }
        }
        System.out.println("customer not found");
        return null;
    }

    public static void addCustomer(Customer customer) {
        int length = String.valueOf(customer.getPassword()).length();
        if(customer.getName() == null || customer.getName().isEmpty()
                || customer.getEmail() == null || customer.getEmail().isEmpty()
                || length<4){
            System.out.println("please enter valide details");
            return;
        }

        if(getCustomer(customer.getName(),customer.getEmail()) != null){
            System.out.println("customer is alrady in the system");
            return ;
        }
        customers.add(customer);
        Cart cart = new Cart(customer.getEmail());
        customer.setCart(cart);
        CartDataStore.addCart(cart);
        System.out.println("customer added");
    }
    public static void removeCustomer(String name ,String email) {
        Customer customer = getCustomer(name, email);
        if(customer != null){
            customers.remove(customer);
            System.out.println("customer deleted");
        }
        else {
            System.out.println("customer not found");
        }
    }
    public static void updateCustomer(String name,String email,Customer newCustomer) {
        int length = String.valueOf(newCustomer.getPassword()).length();
        if(newCustomer.getName() == null || newCustomer.getName().isEmpty()
                || newCustomer.getEmail() == null || newCustomer.getEmail().isEmpty()
                || length<4){
            System.out.println("please enter valide details");
            return;
        }

        Customer customer = getCustomer(name, email);
        if(customer != null){
            customer.setName(newCustomer.getName());
            customer.setEmail(newCustomer.getEmail());
            customer.setPassword(newCustomer.getPassword());
            System.out.println("Customer updated");
        }
    }


}
