package expression.genericParser;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class ParseException extends RuntimeException {
    public ParseException(final String message) {
        super(message);
    }
}
