package expression.adapters;

import expression.exception.CalculationException;
import expression.exception.DivisionByZeroException;

import java.math.BigInteger;

public class BigIntegerComputationAdapter implements ComputationAdapter<BigInteger> {

    @Override
    public BigInteger add(BigInteger x, BigInteger y) {
        return x.add(y);
    }

    @Override
    public BigInteger subtract(BigInteger x, BigInteger y) {
        return x.subtract(y);
    }

    @Override
    public BigInteger multiply(BigInteger x, BigInteger y) {
        return x.multiply(y);
    }

    @Override
    public BigInteger divide(BigInteger x, BigInteger y) {
        divisionByZeroCheck(x, y);
        return x.divide(y);
    }

    @Override
    public BigInteger negate(BigInteger x) {
        return x.negate();
    }

    @Override
    public BigInteger abs(BigInteger x) {
        return x.abs();
    }


    @Override
    public BigInteger square(BigInteger x) {
        return x.multiply(x);
    }

    @Override
    public BigInteger mod(BigInteger x, BigInteger y) {
        modulusPositiveCheck(x, y);
        return x.mod(y);
    }

    @Override
    public BigInteger getValue(String number) {
        return new BigInteger(number);
    }

    @Override
    public BigInteger getValue(int number) {
        return BigInteger.valueOf(number);
    }

    private void modulusPositiveCheck(BigInteger x, BigInteger y) {
        if (y.compareTo(BigInteger.ZERO) <= 0) {
            throw new CalculationException("Modulus is not positive!");
        }
    }

    private void divisionByZeroCheck(BigInteger x, BigInteger y) {
        if (y.equals(BigInteger.ZERO)) {
            throw new DivisionByZeroException("Division by zero!");
        }
    }
}
