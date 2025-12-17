package demo_programs;

import java.util.*;
import java.util.stream.*;

public class StreamPrimitiveCollectorsExample {

    public static void main(String[] args) {

        // ===================================================
        // 1️⃣ IntStream
        // ===================================================
        IntStream intStream = IntStream.of(1, 2, 3, 4, 5);
        System.out.println("IntStream sum: " + intStream.sum());

        // Re-create stream because streams cannot be reused after a terminal operation
        intStream = IntStream.of(1, 2, 3, 4, 5);
        System.out.println("IntStream average: " + intStream.average().orElse(0));

        intStream = IntStream.of(1, 2, 3, 4, 5);
        System.out.println("IntStream min: " + intStream.min().orElseThrow());
        intStream = IntStream.of(1, 2, 3, 4, 5);
        System.out.println("IntStream max: " + intStream.max().orElseThrow());

        // summaryStatistics: count, sum, min, max, average
        intStream = IntStream.of(1, 2, 3, 4, 5);
        IntSummaryStatistics stats = intStream.summaryStatistics();
        System.out.println("IntStream summaryStatistics: " + stats);

        // boxed(): converts primitive stream to Stream<Integer>
        intStream = IntStream.of(1, 2, 3, 4, 5);
        Stream<Integer> boxedStream = intStream.boxed();
        System.out.println("Boxed IntStream: " + boxedStream.toList());

        // ===================================================
        // 2️⃣ Numeric Ranges
        // ===================================================
        // range(): start inclusive, end exclusive
        IntStream range = IntStream.range(1, 5);
        System.out.println("IntStream.range(1,5): " + range.boxed().toList());

        // rangeClosed(): start inclusive, end inclusive
        IntStream rangeClosed = IntStream.rangeClosed(1, 5);
        System.out.println("IntStream.rangeClosed(1,5): " + rangeClosed.boxed().toList());

        // ===================================================
        // 3️⃣ Type Conversions
        // ===================================================
        IntStream intStream2 = IntStream.of(1, 2, 3);
        LongStream longStream = intStream2.asLongStream();
        System.out.println("IntStream -> LongStream: " + longStream.boxed().toList());

        LongStream longStream2 = LongStream.of(10L, 20L, 30L);
        DoubleStream doubleStream = longStream2.asDoubleStream();
        System.out.println("LongStream -> DoubleStream: " + doubleStream.boxed().toList());

        // ===================================================
        // 4️⃣ LongStream and DoubleStream examples
        // ===================================================
        LongStream longStream3 = LongStream.of(100L, 200L, 300L);
        System.out.println("LongStream sum: " + longStream3.sum());

        DoubleStream doubleStream2 = DoubleStream.of(1.5, 2.5, 3.5);
        System.out.println("DoubleStream average: " + doubleStream2.average().orElse(0));

        // ===================================================
        // 🔑 Notes:
        // - Primitive streams avoid boxing/unboxing overhead
        // - Use summaryStatistics() for efficient multi-metric aggregation
        // - range()/rangeClosed() are convenient for generating sequences
        // - boxed() converts to object streams if needed for generic operations
    }
}
