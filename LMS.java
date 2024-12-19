import java.util.ArrayList;
import java.util.Scanner;

// Book Class
class Book {
    private String title;
    private String author;
    private boolean isAvailable;

    // Constructor
    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.isAvailable = true;
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public String toString() {
        return "Title: " + title + ", Author: " + author + ", Available: " + isAvailable;
    }
}

// Patron Class
class Patron {
    private String name;
    private ArrayList<Book> borrowedBooks;

    // Constructor
    public Patron(String name) {
        this.name = name;
        this.borrowedBooks = new ArrayList<>();
    }

    // Getters
    public String getName() {
        return name;
    }

    public ArrayList<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    // Borrow a Book
    public void borrowBook(Book book) {
        borrowedBooks.add(book);
    }

    // Return a Book
    public void returnBook(Book book) {
        borrowedBooks.remove(book);
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Borrowed Books: " + borrowedBooks.size();
    }
}

// Library Class
class Library {
    private ArrayList<Book> books;

    // Constructor
    public Library() {
        this.books = new ArrayList<>();
    }

    // Add a Book to the Library
    public void addBook(Book book) {
        books.add(book);
    }

    // Display all Books
    public void displayBooks() {
        System.out.println("Books in the Library:");
        for (int i = 0; i < books.size(); i++) {
            System.out.println((i + 1) + ". " + books.get(i));
        }
    }

    // Borrow a Book
    public Book borrowBook(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title) && book.isAvailable()) {
                book.setAvailable(false);
                return book;
            }
        }
        return null;
    }

    // Return a Book
    public void returnBook(Book book) {
        book.setAvailable(true);
    }
}

// App Class
public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Library library = new Library();

        // Add some initial books
        library.addBook(new Book("The Great Gatsby", "F. Scott Fitzgerald"));
        library.addBook(new Book("1984", "George Orwell"));
        library.addBook(new Book("To Kill a Mockingbird", "Harper Lee"));

        // Patron instance
        Patron patron = new Patron("John Doe");

        while (true) {
            System.out.println("\nLibrary Management System");
            System.out.println("1. View Books");
            System.out.println("2. Borrow a Book");
            System.out.println("3. Return a Book");
            System.out.println("4. View Borrowed Books");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    library.displayBooks();
                    break;

                case 2:
                    System.out.print("Enter the title of the book to borrow: ");
                    String borrowTitle = scanner.nextLine();
                    Book borrowedBook = library.borrowBook(borrowTitle);
                    if (borrowedBook != null) {
                        patron.borrowBook(borrowedBook);
                        System.out.println("You borrowed: " + borrowedBook.getTitle());
                    } else {
                        System.out.println("Book is not available.");
                    }
                    break;

                case 3:
                    System.out.println("Your borrowed books:");
                    ArrayList<Book> borrowedBooks = patron.getBorrowedBooks();
                    if (borrowedBooks.isEmpty()) {
                        System.out.println("No borrowed books to return.");
                        break;
                    }
                    for (int i = 0; i < borrowedBooks.size(); i++) {
                        System.out.println((i + 1) + ". " + borrowedBooks.get(i));
                    }
                    System.out.print("Enter the number of the book to return: ");
                    int returnChoice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    if (returnChoice > 0 && returnChoice <= borrowedBooks.size()) {
                        Book bookToReturn = borrowedBooks.get(returnChoice - 1);
                        patron.returnBook(bookToReturn);
                        library.returnBook(bookToReturn);
                        System.out.println("You returned: " + bookToReturn.getTitle());
                    } else {
                        System.out.println("Invalid choice.");
                    }
                    break;

                case 4:
                    System.out.println("Borrowed Books:");
                    for (Book book : patron.getBorrowedBooks()) {
                        System.out.println(book);
                    }
                    break;

                case 5:
                    System.out.println("Exiting the system. Goodbye!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
