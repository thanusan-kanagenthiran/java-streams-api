package demo_programs;

import java.util.*;
import java.util.stream.*;

public class StreamParallelExample {

    public static void main(String[] args) {

        // ===================================================
        // Sample Stream Data
        // ===================================================
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

        // ===================================================
        // 1️⃣ parallel() / sequential()
        // ===================================================
        // Switch execution mode between parallel and sequential
        Stream<Integer> sequential = numbers.stream(); // default: sequential
        Stream<Integer> parallel = sequential.parallel(); // switch to parallel
        System.out.println("Is parallel? " + parallel.isParallel()); // true

        Stream<Integer> backToSequential = parallel.sequential();
        System.out.println("Is parallel after sequential()? " + backToSequential.isParallel()); // false

        // ===================================================
        // 2️⃣ unordered()
        // ===================================================
        // Hints that encounter order does not matter
        // Can improve performance for some operations, especially parallel
        List<Integer> unorderedResult = numbers.stream()
                .unordered()
                .parallel() // combine with parallel for optimization
                .map(n -> n * 2)
                .toList();
        System.out.println("Unordered result (parallel map x2): " + unorderedResult);

        // ===================================================
        // 3️⃣ isParallel()
        // ===================================================
        // Checks if a stream is currently parallel
        Stream<Integer> checkParallel = numbers.stream();
        System.out.println("Sequential stream isParallel? " + checkParallel.isParallel());
        Stream<Integer> checkParallel2 = numbers.parallelStream();
        System.out.println("Parallel stream isParallel? " + checkParallel2.isParallel());

        // ===================================================
        // 4️⃣ iterator() / spliterator()
        // ===================================================
        // Traditional traversal mechanisms for interoperability
        System.out.print("Iterator: ");
        Iterator<Integer> it = numbers.stream().iterator();
        while (it.hasNext()) {
            System.out.print(it.next() + " ");
        }
        System.out.println();

        System.out.print("Spliterator: ");
        Spliterator<Integer> sp = numbers.stream().spliterator();
        sp.forEachRemaining(n -> System.out.print(n + " "));
        System.out.println();

        // ===================================================
        // 5️⃣ onClose() / close()
        // ===================================================
        // Register cleanup actions and close the stream
        try (Stream<Integer> closableStream = numbers.stream()
                .onClose(() -> System.out.println("Stream closed!"))) {
            List<Integer> result = closableStream.toList(); // terminal operation
            System.out.println("Closable stream result: " + result);
        } // try-with-resources auto-closes the stream and triggers the onClose handler

        // ===================================================
        // 🔑 Notes:
        // - parallel() / sequential() only affect the current stream pipeline.
        // - unordered() is a hint; results may still appear in original order.
        // - Always close streams that hold resources (I/O, files) to prevent leaks.
    }
}
