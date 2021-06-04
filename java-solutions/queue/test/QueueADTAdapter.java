package queue.test;

import queue.ArrayQueueADT;

public class QueueADTAdapter implements QueueAdapterInterface {
    ArrayQueueADT queue;

    QueueADTAdapter() {
        this.queue = new ArrayQueueADT();
    }

    @Override
    public void enqueue(Object element) {
        ArrayQueueADT.enqueue(queue, element);
    }

    @Override
    public Object dequeue() {
        return ArrayQueueADT.dequeue(queue);
    }

    @Override
    public Object element() {
        return ArrayQueueADT.element(queue);
    }

    @Override
    public int size() {
        return ArrayQueueADT.size(queue);
    }

    @Override
    public boolean isEmpty() {
        return ArrayQueueADT.isEmpty(queue);
    }

    @Override
    public void clear() {
        ArrayQueueADT.clear(queue);
    }

    @Override
    public void push(Object element) {
        ArrayQueueADT.push(queue, element);
    }

    @Override
    public Object peek() {
        return ArrayQueueADT.peek(queue);
    }

    @Override
    public Object remove() {
        return ArrayQueueADT.remove(queue);
    }
}

