# Java Streams – Primitive Streams and Collectors

## 6. Primitive Streams (organized)

Primitive streams provide specialized APIs for numeric data to avoid boxing and improve performance. The main types are `IntStream`, `LongStream`, and `DoubleStream`.

Performance note

- Primitive streams (`IntStream`, `LongStream`, `DoubleStream`) avoid boxing/unboxing overhead compared to `Stream<Integer>` etc., which improves performance for numeric-heavy operations and large datasets.

### Generation

- **`range(startInclusive, endExclusive)`**: Generates an `IntStream` of values from `startInclusive` up to `endExclusive - 1`.

  Example:
  ```java
  IntStream.range(0, 3).forEach(System.out::println); // 0, 1, 2
  ```

- **`rangeClosed(startInclusive, endInclusive)`**: Generates an `IntStream` including the end value.

  Example:
  ```java
  IntStream.rangeClosed(0, 3).forEach(System.out::println); // 0, 1, 2, 3
  ```

### Conversion

- **`boxed()`**: Converts a primitive stream to an object `Stream<T>` (e.g., `IntStream` → `Stream<Integer>`). Use when you must interoperate with APIs requiring object streams, but be aware of boxing overhead.

  Example:
  ```java
  List<Integer> boxed = IntStream.of(1,2,3)
      .boxed()
      .collect(Collectors.toList());
  ```

- **`asLongStream()` / `asDoubleStream()`**: Convert between primitive stream types when needed (e.g., widening conversions).

### Aggregation (terminal operations)

- **`sum()`**: Returns the sum of elements (int/long/double).
- **`average()`**: Returns an `OptionalDouble` (or `OptionalInt` for primitives) with the average; handle empty streams accordingly.
- **`min()` / `max()`**: Return an optional with the minimum/maximum element.
- **`summaryStatistics()`**: Returns a statistics object (`IntSummaryStatistics`, `DoubleSummaryStatistics`) summarizing count, sum, min, average, and max in a single pass.

Example:
```java
IntSummaryStatistics stats = IntStream.of(1,2,3).summaryStatistics();
System.out.println(stats.getSum()); // 6
```

## 7. Collectors (consistent descriptions)

Below collectors are listed with: method name — purpose — notes/warnings (if any).

- **`Collectors.toList()`** — Collects elements into a `List<T>`. Note: the returned list is not guaranteed to be mutable/immutable across Java versions; prefer `Collectors.toUnmodifiableList()` when immutability is needed (Java 10+).
- **`Collectors.toSet()`** — Collects elements into a `Set<T>`. Note: no guaranteed iteration order.
- **`Collectors.toMap(keyMapper, valueMapper)`** — Collects elements into a `Map<K,V>` using mapping functions; beware of key collisions (use overloads that accept merge functions to handle duplicates).
- **`Collectors.joining(delimiter, prefix, suffix)`** — Concatenates `CharSequence` elements into a single `String`, with optional delimiter/prefix/suffix.
- **`Collectors.groupingBy(classifier)`** — Groups elements by a classifier function and returns a `Map<K,List<T>>`. Use when you need buckets keyed by a property. Note: use `groupingByConcurrent` for parallel-friendly grouping when order is not required.
- **`Collectors.partitioningBy(predicate)`** — Partitions elements into a `Map<Boolean,List<T>>` with exactly two buckets (`true` and `false`) based on the predicate. Use when you need a boolean split (yes/no) rather than many keys.

  Short comparison — `groupingBy` vs `partitioningBy`:
  - `groupingBy`: Many groups keyed by classifier result (Map<K, List<T>>). Use when grouping by a multi-valued classifier (e.g., by department).
  - `partitioningBy`: Exactly two groups (Map<Boolean, List<T>>). Use when splitting by a predicate (e.g., even/odd).

- **`Collectors.mapping(mapper, downstream)`** — Applies `mapper` then collects using `downstream` collector. Useful to transform elements as part of a larger collection operation.
- **`Collectors.collectingAndThen(downstream, finisher)`** — Applies a downstream collector then transforms its result via a finisher function.
- **`Collectors.summarizingInt()` / `summarizingLong()` / `summarizingDouble()`** — Collects statistics (count, sum, min, average, max) for numeric values and returns the corresponding SummaryStatistics object.
- **`Collectors.groupingByConcurrent(classifier)`** — Concurrent variant of `groupingBy` optimized for parallel streams. Warning: Does not preserve encounter order and can have different performance characteristics.

**Example for `collectingAndThen()`**
```java
// Collect to a list, then make it unmodifiable
List<String> result = Stream.of("a", "b")
    .collect(Collectors.collectingAndThen(
        Collectors.toList(),
        Collections::unmodifiableList));
```

### Summary table (collector — return type — use case)

| Collector | Return type | Use case |
|---|---|---|
| `toList()` | `List<T>` | Collect into a list |
| `toSet()` | `Set<T>` | Collect into a set (unique elements) |
| `toMap()` | `Map<K,V>` | Map by key/value mappers; watch for key collisions |
| `joining()` | `String` | Concatenate strings with delimiter/prefix/suffix |
| `groupingBy()` | `Map<K,List<T>>` | Group elements into buckets by classifier |
| `partitioningBy()` | `Map<Boolean,List<T>>` | Split elements into true/false buckets |
| `mapping()` | Depends on downstream | Transform elements then collect with downstream |
| `collectingAndThen()` | Depends on downstream + finisher | Post-process collected result |
| `summarizingInt()` | `IntSummaryStatistics` | Numeric statistics (count/sum/min/avg/max) |
| `groupingByConcurrent()` | `ConcurrentMap<K, ...>` | Parallel-friendly grouping; unordered result |

**Version notes:** If a method requires Java 9+ or later, it is marked in-line (e.g., `Collectors.toUnmodifiableList()` is Java 10+). If you want, I can add Java-version markers to every collector method in this file.
