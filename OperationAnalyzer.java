// OperationAnalyzer.java
import java.io.File;
import java.util.ArrayList;

public class OperationAnalyzer implements Runnable {
    private String operation;
    private ArrayList<Book> bookList;
    private File catalogFile;

    public OperationAnalyzer(String operation, ArrayList<Book> bookList, File catalogFile) {
        this.operation = operation;
        this.bookList = bookList;
        this.catalogFile = catalogFile;
    }

    @Override
    public void run() {
        if (operation.contains(":")) {
            try {
                Book newBook = LibraryBookTracker.parseBook(operation);
                bookList.add(newBook);
                LibraryBookTracker.sortBooks(bookList);
                LibraryBookTracker.writeToCatalog(catalogFile, bookList);
                LibraryBookTracker.booksAdded++;
                System.out.println("Book added successfully:");
                LibraryBookTracker.printHeader();
                LibraryBookTracker.printBookDetails(newBook);
            } catch (Exception e) {
                LibraryBookTracker.errorsCount++;
                LibraryBookTracker.logError(operation, e);
                System.out.println("Error adding book: " + e.getMessage());
            }
        } else if (operation.length() == 13 && operation.matches("\\d+")) {
            try {
                LibraryBookTracker.startISBNSearch(bookList, operation);
            } catch (exceptions.DuplicateISBNException e) {
                LibraryBookTracker.errorsCount++;
                LibraryBookTracker.logError(operation, e);
                System.out.println(e.getMessage());
            }
        } else {
            LibraryBookTracker.startTitleSearch(bookList, operation);
        }
    }
}
