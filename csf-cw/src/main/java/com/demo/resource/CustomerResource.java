package com.demo.resource;

import com.demo.dataModel.CustomerDataStore;
import com.demo.exception.CustomerNotFoundException;
import com.demo.exception.InvalidInputException;
import com.demo.model.Customer;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/customer")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerResource {

    @GET
    @Path("/getName/{name}")
    public Response getCustomerByName(@PathParam("name") String name) {

        if (name == null || name.isEmpty()) {
            throw new InvalidInputException("Customer name is missing.");
        }

        if (!name.matches("^[A-Za-z ]+$")) {
            throw new InvalidInputException("Invalid name. Name should contain only letters and spaces.");
        }

        Customer customer = CustomerDataStore.getCustomerByName(name);

        if (customer == null) {
            throw new CustomerNotFoundException("Customer not found with the given name.");
        }

        return Response.ok(customer).build();
    }

    @GET
    @Path("/getEmail/{email}")
    public Response getCustomerByEmail(@PathParam("email") String email) {

        if (email == null || email.isEmpty()) {
            throw new InvalidInputException("Customer email is missing.");
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new InvalidInputException("Invalid email format.");
        }

        Customer customer = CustomerDataStore.getCustomerByEmail(email);

        if (customer == null) {
            throw new CustomerNotFoundException("Customer not found with the given email.");
        }

        return Response.ok(customer).build();
    }

    @POST
    @Path("/add")
    public Response addCustomer(Customer customer) {

        if (customer == null) {
            throw new InvalidInputException("Customer details are missing.");
        }

        if (customer.getName() == null || customer.getName().trim().isEmpty()) {
            throw new InvalidInputException("Name is required.");
        }

        if (!customer.getName().matches("^[A-Za-z ]+$")) {
            throw new InvalidInputException("Invalid name. Name should contain only letters and spaces.");
        }

        if (customer.getEmail() == null || customer.getEmail().trim().isEmpty()) {
            throw new InvalidInputException("Email is required.");
        }

        if (!customer.getEmail().matches("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$")) {
            throw new InvalidInputException("Email format is invalid.");
        }

        String passwordStr = String.valueOf(customer.getPassword());
        if (passwordStr.length() < 6) {
            throw new InvalidInputException("Password must be at least 6 characters long.");
        }

        if (CustomerDataStore.getCustomerByEmail(customer.getEmail()) != null) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("{\"error\":\"A customer with this email already exists.\"}")
                    .build();
        }

        CustomerDataStore.addCustomer(customer);
        return Response.status(Response.Status.CREATED)
                .entity("{\"message\":\"Customer successfully added.\"}")
                .build();
    }

    @PUT
    @Path("/update/{name}/{email}/{password}")
    public Response updateCustomer(
            @PathParam("name") String name,
            @PathParam("email") String email,
            @PathParam("password") String password,
            Customer updatedCustomer) {

        if (updatedCustomer == null) {
            throw new InvalidInputException("Updated customer object is missing.");
        }

        if (updatedCustomer.getName() == null || updatedCustomer.getName().trim().isEmpty()) {
            throw new InvalidInputException("New name is required.");
        }

        if (!updatedCustomer.getName().matches("^[A-Za-z ]+$")) {
            throw new InvalidInputException("Invalid name. Only letters and spaces allowed.");
        }

        if (updatedCustomer.getEmail() == null || updatedCustomer.getEmail().trim().isEmpty()) {
            throw new InvalidInputException("New email is required.");
        }

        if (!updatedCustomer.getEmail().matches("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$")) {
            throw new InvalidInputException("New email format is invalid.");
        }

        String passwordStr = String.valueOf(updatedCustomer.getPassword());
        if (passwordStr.length() < 6) {
            throw new InvalidInputException("Password must be at least 6 characters long.");
        }

        Customer existing = CustomerDataStore.getCustomer(name, email);
        if (existing == null) {
            throw new CustomerNotFoundException("Customer not found.");
        }

        if (!password.equals(existing.getPassword())) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"error\":\"Password does not match.\"}")
                    .build();
        }

        CustomerDataStore.updateCustomer(name, email, updatedCustomer);
        return Response.status(Response.Status.OK)
                .entity("{\"message\":\"Customer updated successfully.\"}")
                .build();
    }

    @DELETE
    @Path("/delete/{name}/{email}")
    public Response removeCustomer(@PathParam("name") String name, @PathParam("email") String email) {

        if (name == null || name.trim().isEmpty() || email == null || email.trim().isEmpty()) {
            throw new InvalidInputException("Name and email are required.");
        }

        Customer customer = CustomerDataStore.getCustomer(name, email);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer not found.");
        }

        CustomerDataStore.removeCustomer(name, email);
        return Response.status(Response.Status.OK)
                .entity("{\"message\":\"Customer removed successfully.\"}")
                .build();
    }
}