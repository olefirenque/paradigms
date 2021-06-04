const variableIndexes = {
    'x': 0,
    'y': 1,
    'z': 2
};

const {
    AbstractOperation,
    Add,
    Subtract,
    Multiply,
    Divide,
    Negate,
    Const,
    Variable,
    Sumsq,
    Length,
    Cbrt,
    Cube
} = (function () {
    class AbstractOperation {
        constructor(...innerValues) {
            this.values = innerValues;
        }

        evaluate(...args) {
            return this.operation(...this.values.map(g => g.evaluate(...args)));
        }

        print(op) {
            return this.values.map(op).join(' ');
        };

        toString() {
            return `${this.print(x => x.toString())} ${this.sign}`;
        }

        prefix() {
            return `(${this.sign} ${this.print(x => x.prefix())})`;
        }

        postfix() {
            return `(${this.print(x => x.postfix())} ${this.sign})`;
        }
    }

    function operationFactory(sign, op, differentiate) {
        return class extends AbstractOperation {
            get sign() {
                return sign;
            };

            static get arity() {
                return op.length;
            };

            operation(...args) {
                return op(...args);
            };

            diff(variable) {
                return differentiate.call(this, this.values.map(x => x.diff(variable)));
            };
        };
    }

    function multiplyFunctionByDerivative(df) {
        let expr = constants.Zero;
        for (let i = 0; i < df.length; i++) {
            expr = new Add(expr, new Multiply(this.values[i], df[i]));
        }
        return expr;
    }

    const Cube = operationFactory('cube',
        x => x * x * x,
        function (df) {
            return new Multiply(constants.Three, new Multiply(new Multiply(this.values[0], this.values[0]), df[0]));
        }
    );

    const Cbrt = operationFactory('cbrt',
        Math.cbrt,
        function (df) {
            return new Divide(df[0], new Multiply(constants.Three, new Multiply(this, this)));
        }
    );

    const Sumsq = operationFactory('sumsq',
        (...args) => args.reduce((acc, cur) => acc + cur * cur, 0),
        function (df) {
            return new Multiply(multiplyFunctionByDerivative.call(this, df), constants.Two);
        }
    );

    const Length = operationFactory('length',
        (...args) => Math.sqrt(args.reduce((acc, cur) => (acc + cur * cur), 0)),
        function (df) {
            return new Divide(multiplyFunctionByDerivative.call(this, df), this);
        }
    );

    const Add = operationFactory('+',
        (x, y) => x + y,
        function (df) {
            return new Add(...df);
        }
    );

    const Subtract = operationFactory('-',
        (x, y) => x - y,
        function (df) {
            return new Subtract(...df);
        }
    );

    const Multiply = operationFactory('*',
        (x, y) => x * y,
        function (df) {
            return new Add(new Multiply(df[0], this.values[1]), new Multiply(this.values[0], df[1]));
        }
    );

    const Divide = operationFactory('/',
        (x, y) => x / y,
        function (df) {
            return new Divide(new Divide(new Subtract(
                new Multiply(df[0], this.values[1]),
                new Multiply(this.values[0], df[1])),
                this.values[1]), this.values[1]);
        }
    );

    const Negate = operationFactory('negate',
        x => -x,
        function (df) {
            return new Negate(df[0]);
        }
    );

    class AbstractValue extends AbstractOperation {
        evaluate(...args) {
            return this.operation(...args);
        }

        toString() {
            return `${this.x}`;
        }

        prefix() {
            return this.toString();
        }

        postfix() {
            return this.toString();
        }
    }

    function valueFactory(init, op, differentiate) {
        return class extends AbstractValue {
            constructor(x) {
                super();
                init.call(this, x);
            }

            operation(...args) {
                return op.call(this, ...args);
            };

            diff(variable) {
                return differentiate.call(this, variable);
            };
        };
    }

    const Const = valueFactory(
        function (x) {
            this.x = x;
        },
        function () {
            return this.x;
        },
        () => constants.Zero
    );

    const Variable = valueFactory(
        function (x) {
            this.x = x;
            this.index = variableIndexes[x];
        },
        function (...args) {
            return args[this.index];
        },
        function (variable) {
            return this.x === variable ? constants.One : constants.Zero;
        }
    );

    const constants = {
        'Zero': new Const(0),
        'One': new Const(1),
        'Two': new Const(2),
        'Three': new Const(3),
    };
    return {AbstractOperation, Add, Subtract, Multiply, Divide, Negate, Const, Variable, Sumsq, Length, Cbrt, Cube}
})();

const operations = new Map([
    ['+', Add],
    ['-', Subtract],
    ['*', Multiply],
    ['/', Divide],
    ['negate', Negate],
    ['sumsq', Sumsq],
    ['length', Length],
    ['cube', Cube],
    ['cbrt', Cbrt],
]);

const splitExpression = expression => expression.split(" ").filter(c => c !== '');

const parse = (expression) => splitExpression(expression).reduce((acc, cur) => {
    if (operations.has(cur)) {
        const operation = operations.get(cur);
        acc.push(new operation(...acc.splice(-operation.arity)));
    } else if (cur in variableIndexes) {
        acc.push(new Variable(cur));
    } else {
        acc.push(new Const(parseFloat(cur)));
    }
    return acc;
}, []).pop();


const {
    FalseOperationError,
    InvalidNumberOfArgumentsError,
    InvalidNumberError,
    InvalidArgumentsError,
    EmptyOperationError,
    InvalidBracketSequenceError,
    NoInputError,
    ExcessiveInfoError,
    checkIfNotOperation
} = (function () {
    class ParseError extends Error {
        constructor(message, position) {
            super(`${message} at position ${position}`
            );
        }

        get name() {
            return this.constructor.name;
        }
    }

    function ExceptionFactory() {
        return class extends ParseError {
        }
    }

    return {
        FalseOperationError: ExceptionFactory(),
        InvalidNumberOfArgumentsError: ExceptionFactory(),
        InvalidNumberError: ExceptionFactory(),
        InvalidArgumentsError: ExceptionFactory(),
        InvalidBracketSequenceError: ExceptionFactory(),
        EmptyOperationError: ExceptionFactory(),
        NoInputError: ExceptionFactory(),
        ExcessiveInfoError: ExceptionFactory(),
        checkIfNotOperation: function checkIfNotOperation(str, ptr) {
            if (str in variableIndexes) {
                throw new FalseOperationError(`Expected operation, found variable ${str}`, ptr);
            } else if (!isNaN(str)) {
                throw new FalseOperationError(`Expected operation, found const ${str}`, ptr);
            } else if (str === undefined) {
                throw new EmptyOperationError(`Empty operation`, ptr);
            }
        }
    }
})();

function isLetter(char) {
    return char.toLowerCase() !== char.toUpperCase();
}

const modes = {
    PREFIX: "prefix",
    POSTFIX: "postfix",
};

class Parser {
    constructor(source, mode) {
        this.source = source;
        this.mode = mode;
        this.ptr = 0;
    }

    next() {
        this.ch = this.source[this.ptr] ? this.source[this.ptr] : '\0';
        this.ptr++;
        return this.ch;
    }

    skipWhitespaces() {
        while (this.ch === ' ') {
            this.next();
        }
    }

    parse() {
        if (this.source.length === 0) {
            throw new NoInputError('Empty input', this.ptr);
        }
        this.next();
        this.skipWhitespaces();
        const result = this.parseValue();
        this.skipWhitespaces();
        if (this.ptr <= this.source.length) {
            throw new ExcessiveInfoError(`Excessive info`, this.ptr);
        }
        return result;
    }

    parseExpression() {
        this.next();
        const args = this.parseArguments();
        const operationSign = this.mode === modes.PREFIX ? args.shift() : args.pop();
        checkIfNotOperation(operationSign, this.ptr);
        if (!args.every(x => x instanceof AbstractOperation)) {
            throw new InvalidArgumentsError(`Invalid arguments`, this.ptr);
        }
        const operation = operations.get(operationSign);
        if (operation.arity !== 0 && args.length !== operation.arity) {
            throw new InvalidNumberOfArgumentsError(`Invalid number of arguments, expected ${operation.arity} argument(s), found ${args.length}`, this.ptr);
        }
        this.skipWhitespaces();
        return new operation(...args);
    }

    parseOperation() {
        const operationSign = this.parseString();
        this.skipWhitespaces();
        if (!operations.has(operationSign)) {
            checkIfNotOperation(operationSign, this.ptr);
            throw new FalseOperationError(`Unknown operation ${operationSign}`, this.ptr);
        }
        return operationSign;
    }

    parseArguments() {
        let args = [];
        while (this.ch !== ')') {
            if (this.ptr >= this.source.length) {
                throw new InvalidBracketSequenceError(`Missing )`, this.ptr);
            }
            args.push(this.parseValue());
        }
        this.next();
        return args;
    }

    parseValue() {
        this.skipWhitespaces();
        if (!isNaN(this.ch) || this.ch === '-') {
            const result = this.parseString();
            if (result === '-') {
                this.ptr--;
                this.ch = this.source[this.ptr - 1];
                return this.parseOperation();
            }
            const number = +result;
            if (isNaN(number)) {
                throw new InvalidNumberError(`Invalid number: ${this.ch} after ${result}`, this.ptr);
            }
            this.skipWhitespaces();
            return new Const(number);
        } else if (this.ch === '(') {
            const expression = this.parseExpression();
            this.skipWhitespaces();
            return expression;
        } else if (isLetter(this.ch)) {
            const variable = this.ch;
            if (!(variable in variableIndexes)) {
                return this.parseOperation();
            }
            this.next();
            this.skipWhitespaces();
            return new Variable(variable);
        } else {
            return this.parseOperation();
        }
    }

    parseString() {
        this.skipWhitespaces();
        const start = this.ptr;
        while (!' ()\0'.includes(this.next())) {
        }
        return this.source.substring(start - 1, this.ptr - 1);
    }
}

const parsePrefix = (input) => new Parser(input, modes.PREFIX).parse();
const parsePostfix = (input) => new Parser(input, modes.POSTFIX).parse();