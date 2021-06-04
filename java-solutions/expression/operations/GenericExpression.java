package expression.operations;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface GenericExpression<T extends Number> {
    T evaluate(T x, T y, T z);
}
