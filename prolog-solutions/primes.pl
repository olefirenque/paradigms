sieve(X, N) :- X * X =< N, (\+ (prime(X), sieve_multiple(X, 0, N))), NX is X + 1, sieve(NX, N).
sieve_multiple(X, I, N) :- Y is (X * X + I * X),
    Y =< N,
    assert(composite(Y)),
    assert(divisor(Y, X)),
    NEXT_I is I + 1,
    sieve_multiple(X, NEXT_I, N).

init(X) :- sieve(2, X).
prime(2) :- !.
prime(3) :- !.
prime(X) :- \+ composite(X).


gcd(X, Y, T) :- prime_divisors(X, R1), prime_divisors(Y, R2), intersec(R1, R2, R), prime_divisors(T, R).

intersec(H, [], []) :- !.
intersec([], H, []) :- !.
intersec([H | T1], [H | T2], [H | T3]) :- intersec(T1, T2, T3), !.
intersec([H1 | T1], [H2 | T2], T3) :- H1 > H2, intersec([H1 | T1], T2, T3), !.
intersec([H1 | T1], [H2 | T2], T3) :- H1 < H2, intersec(T1, [H2 | T2], T3).


reduce(X, ACC, []) :- X is ACC, !.
reduce(X, ACC, [H, S | T]) :- !,
    prime(H),
    prime(S),
    H =< S,
    NEW_ACC is ACC * H,
    reduce(X, NEW_ACC, [S | T]).
reduce(X, ACC, [H | T]) :- prime(H),
    NEW_ACC is ACC * H,
    reduce(X, NEW_ACC, T).

prime_divisors(X, L) :- var(X), reduce(X, 1, L), !.
divisor(X, Y) :- prime(X), Y is X, !.

prime_divisors(1, []) :- !.
prime_divisors(X, L) :- divisor(X, M),
    X1 is X / M,
    prime_divisors(X1, NL),
    append([M], NL, L), !.