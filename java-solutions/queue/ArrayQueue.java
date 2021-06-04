package queue;

import java.util.Arrays;
import java.util.Objects;

public class ArrayQueue extends AbstractQueue {
    private int head;
    private Object[] elements = new Object[5];

    private int tail() {
        return (head + size) % elements.length;
    }

    private void ensureCapacity() {
        if (size == elements.length) {
            Object[] newElements = new Object[2 * elements.length];
            System.arraycopy(elements, head, newElements, 0, elements.length - head);
            if (head != 0) {
                System.arraycopy(elements, 0, newElements, elements.length - head, tail());
            }
            head = 0;
            elements = newElements;
        }
    }

    @Override
    protected void enqueueImplementation(Object element) {
        ensureCapacity();
        elements[tail()] = element;
    }

    @Override
    protected void dequeueImplementation() {
        elements[head] = null;
        head = (head + 1) % elements.length;
    }

    @Override
    protected Object elementImplementation() {
        return elements[head];
    }

    @Override
    public void clear() {
        Arrays.fill(elements, null);
        head = size = 0;
    }

    public void push(Object element) {
        Objects.requireNonNull(element);

        ensureCapacity();
        head = (head - 1 + elements.length) % elements.length;
        size++;
        elements[head] = element;
    }

    public Object peek() {
        assert size() > 0 : "Size must be > 0";

        return elements[((head + size) - 1) % elements.length];
    }

    public Object remove() {
        assert size() > 0 : "Size must be > 0";

        size--;
        Object result = elements[tail()];
        elements[tail()] = null;
        return result;
    }

    @Override
    public Creator getInstance() {
        return new ArrayQueue();
    }
}
