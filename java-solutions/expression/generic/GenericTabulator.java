package expression.generic;

import expression.adapters.*;
import expression.genericParser.ExpressionParser;

import java.util.Map;
import java.util.Objects;


public class GenericTabulator implements Tabulator {

    static Map<String, ComputationAdapter<?>> modes = Map.of(
            "i", new IntegerComputatationAdapter(true),
            "u", new IntegerComputatationAdapter(false),
            "d", new DoubleComputationAdapter(),
            "bi", new BigIntegerComputationAdapter(),
            "s", new ShortComputatationAdapter(),
            "l", new LongComputatationAdapter()
    );


    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.out.println("Must be at least 2 arguments (mode/expression)");
            return;
        }
        if (Objects.requireNonNull(args[0]).length() < 1) {
            System.out.println("First argument must be longer than 1 symbol");
            return;
        }
        if (!(args[0].charAt(0) == '-')) {
            System.out.println("First argument must start with '-'");
            return;
        }
        if (modes.get(args[0].substring(1)) == null) {
            System.out.println("Unexpected mode");
            return;
        }
        if (args[1] == null) {
            System.out.println("Expression must be not null");
            return;
        }

        var result = new GenericTabulator().tabulate(args[0].substring(1), args[1], -2, 2, -2, 2, -2, 2);
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                for (int k = 0; k < result[0][0].length; k++) {
                    System.out.printf("x = %d, y = %d, z = %d, result = %s%n", i - 2, j - 2, k - 2, result[i][j][k]);
                }
            }
        }
    }

    @Override
    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) {
        return getTable(modes.get(mode), expression, x1, x2, y1, y2, z1, z2);
    }

    private <T extends Number> Object[][][] getTable(ComputationAdapter<T> adapter, String expression, int x1, int x2, int y1, int y2, int z1, int z2) {
        Object[][][] result = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];
        var parsedExpr = new ExpressionParser<>(adapter).parse(expression);
        for (int i = x1; i <= x2; i++) {
            for (int j = y1; j <= y2; j++) {
                for (int k = z1; k <= z2; k++) {
                    try {
                        result[i - x1][j - y1][k - z1] = parsedExpr.evaluate(adapter.getValue(i), adapter.getValue(j), adapter.getValue(k));
                    } catch (ArithmeticException ignored) {
                    }
                }
            }
        }
        return result;
    }
}
