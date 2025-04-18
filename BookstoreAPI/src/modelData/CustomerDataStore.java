package modelData;


import model.Author;
import model.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerDataStore {
    private static final List<Customer> customers = new ArrayList<Customer>();

    public static Customer getCustomer(String name,String email) {
        if(name == null || name.isEmpty() || email == null || email.isEmpty()){
            System.out.println("provide validate details");
            return null;
        }

        if(customers.isEmpty()){
            System.out.println("no customer been added yet");
            return null;
        }
        for(Customer customer: customers){
            if(customer.getName().equalsIgnoreCase(name) && customer.getEmail().equalsIgnoreCase(email)){
                System.out.println("customer found");
                return customer;
            }
        }

        System.out.println("customer not found");
        return null;
    }

    public static void addCustomer(String name , String email ,int psword) {
        int length = String.valueOf(psword).length();
        if(name == null || name.isEmpty() || email == null || email.isEmpty() || length<4){
            System.out.println("please enter valide details");
            return;
        }

        if(getCustomer(name,email) != null){
            System.out.println("customer is alrady in the system");
            return ;
        }
        customers.add(new Customer(name,email,psword));
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
    public static void updateCustomer(String name,String email,String newName,String newEmail,int newPsword) {
        int length = String.valueOf(newPsword).length();
        if(newName == null || newName.isEmpty() || newEmail == null || newEmail.isEmpty() || length<4){
            System.out.println("provide valide details");
            return ;
        }

        Customer customer = getCustomer(name, email);
        if(customer != null){
            customer.setName(newName);
            customer.setEmail(newEmail);
            customer.setPassword(newPsword);
            System.out.println("Author updated");
        }
    }
}
