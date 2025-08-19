// BookService.java
// This class contains all the core logic of your application (the "business logic").
// It doesn't know where the data comes from; it just gets a list of books and performs operations on it.
// This makes our logic reusable and independent of the data source.

import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class BookService {
    private final List<Book> books;

    // The service is initialized with a data source. It immediately reads the books and stores them.
    public BookService(IBookDataSource dataSource) {
        this.books = dataSource.readBooks();
        System.out.println("Successfully loaded " + this.books.size() + " books.");
    }

    // Task 1: Get total number of books by an author.
    public long getTotalBooksByAuthor(String authorName) {
        return books.stream()
                    .filter(book -> book.getAuthor().equalsIgnoreCase(authorName))
                    .count();
    }

    // Task 2: Get all unique authors in the dataset, sorted alphabetically.
    public Set<String> getAllAuthors() {
        return books.stream()
                    .map(Book::getAuthor)
                    .collect(Collectors.toCollection(TreeSet::new));
    }

    // Task 3: Get the names of all books by a specific author.
    public List<String> getBookTitlesByAuthor(String authorName) {
        return books.stream()
                    .filter(book -> book.getAuthor().equalsIgnoreCase(authorName))
                    .map(Book::getTitle)
                    .collect(Collectors.toList());
    }

    // Task 4: Get all books that have a specific user rating.
    public List<Book> getBooksByRating(double rating) {
        // Using an epsilon for comparing double values is a good practice.
        return books.stream()
                    .filter(book -> Math.abs(book.getUserRating() - rating) < 0.01)
                    .collect(Collectors.toList());
    }

    // Task 5: Get all books by an author to show their prices.
    public List<Book> getBooksWithPricesByAuthor(String authorName) {
        return books.stream()
                    .filter(book -> book.getAuthor().equalsIgnoreCase(authorName))
                    .collect(Collectors.toList());
    }
}
