# Lab 3 - Library Management System

A comprehensive school library management system built in Java, generated using AI assistance and refined to compile and run correctly.

## AI System Used

**GitHub Copilot** - https://github.com/features/copilot

The initial code was generated from the prompt in `AI_Prompt.txt` and then fixed to add the required import statements for proper compilation.

## How to Compile and Run

### Prerequisites
- Java Development Kit (JDK) 11 or higher installed
- Terminal/Command Prompt access

### Steps to Run

1. **Open a terminal** and navigate to the project directory:
   ```bash
   cd path/to/Lab3
   ```

2. **Compile the Java files**:
   ```bash
   javac *.java
   ```

3. **Run the program**:
   ```bash
   java LibraryManagementSystem
   ```

4. **Use the menu system** to navigate through the Library Management System features.

## Screenshot of Running Program

![Library Management System Running](https://github.com/user-attachments/assets/cf9f14ea-01c4-45af-86c7-5bc15c1f7be4)

## Example Output

```
Welcome to the School Library Management System!

╔══════════════════════════════════════╗
║        LIBRARY MANAGEMENT SYSTEM     ║
╠══════════════════════════════════════╣
║  1. Book Management                  ║
║  2. Member Management                ║
║  3. Borrowing & Returning            ║
║  4. Search Books                     ║
║  5. Display Reports                  ║
║  6. Exit                             ║
╚══════════════════════════════════════╝
Enter your choice: 5

=== DISPLAY REPORTS ===
1. All Books
2. Available Books
3. Borrowed Books
4. Overdue Books
5. All Members
6. Back to Main Menu
Enter your choice: 1

=== ALL BOOKS ===
ISBN: 978-0596009205 | Title: Head First Design Patterns | Author: Eric Freeman | Year: 2004 | Status: Available
ISBN: 978-0134685991 | Title: Effective Java | Author: Joshua Bloch | Year: 2017 | Status: Available
ISBN: 978-0321356680 | Title: Effective Java Programming | Author: Joshua Bloch | Year: 2008 | Status: Available
ISBN: 978-0132350884 | Title: Clean Code | Author: Robert Martin | Year: 2008 | Status: Available
ISBN: 978-1617291203 | Title: Java 8 in Action | Author: Raoul-Gabriel Urma | Year: 2014 | Status: Available
```

## Features

The Library Management System includes:

- **Book Management**: Add and remove books from the library
- **Member Management**: Register and deregister library members
- **Borrowing & Returning**: Borrow books (with 14-day loan period) and return them
- **Search Books**: Search by title, author, or ISBN
- **Display Reports**: View all books, available books, borrowed books, overdue books, and all members

## Files to Submit

According to the lab instructions, submit the following:

1. ✅ **AI System URL**: GitHub Copilot - https://github.com/features/copilot (enter in the Assignment's "Text Box")
2. ✅ **Screenshot**: A screenshot of VS Code/terminal running the program (included above)
3. ✅ **AI_Prompt.txt**: Only if you modified it from the original
4. ✅ **Java code files**:
   - `LibraryManagementSystem.java` - Main application entry point
   - `Library.java` - Core library class with all management methods
   - `Book.java` - Book entity class
   - `Member.java` - Member/Student entity class

## Project Structure

```
Lab3/
├── LibraryManagementSystem.java  # Main entry point
├── Library.java                  # Library management logic
├── Book.java                     # Book class
├── Member.java                   # Member class
├── AI_Prompt.txt                 # Original AI prompt
├── Lab 3 - Instructions.html     # Lab instructions
└── README.md                     # This file
```
