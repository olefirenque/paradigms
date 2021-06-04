package expression.operations;

import expression.adapters.ComputationAdapter;

public class Negate<T extends Number> extends UnaryOperation<T> {
    public static String symbol = "-";

    public Negate(GenericExpression<T> innerValue, ComputationAdapter<T> adapter) {
        super(innerValue, adapter);
    }

    @Override
    protected String getSymbol() {
        return symbol;
    }

    @Override
    public T evaluate(T x, T y, T z) {
        return computationAdapter.negate(innerValue.evaluate(x, y, z));
    }
}
