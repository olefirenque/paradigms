package expression.genericParser;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface CharSource {
    boolean hasNext();
    boolean hasLast();
    char next();
    char rollback();
    int getPosition();
    ParseException error(final String message);
}
