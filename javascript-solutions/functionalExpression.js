"use strict";

const variableIndexes = {
    "x": 0,
    "y": 1,
    "z": 2
}

const variable = variable => {
    const ind = variableIndexes[variable];
    return (...args) => (args[ind]);
}

const splitExpression = expression => expression.split(" ").filter((c) => (c !== ''))

const cnst = x => _ => x;

const applyOperation = f => (...args) => (...values) => f(...args.map((g) => g(...values)));

const add = applyOperation((x, y) => x + y);
const subtract = applyOperation((x, y) => x - y);
const multiply = applyOperation((x, y) => x * y);
const divide = applyOperation((x, y) => x / y);
const negate = applyOperation(x => -x);

const one = cnst(1);
const two = cnst(2);

const min5 = applyOperation(Math.min);
const max3 = applyOperation(Math.max);


const operations = new Map([
    ["+", {operation: add, arity: 2}],
    ["-", {operation: subtract, arity: 2}],
    ["*", {operation: multiply, arity: 2}],
    ["/", {operation: divide, arity: 2}],
    ["negate", {operation: negate, arity: 1}],
    ["min5", {operation: min5, arity: 5}],
    ["max3", {operation: max3, arity: 3}],
])

const constants = new Map([
    ["one", one],
    ["two", two]
])

const parse = (expression) => splitExpression(expression).reduce((acc, cur) => {
    if (operations.has(cur)) {
        const {operation, arity} = operations.get(cur);
        acc.push(operation(...acc.splice(-arity)));
    } else if (constants.has(cur)) {
        acc.push(constants.get(cur));
    } else if (cur in variableIndexes) {
        acc.push(variable(cur));
    } else {
        acc.push(cnst(parseInt(cur)));
    }
    return acc;
}, [])[0];

const expression = parse("x x * x 2 * - 1 +");
for (let i = 0; i <= 10; i++) {
    println(expression(i))
}