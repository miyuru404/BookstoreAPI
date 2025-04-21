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
    @Path("/name/{name}")
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
    @Path("/email/{email}")
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
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCustomer(@QueryParam("name") String name,
                                   @QueryParam("email") String email,
                                   Customer updatedCustomer) {
        if (updatedCustomer == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Updated customer object is missing.")
                    .build();
        }

        if (updatedCustomer.getName() == null || updatedCustomer.getName().trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("New name is required.")
                    .build();
        }

        if (!updatedCustomer.getName().matches("^[A-Za-z ]+$")) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid name. Only letters and spaces allowed.")
                    .build();
        }

        if (updatedCustomer.getEmail() == null || updatedCustomer.getEmail().trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("New email is required.")
                    .build();
        }

        if (!updatedCustomer.getEmail().matches("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$")) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("New email format is invalid.")
                    .build();
        }

        String passwordStr = String.valueOf(updatedCustomer.getPassword());
        if (passwordStr.length() < 6) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Password must be at least 6 digits long.")
                    .build();
        }

        Customer existing = CustomerDataStore.getCustomer(name, email);
        if (existing == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Original customer not found.")
                    .build();
        }

        CustomerDataStore.updateCustomer(name, email, updatedCustomer);
        return Response.status(Response.Status.OK)
                .entity("Customer updated successfully.")
                .build();
    }
    @DELETE
    @Path("/delete")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeCustomer(@QueryParam("name") String name, @QueryParam("email") String email) {
        if (name == null || name.trim().isEmpty() || email == null || email.trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Name and email are required.")
                    .build();
        }

        Customer customer = CustomerDataStore.getCustomer(name, email);
        if (customer == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Customer not found.")
                    .build();
        }

        CustomerDataStore.removeCustomer(name, email);
        return Response.status(Response.Status.OK)
                .entity("Customer removed successfully.")
                .build();
    }



}

