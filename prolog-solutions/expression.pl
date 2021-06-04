lookup(K, [(K, V) | _], V).
lookup(K, [_ | T], V) :- lookup(K, T, V).

variable(Name, variable(Name)).
const(Value, const(Value)).

op_add(A, B, operation(op_add, A, B)).
op_subtract(A, B, operation(op_subtract, A, B)).
op_multiply(A, B, operation(op_multiply, A, B)).
op_divide(A, B, operation(op_divide, A, B)).
op_negate(A, operation(op_negate, A)).
op_sinh(A, operation(op_sinh, A)).
op_cosh(A, operation(op_cosh, A)).

operation(op_add, A, B, R) :- R is A + B.
operation(op_subtract, A, B, R) :- R is A - B.
operation(op_multiply, A, B, R) :- R is A * B.
operation(op_divide, A, B, R) :- R is A / B.
operation(op_negate, A, R) :- R is -A.
operation(op_sinh, A, R) :- R is (exp(A) - exp(-A)) / 2.
operation(op_cosh, A, R) :- R is (exp(A) + exp(-A)) / 2.

evaluate(const(V), _, V).
evaluate(variable(Name), Vars, R) :- atom_chars(Name, [H | _]), lookup(H, Vars, R).
evaluate(operation(Op, A), Vars, R) :-
    evaluate(A, Vars, AV),
    operation(Op, AV, R).
evaluate(operation(Op, A, B), Vars, R) :-
    evaluate(A, Vars, AV),
    evaluate(B, Vars, BV),
    operation(Op, AV, BV, R).

flatten([], []).
flatten([H | T], R) :- append(H, TR, R), flatten(T, TR).

nonvar(V, _) :- var(V).
nonvar(V, T) :- nonvar(V), call(T).

:- load_library('alice.tuprolog.lib.DCGLibrary').

digits_p([]) --> [].
digits_p([H | T]) -->
  { member(H, ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.', '-']) },
  [H],
  digits_p(T).

op_chars(op_add) --> ['+'].
op_chars(op_subtract) --> ['-'].
op_chars(op_multiply) --> ['*'].
op_chars(op_divide) --> ['/'].
op_chars(op_negate) --> ['n', 'e', 'g', 'a', 't', 'e'].
op_chars(op_sinh) --> ['s', 'i', 'n', 'h'].
op_chars(op_cosh) --> ['c', 'o', 's', 'h'].

ws(Val) --> { var(Val), ! }, [' '], ws(Val).
ws(_) --> [].

set_ws(Val) --> { var(Val) }, [].
set_ws(Val) --> { nonvar(Val) }, [' '].

expr_p(variable(Name), P) --> { nonvar(P, atom_chars(Name, Str)) }, ws(P), parse_string(Str), ws(P), {atom_chars(Name, Str)}.

parse_string([]) --> [].
parse_string([H | T]) -->
  { member(H, ['x', 'y', 'z', 'X', 'Y', 'Z']) },
  [H],
  parse_string(T).

expr_p(const(Value), P) -->
  { nonvar(Value, number_chars(Value, Chars)) },
  ws(P), digits_p(Chars), ws(P),
  { Chars = [_, _ | _], number_chars(Value, Chars) }.

expr_p(operation(Op, A, B), P) --> ws(P), ['('], expr_p(A, P), set_ws(P), op_chars(Op), set_ws(P), expr_p(B, P), [')'], ws(P).
expr_p(operation(Op, A), P) --> ws(P), op_chars(Op), ws(P), ['('], expr_p(A, P), [')'], ws(P).

infix_str(E, A) :- ground(E), phrase(expr_p(E, false), C), atom_chars(A, C).
infix_str(E, A) :-   atom(A), atom_chars(A, C), phrase(expr_p(E, _), C).
