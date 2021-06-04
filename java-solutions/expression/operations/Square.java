package expression.operations;

import expression.adapters.ComputationAdapter;

public class Square<T extends Number> extends UnaryOperation<T> {
    public static final String symbol = "square";

    public Square(GenericExpression<T> innerValue, ComputationAdapter<T> adapter) {
        super(innerValue, adapter);
    }

    @Override
    public T evaluate(T x, T y, T z) {
        return computationAdapter.square(innerValue.evaluate(x, y, z));
    }

    @Override
    protected String getSymbol() {
        return symbol;
    }
}
