import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Library {
    private int id;
    private String address;
    private List<Book> books;

    public Library(int id) {
        this.id = id;
        this.books = new ArrayList<Book>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Book getBook(String bookId) {
        for (Book book : books) {
            if (book.getId() == bookId) {
                return book;
            }
        }
        return null;
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public int getBookCount() {
        return books.size();
    }

    public void removeBook(String bookId) {
        for (Book book : books) {
            if (book.getId() == bookId) {
                books.remove(book);
                break;
            }
        }
    }

    public void saveToDatabase(Connection db) throws SQLException {
        PreparedStatement stmt = db.prepareStatement("INSERT INTO libraries (id, address) VALUES (?, ?) ON DUPLICATE KEY UPDATE address = ?");

        stmt.setInt(1, this.id);
        stmt.setString(2, this.address);
        stmt.setString(3, this.address);

        stmt.executeUpdate();

        for (Book book : books) {
            book.saveToDatabase(db, this.id);
        }
    }
}
