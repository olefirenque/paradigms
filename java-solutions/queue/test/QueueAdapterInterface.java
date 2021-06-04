package queue.test;

interface QueueAdapterInterface {
    void enqueue(Object element);

    Object dequeue();

    Object element();

    int size();

    boolean isEmpty();

    void clear();

    void push(Object element);

    Object peek();

    Object remove();
}
