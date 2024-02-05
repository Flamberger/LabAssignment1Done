import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.ArrayList;


public class BookCollection {
    private ArrayList<Book> books = new ArrayList<>();

    //2, complete constructor that takes a string path (the BookList file name) load the books from BookList into the books arrayList
    //When complete books should have 100 items. Make sure you don't include the header row!
    BookCollection(String path) {
        // try-catch block to throw exceptions for the scanner
        try {
            Scanner scanner = new Scanner(new File(path));

            // Skip the header row
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            // Read and add each book to the books ArrayList
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] bookInfo = line.split(",");

                // Assuming the CSV file format is: Title,Author,ISBN,Pages,CopiesAvailable,CopiesOnLoan
                String title = bookInfo[0];
                String author = bookInfo[1];
                long isbn = Long.parseLong(bookInfo[2]);
                int pages = Integer.parseInt(bookInfo[3]);
                int copiesAvailable = Integer.parseInt(bookInfo[4]);
                int copiesOnLoan = Integer.parseInt(bookInfo[5]);

                Book book = new Book(title, author, isbn, pages, copiesAvailable, copiesOnLoan);
                books.add(book);
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //3, Return a HashSet of all the authors in the book list
    public HashSet<String> getAuthors(){
        HashSet<String> authors = new HashSet<>();
        for (Book b : books) {
            authors.add(b.getAuthor());
        }
        return authors;
    }

    //4, return an arrayList of books with more than 750 pages
    public ArrayList<Book> getLongBooks(){
        ArrayList<Book> longBooks = new ArrayList<>();
        for (Book b : books) {
            if (b.getPages() > 750) {
                longBooks.add(b);
            }
        }
        return longBooks;
    }

    //5, return the book if the given title is in the list.
    public Book getBookByTitle(String title){
        for (Book b : books) {
            if (b.getTitle().equals(title)) {
                return b;
            }
        }
        return null; // Return null if not found
    }

    //6, return an array of the 10 most popular books (That is those that currently have most copies on loan)
    public Book[] mostPopular(){
        // Sort books based on copiesOnLoan in descending order
        books.sort((b1, b2) -> Integer.compare(b2.getCopiesOnLoan(), b1.getCopiesOnLoan()));

        // Return the first 10 books (or fewer if there are less than 10 books in total)
        int numBooks = Math.min(10, books.size());
        Book[] popularBooks = new Book[numBooks];
        for (int i = 0; i < numBooks; i++) {
            popularBooks[i] = books.get(i);
        }
        return popularBooks;
    }

}
