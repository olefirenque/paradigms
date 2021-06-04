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

public class ArrayQueueADT {
    private int head;
    private int tail;
    private Object[] elements = new Object[5];

    // Pred: element != null && queue != null
    // Post: elements[t] = element && t = t' + 1 && Immutable at [h;t') && h == h'
    public static void enqueue(ArrayQueueADT queue, Object element) {
        Objects.requireNonNull(element);

        ensureCapacity(queue);
        queue.elements[queue.tail] = element;
        queue.tail = (queue.tail + 1) % queue.elements.length;
    }

    private static void ensureCapacity(ArrayQueueADT queue) {
        if ((queue.tail + 1) % queue.elements.length == queue.head) {
            Object[] newElements = new Object[2 * queue.elements.length];
            System.arraycopy(queue.elements, queue.head, newElements, 0, queue.elements.length - queue.head);
            if (queue.head != 0) {
                System.arraycopy(queue.elements, 0, newElements, queue.elements.length - queue.head, queue.tail);
            }
            queue.tail = size(queue);
            queue.head = 0;
            queue.elements = newElements;
        }
    }

    // Pred: size > 0 && queue != null
    // Post: R = elements[h'] && h = h' + 1 && Immutable at (h';t) && t == t'
    public static Object dequeue(ArrayQueueADT queue) {
        assert size(queue) > 0 : "Size must be > 0";

        Object result = queue.elements[queue.head];
        queue.elements[queue.head] = null;
        queue.head = (queue.head + 1) % queue.elements.length;
        return result;
    }

    // Pred: size > 0 && queue != null
    // Post: R = elements[h] && Immutable at [h;t) && h == h' && t == t'
    public static Object element(ArrayQueueADT queue) {
        assert size(queue) > 0 : "Size must be > 0";

        return queue.elements[queue.head];
    }

    // Pred: queue != null
    // Post: R = t - h && Immutable at [h;t) && h == h' && t == t'
    public static int size(ArrayQueueADT queue) {
        return ((queue.tail - queue.head) + queue.elements.length) % queue.elements.length;
    }

    // Pred: queue != null
    // Post: R = (t == h) && Immutable at [h;t) && h == h' && t == t'
    public static boolean isEmpty(ArrayQueueADT queue) {
        return size(queue) == 0;
    }

    // Pred: queue != null
    // Post: R = (t == h)
    public static void clear(ArrayQueueADT queue) {
        int upperBound = Math.min(queue.elements.length, queue.tail);
        for (int i = 0; i < upperBound; i++) {
            queue.elements[i] = null;
        }
        for (int i = (queue.tail - 1 + queue.elements.length) % queue.elements.length; i < queue.head; i++) {
            queue.elements[i] = null;
        }
        queue.head = queue.tail = 0;
    }

    // Pred: element != null && queue != null
    // Post: h = h' - 1 && elements[h] = element && Immutable at [h';t) && t == t'
    public static void push(ArrayQueueADT queue, Object element) {
        Objects.requireNonNull(element);

        ensureCapacity(queue);
        queue.head = (queue.head - 1 + queue.elements.length) % queue.elements.length;
        queue.elements[queue.head] = element;
    }

    // Pred: size > 0 && queue != null
    // Post: R = elements[h] && Immutable at [h;t) && h == h' && t == t'
    public static Object peek(ArrayQueueADT queue) {
        assert size(queue) > 0 : "Size must be > 0";

        return queue.elements[(queue.tail - 1 + queue.elements.length) % queue.elements.length];
    }

    // Pred: size > 0 && queue != null
    // Post: t = t' - 1 && R = elements[t] && Immutable at [h;t') && h == h'
    public static Object remove(ArrayQueueADT queue) {
        assert size(queue) > 0 : "Size must be > 0";

        queue.tail = (queue.tail - 1 + queue.elements.length) % queue.elements.length;
        Object result = queue.elements[queue.tail];
        queue.elements[queue.tail] = null;
        return result;
    }
}
