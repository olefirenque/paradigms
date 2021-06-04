package expression.adapters;

import expression.exception.CalculationException;
import expression.exception.DivisionByZeroException;
import expression.exception.OverflowException;

public class IntegerComputatationAdapter implements ComputationAdapter<Integer> {
    boolean check = false;

    public IntegerComputatationAdapter(boolean check) {
        this.check = check;
    }

    @Override
    public Integer add(Integer x, Integer y) {
        if (check) {
            overflowAddCheck(x, y);
        }
        return x + y;
    }

    @Override
    public Integer subtract(Integer x, Integer y) {
        if (check) {
            overflowSubCheck(x, y);
        }
        return x - y;
    }

    @Override
    public Integer multiply(Integer x, Integer y) {
        if (check) {
            overflowMulCheck(x, y);
        }
        return x * y;
    }

    @Override
    public Integer divide(Integer x, Integer y) {
        if (check) {
            overflowDivCheck(x, y);
            divisionByZeroCheck(x, y);
        }
        return x / y;
    }

    @Override
    public Integer negate(Integer x) {
        return -x;
    }

    @Override
    public Integer abs(Integer x) {
        return x >= 0 ? x : -x;
    }

    @Override
    public Integer square(Integer x) {
        if (check) {
            overflowMulCheck(x, x);
        }
        return x * x;
    }

    @Override
    public Integer mod(Integer x, Integer y) {
        if (check) {
            divisionByZeroCheck(x, y);
        }
        return x % y;
    }

    private void overflowAddCheck(Integer x, Integer y) {
        if (y > 0) {
            if (x > Integer.MAX_VALUE - y) {
                throw new OverflowException("add");
            }
        } else if (y < 0) {
            if (x < Integer.MIN_VALUE - y) {
                throw new OverflowException("add");
            }
        }
    }

    private void overflowSubCheck(Integer x, Integer y) {
        if (y > 0) {
            if (x < Integer.MAX_VALUE + y) {
                throw new OverflowException("sub");
            }
        } else if (y < 0) {
            if (x > Integer.MIN_VALUE + y) {
                throw new OverflowException("sub");
            }
        }
    }

    private void overflowDivCheck(Integer x, Integer y) {
        if (x == Integer.MIN_VALUE && y == -1) {
            throw new OverflowException("div");
        }
    }

    private void overflowMulCheck(Integer x, Integer y) {
        int max = (x > 0) == (y > 0) ? Integer.MAX_VALUE : Integer.MIN_VALUE;

        if (x == -1 && y == Integer.MIN_VALUE) {
            throw new OverflowException("mul");
        }

        if (x != -1 && x != 0) {
            if (y > 0 && y > max / x) {
                throw new OverflowException("mul");
            } else if ((y < 0 && y < max / x)) {
                throw new OverflowException("mul");
            }
        }
    }

    private void divisionByZeroCheck(Integer x, Integer y) {
        if (y.equals(0)) {
            throw new DivisionByZeroException("div 0");
        }
    }

    @Override
    public Integer getValue(String number) {
        return Integer.parseInt(number);
    }

    @Override
    public Integer getValue(int number) {
        return number;
    }
}
