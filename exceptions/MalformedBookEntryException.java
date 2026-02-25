//MalformedBookEntryException.java
package exceptions;

public class MalformedBookEntryException extends BookCatalogException {
    public MalformedBookEntryException(String message) {
        super(message);
    }
}
