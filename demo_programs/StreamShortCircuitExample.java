package demo_programs;

import java.util.*;
import java.util.stream.*;

public class StreamShortCircuitExample {

        public static void main(String[] args) {

                // ===================================================
                // 1️⃣ Terminal Short-Circuit Operations
                // ===================================================
                System.out.println("=== Terminal Short-Circuit Examples ===");

                // findFirst(): returns the first element matching criteria
                Optional<Integer> firstEven = Stream.of(1, 3, 5, 6, 7, 8)
                                .filter(n -> n % 2 == 0)
                                .findFirst(); // stops at first even number
                System.out.println("findFirst even: " + firstEven.orElse(null));

                // findAny(): returns any element (deterministic in sequential,
                // non-deterministic in parallel)
                Optional<Integer> anyEven = Stream.of(1, 2, 3, 4, 5).parallel()
                                .filter(n -> n % 2 == 0)
                                .findAny();
                System.out.println("findAny even (parallel): " + anyEven.orElse(null));

                // anyMatch(): returns true if any element matches predicate
                boolean hasEmpty = Stream.of("a", "b", "", "c")
                                .anyMatch(String::isEmpty);
                System.out.println("anyMatch empty string? " + hasEmpty);

                // allMatch(): returns false as soon as an element does not match predicate
                boolean allPositive = Stream.of(1, 2, 3, -1)
                                .allMatch(n -> n > 0);
                System.out.println("allMatch positive? " + allPositive);

                // noneMatch(): returns true if no elements match predicate
                List<Integer> allPositiveList = List.of(1, 2, 3);
                boolean noneNegative = allPositiveList.stream()
                                .noneMatch(n -> n < 0);
                System.out.println("noneMatch negative (all positive): " + noneNegative);

                List<Integer> withNegativeList = List.of(1, -2, 3);
                boolean containsNegative = withNegativeList.stream()
                                .noneMatch(n -> n < 0);
                System.out.println("noneMatch negative (contains negative): " + containsNegative);

                // ===================================================
                // 2️⃣ Intermediate Short-Circuit Operations
                // ===================================================
                System.out.println("\n=== Intermediate Short-Circuit Examples ===");

                // limit(n): takes only first n elements
                List<Integer> firstThree = IntStream.range(1, 100)
                                .limit(3)
                                .boxed()
                                .toList();
                System.out.println("limit(3): " + firstThree);

                // takeWhile(predicate): takes elements while predicate is true (Java 9+)
                List<Integer> lessThanFive = IntStream.range(1, 10)
                                .takeWhile(n -> n < 5)
                                .boxed()
                                .toList();
                System.out.println("takeWhile <5: " + lessThanFive);

                // dropWhile(predicate): drops elements while predicate is true (Java 9+)
                List<Integer> dropWhileLessThanFive = IntStream.range(1, 10)
                                .dropWhile(n -> n < 5)
                                .boxed()
                                .toList();
                System.out.println("dropWhile <5: " + dropWhileLessThanFive);

                // ===================================================
                // 3️⃣ Parallel Streams
                // ===================================================
                System.out.println("\n=== Parallel Stream Examples ===");

                List<Integer> list = IntStream.rangeClosed(1, 10)
                                .boxed()
                                .toList();

                // Parallel sum using parallelStream()
                int sumParallel = list.parallelStream()
                                .mapToInt(x -> x)
                                .sum();
                System.out.println("Parallel sum: " + sumParallel);

                // Parallel forEach (order not guaranteed)
                System.out.print("parallelStream forEach: ");
                list.parallelStream().forEach(n -> System.out.print(n + " "));
                System.out.println();

                // Parallel forEachOrdered (preserves encounter order)
                System.out.print("parallelStream forEachOrdered: ");
                list.parallelStream().forEachOrdered(n -> System.out.print(n + " "));
                System.out.println();

                // ===================================================
                // 🔑 Notes on Parallel Streams
                // ===================================================
                // ✅ Use for CPU-intensive, large data, stateless lambdas
                // ❌ Avoid for I/O-bound tasks, ordered requirements, small datasets, or shared
                // mutable state
        }
}