package expression.genericParser;

import expression.*;
import expression.adapters.ComputationAdapter;
import expression.operations.GenericExpression;

public class ExpressionParser<T extends Number> {

    ComputationAdapter<T> adapter;
    ExpressionFactory<T> factory;


    public ExpressionParser(ComputationAdapter<T> adapter) {
        this.adapter = adapter;
        this.factory = new ExpressionFactory<>(adapter);
    }

    public GenericExpression<T> parse(final String source) {
        return parse(new StringSource(source));
    }

    public GenericExpression<T> parse(CharSource source) {
        return new Parser(source).parse();
    }

    private class Parser extends BaseParser {

        public Parser(final CharSource source) {
            super(source);
            nextChar();
        }

        public String parseOperation() {
            skipWhitespace();

            switch (ch) {
                case '+':
                case '-':
                case '*':
                case '/':
                    return String.valueOf(ch);
                default:
                    String op = stringTerm();
                    backRange(op.length());
                    if (factory.hasOperation(op)) {
                        return op;
                    }
            }
            return "undefined";
        }

        public GenericExpression<T> parse() {
            skipWhitespace();
            final GenericExpression<T> result = parseTerm();
            if (test(')')) {
                throw bracketSequenceException();
            }
            if (eof()) {
                return result;
            }
            throw endOfExpressionException();
        }

        public GenericExpression<T> parseTerm() {
            return parseTerm(0);
        }

        public GenericExpression<T> parseTerm(Integer priorityKey) {
            GenericExpression<T> result;
            skipWhitespace();
            if (factory.isLastOperation(priorityKey)) {
                result = numberTerm();
            } else {
                result = parseTerm(priorityKey + 1);
            }
            while (true) {
                String operation = parseOperation();
                skipWhitespace();
                if (factory.hasCurrentPriority(priorityKey, operation)) {
                    skipWhitespace();
                    forwardRange(operation.length());
                    var operationConstructor = factory.getConstructor(operation);
                    if (factory.isLastOperation(priorityKey)) {
                        result = operationConstructor.apply(result, numberTerm());
                    } else {
                        result = operationConstructor.apply(result, parseTerm(priorityKey + 1));
                    }
                } else {
                    return result;
                }
            }
        }

        public GenericExpression<T> numberTerm() {
            GenericExpression<T> result;
            skipWhitespace();
            if (test('(')) {
                result = parseTerm();
                if (!test(')')) {
                    throw bracketSequenceException();
                }
            } else if (test('-')) {
                if (Character.isDigit(ch)) {
                    result = constTerm("-");
                } else {
                    result = factory.Negate(numberTerm());
                }
            } else if (Character.isDigit(ch)) {
                result = constTerm("");
            } else if (Character.isLetter(ch)) {
                String term = stringTerm();
                switch (term) {
                    case "x":
                    case "y":
                    case "z":
                        result = factory.Variable(term);
                        break;
                    case "abs":
                        result = factory.Abs(numberTerm());
                        break;
                    case "square":
                        result = factory.Square(numberTerm());
                        break;
                    default:
                        throw illegalVariableException();
                }
            } else {
                throw argumentNotFoundException();
            }
            skipWhitespace();
            return result;
        }

        public GenericExpression<T> constTerm(String prefix) {
            StringBuilder sb = new StringBuilder(prefix);
            while (Character.isDigit(ch)) {
                sb.append(ch);
                nextChar();
            }
            skipWhitespace();
            try {
                return factory.Const(adapter.getValue(sb.toString()));
            } catch (NumberFormatException e) {
                throw illegalVariableException();
            }
        }

        public String stringTerm() {
            skipWhitespace();
            StringBuilder sb = new StringBuilder();
            while (Character.isLetter(ch)) {
                sb.append(ch);
                nextChar();
            }
            return sb.toString();
        }

        private void skipWhitespace() {
            while (test(' ') || test('\r') || test('\n') || test('\t')) {
                // skip
            }
        }
    }
}