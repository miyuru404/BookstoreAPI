package modelData;

import model.Author;

import java.util.ArrayList;
import java.util.List;

public class AuthorDataStore {
    private static final List<Author> authors = new ArrayList<Author>();

    public static void addAuthor(String name,String surname,int age) {
        if(!authors.contains(new Author(name,surname,age))){
            authors.add(new Author(name,surname,age));
            System.out.println("Author added");
        }
        else {
            System.out.println("Author already exists");
        }
    }

    public static void deleteAuthor(Author author) {}

    public static void updateAuthor(Author author) {}

    public static void getAuthor() {}
}
