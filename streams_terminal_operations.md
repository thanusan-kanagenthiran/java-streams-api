# Java Streams – Terminal Operations

## 5. Terminal Operations

Terminal operations consume the stream and produce a result or side effect. Grouped below for clarity.

### Iteration

- **`forEach`**: Applies an action to each element (order not guaranteed in parallel streams).
- **`forEachOrdered`**: Applies an action and preserves encounter order (even in parallel streams).

### Conversion

- **`toArray`**: Collects elements into an array.
- **`toList()`** (Java 16+) : Collects elements into an unmodifiable `List`.
- **`toCollection(Supplier)`**: Collects elements into a custom collection instance. The `Supplier` is a functional interface that provides a factory method to create a new collection instance, allowing you to specify the exact type of collection (e.g., `ArrayList`, `LinkedList`).

  Small example (`toCollection`):
  ```java
  // Collect to a specific collection type
  LinkedList<String> linkedList = Stream.of("a","b","c")
      .collect(Collectors.toCollection(LinkedList::new));
  ```
- **`Collectors.toUnmodifiableList()`** (Java 10+): Returns an unmodifiable list (useful pre-Java 16 or when you need explicit collector variant).

### Reduction

- **`reduce()`** (three overloads): Folds elements into a single result. Useful for numeric sums or custom aggregations.

  Small example (`reduce`):
  ```java
  // Sum using reduce (returns Optional<Integer> for single-arg form)
  Optional<Integer> sumOpt = Stream.of(1, 2, 3)
      .reduce(Integer::sum); // Optional[6]

  // Identity + accumulator form (returns primitive type directly)
  int sum = Stream.of(1,2,3)
      .reduce(0, Integer::sum); // 6
  ```

- **`collect(Collector)`**: Most common pattern for building collections and complex reductions. Prefer `collect` when assembling containers because it uses mutable accumulators and is more efficient for that purpose.

  Small example (`collect`):
  ```java
  // Collect to a list
  List<String> list = Stream.of("a","b","c")
      .collect(Collectors.toList());
  ```

**Performance note:** Use `reduce` for pure reductions into a single scalar value. For building collections, prefer `collect` with a mutable accumulator (e.g., `Collectors.toList()`); using `reduce` to build containers tends to be slower because it forces immutable combining.

### Aggregates

- **`count()`**: Returns the number of elements.
- **`min()` / `max()`**: Return the minimum/maximum (Optional).
- **`summaryStatistics()`** (primitive streams): Returns an `IntSummaryStatistics`/`DoubleSummaryStatistics` containing count, sum, min, average, and max.

### Matching (terminal short-circuiting)

These operations may terminate the pipeline early as soon as the result is known:

- **`anyMatch(Predicate)`** — returns true as soon as any element matches.
- **`allMatch(Predicate)`** — returns false as soon as an element fails the predicate.
- **`noneMatch(Predicate)`** — returns false as soon as any element matches.
- **`findFirst()`** — returns the first encountered element and stops.
- **`findAny()`** — returns any element; may be non-deterministic in parallel streams (use only when you don't care which match you get).

Add a short note: these short-circuiting operations are useful for performance and correctness (especially with infinite streams) because they can avoid processing the entire source.

### Lookup

- **`findFirst()`** and **`findAny()`** (see matching above).

### Traversal

- **`iterator()` / `spliterator()`**: Provide traditional traversal mechanisms and consume the stream. Use when you need to integrate with legacy APIs or require spliterator semantics.

---

### Reduce vs Collect — Quick comparison

| Operation | Purpose | Return type | Notes |
|---|---|---:|---|
| `reduce()` | Fold elements to a single value | scalar/Optional | Good for numeric reductions and pure aggregation; avoid using to build collections |
| `collect()` | Build containers or perform mutable reductions | depends on Collector | Efficient for assembling collections; ensure collectors are thread-safe or have proper combiner for parallel use |

### Parallel considerations

- Custom collectors should be thread-safe or provide a correct combiner to work with parallel streams. Use `Collector.of()` with an appropriate combiner for parallel reductions.
- Terminal operations that rely on encounter order (e.g., `forEachOrdered`, `findFirst`) may be slower in parallel streams.

### Java version notes

- Markers used in this file: `(Java 10+)`, `(Java 16+)` indicate the minimum Java version required for those features.
- `toList()` behavior as an unmodifiable list is from Java 16+. `Collectors.toUnmodifiableList()` is Java 10+.

### Exceptions and tips

- For custom collectors, prefer `Collector.of` to implement thread-safe or parallel-friendly collectors.
- Remember streams are single-use; do not attempt to reuse a stream after a terminal operation.
