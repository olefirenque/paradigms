package expression.operations;


import expression.adapters.ComputationAdapter;

public class Subtract<T extends Number> extends BinaryOperation<T> {
    public static final Integer priority = 100;
    public static final String symbol = "-";

    public Subtract(GenericExpression<T> left, GenericExpression<T> right, ComputationAdapter<T> adapter) {
        super(left, right, adapter);
    }

    @Override
    public T evaluate(T x, T y, T z) {
        return computationAdapter.subtract(left.evaluate(x, y, z), right.evaluate(x, y, z));
    }

    @Override
    public String getSymbol() {
        return symbol;
    }

    @Override
    protected Integer getPriority() {
        return priority;
    }
}
