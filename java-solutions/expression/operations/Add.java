package expression.operations;

import expression.adapters.ComputationAdapter;

public class Add<T extends Number> extends BinaryOperation<T> {
    public static final Integer priority = 100;
    public static final String symbol = "+";

    public Add(GenericExpression<T> left, GenericExpression<T> right, ComputationAdapter<T> adapter) {
        super(left, right, adapter);
    }

    @Override
    public String getSymbol() {
        return symbol;
    }

    @Override
    protected Integer getPriority() {
        return priority;
    }

    @Override
    public T evaluate(T x, T y, T z) {
        return computationAdapter.add(left.evaluate(x, y, z), right.evaluate(x, y, z));
    }
}
