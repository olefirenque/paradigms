package expression.operations;

import expression.adapters.ComputationAdapter;

public class Abs<T extends Number> extends UnaryOperation<T> {
    public static final String symbol = "abs";

    public Abs(GenericExpression<T> innerValue, ComputationAdapter<T> adapter) {
        super(innerValue, adapter);
    }

    @Override
    public T evaluate(T x, T y, T z) {
        return computationAdapter.abs(innerValue.evaluate(x, y, z));
    }

    @Override
    protected String getSymbol() {
        return symbol;
    }
}
