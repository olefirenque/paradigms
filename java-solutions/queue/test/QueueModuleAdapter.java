package queue.test;

import queue.ArrayQueueModule;

public class QueueModuleAdapter implements QueueAdapterInterface {
    static private int countOfInstances = 0;

    QueueModuleAdapter() {
        if (countOfInstances > 0) {
            throw new RuntimeException("Cannot create multiple instances of module");
        }
        countOfInstances++;
    }

    @Override
    public void enqueue(Object element) {
        ArrayQueueModule.enqueue(element);
    }

    @Override
    public Object dequeue() {
        return ArrayQueueModule.dequeue();
    }

    @Override
    public Object element() {
        return ArrayQueueModule.element();
    }

    @Override
    public int size() {
        return ArrayQueueModule.size();
    }

    @Override
    public boolean isEmpty() {
        return ArrayQueueModule.isEmpty();
    }

    @Override
    public void clear() {
        ArrayQueueModule.clear();
    }

    @Override
    public void push(Object element) {
        ArrayQueueModule.push(element);
    }

    @Override
    public Object peek() {
        return ArrayQueueModule.peek();
    }

    @Override
    public Object remove() {
        return ArrayQueueModule.remove();
    }
}
