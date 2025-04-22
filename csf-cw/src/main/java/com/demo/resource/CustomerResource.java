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

        if (name == null || name.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Customer name is missing\"}")
                    .build();
        }

        if (!name.matches("^[A-Za-z ]+$")) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Invalid name. Name should contain only letters and spaces.\"}")
                    .build();
        }

        Customer customer = CustomerDataStore.getCustomerByName(name);

        if (customer == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Customer not found\"}")
                    .build();
        }

        return Response.ok(customer).build();
    }

    @GET
    @Path("/getEmail/{email}")
    public Response getCustomerByEmail(@PathParam("email") String email) {

        if (email == null || email.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Customer details is missing\"}")
                    .build();
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Invalid email format.\"}")
                    .build();
        }

        Customer customer = CustomerDataStore.getCustomerByEmail(email);

        if (customer == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Customer not found\"}")
                    .build();
        }

        return Response.ok(customer).build();
    }

    @POST
    @Path("/add")
    public Response addCustomer(Customer customer) {

        if (customer == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Customer details is missing.\"}")
                    .build();
        }

        if (customer.getName() == null || customer.getName().trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Name is required.\"}")
                    .build();
        }

        if (!customer.getName().matches("^[A-Za-z ]+$")) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Invalid name. Name should contain only letters and spaces.\"}")
                    .build();
        }

        if (customer.getEmail() == null || customer.getEmail().trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Email is required.\"}")
                    .build();
        }

        if (!customer.getEmail().matches("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$")) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Email format is invalid.\"}")
                    .build();
        }

        String passwordStr = String.valueOf(customer.getPassword());
        if (passwordStr.length() < 6) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Password must be at least 6 digits long.\"}")
                    .build();
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
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Updated customer object is missing.\"}")
                    .build();
        }

        if (updatedCustomer.getName() == null || updatedCustomer.getName().trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"New name is required.\"}")
                    .build();
        }

        if (!updatedCustomer.getName().matches("^[A-Za-z ]+$")) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Invalid name. Only letters and spaces allowed.\"}")
                    .build();
        }

        if (updatedCustomer.getEmail() == null || updatedCustomer.getEmail().trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"New email is required.\"}")
                    .build();
        }

        if (!updatedCustomer.getEmail().matches("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$")) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"New email format is invalid.\"}")
                    .build();
        }

        String passwordStr = String.valueOf(updatedCustomer.getPassword());
        if (passwordStr.length() < 6) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Password must be at least 6 characters long.\"}")
                    .build();
        }

        Customer existing = CustomerDataStore.getCustomer(name, email);
        if (existing == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Customer not found.\"}")
                    .build();
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
