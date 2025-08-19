// DatasetReader.java
import java.io.*;
import java.util.*;

public class DatasetReader {

    /**
     * Reads a CSV file and returns a list of Book objects
     * @param filename the name of the CSV file to read
     * @return List<Book> containing all the books from the dataset
     */
    public static List<Book> readDataset(String filename) {
        List<Book> books = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                // Skip header row
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                // Parse the CSV line
                try {
                    Book book = parseBookFromCSVLine(line);
                    if (book != null) {
                        books.add(book);
                    }
                } catch (Exception e) {
                    System.err.println("Error parsing line: " + line);
                    System.err.println("Error message: " + e.getMessage());
                }
            }

        } catch (IOException e) {
            System.err.println("Error reading file: " + filename);
            System.err.println("Error message: " + e.getMessage());
        }

        return books;
    }

    /**
     * Parses a single CSV line and creates a Book object
     * @param csvLine a line from the CSV file
     * @return Book object or null if parsing fails
     */
    private static Book parseBookFromCSVLine(String csvLine) {
        try {
            // Handle CSV parsing with proper comma separation
            // This method handles commas within quotes properly
            List<String> fields = parseCSVFields(csvLine);

            if (fields.size() < 7) {
                System.err.println("Insufficient fields in line: " + csvLine);
                return null;
            }

            String title = fields.get(0).trim();
            String author = fields.get(1).trim();
            double userRating = Double.parseDouble(fields.get(2).trim());
            int reviews = Integer.parseInt(fields.get(3).trim());
            double price = Double.parseDouble(fields.get(4).trim());
            int year = Integer.parseInt(fields.get(5).trim());
            String genre = fields.get(6).trim();

            return new Book(title, author, userRating, reviews, price, year, genre);

        } catch (NumberFormatException e) {
            System.err.println("Number format error in line: " + csvLine);
            return null;
        } catch (Exception e) {
            System.err.println("General error parsing line: " + csvLine + " - " + e.getMessage());
            return null;
        }
    }

    /**
     * Parses CSV fields, handling commas within quoted strings
     * @param csvLine the CSV line to parse
     * @return List of fields
     */
    private static List<String> parseCSVFields(String csvLine) {
        List<String> fields = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder currentField = new StringBuilder();

        for (int i = 0; i < csvLine.length(); i++) {
            char c = csvLine.charAt(i);

            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                fields.add(currentField.toString());
                currentField = new StringBuilder();
            } else {
                currentField.append(c);
            }
        }

        // Add the last field
        fields.add(currentField.toString());

        return fields;
    }

    /**
     * Validates if a book has all required fields
     * @param book the Book object to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidBook(Book book) {
        return book != null &&
               book.getTitle() != null && !book.getTitle().trim().isEmpty() &&
               book.getAuthor() != null && !book.getAuthor().trim().isEmpty() &&
               book.getUserRating() >= 0 && book.getUserRating() <= 5 &&
               book.getReviews() >= 0 &&
               book.getPrice() >= 0 &&
               book.getYear() >= 1900 && book.getYear() <= 2030 &&
               book.getGenre() != null && !book.getGenre().trim().isEmpty();
    }

    /**
     * Prints statistics about the loaded dataset
     * @param books the list of books to analyze
     */
    public static void printDatasetStatistics(List<Book> books) {
        if (books == null || books.isEmpty()) {
            System.out.println("No books in the dataset.");
            return;
        }

        System.out.println("\n=== DATASET STATISTICS ===");
        System.out.println("Total books loaded: " + books.size());

        // Count by genre
        int fictionCount = 0;
        int nonFictionCount = 0;

        for (Book book : books) {
            if ("Fiction".equalsIgnoreCase(book.getGenre())) {
                fictionCount++;
            } else if ("Non Fiction".equalsIgnoreCase(book.getGenre())) {
                nonFictionCount++;
            }
        }

        System.out.println("Fiction books: " + fictionCount);
        System.out.println("Non-Fiction books: " + nonFictionCount);

        // Rating statistics
        double totalRating = 0;
        double minRating = Double.MAX_VALUE;
        double maxRating = Double.MIN_VALUE;

        for (Book book : books) {
            double rating = book.getUserRating();
            totalRating += rating;
            if (rating < minRating) minRating = rating;
            if (rating > maxRating) maxRating = rating;
        }

        double avgRating = totalRating / books.size();
        System.out.printf("Average Rating: %.2f\n", avgRating);
        System.out.printf("Minimum Rating: %.1f\n", minRating);
        System.out.printf("Maximum Rating: %.1f\n", maxRating);

        System.out.println("========================\n");
    }
}