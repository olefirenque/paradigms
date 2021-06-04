package queue;

public class LinkedQueue extends AbstractQueue {
    private Node head, last;

    @Override
    protected void enqueueImplementation(Object element) {
        Node tempNode = new Node(element, null);
        if (head == null) {
            head = last = tempNode;
            return;
        }
        last.next = tempNode;
        last = tempNode;
    }

    @Override
    protected void dequeueImplementation() {
        if (head == last) {
            last = null;
        }
        head = head.next;
    }

    @Override
    protected Object elementImplementation() {
        return head.value;
    }

    @Override
    public void clear() {
        head = last = null;
        size = 0;
    }

    @Override
    public Creator getInstance() {
        return new LinkedQueue();
    }

    private static class Node {
        private final Object value;
        private Node next;

        public Node(Object value, Node next) {
            assert value != null;

            this.value = value;
            this.next = next;
        }
    }
}
