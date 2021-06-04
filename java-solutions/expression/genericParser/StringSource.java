package expression.genericParser;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class StringSource implements CharSource {
    private final String data;
    private int pos;

    public StringSource(final String data) {
        this.data = data;
    }

    @Override
    public boolean hasNext() {
        return pos < data.length();
    }

    @Override
    public boolean hasLast() {
        return pos > 0;
    }


    @Override
    public char next() {
        return data.charAt(pos++);
    }

    @Override
    public char rollback() {
        return data.charAt(--pos);
    }

    @Override
    public int getPosition() {
        return pos + 1;
    }


    @Override
    public ParseException error(final String message) {
        return new ParseException(pos + ": " + message);
    }
}
