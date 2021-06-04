package expression;

import expression.adapters.ComputationAdapter;
import expression.operations.*;
import expression.operations.Variable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class ExpressionFactory<T extends Number> {
    ComputationAdapter<T> adapter;

    // Map: Operation -> Priority
    private final Map<String, Integer> priorityOfOperation = new HashMap<>();

    // Map: Operation -> Constructor
    private final Map<String, BiFunction<GenericExpression<T>, GenericExpression<T>, GenericExpression<T>>> constructorOfOperation = new HashMap<>();

    private final List<Integer> priorityList = new ArrayList<>();

    public ExpressionFactory(ComputationAdapter<T> adapter) {
        this.adapter = adapter;

        addOperation(Add.symbol, Add.priority, this::Add);
        addOperation(Subtract.symbol, Subtract.priority, this::Subtract);
        addOperation(Multiply.symbol, Multiply.priority, this::Multiply);
        addOperation(Divide.symbol, Divide.priority, this::Divide);
        addOperation(Mod.symbol, Mod.priority, this::Mod);

        priorityList.addAll(priorityOfOperation.values().stream().distinct().sorted().collect(Collectors.toList()));
    }

    public void addOperation(String symbol, Integer priority, BiFunction<GenericExpression<T>, GenericExpression<T>, GenericExpression<T>> op) {
        constructorOfOperation.put(symbol, op);
        priorityOfOperation.put(symbol, priority);
    }

    public Integer getPriority(String operation) {
        return priorityOfOperation.get(operation);
    }

    public boolean hasCurrentPriority(int priorityKey, String operation) {
        return priorityList.get(priorityKey).equals(getPriority(operation));
    }

    public boolean hasOperation(String operation) {
        return constructorOfOperation.containsKey(operation);
    }

    public BiFunction<GenericExpression<T>, GenericExpression<T>, GenericExpression<T>> getConstructor(String operation) {
        return constructorOfOperation.get(operation);
    }

    public boolean isLastOperation(int key) {
        return key == priorityList.size() - 1;
    }

    public Add<T> Add(GenericExpression<T> first, GenericExpression<T> second) {
        return new Add<>(first, second, adapter);
    }

    public Subtract<T> Subtract(GenericExpression<T> first, GenericExpression<T> second) {
        return new Subtract<>(first, second, adapter);
    }

    public Multiply<T> Multiply(GenericExpression<T> first, GenericExpression<T> second) {
        return new Multiply<>(first, second, adapter);
    }

    public Divide<T> Divide(GenericExpression<T> first, GenericExpression<T> second) {
        return new Divide<>(first, second, adapter);
    }

    public Mod<T> Mod(GenericExpression<T> first, GenericExpression<T> second) {
        return new Mod<>(first, second, adapter);
    }

    public Square<T> Square(GenericExpression<T> inner) {
        return new Square<>(inner, adapter);
    }

    public Abs<T> Abs(GenericExpression<T> inner) {
        return new Abs<>(inner, adapter);
    }

    public Const<T> Const(T inner) {
        return new Const<>(inner);
    }

    public Variable<T> Variable(String name) {
        return new Variable<>(name);
    }

    public Negate<T> Negate(GenericExpression<T> inner) {
        return new Negate<>(inner, adapter);
    }

}