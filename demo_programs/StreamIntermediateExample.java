package streams;

import java.util.*;
import java.util.stream.*;

public class StreamIntermediateExample {

    public static void main(String[] args) {

        // ===================================================
        // Sample Stream Data
        // ===================================================
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 2, 3);
        System.out.println("Original List: " + numbers);

        // ===================================================
        // 1️⃣ filter
        // ===================================================
        // Retains only elements matching the predicate
        List<Integer> filtered = numbers.stream()
                .filter(n -> n % 2 == 0)
                .toList();
        System.out.println("Filter even numbers: " + filtered);

        // ===================================================
        // 2️⃣ map / mapToX
        // ===================================================
        // Transforms each element
        List<Integer> squared = numbers.stream()
                .map(n -> n * n) // square each number
                .toList();
        System.out.println("Squared numbers: " + squared);

        int mapToIntExample = numbers.stream()
                .mapToInt(n -> n) // convert to IntStream
                .sum();
        System.out.println("mapToInt example sum: " + mapToIntExample);

        // ===================================================
        // 3️⃣ flatMap
        // ===================================================
        // Maps each element to a stream, then flattens
        List<List<Integer>> listOfLists = List.of(
                List.of(1, 2),
                List.of(3, 4),
                List.of(5)
        );
        List<Integer> flattened = listOfLists.stream()
                .flatMap(List::stream)
                .toList();
        System.out.println("Flattened list: " + flattened);

        // ===================================================
        // 4️⃣ distinct
        // ===================================================
        // Removes duplicates
        List<Integer> distinctNumbers = numbers.stream()
                .distinct()
                .toList();
        System.out.println("Distinct numbers: " + distinctNumbers);

        // ===================================================
        // 5️⃣ sorted
        // ===================================================
        // Sorts elements naturally or using a comparator
        List<Integer> sortedNumbers = numbers.stream()
                .sorted()
                .toList();
        System.out.println("Sorted numbers: " + sortedNumbers);

        List<Integer> reverseSorted = numbers.stream()
                .sorted(Comparator.reverseOrder())
                .toList();
        System.out.println("Reverse sorted numbers: " + reverseSorted);

        // ===================================================
        // 6️⃣ limit / skip
        // ===================================================
        // Truncates stream size or skips first N elements
        List<Integer> limited = numbers.stream()
                .limit(3)
                .toList();
        System.out.println("Limited (first 3): " + limited);

        List<Integer> skipped = numbers.stream()
                .skip(2)
                .toList();
        System.out.println("Skipped first 2: " + skipped);

        // ===================================================
        // 7️⃣ takeWhile / dropWhile (Java 9+)
        // ===================================================
        // takeWhile: process elements while predicate is true
        List<Integer> taken = numbers.stream()
                .takeWhile(n -> n < 4)
                .toList();
        System.out.println("takeWhile < 4: " + taken);

        // dropWhile: skip elements while predicate is true
        List<Integer> dropped = numbers.stream()
                .dropWhile(n -> n < 4)
                .toList();
        System.out.println("dropWhile < 4: " + dropped);

        // ===================================================
        // 8️⃣ mapMulti (Java 16+)
        // ===================================================
        // Efficient one-to-many mapping
        List<String> mapMultiExample = Stream.of("a", "b", "c")
                .<String>mapMulti((ch, consumer) -> {
                    consumer.accept(ch + "1");
                    consumer.accept(ch + "2");
                })
                .toList();
        System.out.println("mapMulti result: " + mapMultiExample);

        // ===================================================
        // 9️⃣ peek
        // ===================================================
        // Mainly for debugging, does not alter elements
        List<Integer> peeked = numbers.stream()
                .peek(n -> System.out.println("Peek: " + n))
                .map(n -> n * 10)
                .toList();
        System.out.println("After peek & multiply by 10: " + peeked);

        // ===================================================
        // 🔑 Laziness Note
        // ===================================================
        // None of the above intermediate operations execute until a terminal operation (like collect, forEach) is invoked.
    }
}