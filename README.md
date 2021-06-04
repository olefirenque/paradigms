# Тесты к курсу «Парадигмы программирования»

[Условия домашних заданий](http://www.kgeorgiy.info/courses/paradigms/homeworks.html)

## Домашнее задание 14. Разбор выражений на Prolog

Модификации
 * *Base* (32-33)
    * Код должен находиться в файле `prolog-solutions/expression.pl`.
    * [Исходный код тестов](prolog/prtest/parsing/ParserTest.java)
        * Запускать c указанием модификации и сложности (`easy` или `hard`).
 * *Variables*. Дополнительно реализовать поддержку:
    * Переменных, состоящих из произвольного количества букв `XYZ` в любом регистре
        * Настоящее имя переменной определяется первой буквой ее имени
 * *VarSinhCosh* (36-37). Сделать модификацию *Variables* и дополнительно реализовать поддержку:
    * унарных операций:
        * `op_sinh` (`sinh`) – гиперболический синус, `sinh(3)` немного больше 10;
        * `op_cosh` (`cosh`) – гиперболический косинус, `cosh(3)` немного меньше 10.

## Домашнее задание 13. Дерево поиска на Prolog

Модификации
 * *Базовая*
    * Код должен находиться в файле `prolog-solutions/tree-map.pl`.
    * [Исходный код тестов](prolog/prtest/tree/PrologTreeTest.java)
        * Запускать c аргументом `easy` или `hard`
 * *Last* (36-37)
    * Добавьте правила:
        * `map_getLast(Map, (Key, Value))`, возвращающее максимальную пару;
        * `map_removeLast(Map, Result)`, удаляющее максимальную пару.
    * [Исходный код тестов](prolog/prtest/tree/PrologTreeLastTest.java)


## Домашнее задание 12. Простые числа на Prolog

Модификации
 * *Базовая*
    * Код должен находиться в файле `prolog-solutions/primes.pl`.
    * [Исходный код тестов](prolog/prtest/primes/PrologPrimesTest.java)
        * Запускать c аргументом `easy`, `hard` или `bonus`
 * *Gcd* (36-37)
    * Добавьте правило `gcd(A, B, GCD)`,
      подсчитывающее НОД(`A`, `B`) через разложение на простые множители:
      `gcd(4, 6, 2)`.
    * [Исходный код тестов](prolog/prtest/primes/PrologGcdTest.java)


## Домашнее задание 11. Комбинаторные парсеры

Модификации
 * *Базовая*
    * Код должен находиться в файле `clojure-solutions/expression.clj`.
    * [Исходный код тестов](clojure/cljtest/parsing/ParserTest.java)
        * Запускать c указанием модификации и сложности (`easy` или `hard`).
 * *PowLog* (36-37). Сделать модификацию *Variables* и дополнительно реализовать поддержку:
    * Бинарных правоассоциативных операций максимального приоритета:
        * `IPow` (`**`) – возведения в степень:
            `4 ** 3 ** 2` равно `4 ** (3 ** 2)` равно 262144
        * `ILog` (`//`) – взятия логарифма:
            `8 // 9 // 3` равно `8 // (9 // 3)` равно 3


## Домашнее задание 10. Объектные выражения на Clojure

Модификации
 * *Base*
    * Код должен находиться в файле `clojure-solutions/expression.clj`.
    * [Исходный код тестов](clojure/cljtest/object/ObjectTest.java)
        * Запускать c указанием модификации и сложности (`easy` или `hard`).
 * *SumAvg* (36-37). Дополнительно реализовать поддержку:
    * операций произвольного числа аргументов:
        * `Sum` (`sum`) – сумма, `(sum 1 2 3)` равно 6;
        * `Avg` (`avg`) – арифметическое среднее, `(avg 1 2 3)` равно 2;


## Домашнее задание 9. Функциональные выражения на Clojure

Модификации
 * *Base*
    * Код должен находиться в файле `clojure-solutions/expression.clj`.
    * [Исходный код тестов](clojure/cljtest/functional/FunctionalTest.java)
        * Запускать c указанием модификации и сложности (`easy` или `hard`).
 * *SumAvg* (36-37). Дополнительно реализовать поддержку:
    * операций произвольного числа аргументов:
        * `sum` – сумма, `(sum 1 2 3)` равно 6;
        * `avg` – среднее, `(avg 1 2 3)` равно 2;


## Домашнее задание 8. Линейная алгебра на Clojure

Модификации
 * *Базовая*
    * Код должен находиться в файле `clojure-solutions/linear.clj`.
    * [Исходный код тестов](clojure/cljtest/linear/LinearTest.java)
        * Запускать c аргументом `easy` или `hard`
 * *Simplex* (36-37)
    * Назовем _симплексом_ многомерную таблицу чисел, 
      такую что для некоторого `n` в ней существуют все значения
      с суммой индексов не превышающей `n` и только эти значения.
    * Добавьте операции поэлементного сложения (`x+`),
        вычитания (`x-`) и умножения (`x*`) симплексов.
        Например, `(x+ [[1 2] [3]] [[5 6] [7]])` должно быть равно `[[6 8] [10]]`.
    * [Исходный код тестов](clojure/cljtest/linear/SimplexTest.java)
   

## Домашнее задание 7. Обработка ошибок на JavaScript

Модификации
 * *Base*
    * Код должен находиться в файле `javascript-solutions/objectExpression.js`.
    * [Исходный код тестов](javascript/jstest/prefix/ParserTest.java)
        * Запускать c указанием модификации и сложности (`easy` или `hard`).
 * *Postfix*: *SumsqLength* (36-37). Дополнительно реализовать поддержку:
    * выражений в постфиксной записи: `(2 3 +)` равно 5
    * операций произвольного числа аргументов:
        * `Sumsq` (`sumsq`) – сумма квадратов, `(1 2 3 sumsq)` равно 14;
        * `Length` (`length`у) – длина вектора, `(3 4 length)` равно 5;
    * [Исходный код тестов](javascript/jstest/prefix/PostfixTest.java)


## Домашнее задание 6. Объектные выражения на JavaScript

Модификации
 * *Base*
    * Код должен находиться в файле `javascript-solutions/objectExpression.js`.
    * [Исходный код тестов](javascript/jstest/object/ObjectTest.java)
        * Запускать c указанием модификации и сложности (`easy`, `hard` или `bonus`).
 * *Cube* (36, 37). Дополнительно реализовать поддержку:
    * унарных функций:
        * `Cube` (`cube`) – возведение в куб, `3 cube` равно 27;
        * `Cbrt` (`cbrt`) – извлечение кубического корня, `-27 cbrt` равно −3;


## Домашнее задание 5. Функциональные выражения на JavaScript

Модификации
 * *Базовая*
    * Код должен находиться в файле `javascript-solutions/functionalExpression.js`.
    * [Исходный код тестов](javascript/jstest/functional/ExpressionTest.java)
        * Запускать c аргументом `hard` или `easy`;
 * *Mini* (для тестирования)
    * Не поддерживаются бинарные операции
    * Код находится в файле [functionalMiniExpression.js](javascript/MiniExpression.js).
    * [Исходный код тестов](javascript/jstest/functional/MiniTest.java)
        * Запускать c аргументом `hard` или `easy`;
 * *OneMinMax* (36, 37). Дополнительно реализовать поддержку:
    * переменных: `y`, `z`;
    * констант:
        * `one` – 1;
        * `two` – 2;
    * операций:
        * `min5` – минимальный из пяти аргументов, `3 1 4 0 2 min5` равно 0;
        * `max3` – максимальный из трех аргументов, `3 1 4 max3` равно 4.
    * [Исходный код тестов](javascript/jstest/functional/FunctionalTest.java)
   

## Домашнее задание 4. Вычисление в различных типах

Модификации
 * *Базовая*
    * Класс `GenericTabulator` должен реализовывать интерфейс
      [Tabulator](java/expression/generic/Tabulator.java) и
      сроить трехмерную таблицу значений заданного выражения.
        * `mode` – режим вычислений:
           * `i` – вычисления в `int` с проверкой на переполнение;
           * `d` – вычисления в `double` без проверки на переполнение;
           * `bi` – вычисления в `BigInteger`.
        * `expression` – выражение, для которого надо построить таблицу;
        * `x1`, `x2` – минимальное и максимальное значения переменной `x` (включительно)
        * `y1`, `y2`, `z1`, `z2` – аналогично для `y` и `z`.
        * Результат: элемент `result[i][j][k]` должен содержать
          значение выражения для `x = x1 + i`, `y = y1 + j`, `z = z1 + k`.
          Если значение не определено (например, по причине переполнения),
          то соответствующий элемент должен быть равен `null`.
    * [Исходный код тестов](java/expression/generic/GenericTest.java)
 * *AsmUls* (36-37)
    * Реализовать режимы из модификации *Uls*.
    * Дополнительно реализовать унарные операции:
        * `abs` – модуль числа, `abs -5` равно 5;
        * `square` – возведение в квадрат, `square 5` равно 25.
    * Дополнительно реализовать бинарную операцию (максимальный приоритет):
        * `mod` – взятие по модулю, приоритет как у умножения (`1 + 5 mod 3` равно `1 + (5 mod 3)` равно `3`).
    * [Исходный код тестов](java/expression/generic/GenericAsmUlsTest.java)


## Домашнее задание 3. Очередь на связном списке

Модификации
 * *Базовая*
    * [Исходный код тестов](java/queue/QueueTest.java)
    * [Откомпилированные тесты](artifacts/queue/QueueTest.jar)
 * *Contains* (36-37)
    * Добавить в интерфейс очереди и реализовать методы
        * `contains(element)` – проверяет, содержится ли элемент в очереди
        * `removeFirstOccurrence(element)` – удаляет первое вхождение элемента в очередь 
            и возвращает было ли такое
    * Дублирования кода быть не должно
    * [Исходный код тестов](java/queue/QueueContainsTest.java)
    * [Откомпилированные тесты](artifacts/queue/QueueContainsTest.jar)


## Домашнее задание 2. Очередь на массиве

Модификации
 * *Базовая*
    * Классы должны находиться в пакете `queue`
    * [Исходный код тестов](java/queue/ArrayQueueTest.java)
    * [Откомпилированные тесты](artifacts/queue/ArrayQueueTest.jar)
 * *Deque* (36-37)
    * Реализовать методы
        * `push` – добавить элемент в начало очереди
        * `peek` – вернуть последний элемент в очереди
        * `remove` – вернуть и удалить последний элемент из очереди
    * [Исходный код тестов](java/queue/ArrayDequeTest.java)
    * [Откомпилированные тесты](artifacts/queue/ArrayDequeTest.jar)

## Домашнее задание 1. Бинарный поиск

Модификации
 * *Базовая*
    * Класс `BinarySearch` должен находиться в пакете `search`
    * [Исходный код тестов](java/search/BinarySearchTest.java)
    * [Откомпилированные тесты](artifacts/search/BinarySearchTest.jar)
 * *Span* (36-37)
    * Требуется вывести два числа: начало и длину диапазона элементов,
      равных `x`. Если таких элементов нет, то следует вывести
      пустой диапазон, у которого левая граница совпадает с местом
      вставки элемента `x`.
    * Не допускается использование типов `long` и `BigInteger`.
    * Класс должен иметь имя `BinarySearchSpan`
    * [Исходный код тестов](java/search/BinarySearchSpanTest.java)
    * [Откомпилированные тесты](artifacts/search/BinarySearchSpanTest.jar)