// Member/Student Class
class Member {
    private String memberId;
    private String name;
    private String email;
    private String phone;
    private List<String> borrowedBooks;
    private LocalDate registrationDate;

    public Member(String memberId, String name, String email, String phone) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.borrowedBooks = new ArrayList<>();
        this.registrationDate = LocalDate.now();
    }

    // Getters
    public String getMemberId() { return memberId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public List<String> getBorrowedBooks() { return new ArrayList<>(borrowedBooks); }
    public LocalDate getRegistrationDate() { return registrationDate; }

    // Setters
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }

    public void borrowBook(String isbn) {
        borrowedBooks.add(isbn);
    }

    public void returnBook(String isbn) {
        borrowedBooks.remove(isbn);
    }

    public boolean canBorrowMore() {
        return borrowedBooks.size() < 5; // Maximum 5 books per member
    }

    @Override
    public String toString() {
        return String.format("ID: %s | Name: %s | Email: %s | Phone: %s | Books Borrowed: %d | Registered: %s", 
                           memberId, name, email, phone, borrowedBooks.size(), 
                           registrationDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }
}