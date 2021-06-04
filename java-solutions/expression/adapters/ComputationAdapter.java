package expression.adapters;

public interface ComputationAdapter<T extends Number> {
    T add(T x, T y);

    T subtract(T x, T y);

    T multiply(T x, T y);

    T divide(T x, T y);

    T negate(T x);

    T abs(T x);

    T square(T x);

    T mod(T x, T y);

    T getValue(String number);

    T getValue(int number);
}
