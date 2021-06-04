package expression.adapters;

public class DoubleComputationAdapter implements ComputationAdapter<Double> {

    @Override
    public Double add(Double x, Double y) {
        return x + y;
    }

    @Override
    public Double subtract(Double x, Double y) {
        return x - y;
    }

    @Override
    public Double multiply(Double x, Double y) {
        return x * y;
    }

    @Override
    public Double divide(Double x, Double y) {
        return x / y;
    }

    @Override
    public Double negate(Double x) {
        return -x;
    }

    @Override
    public Double abs(Double x) {
        return x >= 0 ? x : -x;
    }

    @Override
    public Double square(Double x) {
        return x * x;
    }

    @Override
    public Double mod(Double x, Double y) {
        return x % y;
    }

    @Override
    public Double getValue(String number) {
        return Double.parseDouble(number);
    }

    @Override
    public Double getValue(int number) {
        return (double) number;
    }
}
