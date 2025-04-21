package com.demo.dataModel;



import com.demo.model.Author;

import java.util.ArrayList;
import java.util.List;

public class AuthorDataStore {
    private static final List<Author> authors = new ArrayList<Author>();

    public static void addAuthor(Author author) {
        if(author.getName() == null || author.getName().isEmpty() || author.getSurname() == null || author.getSurname().isEmpty()){
            System.out.println("provide valide details");
            return ;
        }

        if(getAuthor( author.getName(), author.getSurname()) != null){
            System.out.println("author already exists");
            return;
        }


        authors.add(author);

    }

    public static void deleteAuthor(String name,String surname) {
        Author author = getAuthor(name, surname);
        if(author != null){
            authors.remove(author);
            System.out.println("Author deleted");
        }
        else {
            System.out.println("author not found");
        }
    }


    public static void updateAuthor(String name,String surname ,Author  newAuthor) {
        if(newAuthor.getName() == null || newAuthor.getName().isEmpty() || newAuthor.getSurname() == null || newAuthor.getSurname().isEmpty()){
            System.out.println("provide valide details");
            return ;
        }

        Author author = getAuthor(name, surname);
        if(author != null){
            author.setName(newAuthor.getName());
            author.setSurname(newAuthor.getSurname());
            author.setAge(newAuthor.getAge());
            System.out.println("Author updated");
        }
        else {
            System.out.println("author not found");
        }

    }


    public static Author getAuthor(String name,String surname) {
        if(name == null || name.isEmpty() || surname == null || surname.isEmpty()){
            System.out.println("provide valide details");
            return null;
        }
        if (authors.isEmpty()) {
            System.out.println("no authors been added yet");
            return null;
        }
        for (Author author : authors) {
            if (author.getName().equals(name) && author.getSurname().equals(surname)) {
                System.out.println("Author found");
                return author;
            }
        }
        System.out.println("Author not found");
        return null;
    }
}
