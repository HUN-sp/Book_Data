import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvBookDataReader implements IBookDataSource {
    private final String filename;

    // The constructor takes the path to the CSV file.
    public CsvBookDataReader(String filename) {
        this.filename = filename;
    }

    // This is the implementation of the method required by the IBookDataSource interface.
    @Override
    public List<Book> readBooks() {
        List<Book> books = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            // Skip header row
            br.readLine(); 
            
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    // Split the line by commas, but handle commas inside quotes
                    String[] values = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                    if (values.length == 7) {
                        String title = values[0].trim();
                        String author = values[1].trim();
                        double userRating = Double.parseDouble(values[2].trim());
                        int reviews = Integer.parseInt(values[3].trim());
                        double price = Double.parseDouble(values[4].trim());
                        int year = Integer.parseInt(values[5].trim());
                        String genre = values[6].trim();
                        
                        books.add(new Book(title, author, userRating, reviews, price, year, genre));
                    }
                } catch (Exception e) {
                    System.err.println("Skipping malformed line: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + filename);
            e.printStackTrace();
        }
        return books;
    }
}
