// IBookDataSource.java
// This is an interface, which acts as a blueprint or a contract.
// It declares that any class wanting to be a "book data source" MUST provide a method called readBooks().
// This allows us to swap data sources (CSV, Excel, Database) without changing the rest of the application.

import java.util.List;

public interface IBookDataSource {
    /**
     * Reads book data from a source and returns it as a list of Book objects.
     * @return A list of books.
     */
    List<Book> readBooks();
}
