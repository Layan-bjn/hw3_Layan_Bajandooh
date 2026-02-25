//FileReader.java
import java.io.*;
import java.util.*;

public class FileReader implements Runnable {
    private File catalogFile;
    private ArrayList<Book> bookList;

    public FileReader(File file, ArrayList<Book> list) {
        this.catalogFile = file;
        this.bookList = list;
    }

    @Override
    public void run() {
        try {
            Scanner src = new Scanner(catalogFile);
            while (src.hasNextLine()) {
                String line = src.nextLine();
                try {
                    if (line.trim().isEmpty())
                        continue;
                    bookList.add(LibraryBookTracker.parseBook(line));
                    LibraryBookTracker.validRecords++;
                } catch (Exception e) {
                    LibraryBookTracker.errorsCount++;
                    LibraryBookTracker.logError(line, e);
                }
            }
            src.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }
}
