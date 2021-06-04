package expression.genericParser;

import expression.exception.*;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class BaseParser {
    public static final char END = '\0';
    private final CharSource source;
    protected char ch = 0xffff;

    protected BaseParser(final CharSource source) {
        this.source = source;
    }

    protected void nextChar() {
        ch = source.hasNext() ? source.next() : END;
    }

    protected void rollback() {
        ch = source.hasLast() ? source.rollback() : END;
    }

    protected boolean test(char expected) {
        if (ch == expected) {
            nextChar();
            return true;
        }
        return false;
    }

    protected void expect(final char c) {
        if (ch != c) {
            throw error("Expected '" + c + "', found '" + ch + "'");
        }
        nextChar();
    }

    protected void expect(final String value) {
        for (char c : value.toCharArray()) {
            expect(c);
        }
    }

    public void forwardRange(int n) {
        for (int i = 0; i < n; i++) {
            nextChar();
        }
    }

    public void backRange(int n) {
        for (int i = 0; i < n; i++) {
            rollback();
        }
    }

    protected boolean eof() {
        return test(END);
    }

    protected ParseException error(final String message) {
        return source.error(message);
    }

    protected boolean between(final char from, final char to) {
        return from <= ch && ch <= to;
    }

    protected BracketSequenceException bracketSequenceException() {
        return new BracketSequenceException("Bracket sequence isn't correct at position: " + source.getPosition());
    }

    protected ArgumentNotFoundException argumentNotFoundException() {
        return new ArgumentNotFoundException("No argument found at position: " + source.getPosition());
    }

    protected IllegalVariableException illegalVariableException() {
        return new IllegalVariableException("Illegal variable at position: " + source.getPosition());
    }

    protected IllegalValueException illegalValueException() {
        return new IllegalValueException("Illegal value at position: " + source.getPosition());
    }

    protected EndOfExpressionException endOfExpressionException() {
        return new EndOfExpressionException("End of expression expected: " + source.getPosition());
    }
}
