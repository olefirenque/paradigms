% :NOTE: Упростить
next_level(empty, empty, 1).
%next_level(empty, empty, R) :- R is 1.

next_level(node(_, _, _, _, LH), empty, R) :- R is LH + 1.
next_level(empty, node(_, _, _, _, RH), R) :- R is RH + 1.
next_level(node(_, _, _, _, LH), node(_, _, _, _, RH), R) :- LH > RH, R is LH + 1, !.
next_level(node(_, _, _, _, _), node(_, _, _, _, RH), R) :- R is RH + 1.

map_build([], R) :- R = empty.
map_build([(K, V) | T], R) :- reduce([(K, V) | T], empty, R).

reduce([], ACC, R) :- R = ACC.
reduce([(K, V) | T], ACC, R) :- map_put(ACC, K, V, Tree), reduce(T, Tree, R).

map_get(node(K, V, _, _, _), K, V) :- !.
map_get(node(K, _, L, _, _), Key, Value) :- Key < K, map_get(L, Key, Value).
map_get(node(K, _, _, R, _), Key, Value) :- Key > K, map_get(R, Key, Value).

next(node(K, V, _, empty, _), K,V) :- !.
next(node(_, _, _, R, _), K, V) :- next(R, K, V).

map_remove(empty, _, empty) :- !.
map_remove(node(K, _, empty, empty, _), K, empty) :- !.
map_remove(node(K, _, L, empty, _), K, L) :- !.
map_remove(node(K, _, empty, R, _), K, R) :- !.
map_remove(node(K, _, L, R, _), K, Result) :-
		next(L, NewK, NewV),
		map_remove(L, NewK, NewL),
		balance(node(NewK, NewV, NewL, R, _), Result), !.

map_remove(node(K, V, L, R, _), Key, Result) :-
		Key < K, 
		map_remove(L, Key, NewL), 
		balance(node(K, V, NewL, R, _), Result), !.
		
map_remove(node(K, V, L, R, _), Key, Result) :-
		Key > K, 
		map_remove(R, Key, NewR), 
		balance(node(K, V, L, NewR, _), Result).

map_getLast(node(K, V, _, empty, _), (K, V)) :- !.
map_getLast(node(_, _, _, R, _), (Key, Value)) :- map_getLast(R, (Key, Value)).

map_removeLast(empty, empty) :- !.
map_removeLast(node(_, _, empty, empty, _), empty) :- !.
map_removeLast(node(_, _, L, empty, _), L) :- !.
map_removeLast(node(K, V, L, R, _), Result) :- 
		map_removeLast(R, NewR), 
		balance(node(K, V, L, NewR, _), Result).

diff(empty, 0).
diff(node(_, _, empty, empty, _), 1).
diff(node(_, _, empty, node(_, _, _, _, RH), _), R) :- R is -RH.
diff(node(_, _, node(_, _, _, _, LH), empty, _), LH).
diff(node(_, _, node(_, _, _, _, LH), node(_, _, _, _, RH), _), R) :- R is LH - RH.

map_put(empty, Key, Value, node(Key, Value, empty, empty, 1)).
map_put(node(K, _, L, R, H), K, Value, node(K, Value, L, R, H)).

map_put(node(K, V, L, R, _), Key, Value, Result) :-
 		Key > K,
 		map_put(R, Key, Value, node(RK, RV, RL, RR, RH)),
 		Node = node(K, V, L, node(RK, RV, RL, RR, RH), _), balance(Node, Result).

map_put(node(K, V, L, R, _), Key, Value, Result) :-
 		Key < K,
 		map_put(L, Key, Value, node(LK, LV, LL, LR, LH)),
 		Node = node(K, V, node(LK, LV, LL, LR, LH), R, _), balance(Node, Result).

balance(node(K, V, L, R, NewH), Result) :- 
		next_level(L, R, NewH), 
		diff(node(K, V, L, R, NewH), -2), 
		rotate_left(node(K, V, L, R, NewH), Result), !.

balance(node(K, V, L, R, NewH), Result) :- 
		next_level(L, R, NewH), 
		diff(node(K, V, L, R, NewH), 2), 
		rotate_right(node(K, V, L, R, NewH), Result), !.

balance(node(K, V, L, R, _), node(K, V, L, R, NewH)) :- next_level(L, R, NewH).

rotate_left(node(K, V, L, R, _), node(RK, RV, NewA, RR, H_B)) :-
		node(RK, RV, RL, RR, _) = R, diff(R, B_RES), B_RES =< 0, !,
		next_level(L, RL, H_A),
		NewA = node(K, V, L, RL, H_A),
		next_level(NewA, RR, H_B).

rotate_left(node(K, V, L, R, H), Result) :-
		node(_, _, RL, _, _) = R, diff(R, 1), diff(RL, C_RES), abs(C_RES) =< 1, !,
		rotate_right(R, NewR),
		rotate_left(node(K, V, L, NewR, H), Result).

rotate_right(node(K, V, L, R, _), node(LK, LV, LL, NewA, H_B)) :-
		node(LK, LV, LL, LR, _) = L, diff(R, B_RES), 0 =< B_RES, !,
		next_level(R, LR, H_A),
		NewA = node(K, V, LR, R, H_A),
		next_level(NewA, LL, H_B).

rotate_right(node(K, V, L, R, H), Result) :-
		node(_, _, RL, _, _) = R, diff(R, -1), diff(RL, C_RES), abs(C_RES) =< 1, !,
		rotate_left(L, NewL), 
		rotate_right(node(K, V, NewL, R, H), Result).