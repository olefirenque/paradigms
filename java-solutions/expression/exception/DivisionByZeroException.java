package expression.exception;

public class DivisionByZeroException extends CalculationException {
    public DivisionByZeroException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
