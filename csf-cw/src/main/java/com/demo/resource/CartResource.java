package com.demo.resource;

import com.demo.dataModel.BookDataStore;
import com.demo.dataModel.CartDataStore;
import com.demo.dataModel.CustomerDataStore;
import com.demo.model.*;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/carts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CartResource {

    @POST
    @Path("/addCart/{name}/{email}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCart(@PathParam("name") String name, @PathParam("email") String email) {
        if (name == null || name.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Customer name is missing\"}").build();
        }

        if (!name.matches("^[A-Za-z ]+$")) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Invalid name. Name should contain only letters and spaces.\"}").build();
        }

        if (email == null || email.trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Email is required.\"}").build();
        }

        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$")) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Email format is invalid.\"}").build();
        }

        Customer customer = CustomerDataStore.getCustomer(name, email);
        if (customer == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Customer not found!\"}").build();
        }

        Cart newCart = new Cart(customer);
        CartDataStore.addCart(newCart);
        return Response.status(Response.Status.CREATED)
                .entity("{\"message\":\"New cart added!\"}").build();
    }

    @GET
    @Path("/getcart/{email}")
    public Response getCartByCustomer(@PathParam("email") String email) {
        Customer customer = CustomerDataStore.getCustomerByEmail(email);

        if (customer == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Customer not found!\"}").build();
        }

        Cart cart = CartDataStore.getCartByCustomer(customer);
        if (cart == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Cart not found!\"}").build();
        }

        return Response.status(Response.Status.OK).entity(cart).build();
    }

    @POST
    @Path("/addBook/{email}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addBookToCart(@PathParam("email") String email, BookInput input) {
        Customer customer = CustomerDataStore.getCustomerByEmail(email);

        if (customer == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Customer not found!\"}").build();
        }

        if (input.ISBN == null || input.ISBN.trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"ISBN is required.\"}").build();
        }

        if (input.title == null || input.title.trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Title is required.\"}").build();
        }

        if (input.author == null || input.author.trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Author is required.\"}").build();
        }

        Book book = BookDataStore.getBook(input.title, input.author, input.ISBN);
        if (book == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Book not found in database.\"}").build();
        }

        Cart cart = CartDataStore.getCartByCustomer(customer);
        if (cart == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Cart not found!\"}").build();
        }

        cart.addItem(book);
        return Response.status(Response.Status.OK)
                .entity("{\"message\":\"Book added to cart.\"}").build();
    }

    @GET
    @Path("/all")
    public Response getAllCarts() {
        List<Cart> carts = CartDataStore.getAllCarts();
        return Response.status(Response.Status.OK).entity(carts).build();
    }
}
 class BookInput {
    public String title;
    public String author;
    public String ISBN;
}

// need to done
//  remove book from cart
//  remove cart