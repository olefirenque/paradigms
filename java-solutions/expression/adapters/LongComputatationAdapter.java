package expression.adapters;

public class LongComputatationAdapter implements ComputationAdapter<Long> {
    @Override
    public Long add(Long x, Long y) {
        return x + y;
    }

    @Override
    public Long subtract(Long x, Long y) {
        return x - y;
    }

    @Override
    public Long multiply(Long x, Long y) {
        return x * y;
    }

    @Override
    public Long divide(Long x, Long y) {
        return x / y;
    }

    @Override
    public Long negate(Long x) {
        return -x;
    }

    @Override
    public Long abs(Long x) {
        return x >= 0 ? x : -x;
    }

    @Override
    public Long square(Long x) {
        return x * x;
    }

    @Override
    public Long mod(Long x, Long y) {
        return x % y;
    }

    @Override
    public Long getValue(String number) {
        return Long.parseLong(number);
    }

    @Override
    public Long getValue(int number) {
        return (long) number;
    }
}
