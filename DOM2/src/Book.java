import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Book {
    private String id;
    private String author;
    private String title;
    private String genre;
    private Double price;
    private String publish_date;
    private String description;

    public Book(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Double getPrice() {
        return price;
    }
    
    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPublish_date() {
        return publish_date;
    }

    public void setPublish_date(String publish_date) {
        this.publish_date = publish_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void saveToDatabase(Connection db, int library) throws SQLException {
        PreparedStatement stmt = db.prepareStatement("INSERT INTO books (id, author, title, genre, price, publish_date, description, library_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE author = ?, title = ?, genre = ?, price = ?, publish_date = ?, description = ?, library_id = ?");

        stmt.setString(1, this.id);
        stmt.setString(2, this.author);
        stmt.setString(3, this.title);
        stmt.setString(4, this.genre);
        stmt.setDouble(5, this.price);
        stmt.setString(6, this.publish_date);
        stmt.setString(7, this.description);
        stmt.setInt(8, library);
        stmt.setString(9, this.author);
        stmt.setString(10, this.title);
        stmt.setString(11, this.genre);
        stmt.setDouble(12, this.price);
        stmt.setString(13, this.publish_date);
        stmt.setString(14, this.description);
        stmt.setInt(15, library);

        stmt.executeUpdate();
    }
}
