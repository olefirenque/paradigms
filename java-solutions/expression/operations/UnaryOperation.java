package expression.operations;


import expression.adapters.ComputationAdapter;

public abstract class UnaryOperation<T extends Number> implements GenericExpression<T> {
    protected Integer priority = 1000;

    protected GenericExpression<T> innerValue;
    protected ComputationAdapter<T> computationAdapter;

    public UnaryOperation(GenericExpression<T> innerValue, ComputationAdapter<T> adapter) {
        this.innerValue = innerValue;
        this.computationAdapter = adapter;
    }

    abstract protected String getSymbol();

    @Override
    public String toString() {
        return getSymbol() + innerValue.toString();
    }
}
