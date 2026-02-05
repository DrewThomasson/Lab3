import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// Book Class
class Book {
    private String isbn;
    private String title;
    private String author;
    private int publicationYear;
    private boolean isAvailable;
    private String borrowedBy;
    private LocalDate borrowDate;
    private LocalDate dueDate;

    public Book(String isbn, String title, String author, int publicationYear) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.isAvailable = true;
        this.borrowedBy = null;
        this.borrowDate = null;
        this.dueDate = null;
    }

    // Getters
    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getPublicationYear() { return publicationYear; }
    public boolean isAvailable() { return isAvailable; }
    public String getBorrowedBy() { return borrowedBy; }
    public LocalDate getBorrowDate() { return borrowDate; }
    public LocalDate getDueDate() { return dueDate; }

    // Setters for borrowing/returning
    public void borrowBook(String memberId) {
        this.isAvailable = false;
        this.borrowedBy = memberId;
        this.borrowDate = LocalDate.now();
        this.dueDate = LocalDate.now().plusDays(14); // 2 weeks loan period
    }

    public void returnBook() {
        this.isAvailable = true;
        this.borrowedBy = null;
        this.borrowDate = null;
        this.dueDate = null;
    }

    public boolean isOverdue() {
        return !isAvailable && dueDate != null && LocalDate.now().isAfter(dueDate);
    }

    @Override
    public String toString() {
        String status = isAvailable ? "Available" : "Borrowed by " + borrowedBy;
        String overdueInfo = "";
        if (!isAvailable && isOverdue()) {
            overdueInfo = " [OVERDUE]";
        }
        return String.format("ISBN: %s | Title: %s | Author: %s | Year: %d | Status: %s%s", 
                           isbn, title, author, publicationYear, status, overdueInfo);
    }
}