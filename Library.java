import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// Library Class
class Library {
    private Map<String, Book> books;
    private Map<String, Member> members;
    private Scanner scanner;

    public Library() {
        this.books = new HashMap<>();
        this.members = new HashMap<>();
        this.scanner = new Scanner(System.in);
        initializeSampleData();
    }

    private void initializeSampleData() {
        // Add some sample books
        addBook(new Book("978-0134685991", "Effective Java", "Joshua Bloch", 2017));
        addBook(new Book("978-0596009205", "Head First Design Patterns", "Eric Freeman", 2004));
        addBook(new Book("978-0132350884", "Clean Code", "Robert Martin", 2008));
        addBook(new Book("978-0321356680", "Effective Java Programming", "Joshua Bloch", 2008));
        addBook(new Book("978-1617291203", "Java 8 in Action", "Raoul-Gabriel Urma", 2014));

        // Add some sample members
        registerMember(new Member("STU001", "Alice Johnson", "alice@school.edu", "555-0101"));
        registerMember(new Member("STU002", "Bob Smith", "bob@school.edu", "555-0102"));
        registerMember(new Member("STU003", "Carol Davis", "carol@school.edu", "555-0103"));
    }

    // Book Management Methods
    public boolean addBook(Book book) {
        if (books.containsKey(book.getIsbn())) {
            return false; // Book already exists
        }
        books.put(book.getIsbn(), book);
        return true;
    }

    public boolean removeBook(String isbn) {
        Book book = books.get(isbn);
        if (book != null && book.isAvailable()) {
            books.remove(isbn);
            return true;
        }
        return false; // Book not found or currently borrowed
    }

    // Member Management Methods
    public boolean registerMember(Member member) {
        if (members.containsKey(member.getMemberId())) {
            return false; // Member already exists
        }
        members.put(member.getMemberId(), member);
        return true;
    }

    public boolean deregisterMember(String memberId) {
        Member member = members.get(memberId);
        if (member != null && member.getBorrowedBooks().isEmpty()) {
            members.remove(memberId);
            return true;
        }
        return false; // Member not found or has borrowed books
    }

    // Borrowing and Returning Methods
    public boolean borrowBook(String isbn, String memberId) {
        Book book = books.get(isbn);
        Member member = members.get(memberId);

        if (book == null) {
            System.out.println("Book not found!");
            return false;
        }
        if (member == null) {
            System.out.println("Member not found!");
            return false;
        }
        if (!book.isAvailable()) {
            System.out.println("Book is not available!");
            return false;
        }
        if (!member.canBorrowMore()) {
            System.out.println("Member has reached maximum borrowing limit (5 books)!");
            return false;
        }

        book.borrowBook(memberId);
        member.borrowBook(isbn);
        System.out.println("Book borrowed successfully! Due date: " + 
                         book.getDueDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        return true;
    }

    public boolean returnBook(String isbn, String memberId) {
        Book book = books.get(isbn);
        Member member = members.get(memberId);

        if (book == null) {
            System.out.println("Book not found!");
            return false;
        }
        if (member == null) {
            System.out.println("Member not found!");
            return false;
        }
        if (book.isAvailable() || !book.getBorrowedBy().equals(memberId)) {
            System.out.println("This book was not borrowed by this member!");
            return false;
        }

        if (book.isOverdue()) {
            System.out.println("Warning: Book was returned overdue!");
        }

        book.returnBook();
        member.returnBook(isbn);
        System.out.println("Book returned successfully!");
        return true;
    }

    // Search Methods
    public List<Book> searchByTitle(String title) {
        return books.values().stream()
                   .filter(book -> book.getTitle().toLowerCase().contains(title.toLowerCase()))
                   .collect(ArrayList::new, (list, book) -> list.add(book), (list1, list2) -> list1.addAll(list2));
    }

    public List<Book> searchByAuthor(String author) {
        return books.values().stream()
                   .filter(book -> book.getAuthor().toLowerCase().contains(author.toLowerCase()))
                   .collect(ArrayList::new, (list, book) -> list.add(book), (list1, list2) -> list1.addAll(list2));
    }

    public Book searchByISBN(String isbn) {
        return books.get(isbn);
    }

    // Display Methods
    public void displayAllBooks() {
        if (books.isEmpty()) {
            System.out.println("No books in the library.");
            return;
        }
        System.out.println("\n=== ALL BOOKS ===");
        books.values().forEach(System.out::println);
    }

    public void displayAvailableBooks() {
        List<Book> availableBooks = books.values().stream()
                                        .filter(Book::isAvailable)
                                        .collect(ArrayList::new, (list, book) -> list.add(book), (list1, list2) -> list1.addAll(list2));
        
        if (availableBooks.isEmpty()) {
            System.out.println("No books available.");
            return;
        }
        System.out.println("\n=== AVAILABLE BOOKS ===");
        availableBooks.forEach(System.out::println);
    }

    public void displayBorrowedBooks() {
        List<Book> borrowedBooks = books.values().stream()
                                       .filter(book -> !book.isAvailable())
                                       .collect(ArrayList::new, (list, book) -> list.add(book), (list1, list2) -> list1.addAll(list2));
        
        if (borrowedBooks.isEmpty()) {
            System.out.println("No books currently borrowed.");
            return;
        }
        System.out.println("\n=== BORROWED BOOKS ===");
        borrowedBooks.forEach(System.out::println);
    }

    public void displayAllMembers() {
        if (members.isEmpty()) {
            System.out.println("No members registered.");
            return;
        }
        System.out.println("\n=== ALL MEMBERS ===");
        members.values().forEach(System.out::println);
    }

    public void displayOverdueBooks() {
        List<Book> overdueBooks = books.values().stream()
                                      .filter(Book::isOverdue)
                                      .collect(ArrayList::new, (list, book) -> list.add(book), (list1, list2) -> list1.addAll(list2));
        
        if (overdueBooks.isEmpty()) {
            System.out.println("No overdue books.");
            return;
        }
        System.out.println("\n=== OVERDUE BOOKS ===");
        overdueBooks.forEach(System.out::println);
    }

    public void displayMemberDetails(String memberId) {
        Member member = members.get(memberId);
        if (member == null) {
            System.out.println("Member not found!");
            return;
        }
        
        System.out.println("\n=== MEMBER DETAILS ===");
        System.out.println(member);
        
        if (!member.getBorrowedBooks().isEmpty()) {
            System.out.println("\nBorrowed Books:");
            for (String isbn : member.getBorrowedBooks()) {
                Book book = books.get(isbn);
                if (book != null) {
                    System.out.println("  - " + book.getTitle() + " (Due: " + 
                                     book.getDueDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + ")");
                }
            }
        }
    }

    // Console UI Methods
    public void showMainMenu() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║        LIBRARY MANAGEMENT SYSTEM     ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║  1. Book Management                  ║");
        System.out.println("║  2. Member Management                ║");
        System.out.println("║  3. Borrowing & Returning            ║");
        System.out.println("║  4. Search Books                     ║");
        System.out.println("║  5. Display Reports                  ║");
        System.out.println("║  6. Exit                             ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.print("Enter your choice: ");
    }

    public void handleBookManagement() {
        while (true) {
            System.out.println("\n=== BOOK MANAGEMENT ===");
            System.out.println("1. Add New Book");
            System.out.println("2. Remove Book");
            System.out.println("3. Display All Books");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = getIntInput();
            switch (choice) {
                case 1:
                    addNewBook();
                    break;
                case 2:
                    removeExistingBook();
                    break;
                case 3:
                    displayAllBooks();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    public void handleMemberManagement() {
        while (true) {
            System.out.println("\n=== MEMBER MANAGEMENT ===");
            System.out.println("1. Register New Member");
            System.out.println("2. Deregister Member");
            System.out.println("3. Display All Members");
            System.out.println("4. View Member Details");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = getIntInput();
            switch (choice) {
                case 1:
                    registerNewMember();
                    break;
                case 2:
                    deregisterExistingMember();
                    break;
                case 3:
                    displayAllMembers();
                    break;
                case 4:
                    viewMemberDetails();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    public void handleBorrowingReturning() {
        while (true) {
            System.out.println("\n=== BORROWING & RETURNING ===");
            System.out.println("1. Borrow Book");
            System.out.println("2. Return Book");
            System.out.println("3. Display Borrowed Books");
            System.out.println("4. Display Overdue Books");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = getIntInput();
            switch (choice) {
                case 1:
                    borrowBookProcess();
                    break;
                case 2:
                    returnBookProcess();
                    break;
                case 3:
                    displayBorrowedBooks();
                    break;
                case 4:
                    displayOverdueBooks();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    public void handleSearchBooks() {
        while (true) {
            System.out.println("\n=== SEARCH BOOKS ===");
            System.out.println("1. Search by Title");
            System.out.println("2. Search by Author");
            System.out.println("3. Search by ISBN");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = getIntInput();
            switch (choice) {
                case 1:
                    searchBooksByTitle();
                    break;
                case 2:
                    searchBooksByAuthor();
                    break;
                case 3:
                    searchBookByISBN();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    public void handleDisplayReports() {
        while (true) {
            System.out.println("\n=== DISPLAY REPORTS ===");
            System.out.println("1. All Books");
            System.out.println("2. Available Books");
            System.out.println("3. Borrowed Books");
            System.out.println("4. Overdue Books");
            System.out.println("5. All Members");
            System.out.println("6. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = getIntInput();
            switch (choice) {
                case 1:
                    displayAllBooks();
                    break;
                case 2:
                    displayAvailableBooks();
                    break;
                case 3:
                    displayBorrowedBooks();
                    break;
                case 4:
                    displayOverdueBooks();
                    break;
                case 5:
                    displayAllMembers();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    // Helper methods for user input
    private void addNewBook() {
        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine().trim();
        
        if (books.containsKey(isbn)) {
            System.out.println("Book with this ISBN already exists!");
            return;
        }

        System.out.print("Enter title: ");
        String title = scanner.nextLine().trim();
        System.out.print("Enter author: ");
        String author = scanner.nextLine().trim();
        System.out.print("Enter publication year: ");
        int year = getIntInput();

        Book book = new Book(isbn, title, author, year);
        if (addBook(book)) {
            System.out.println("Book added successfully!");
        } else {
            System.out.println("Failed to add book!");
        }
    }

    private void removeExistingBook() {
        System.out.print("Enter ISBN of book to remove: ");
        String isbn = scanner.nextLine().trim();
        
        if (removeBook(isbn)) {
            System.out.println("Book removed successfully!");
        } else {
            System.out.println("Book not found or currently borrowed!");
        }
    }

    private void registerNewMember() {
        System.out.print("Enter member ID: ");
        String memberId = scanner.nextLine().trim();
        
        if (members.containsKey(memberId)) {
            System.out.println("Member with this ID already exists!");
            return;
        }

        System.out.print("Enter name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter email: ");
        String email = scanner.nextLine().trim();
        System.out.print("Enter phone: ");
        String phone = scanner.nextLine().trim();

        Member member = new Member(memberId, name, email, phone);
        if (registerMember(member)) {
            System.out.println("Member registered successfully!");
        } else {
            System.out.println("Failed to register member!");
        }
    }

    private void deregisterExistingMember() {
        System.out.print("Enter member ID to deregister: ");
        String memberId = scanner.nextLine().trim();
        
        if (deregisterMember(memberId)) {
            System.out.println("Member deregistered successfully!");
        } else {
            System.out.println("Member not found or has borrowed books!");
        }
    }

    private void viewMemberDetails() {
        System.out.print("Enter member ID: ");
        String memberId = scanner.nextLine().trim();
        displayMemberDetails(memberId);
    }

    private void borrowBookProcess() {
        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine().trim();
        System.out.print("Enter member ID: ");
        String memberId = scanner.nextLine().trim();
        
        borrowBook(isbn, memberId);
    }

    private void returnBookProcess() {
        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine().trim();
        System.out.print("Enter member ID: ");
        String memberId = scanner.nextLine().trim();
        
        returnBook(isbn, memberId);
    }

    private void searchBooksByTitle() {
        System.out.print("Enter title to search: ");
        String title = scanner.nextLine().trim();
        
        List<Book> results = searchByTitle(title);
        if (results.isEmpty()) {
            System.out.println("No books found with that title.");
        } else {
            System.out.println("\n=== SEARCH RESULTS ===");
            results.forEach(System.out::println);
        }
    }

    private void searchBooksByAuthor() {
        System.out.print("Enter author to search: ");
        String author = scanner.nextLine().trim();
        
        List<Book> results = searchByAuthor(author);
        if (results.isEmpty()) {
            System.out.println("No books found by that author.");
        } else {
            System.out.println("\n=== SEARCH RESULTS ===");
            results.forEach(System.out::println);
        }
    }

    private void searchBookByISBN() {
        System.out.print("Enter ISBN to search: ");
        String isbn = scanner.nextLine().trim();
        
        Book book = searchByISBN(isbn);
        if (book == null) {
            System.out.println("No book found with that ISBN.");
        } else {
            System.out.println("\n=== SEARCH RESULT ===");
            System.out.println(book);
        }
    }

    private int getIntInput() {
        while (true) {
            try {
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }

    public void run() {
        System.out.println("Welcome to the School Library Management System!");
        
        while (true) {
            showMainMenu();
            int choice = getIntInput();
            
            switch (choice) {
                case 1:
                    handleBookManagement();
                    break;
                case 2:
                    handleMemberManagement();
                    break;
                case 3:
                    handleBorrowingReturning();
                    break;
                case 4:
                    handleSearchBooks();
                    break;
                case 5:
                    handleDisplayReports();
                    break;
                case 6:
                    System.out.println("Thank you for using the Library Management System!");
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }
}
