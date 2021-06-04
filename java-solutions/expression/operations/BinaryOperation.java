package expression.operations;

import expression.adapters.ComputationAdapter;

public abstract class BinaryOperation<T extends Number> implements GenericExpression<T> {
    protected GenericExpression<T> left;
    protected GenericExpression<T> right;
    protected ComputationAdapter<T> computationAdapter;

    public BinaryOperation(GenericExpression<T> left, GenericExpression<T> right, ComputationAdapter<T> adapter) {
        this.left = left;
        this.right = right;
        this.computationAdapter = adapter;
    }

    public abstract String getSymbol();

    protected abstract Integer getPriority();

    @Override
    public String toString() {
        return "(" + left.toString() + " " + getSymbol() + " " + right.toString() + ")";
    }

}
