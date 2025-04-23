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



    @GET
    @Path("/getcart/{email}")
    public Response getCartByCustomer(@PathParam("email") String email) {
        Customer customer = CustomerDataStore.getCustomerByEmail(email);

        if (customer == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Customer not found!\"}").build();
        }

        Cart cart = CartDataStore.getCart(email);
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

        Cart cart = CartDataStore.getCart(email);

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

    @POST
    @Path("/removeBook/{email}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeBook(@PathParam("email") String email , BookInput input) {
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

        Cart cart = CartDataStore.getCart(email);

        if (cart == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"No books found in the cart.\"}").build();
        }
        cart.removeItem(book);
        return Response.status(Response.Status.OK)
                .entity("{\"error\":\"Book is remove from the cart.\"}").build();
    }

}
 class BookInput {
    public String title;
    public String author;
    public String ISBN;
}

