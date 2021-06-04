package expression.adapters;

public class ShortComputatationAdapter implements ComputationAdapter<Short> {
    @Override
    public Short add(Short x, Short y) {
        return (short) (x + y);
    }

    @Override
    public Short subtract(Short x, Short y) {
        return (short) (x - y);
    }

    @Override
    public Short multiply(Short x, Short y) {
        return (short) (x * y);
    }

    @Override
    public Short divide(Short x, Short y) {
        return (short) (x / y);
    }

    @Override
    public Short negate(Short x) {
        return (short) -x;
    }

    @Override
    public Short abs(Short x) {
        return x >= 0 ? x : negate(x);
    }

    @Override
    public Short square(Short x) {
        return multiply(x, x);
    }

    @Override
    public Short mod(Short x, Short y) {
        return (short) (x % y);
    }

    @Override
    public Short getValue(String number) {
        return Short.parseShort(number);
    }

    @Override
    public Short getValue(int number) {
        return (short) number;
    }
}
