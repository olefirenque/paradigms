package queue.test;

import queue.ArrayQueue;

public class QueueClassAdapter implements QueueAdapterInterface {
    ArrayQueue queue;

    QueueClassAdapter() {
        this.queue = new ArrayQueue();
    }

    @Override
    public void enqueue(Object element) {
        queue.enqueue(element);
    }

    @Override
    public Object dequeue() {
        return queue.dequeue();
    }

    @Override
    public Object element() {
        return queue.element();
    }

    @Override
    public int size() {
        return queue.size();
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    @Override
    public void clear() {
        queue.clear();
    }

    @Override
    public void push(Object element) {
        queue.push(element);
    }

    @Override
    public Object peek() {
        return queue.peek();
    }

    @Override
    public Object remove() {
        return queue.remove();
    }
}

