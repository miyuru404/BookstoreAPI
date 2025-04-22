package com.demo.resource;

import com.demo.dataModel.CustomerDataStore;
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

        if(name == null || name.isEmpty() ) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\":\"Customer name is missing\"}").build();
        }
        // Validate name: only letters and spaces
        if (!name.matches("^[A-Za-z ]+$")) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Invalid name. Name should contain only letters and spaces.\"}")
                    .build();
        }


        Customer customer = CustomerDataStore.getCustomerByName(name);

        if (customer == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Customer not found\"}").build();
        }

        return Response.ok(customer).build();
    }

    @GET
    @Path("/getEmail/{email}")
    public Response getCustomerByEmail( @PathParam("email") String email) {

        if( email == null || email.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\":\"Customer details is missing\"}").build();
        }

        // Validate email format
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Invalid email format.\"}")
                    .build();
        }


        Customer customer = CustomerDataStore.getCustomerByEmail(email);

        if (customer == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Customer not found\"}").build();
        }

        return Response.ok(customer).build();
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCustomer(Customer customer) {

        if (customer == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Customer details is missing.")
                    .build();
        }

        if (customer.getName() == null || customer.getName().trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Name is required.")
                    .build();
        }
        // Validate name: only letters and spaces
        if (!customer.getName().matches("^[A-Za-z ]+$")) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Invalid name. Name should contain only letters and spaces.\"}")
                    .build();
        }

        if (customer.getEmail() == null || customer.getEmail().trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Email is required.")
                    .build();
        }

        if (!customer.getEmail().matches("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$")) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Email format is invalid.")
                    .build();
        }

        // Convert int password to String for length check
        String passwordStr = String.valueOf(customer.getPassword());

        if (passwordStr.length() < 6) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Password must be at least 6 digits long.")
                    .build();
        }


        // Check if customer already exists
        if (CustomerDataStore.getCustomerByEmail(customer.getEmail()) != null) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("A customer with this email already exists.")
                    .build();
        }

        // Add customer
        CustomerDataStore.addCustomer(customer);

        return Response.status(Response.Status.CREATED)
                .entity("Customer successfully added.")
                .build();
    }
    @PUT
    @Path("/update/{name}/{email}/{password}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCustomer(
            @PathParam("name") String name,
            @PathParam("email") String email,
            @PathParam("password") String password,
            Customer updatedCustomer) {

        // Validate that updatedCustomer object is not null
        if (updatedCustomer == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Updated customer object is missing.")
                    .build();
        }

        // Check if the updated name is provided and valid
        if (updatedCustomer.getName() == null || updatedCustomer.getName().trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("New name is required.")
                    .build();
        }

        // Validate the name format (only letters and spaces)
        if (!updatedCustomer.getName().matches("^[A-Za-z ]+$")) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid name. Only letters and spaces allowed.")
                    .build();
        }

        // Check if the updated email is provided and valid
        if (updatedCustomer.getEmail() == null || updatedCustomer.getEmail().trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("New email is required.")
                    .build();
        }

        // Validate the email format
        if (!updatedCustomer.getEmail().matches("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$")) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("New email format is invalid.")
                    .build();
        }

        // Check if password is at least 6 characters long
        String passwordStr = String.valueOf(updatedCustomer.getPassword());
        if (passwordStr.length() < 6) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Password must be at least 6 characters long.")
                    .build();
        }

        // Fetch the existing customer from the database using the name and email
        Customer existing = CustomerDataStore.getCustomer(name, email);
        if (existing == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Customer not found.")
                    .build();
        }

        // Check if the provided password matches the existing customer's password
        if (password.equals(existing.getPassword())) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Password does not match.")
                    .build();
        }

        // Update the customer details in the database
        CustomerDataStore.updateCustomer(name, email, updatedCustomer);

        // Respond with success message
        return Response.status(Response.Status.OK)
                .entity("Customer updated successfully.")
                .build();
    }

    @DELETE
    @Path("/delete/{name}/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeCustomer(@PathParam("name") String name, @PathParam("email") String email) {
        if (name == null || name.trim().isEmpty() || email == null || email.trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Name and email are required.\"}")
                    .build();
        }

        Customer customer = CustomerDataStore.getCustomer(name, email);
        if (customer == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Customer not found.\"}")
                    .build();
        }

        CustomerDataStore.removeCustomer(name, email);
        return Response.status(Response.Status.OK)
                .entity("{\"message\":\"Customer removed successfully.\"}")
                .build();
    }





}

