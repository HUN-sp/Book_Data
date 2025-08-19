// Driver.java
// This is the entry point of your application.
// Its main responsibilities are:
// 1. Configuration: Deciding which data source to use.
// 2. User Interface: Displaying the menu and getting user input.
// 3. Orchestration: Calling the correct service method and printing the results.

import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Driver {

    public static void main(String[] args) {
        // --- CONFIGURATION ---
        // Here, we decide which data source to use.
        // To switch to an Excel file, you would just change this one line:
        // IBookDataSource dataSource = new ExcelBookDataReader("books.xlsx");
        IBookDataSource dataSource = new CsvBookDataReader("dataset.csv");

        // Create the service layer, injecting the chosen data source.
        BookService bookService = new BookService(dataSource);

        // --- USER INTERFACE ---
        runMenu(bookService);
    }

    // The main menu loop for user interaction.
    private static void runMenu(BookService bookService) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Amazon Bestselling Books Analyzer ---");
            System.out.println("1. Get total book count by an author");
            System.out.println("2. List all authors");
            System.out.println("3. Find all books by an author");
            System.out.println("4. Find all books with a specific rating");
            System.out.println("5. Show book prices for an author");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        System.out.print("Enter author's name: ");
                        String authorCount = scanner.nextLine();
                        long count = bookService.getTotalBooksByAuthor(authorCount);
                        System.out.printf("'%s' has %d book(s) in the dataset.\n", authorCount, count);
                        break;
                    case 2:
                        Set<String> authors = bookService.getAllAuthors();
                        System.out.println("--- All Authors ---");
                        authors.forEach(System.out::println);
                        System.out.printf("Total unique authors: %d\n", authors.size());
                        break;
                    case 3:
                        System.out.print("Enter author's name: ");
                        String authorBooks = scanner.nextLine();
                        List<String> titles = bookService.getBookTitlesByAuthor(authorBooks);
                        System.out.printf("--- Books by %s ---\n", authorBooks);
                        titles.forEach(title -> System.out.println("- " + title));
                        break;
                    case 4:
                        System.out.print("Enter user rating (e.g., 4.7): ");
                        double rating = Double.parseDouble(scanner.nextLine());
                        List<Book> booksByRating = bookService.getBooksByRating(rating);
                        System.out.printf("--- Books with rating %.1f ---\n", rating);
                        booksByRating.forEach(book -> System.out.println(book.getTitle()));
                        break;
                    case 5:
                        System.out.print("Enter author's name: ");
                        String authorPrices = scanner.nextLine();
                        List<Book> booksWithPrices = bookService.getBooksWithPricesByAuthor(authorPrices);
                        System.out.printf("--- Book Prices by %s ---\n", authorPrices);
                        booksWithPrices.forEach(book -> System.out.printf("- %s: $%.2f\n", book.getTitle(), book.getPrice()));
                        break;
                    case 0:
                        System.out.println("Exiting application. Goodbye!");
                        return;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 0 and 5.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }
}
