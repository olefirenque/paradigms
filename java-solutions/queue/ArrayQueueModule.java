package queue;

//  Model:
//      ∃ circular shift s: queue.shift(s) would have following representation:
//      [..., elements[h], elements[h+1], ...elements[t-1], ...]
//      h -- queue head
//      t -- queue tail
//      [h;t) - elements of queue

// Inv:
//      0 <= h <= t < elements.length
//      ∀ i ∈ [h;t): elements[i] != null

// Immutable at <x,y> <=> ∀ i ∈ <x;y>: elements'[i] == elements[i]

import java.util.Objects;

public class ArrayQueueModule {
    public static int head;
    public static int tail;
    private static Object[] elements = new Object[5];

    // Pred: element != null
    // Post: elements[t] = element && t = t' + 1 && Immutable at [h;t') && h == h'
    public static void enqueue(Object element) {
        Objects.requireNonNull(element);

        ensureCapacity();
        elements[tail] = element;
        tail = (tail + 1) % elements.length;
    }

    private static void ensureCapacity() {
        if ((tail + 1) % elements.length == head) {
            Object[] newElements = new Object[2 * elements.length];
            System.arraycopy(elements, head, newElements, 0, elements.length - head);
            if (head != 0) {
                System.arraycopy(elements, 0, newElements, elements.length - head, tail);
            }
            tail = size();
            head = 0;
            elements = newElements;
        }
    }

    // Pred: size > 0
    // Post: R = elements[h'] && h = h' + 1 && Immutable at (h';t) && t == t'
    public static Object dequeue() {
        assert size() > 0 : "Size must be > 0";

        Object result = elements[head];
        elements[head] = null;
        head = (head + 1) % elements.length;
        return result;
    }

    // Pred: size > 0
    // Post: R = elements[h] && Immutable at [h;t) && h == h' && t == t'
    public static Object element() {
        assert size() > 0 : "Size must be > 0";

        return elements[head];
    }

    // Pred: true
    // Post: R = t - h && Immutable at [h;t) && h == h' && t == t'
    public static int size() {
        return ((tail - head) + elements.length) % elements.length;
    }

    // Pred: true
    // Post: R = (t == h) && Immutable at [h;t) && h == h' && t == t'
    public static boolean isEmpty() {
        return size() == 0;
    }

    // Pred: true
    // Post: R = (t == h)
    public static void clear() {
        int upperBound = Math.min(elements.length, tail);
        for (int i = 0; i < upperBound; i++) {
            elements[i] = null;
        }
        for (int i = (tail - 1 + elements.length) % elements.length; i < head; i++) {
            elements[i] = null;
        }
        head = tail = 0;
    }

    // Pred: element != null
    // Post: h = h' - 1 && elements[h] = element && Immutable at [h';t) && t == t'
    public static void push(Object element) {
        Objects.requireNonNull(element);

        ensureCapacity();
        head = (head - 1 + elements.length) % elements.length;
        elements[head] = element;
    }

    // Pred: size > 0
    // Post: R = elements[h] && Immutable at [h;t) && h == h' && t == t'
    public static Object peek() {
        assert size() > 0 : "Size must be > 0";

        return elements[(tail - 1 + elements.length) % elements.length];
    }

    // Pred: size > 0
    // Post: t = t' - 1 && R = elements[t] && Immutable at [h;t') && h == h'
    public static Object remove() {
        assert size() > 0 : "Size must be > 0";

        tail = (tail - 1 + elements.length) % elements.length;
        Object result = elements[tail];
        elements[tail] = null;
        return result;
    }
}
