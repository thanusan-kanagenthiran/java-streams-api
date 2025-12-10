package streams;
import java.util.*;
import java.util.stream.*;

public class StreamCreationExample {

    public static void main(String[] args) {

        // NOTE: Streams are single-use. After a terminal operation (like collect), the stream is consumed
        // and cannot be reused. Do not attempt to call another terminal operation on the same Stream.

        // ===================================================
        // 1️⃣ From Collections - Sequential Stream
        // ===================================================
        List<String> list = Arrays.asList("a", "b", "c");
        Stream<String> sequentialStream = list.stream();
        System.out.println("Sequential Stream: " + sequentialStream.toList());
        System.out.println(); // spacer for readability

        // ===================================================
        // 2️⃣ From Collections - Parallel Stream
        // ===================================================
        Stream<String> parallelStream = list.parallelStream();
        System.out.println("Parallel Stream: " + parallelStream.toList());
        System.out.println();

        // ===================================================
        // 3️⃣ From Arrays
        // ===================================================
        String[] array = {"x", "y", "z"};
        Stream<String> arrayStream = Arrays.stream(array);
        System.out.println("Array Stream: " + arrayStream.toList());
        System.out.println();

        // ===================================================
        // 4️⃣ From Maps - Keys, Values, Entries
        // ===================================================
        // Map itself has no stream() method. Use keySet(), values(), or entrySet().
        Map<Integer, String> map = Map.of(1, "one", 2, "two", 3, "three");

        // Keys
        Stream<Integer> keyStream = map.keySet().stream();
        System.out.println("Map Keys Stream: " + keyStream.toList());
        System.out.println();

        // Values
        Stream<String> valueStream = map.values().stream();
        System.out.println("Map Values Stream: " + valueStream.toList());
        System.out.println();

        // Entries (Map.Entry<K,V>)
        Stream<Map.Entry<Integer, String>> entryStream = map.entrySet().stream();
        System.out.println("Map Entries Stream: " +
                entryStream.map(e -> e.getKey() + "=" + e.getValue()).toList());
        System.out.println();

        // ===================================================
        // 5️⃣ Static Factory - Stream.of
        // ===================================================
        Stream<String> fixedStream = Stream.of("p", "q", "r");
        System.out.println("Fixed Stream (Stream.of): " + fixedStream.toList());
        System.out.println();

        // ===================================================
        // 6️⃣ Static Factory - Stream.ofNullable  (Java 9+)
        // ===================================================
        // Stream.ofNullable returns a single-element stream when the item is non-null, otherwise an empty stream.
        // Note: ofNullable was added in Java 9.
        Stream<String> nullSafeStream = Stream.ofNullable("not null");
        System.out.println("ofNullable (non-null): " + nullSafeStream.toList());

        Stream<String> nullStream = Stream.ofNullable(null);
        System.out.println("ofNullable (null): " + nullStream.toList());
        System.out.println();

        // ===================================================
        // 7️⃣ Static Factory - Stream.empty
        // ===================================================
        Stream<String> emptyStream = Stream.empty();
        System.out.println("Empty Stream: " + emptyStream.toList());
        System.out.println();

        // ===================================================
        // 8️⃣ Static Factory - Stream.generate (infinite)
        // ===================================================
        // Stream.generate creates an infinite stream. Always limit() it (or otherwise short-circuit) before
        // collecting, or you'll run indefinitely or OOM.
        Stream<Double> generatedStream = Stream.generate(Math::random).limit(3);
        System.out.println("Generated Stream (limited): " + generatedStream.toList());
        System.out.println();

        // ===================================================
        // 9️⃣ Static Factory - Stream.iterate (infinite)
        // ===================================================
        // iterate(seed, f) creates an infinite stream by default. Use limit() or another short-circuit.
        Stream<Integer> iteratedStream = Stream.iterate(1, n -> n + 1).limit(5);
        System.out.println("Iterated Stream (limited): " + iteratedStream.toList());
        System.out.println();

        // ===================================================
        // 🔟 Stream.iterate with Predicate (finite) (Java 9+)
        // ===================================================
        // The 3-arg Stream.iterate(seed, predicate, f) stops when predicate returns false. This overload was
        // added in Java 9.
        Stream<Integer> finiteIteratedStream = Stream.iterate(1, n -> n <= 5, n -> n + 1);
        System.out.println("Finite Iterated Stream (iterate with predicate): " + finiteIteratedStream.toList());
        System.out.println();

        // ===================================================
        // 1️⃣1️⃣ Static Factory - Stream.concat
        // ===================================================
        // Concatenates two streams into one. Important: the source streams (streamA and streamB) are consumed
        // by the concat operation. Attempting to reuse streamA or streamB after concat/terminal op will throw
        // IllegalStateException: "stream has already been operated upon or closed".
        Stream<String> streamA = Stream.of("A", "B");
        Stream<String> streamB = Stream.of("C", "D");
        Stream<String> concatenatedStream = Stream.concat(streamA, streamB);
        System.out.println("Concatenated Stream: " + concatenatedStream.toList());
        // Example (commented out) showing misuse — uncommenting will cause runtime IllegalStateException:
        // System.out.println(streamA.collect(Collectors.toList()));
        System.out.println();

        // ===================================================
        // 1️⃣2️⃣ Static Factory - Stream.builder (conditional add example)
        // ===================================================
        // Stream.Builder allows conditional or step-by-step population before building the stream.
        // Use a runtime condition so the example demonstrates conditional adds (varies between runs).
        boolean addExtra = new Random().nextBoolean();
        Stream.Builder<String> builder = Stream.builder();
        builder.add("build1");
        if (addExtra) {
            builder.add("build-extra");
        }
        builder.add("build2");
        Stream<String> builtStream = builder.build();
        System.out.println("Built Stream (conditional builder): " + builtStream.toList());
        System.out.println();
    }
}
