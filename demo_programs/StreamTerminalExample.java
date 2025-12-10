package streams;
import java.util.*;
import java.util.stream.*;

public class StreamTerminalExample {

    public static void main(String[] args) {

        // ===================================================
        // Sample Stream Data
        // ===================================================
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 2, 3);
        System.out.println("Original List: " + numbers);

        // ===================================================
        // 1️⃣ Iteration: forEach / forEachOrdered
        // ===================================================
        System.out.print("forEach (order not guaranteed in parallel): ");
        numbers.parallelStream()
                .forEach(n -> System.out.print(n + " "));
        System.out.println();

        System.out.print("forEachOrdered (preserves order): ");
        numbers.parallelStream()
                .forEachOrdered(n -> System.out.print(n + " "));
        System.out.println();

        // ===================================================
        // 2️⃣ Conversion: toArray / toList / toCollection
        // ===================================================
        Object[] array = numbers.toArray();
        System.out.println("toArray(): " + Arrays.toString(array));

        List<Integer> list16 = numbers.stream().toList(); // Java 16+, unmodifiable
        System.out.println("toList() (Java 16+): " + list16);

        Set<Integer> set = new HashSet<>(numbers);
        System.out.println("toCollection(HashSet): " + set);

        List<Integer> unmodifiableList = numbers.stream()
                .toList(); // Java 10+
        System.out.println("Collectors.toUnmodifiableList(): " + unmodifiableList);

        // ===================================================
        // 3️⃣ Reduction: reduce
        // ===================================================
        Optional<Integer> sumOptional = numbers.stream()
                .reduce(Integer::sum); // Optional<T>
        System.out.println("reduce(Integer::sum): " + sumOptional.orElse(0));

        int sumWithIdentity = numbers.stream()
                .reduce(0, Integer::sum); // identity + operator
        System.out.println("reduce(0, Integer::sum): " + sumWithIdentity);

        String concatenated = numbers.stream()
                .map(String::valueOf)
                .reduce("", (s, n) -> s + n + "-");
        System.out.println("Custom reduce (concatenate): " + concatenated);

        // Three-arg reduce for parallel streams (identity, accumulator, combiner)
        Integer sumParallel = numbers.parallelStream()
                .reduce(0, Integer::sum, Integer::sum);
        System.out.println("reduce(0, (a,b)->a+b, Integer::sum) parallel: " + sumParallel);

        // ===================================================
        // 4️⃣ Reduction: collect
        // ===================================================
        List<Integer> collectedList = numbers.stream()
                .toList();
        System.out.println("collect(Collectors.toList()): " + collectedList);

        Set<Integer> collectedSet = new HashSet<>(numbers);
        System.out.println("collect(Collectors.toCollection(HashSet)): " + collectedSet);

        String joinedString = numbers.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", "));
        System.out.println("collect(Collectors.joining): " + joinedString);

        // ===================================================
        // 5️⃣ Aggregates: count / min / max / summaryStatistics
        // ===================================================
        long count = numbers.size();
        System.out.println("count(): " + count);

        int min = numbers.stream().min(Integer::compare).orElseThrow();
        int max = numbers.stream().max(Integer::compare).orElseThrow();
        System.out.println("min(): " + min + ", max(): " + max);

        IntSummaryStatistics stats = numbers.stream().mapToInt(Integer::intValue).summaryStatistics();
        System.out.println("summaryStatistics(): " + stats);

        // ===================================================
        // 6️⃣ Matching: anyMatch / allMatch / noneMatch
        // ===================================================
        boolean anyEven = numbers.stream().anyMatch(n -> n % 2 == 0);
        boolean allPositive = numbers.stream().allMatch(n -> n > 0);
        boolean noneNegative = numbers.stream().noneMatch(n -> n < 0);
        System.out.println("anyMatch even? " + anyEven);
        System.out.println("allMatch positive? " + allPositive);
        System.out.println("noneMatch negative? " + noneNegative);

        // ===================================================
        // 7️⃣ Lookup: findFirst / findAny
        // ===================================================
        Optional<Integer> first = numbers.stream().findFirst();
        Optional<Integer> any = numbers.parallelStream().findAny(); // non-deterministic in parallel
        System.out.println("findFirst(): " + first.orElse(null));
        System.out.println("findAny(): " + any.orElse(null));

        // ===================================================
        // 8️⃣ Traversal: iterator / spliterator
        // ===================================================
        System.out.print("iterator(): ");
        Iterator<Integer> it = numbers.stream().iterator();
        while (it.hasNext()) {
            System.out.print(it.next() + " ");
        }
        System.out.println();

        System.out.print("spliterator(): ");
        Spliterator<Integer> sp = numbers.stream().spliterator();
        sp.forEachRemaining(n -> System.out.print(n + " "));
        System.out.println();

        // ===================================================
        // 🔑 Notes:
        // - reduce() → aggregation / combination of elements
        // - collect() → building containers (list, set, map, string, etc.)
        // - Terminal operations trigger stream processing (lazy evaluation)
    }
}
