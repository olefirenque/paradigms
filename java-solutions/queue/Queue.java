package queue;

//  Model:
//      Q = [elements[h], elements[h+1], ..., elements[t - 1]]
//      h -- queue head
//      t -- queue tail
//      [h;t) - elements of queue

// Inv:
//      0 <= h <= t < elements.length
//      ∀ i ∈ [h;t): elements[i] != null

// Immutable at <x,y> <=> ∀ i ∈ <x;y>: elements'[i] == elements[i]

public interface Queue {
    // Pred: element != null
    // Post: elements[t] = element && Immutable at [h;t') && h == h' && size = size' + 1
    void enqueue(Object element);

    // Pred: size > 0
    // Post: R = elements[h'] && Immutable at (h';t) && h = h' + 1 && t == t' - 1
    Object dequeue();

    // Pred: size > 0
    // Post: R = elements[h] && Immutable at [h;t) && h == h' && t == t'
    Object element();

    // Pred: true
    // Post: R = size && Immutable at [h;t) && h == h' && t == t'
    int size();

    // Pred: true
    // Post: R = (size == 0) && Immutable at [h;t) && h == h' && t == t'
    boolean isEmpty();

    // Pred: true
    // Post: R = (h == t == 0)
    void clear();

    // Pred: true
    // Post: (R = exists, where
    // exists = (∃i ∈ [h;t): elements[i] == element)) &&
    // Immutable at [h;t) && h == h' && t == t'
    boolean contains(Object element);

    // Pred: true
    // Post: (R = i != t) && Immutable at [h; i) && ∀ k ∈ [i;t): elements[k] = elements'[k+1] where
    // (i = min {ind : elements[ind] == element || ind == t})
    boolean removeFirstOccurrence(Object element);
}