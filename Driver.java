// Driver.java
import java.util.*;
import java.util.stream.Collectors;

public class Driver {

    // Main method - entry point of the program
    public static void main(String[] args) {
        // Read the dataset
        String filename = "dataset.csv";  // You can change this to your dataset file
        System.out.println("Reading Amazon Bestselling Books dataset from: " + filename);

        List<Book> books = DatasetReader.readDataset(filename);

        if (books.isEmpty()) {
            System.out.println("No books were loaded. Please check the dataset file.");
            return;
        }

        // Print dataset statistics
        DatasetReader.printDatasetStatistics(books);

        // Demonstrate all the required tasks
        demonstrateAllTasks(books);
    }

    /**
     * Demonstrates all the required tasks with sample data
     */
    public static void demonstrateAllTasks(List<Book> books) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== AMAZON BESTSELLING BOOKS ANALYSIS ===\n");

        while (true) {
            System.out.println("Choose an option:");
            System.out.println("1. Total number of books by an author");
            System.out.println("2. All authors in the dataset");
            System.out.println("3. Names of all books by an author");
            System.out.println("4. Classify books with a user rating");
            System.out.println("5. Price of all books by an author");
            System.out.println("6. Display all books");
            System.out.println("7. Additional Analysis");
            System.out.println("0. Exit");
            System.out.print("\nEnter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter author name: ");
                    String authorForCount = scanner.nextLine();
                    int count = getTotalBooksByAuthor(books, authorForCount);
                    System.out.printf("Total books by %s: %d\n\n", authorForCount, count);
                    break;

                case 2:
                    System.out.println("All authors in the dataset:");
                    getAllAuthors(books);
                    System.out.println();
                    break;

                case 3:
                    System.out.print("Enter author name: ");
                    String authorForBooks = scanner.nextLine();
                    System.out.printf("Books by %s:\n", authorForBooks);
                    getBooksByAuthor(books, authorForBooks);
                    System.out.println();
                    break;

                case 4:
                    System.out.print("Enter user rating (e.g., 4.7): ");
                    double rating = scanner.nextDouble();
                    System.out.printf("Books with rating %.1f:\n", rating);
                    classifyByUserRating(books, rating);
                    System.out.println();
                    break;

                case 5:
                    System.out.print("Enter author name: ");
                    String authorForPrices = scanner.nextLine();
                    System.out.printf("Books and prices by %s:\n", authorForPrices);
                    getPricesByAuthor(books, authorForPrices);
                    System.out.println();
                    break;

                case 6:
                    System.out.println("All books in the dataset:");
                    displayAllBooks(books);
                    System.out.println();
                    break;

                case 7:
                    performAdditionalAnalysis(books);
                    break;

                case 0:
                    System.out.println("Thank you for using the Amazon Books Analyzer!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.\n");
            }
        }
    }

    /**
     * Task 1: Total number of books by an author
     * @param books List of all books
     * @param authorName Name of the author to search for
     * @return Total number of books by the author
     */
    public static int getTotalBooksByAuthor(List<Book> books, String authorName) {
        if (books == null || authorName == null || authorName.trim().isEmpty()) {
            return 0;
        }

        int count = 0;
        String targetAuthor = authorName.trim().toLowerCase();

        for (Book book : books) {
            if (book.getAuthor().toLowerCase().contains(targetAuthor)) {
                count++;
            }
        }

        return count;
    }

    /**
     * Task 2: Print names of all authors in the dataset
     * @param books List of all books
     */
    public static void getAllAuthors(List<Book> books) {
        if (books == null || books.isEmpty()) {
            System.out.println("No books in the dataset.");
            return;
        }

        // Use Set to avoid duplicate authors
        Set<String> uniqueAuthors = new TreeSet<>(); // TreeSet for alphabetical order

        for (Book book : books) {
            uniqueAuthors.add(book.getAuthor());
        }

        int count = 1;
        for (String author : uniqueAuthors) {
            System.out.println(count + ". " + author);
            count++;
        }

        System.out.println("\nTotal unique authors: " + uniqueAuthors.size());
    }

    /**
     * Task 3: Names of all books by an author
     * @param books List of all books
     * @param authorName Name of the author to search for
     */
    public static void getBooksByAuthor(List<Book> books, String authorName) {
        if (books == null || authorName == null || authorName.trim().isEmpty()) {
            System.out.println("Invalid input.");
            return;
        }

        String targetAuthor = authorName.trim().toLowerCase();
        List<String> bookTitles = new ArrayList<>();

        for (Book book : books) {
            if (book.getAuthor().toLowerCase().contains(targetAuthor)) {
                bookTitles.add(book.getTitle());
            }
        }

        if (bookTitles.isEmpty()) {
            System.out.println("No books found for author: " + authorName);
        } else {
            for (int i = 0; i < bookTitles.size(); i++) {
                System.out.println((i + 1) + ". " + bookTitles.get(i));
            }
            System.out.println("\nTotal books found: " + bookTitles.size());
        }
    }

    /**
     * Task 4: Classify books with a specific user rating
     * @param books List of all books
     * @param rating Target user rating to filter by
     */
    public static void classifyByUserRating(List<Book> books, double rating) {
        if (books == null) {
            System.out.println("No books in the dataset.");
            return;
        }

        List<Book> matchingBooks = new ArrayList<>();

        for (Book book : books) {
            if (Math.abs(book.getUserRating() - rating) < 0.01) { // Using small epsilon for double comparison
                matchingBooks.add(book);
            }
        }

        if (matchingBooks.isEmpty()) {
            System.out.printf("No books found with rating %.1f\n", rating);
        } else {
            for (int i = 0; i < matchingBooks.size(); i++) {
                Book book = matchingBooks.get(i);
                System.out.printf("%d. %s by %s (Rating: %.1f, Year: %d)\n", 
                                i + 1, book.getTitle(), book.getAuthor(), 
                                book.getUserRating(), book.getYear());
            }
            System.out.println("\nTotal books found: " + matchingBooks.size());
        }
    }

    /**
     * Task 5: Price of all books by an author
     * @param books List of all books
     * @param authorName Name of the author to search for
     */
    public static void getPricesByAuthor(List<Book> books, String authorName) {
        if (books == null || authorName == null || authorName.trim().isEmpty()) {
            System.out.println("Invalid input.");
            return;
        }

        String targetAuthor = authorName.trim().toLowerCase();
        List<Book> authorBooks = new ArrayList<>();

        for (Book book : books) {
            if (book.getAuthor().toLowerCase().contains(targetAuthor)) {
                authorBooks.add(book);
            }
        }

        if (authorBooks.isEmpty()) {
            System.out.println("No books found for author: " + authorName);
        } else {
            double totalPrice = 0;
            System.out.println("Book Name | Price");
            System.out.println("----------------------------------------");

            for (Book book : authorBooks) {
                System.out.printf("%-30s | $%.2f\n", 
                                book.getTitle().length() > 30 ? 
                                book.getTitle().substring(0, 27) + "..." : book.getTitle(), 
                                book.getPrice());
                totalPrice += book.getPrice();
            }

            System.out.println("----------------------------------------");
            System.out.printf("Total books: %d\n", authorBooks.size());
            System.out.printf("Total price: $%.2f\n", totalPrice);
            System.out.printf("Average price: $%.2f\n", totalPrice / authorBooks.size());
        }
    }

    /**
     * Display all books in a formatted manner
     * @param books List of all books
     */
    public static void displayAllBooks(List<Book> books) {
        if (books == null || books.isEmpty()) {
            System.out.println("No books in the dataset.");
            return;
        }

        System.out.printf("%-40s %-25s %-6s %-8s %-6s %-4s %-12s\n", 
                        "Title", "Author", "Rating", "Reviews", "Price", "Year", "Genre");
        System.out.println("=".repeat(110));

        for (Book book : books) {
            System.out.printf("%-40s %-25s %-6.1f %-8d $%-5.1f %-4d %-12s\n",
                            book.getTitle().length() > 40 ? 
                            book.getTitle().substring(0, 37) + "..." : book.getTitle(),
                            book.getAuthor().length() > 25 ? 
                            book.getAuthor().substring(0, 22) + "..." : book.getAuthor(),
                            book.getUserRating(),
                            book.getReviews(),
                            book.getPrice(),
                            book.getYear(),
                            book.getGenre());
        }
    }

    /**
     * Perform additional analysis on the dataset
     * @param books List of all books
     */
    public static void performAdditionalAnalysis(List<Book> books) {
        if (books == null || books.isEmpty()) {
            System.out.println("No books in the dataset.");
            return;
        }

        System.out.println("=== ADDITIONAL ANALYSIS ===\n");

        // 1. Most expensive book
        Book mostExpensive = books.stream()
                .max(Comparator.comparing(Book::getPrice))
                .orElse(null);

        if (mostExpensive != null) {
            System.out.println("Most Expensive Book:");
            System.out.printf("  %s by %s - $%.2f\n\n", 
                            mostExpensive.getTitle(), 
                            mostExpensive.getAuthor(), 
                            mostExpensive.getPrice());
        }

        // 2. Highest rated book
        Book highestRated = books.stream()
                .max(Comparator.comparing(Book::getUserRating))
                .orElse(null);

        if (highestRated != null) {
            System.out.println("Highest Rated Book:");
            System.out.printf("  %s by %s - %.1f rating\n\n", 
                            highestRated.getTitle(), 
                            highestRated.getAuthor(), 
                            highestRated.getUserRating());
        }

        // 3. Most reviewed book
        Book mostReviewed = books.stream()
                .max(Comparator.comparing(Book::getReviews))
                .orElse(null);

        if (mostReviewed != null) {
            System.out.println("Most Reviewed Book:");
            System.out.printf("  %s by %s - %d reviews\n\n", 
                            mostReviewed.getTitle(), 
                            mostReviewed.getAuthor(), 
                            mostReviewed.getReviews());
        }

        // 4. Genre distribution
        Map<String, Long> genreCount = books.stream()
                .collect(Collectors.groupingBy(Book::getGenre, Collectors.counting()));

        System.out.println("Genre Distribution:");
        genreCount.forEach((genre, count) -> 
            System.out.printf("  %s: %d books\n", genre, count));

        // 5. Year with most books
        Map<Integer, Long> yearCount = books.stream()
                .collect(Collectors.groupingBy(Book::getYear, Collectors.counting()));

        Optional<Map.Entry<Integer, Long>> mostProductiveYear = yearCount.entrySet().stream()
                .max(Map.Entry.comparingByValue());

        if (mostProductiveYear.isPresent()) {
            System.out.printf("\nMost Productive Year: %d with %d books\n\n", 
                            mostProductiveYear.get().getKey(), 
                            mostProductiveYear.get().getValue());
        }

        // 6. Average statistics
        double avgPrice = books.stream().mapToDouble(Book::getPrice).average().orElse(0.0);
        double avgRating = books.stream().mapToDouble(Book::getUserRating).average().orElse(0.0);
        double avgReviews = books.stream().mapToInt(Book::getReviews).average().orElse(0.0);

        System.out.println("Dataset Averages:");
        System.out.printf("  Average Price: $%.2f\n", avgPrice);
        System.out.printf("  Average Rating: %.2f\n", avgRating);
        System.out.printf("  Average Reviews: %.0f\n\n", avgReviews);
    }
}