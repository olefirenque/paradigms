package expression.operations;

public class Const<T extends Number> implements GenericExpression<T> {
    T value;

    public Const(T value){
        this.value = value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public T evaluate(T x, T y, T z) {
        return value;
    }

}
