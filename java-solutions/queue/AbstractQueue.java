package queue;

public abstract class AbstractQueue implements Queue, Creator {
    protected int size;

    protected abstract void enqueueImplementation(Object element);

    protected abstract void dequeueImplementation();

    protected abstract Object elementImplementation();

    public void enqueue(Object element) {
        assert element != null : "Element must be not null";

        enqueueImplementation(element);
        size++;
    }

    public Object dequeue() {
        assert size() > 0 : "Size must be > 0";

        Object result = element();
        dequeueImplementation();
        size--;
        return result;
    }

    public Object element() {
        assert size() > 0 : "Size must be > 0";

        return elementImplementation();
    }

    public void clear() {
        while (!isEmpty()) {
            dequeue();
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    private boolean firstOccurrence(Object element, boolean remove) {
        if (element == null) {
            return false;
        }

        boolean contains = false;

        int startSize = size;
        for (int i = 0; i < startSize; i++) {
            var obj = dequeue();
            if (element.equals(obj) && !contains) {
                contains = true;
                if (remove) {
                    continue;
                }
            }
            enqueue(obj);
        }
        return contains;
    }

    public boolean contains(Object element) {
        return firstOccurrence(element, false);
    }

    public boolean removeFirstOccurrence(Object element) {
        return firstOccurrence(element, true);
    }
}
