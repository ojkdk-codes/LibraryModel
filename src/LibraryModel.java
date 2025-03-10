/**
 * A Basic Library Model
 * Saves book information in a BST data structure
 *
 * @author Manoj Khadka
 * Last Modified: 2025-01-23
 */

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class LibraryModel {
    private BookNode<Book> root;
    private int size;

    /**
     * Constructs an empty LibraryModel.
     */
    public LibraryModel() {
        root = null;
        size = 0;
    }

    /**
     * Inserts a new Book into the binary search tree.
     * Books are sorted by their name.
     *
     * @param e The book to insert.
     * @return true if insertion is successful, false if the book already exists.
     */
    public boolean insert(Book e) {
        if (root == null) {
            root = new BookNode<>(e);
            size++;
            return true;
        }
        return insert(root, e);
    }

    /**
     * Helper method for inserting a book recursively into the BST.
     *
     * @param root The current root of the subtree.
     * @param e    The book to insert.
     * @return true if inserted, false if the book already exists.
     */
    private boolean insert(BookNode<Book> root, Book e) {
        int compareValue = root.value.compareTo(e);

        if (compareValue == 0) {
            return false; // Duplicate book name
        } else if (compareValue > 0) {
            if (root.leftNode == null) {
                root.leftNode = new BookNode<>(e);
                size++;
                return true;
            }
            return insert(root.leftNode, e);
        } else {
            if (root.rightNode == null) {
                root.rightNode = new BookNode<>(e);
                size++;
                return true;
            }
            return insert(root.rightNode, e);
        }
    }

    /**
     * Searches for a book by its name in the BST.
     *
     * @param bookName The name of the book to look up.
     * @return The Book object if found, null otherwise.
     */
    public Book lookUp(String bookName) {
        return lookUp(root, bookName);
    }

    /**
     * Helper method to search for a book in the BST recursively.
     *
     * @param root     The current root of the subtree.
     * @param bookName The name of the book to look up.
     * @return The Book object if found, null otherwise.
     */
    private Book lookUp(BookNode<Book> root, String bookName) {
        if (root == null) { // No books in the library
            return null;
        }

        int compareValue = root.value.name.compareTo(bookName);

        if (compareValue == 0) {
            return root.value;
        } else if (compareValue > 0) {
            return lookUp(root.leftNode, bookName);
        } else {
            return lookUp(root.rightNode, bookName);
        }
    }

    /**
     * Finds the book with the smallest name in the BST.
     *
     * @return The Book object with the smallest name, or null if the tree is empty.
     */
    public Book findMin() {
        return findMin(root);
    }

    /**
     * Helper method to find the book with the smallest name recursively.
     *
     * @param root The current root of the subtree.
     * @return The Book object with the smallest name.
     */
    private Book findMin(BookNode<Book> root) {
        if (root == null) {
            return null;
        } else if (root.leftNode == null) {
            return root.value;
        } else {
            return findMin(root.leftNode);
        }
    }

    /**
     * Node class representing a book in the BST.
     *
     * @param <Book> The type of the book stored in the node.
     */
    private static class BookNode<Book> {
        private Book value;
        private BookNode<Book> leftNode;
        private BookNode<Book> rightNode;

        public BookNode(Book value) {
            this.value = value;
        }
    }

    /**
     * Models a Book with a name, ISBN, and publication date.
     */
    public static class Book implements Comparable<Book> {
        private String name;
        private String isbn;
        private Date date;

        public Book(String name, String isbn, Date date) {
            this.name = name;
            this.isbn = isbn;
            this.date = date;
        }

        /**
         * Returns a string representation of the book.
         *
         * @return Book details as a formatted string.
         */
        public String toString() {
            return String.format("Book Name: %s\nISBN: %s\nDate: %s", name, isbn, date);
        }

        /**
         * Compares two books based on their names.
         *
         * @param other The other book to compare.
         * @return A negative value if this book comes first,
         *         zero if equal, or a positive value if it comes later.
         */
        public int compareTo(Book other) {
            return this.name.compareTo(other.name);
        }

        /**
         * Represents the publication date of a book.
         */
        private static class Date {
            private int year;
            private int month;
            private int day;

            public Date(int year, int month, int day) {
                this.year = year;
                this.month = month;
                this.day = day;
            }

            /**
             * Returns a string representation of the date.
             *
             * @return Formatted date string.
             */
            public String toString() {
                return String.format("%d-%d-%d", year, month, day);
            }
        }
    }

    public static void main(String[] args) {
        LibraryModel lm = new LibraryModel();

        try {
            Scanner scanner = new Scanner(new FileReader("data.txt"));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] fields = line.split(",");
                String bookName = fields[0];
                String isbn = fields[1];
                String date = fields[2];
                String[] dates = date.split("-");
                int year = Integer.parseInt(dates[0]);
                int month = Integer.parseInt(dates[1]);
                int day = Integer.parseInt(dates[2]);

                // Creating a Book object from file data and inserting it
                Book b = new Book(bookName, isbn, new Book.Date(year, month, day));
                lm.insert(b);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("File Not Found");
        }

        System.out.println(lm.lookUp("Animal Farm"));
        System.out.println(lm.lookUp("War and Peace"));
        System.out.println(lm.findMin());
    }
}