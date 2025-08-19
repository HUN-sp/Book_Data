// Book.java
public class Book {
    // Private attributes
    private String title;
    private String author;
    private double userRating;
    private int reviews;
    private double price;
    private int year;
    private String genre;

    // Constructor
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

    // Getter methods
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

    // Setter methods
    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setUserRating(double userRating) {
        this.userRating = userRating;
    }

    public void setReviews(int reviews) {
        this.reviews = reviews;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    // Method to print book details in a formatted manner
    public void printDetails() {
        System.out.println("=================================");
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("User Rating: " + userRating);
        System.out.println("Reviews: " + reviews);
        System.out.println("Price: $" + price);
        System.out.println("Year: " + year);
        System.out.println("Genre: " + genre);
        System.out.println("=================================");
    }

    // Override toString method for easy printing
    @Override
    public String toString() {
        return String.format("Book{title='%s', author='%s', userRating=%.1f, reviews=%d, price=$%.1f, year=%d, genre='%s'}", 
                           title, author, userRating, reviews, price, year, genre);
    }
}