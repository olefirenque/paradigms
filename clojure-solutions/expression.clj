(load-file "parser.clj")


(defn expressionParser [constant variable operations]
  (letfn [(parse [x] (cond
                       (number? x) (constant x)
                       (symbol? x) (variable (str x))
                       :else (apply (operations (first x)) (map parse (rest x)))
                       ))]
    #(parse (read-string %))))

(defn div ([x] (/ 1.0 x))
  ([x & xs] (/ x (double (apply * xs)))))


; HW 10 {

(definterface Expression
  (^Number evaluate [args])
  (^String toStringInfix [])
  (diff [var])
  )

(defn evaluate [expr args] (.evaluate expr args))
(defn diff [expr var] (.diff expr var))
(defn toString [expr] (.toString expr))
(defn toStringInfix [expr] (.toStringInfix expr))


(defn toStringExpr [op x]
  (str "(" op " " (clojure.string/join " " (mapv toString x)) ")"))

(defn toMiniVar [x] ((comp str first #(clojure.string/lower-case %)) x))

(declare ZERO ONE)

(defrecord ConstantExpr [x]
  Expression
  (evaluate [_ _] x)
  (diff [_ _] ZERO)
  (toStringInfix [_] (format "%.1f" (double x)))
  Object
  (toString [_] (format "%.1f" (double x))))


(defrecord VariableExpr [x]
  Expression
  (evaluate [_ args] (args (toMiniVar x)))
  (diff [_ var] (if (= var (toMiniVar x)) ONE ZERO))
  (toStringInfix [_] (str x))
  Object
  (toString [_] (str x)))

(defn Constant [x] (ConstantExpr. x))
(defn Variable [x] (VariableExpr. x))

(def ZERO (Constant 0))
(def ONE (Constant 1))

(defn diffNth [x var] (fn [i] (map-indexed #(if (== %1 i) (.diff %2 var) %2) x)))

(defrecord AbstractOperation [operation sign diff-impl values]
  Expression
  (evaluate [_ args] (apply operation (map #(.evaluate % args) values)))
  (toStringInfix [_] (if (== (count values) 1) (str sign "(" (toStringInfix (first values)) ")")
                                               (str "(" (clojure.string/join (str " " sign " ") (mapv toStringInfix values)) ")")))
  (diff [_ var] (diff-impl values var))
  Object
  (toString [_] (toStringExpr sign values)))

(defn operationFactory [operation sign diff-impl]
  (fn [& inner-values] (AbstractOperation. operation sign diff-impl inner-values)))

(declare Multiply)

(defn multiplication-diff-terms [x var] (mapv
                                          (comp #(apply Multiply %)
                                                (diffNth x var))
                                          (range 0 (count x))))

(defn linear-diff [op] (fn [x var] (apply op (mapv #(.diff % var) x))))

(def Negate
  (operationFactory
    -
    'negate
    (linear-diff (delay Negate))))

(def Add
  (operationFactory
    +
    '+
    (linear-diff (delay Add))))

(def Subtract
  (operationFactory
    -
    '-
    (linear-diff (delay Subtract))))

(def Multiply
  (operationFactory
    *
    '*
    (fn [x var] (apply Add (multiplication-diff-terms x var)))))

(def Divide
  (operationFactory
    div
    '/
    (fn [[x & xs :as all] var] (if (== (count all) 1) (Negate (Divide (.diff x var) (Multiply x x)))
                                                      (Divide
                                                        (apply Subtract (multiplication-diff-terms all var))
                                                        (apply Multiply (mapv #(Multiply % %) xs)))))))

(def Sum
  (operationFactory
    +
    'sum
    (linear-diff (delay Add))))

(def Avg
  (operationFactory
    (fn [& args] (/ (apply + args) (count args)))
    'avg
    (linear-diff (delay Avg))))

(def IPow
  (operationFactory
    #(Math/pow %1 %2)
    '**
    (fn [_ _] identity)))

(def ILog
  (operationFactory
    #(/ (Math/log (Math/abs %2)) (Math/log (Math/abs %1)))
    (symbol "//")
    (fn [_ _] identity)))

(def parseObject (expressionParser Constant Variable {
                                                      '+      Add
                                                      '-      Subtract
                                                      '*      Multiply
                                                      '/      Divide
                                                      'negate Negate
                                                      'sum    Sum
                                                      'avg    Avg
                                                      }))

; } HW 10


; HW 9 {

(defn constant [x] (fn [_] x))
(defn variable [x] (fn [args] (args x)))

(defn evaluateExpr [f] (fn [& expressions]
                         (fn [args]
                           (apply f (mapv (fn [inner-expr] (inner-expr args)) expressions)))))

(def add (evaluateExpr +))
(def subtract (evaluateExpr -))
(def multiply (evaluateExpr *))
(def divide (evaluateExpr div))
(def negate subtract)
(def sum add)
(def avg (evaluateExpr #(/ (reduce + %&) (count %&))))

(def parseFunction (expressionParser constant variable {'+      add
                                                        '-      subtract
                                                        '*      multiply
                                                        '/      divide
                                                        'sum    sum
                                                        'avg    avg
                                                        'negate negate}))

; } HW 9

; { HW 11

(declare *factor)

(def *digits (+char "0123456789"))
(def *number (+map (comp Constant read-string) (+str (+seqf (comp flatten vector) (+opt (+char "+-")) (+plus *digits) (+opt (+char ".")) (+star *digits)))))
(def *chars (mapv char (range 32 128)))
(def *letter (+char (apply str (filter #(Character/isLetter %) *chars))))
(def *string (fn [s] (apply +seq (mapv #(+char (str %1)) s))))
(def *space (+char (apply str (filter #(Character/isWhitespace %) *chars))))
(def *ws (+ignore (+star *space)))
(def *variable (+map (comp Variable str read-string) (+str (+plus (+char "xyzXYZ")))))

(defn *unary-cons [x] (+seqn 0 (+ignore (*string x)) *ws (delay *factor)))

(def ops {
          "+"      Add
          "-"      Subtract
          "*"      Multiply
          "/"      Divide
          "negate" Negate
          "sum"    Sum
          "avg"    Avg
          "**"     IPow
          "//"     ILog
          })

(defn commonReduce [reduce-type [x y z & rest :as all]]
  (let [[f s] (reduce-type x z)]
    (cond (== (count all) 1) x
          (= rest nil) ((ops (str y)) f s)
          :else (commonReduce reduce-type (cons ((ops (str y)) f s) rest)))))

(defn reduceToPrefix [x]
  (commonReduce #(vector %1 %2) x))

(defn reduceToPrefixReverse [x]
  (commonReduce #(vector %2 %1) (reverse x)))

(declare *expr)

(defn *unary-term [& xs] (apply +or (mapv #(+map (ops %) (*unary-cons %)) xs)))

(def *factor (+or (*unary-term "negate")
                  *number
                  *variable
                  (+seqn 1 (+char "(") *ws (delay *expr) *ws (+char ")"))))

(defn *common-term [fold]
  (fn [lower-term & operations]
    (+seqf (comp fold flatten vector) lower-term (+star (+seq *ws (+str (apply +or (mapv *string operations))) *ws lower-term)))))

(def *common-left-associative-term
  (*common-term reduceToPrefix))

(def *common-right-associative-term
  (*common-term reduceToPrefixReverse))

(def *exponential-term (*common-right-associative-term *factor "**" "//"))
(def *multiplicative-term (*common-left-associative-term *exponential-term "*" "/"))
(def *additive-term (*common-left-associative-term *multiplicative-term "+" "-"))

(def *expr *additive-term)

(def parseObjectInfix (+parser (+seqn 0 *ws *expr *ws)))

(defn tabulate [parser inputs]
  (run! (fn [input] (printf "    %-10s %s\n" (pr-str input) (parser input))) inputs))

(tabulate parseObjectInfix ["(()" "())" "1." "10" "." "1+" "x*1" "1,0" "1.y"
                            "(" ")" "(x)" "(x)+(x)+(x)" "((x)" "(2)" "1 + 2"
                            "x * ()"  "5 + x"])

; } HW 11