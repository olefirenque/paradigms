package queue.test;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Random;

public class ArrayQueueOwnTest {
    final static int PUSH_COUNT = 1000;

    public static void main(String[] args) {
        List<QueueAdapterInterface> queues = List.of(
                new QueueClassAdapter(),
                new QueueADTAdapter(),
                new QueueModuleAdapter()
        );
        System.out.println(queues.stream().allMatch(ArrayQueueOwnTest::test) ? "Тесты пройдены!" : "Тесты не пройдены");
    }


    private static boolean test(QueueAdapterInterface queue) {
        ArrayDeque<Object> sampleQueue = new ArrayDeque<>();
        Random random = new Random(1337);
        for (int i = 0; i < PUSH_COUNT; i++) {
            int obj = random.nextInt();
            sampleQueue.addLast(obj);
            queue.enqueue(obj);
        }

        assert sampleQueue.isEmpty() == queue.isEmpty() : "=== Didn't pass isEmpty test";

        sampleQueue.clear();
        queue.clear();

        assert sampleQueue.isEmpty() == queue.isEmpty() : "=== Didn't pass isEmpty test";

        for (int i = 0; i < PUSH_COUNT; i++) {
            int obj = random.nextInt();
            sampleQueue.addLast(obj);
            queue.enqueue(obj);
        }

        for (int i = 1; i < PUSH_COUNT; i++) {
            Object first = sampleQueue.removeFirst();
            Object second = queue.dequeue();
            assert first.equals(second) : String.format("=== Didn't pass dequeue test: expected = %s, got = %s", first, second);
            assert sampleQueue.getFirst().equals(queue.element()) :
                    String.format("=== Didn't pass element test: expected = %s, got = %s", sampleQueue.getFirst(), queue.element());
        }

        for (int i = 0; i < PUSH_COUNT; i++) {
            int obj = random.nextInt();
            sampleQueue.addFirst(obj);
            queue.push(obj);
        }

        for (int i = 1; i < PUSH_COUNT; i++) {
            Object first = sampleQueue.removeLast();
            Object second = queue.remove();
            assert first.equals(second) : String.format("=== Didn't pass remove test: expected = %s, got = %s", first, second);
            assert sampleQueue.getLast().equals(queue.peek()) :
                    String.format("=== Didn't pass peek test: expected = %s, got = %s", sampleQueue.getLast(), queue.peek());
        }

        assert sampleQueue.isEmpty() == queue.isEmpty() : "=== Didn't pass isEmpty test";
        sampleQueue.clear();
        queue.clear();
        assert sampleQueue.isEmpty() == queue.isEmpty() : "=== Didn't pass clear test";

        for (int i = 0; i < PUSH_COUNT; i++) {
            int obj = random.nextInt();
            sampleQueue.addLast(obj);
            queue.enqueue(obj);
        }
        return true;
    }
}
