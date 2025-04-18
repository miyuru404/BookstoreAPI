package modelData;

import model.Author;

import java.util.ArrayList;
import java.util.List;

public class AuthorDataStore {
    private static final List<Author> authors = new ArrayList<Author>();

    public static void addAuthor(String name,String surname,int age) {
        if(name == null || name.isEmpty() || surname == null || surname.isEmpty()){
            System.out.println("provide valide details");
            return ;
        }

        if(getAuthor( name, surname) != null){
            System.out.println("author already exists");
            return;
        }

        Author author = new Author(name,surname,age);
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


    public static void updateAuthor(String name,String surname ,String newName,String newSurname,int age) {
        if(newName == null || newName.isEmpty() || newSurname == null || newSurname.isEmpty()){
            System.out.println("provide valide details");
            return ;
        }

        Author author = getAuthor(name, surname);
        if(author != null){
            author.setName(newName);
            author.setSurname(newSurname);
            author.setAge(age);
            System.out.println("Author updated");
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
