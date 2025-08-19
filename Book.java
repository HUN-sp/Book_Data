public class Book {
    private String title;
    private String author;
    private double userRating;
    private int reviews;
    private double price;
    private int year;
    private String genre;

    // Constructor to initialize a new Book object.
    public Book(String title, String author, double userRating, int reviews, 
                double price, int year, String genre) {
        this.title = title;
        this.author = author;
        this.userRating = userRating;
        this.reviews = reviews;
        this.price = price;
        this.year = year;
        this.genre = genre;
    }

    // Getter methods to access the private fields.
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public double getUserRating() {
        return userRating;
    }

    public int getReviews() {
        return reviews;
    }

    public double getPrice() {
        return price;
    }

    public int getYear() {
        return year;
    }

    public String getGenre() {
        return genre;
    }

    // A helpful method to print book details in a readable format.
    @Override
    public String toString() {
        return String.format("Title: %s, Author: %s, Rating: %.1f, Reviews: %d, Price: $%.2f, Year: %d, Genre: %s", 
                             title, author, userRating, reviews, price, year, genre);
    }
}
