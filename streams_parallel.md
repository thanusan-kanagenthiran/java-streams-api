# Java Streams – Parallel Streams and Best Practices

## 9. Parallel Streams

- **When to Use**: CPU-intensive operations, large data, stateless lambdas, no synchronization.  Use parallel streams when the work per element is significant and can be done independently so the parallel overhead is amortized.
- **When NOT to Use**: I/O workloads, ordering matters, small datasets, shared mutable state.  For I/O or small collections the threading overhead or blocking will often make parallel streams slower or unsafe.
- **Example**: `int sum = list.parallelStream().mapToInt(x -> x).sum();`
- **Ordering Guarantee**: Most operations in parallel streams do NOT guarantee encounter order—only `forEachOrdered`, `findFirst`, or ordered collectors do. Use these when the result order must match the source.

### Visualizing Parallel Processing (threads/tasks labeled)

Parallel streams split data for concurrent processing, which can improve performance but may not preserve order. Each chunk may be processed by a separate thread or task.

```
Data Source: [a, b, c, d, e, f]
         ↓
     Split into chunks (tasks/threads)
    /       |       \
[a,b]    [c,d]    [e,f]
  (T1)     (T2)     (T3)
   ↓         ↓         ↓
Process   Process   Process  (concurrent, e.g., map/filter)
   ↓         ↓         ↓
Combine results (join tasks)
         ↓
Final Result: [processed_a, processed_b, ...] (order may vary unless using ordered terminal ops)
```

This diagram shows how data is partitioned into tasks (often executed on different threads), processed concurrently, and then combined. Use `forEachOrdered` or ordered collectors when you need to preserve encounter order across the combination step.

### Shared mutable state — caution

Parallel streams combined with shared mutable state can produce subtle, hard-to-debug bugs (race conditions, lost updates). Always avoid mutating shared collections or state from within parallel pipeline operations; prefer thread-safe collectors or pure functional operations.

## 10. Exceptions & Resource Management

- Streams wrapping I/O resources should always be used in try-with-resources to prevent resource leaks.

  Example:
  ```text
  try (Stream<String> s = Files.lines(path)) {
      s.forEach(System.out::println);
  }
  ```

- Lambdas may throw runtime errors; handle exceptions around terminal operations or use helper methods to convert checked exceptions inside lambdas.

## 11. Best Practices (grouped)

Resource management

- Close resource-backed streams (prefer try-with-resources).
- Use `onClose` only when you need custom cleanup handlers for non-trivial resource management.

Performance

- Measure before parallelizing — benchmarking prevents surprises.
- Prefer numeric streams (`IntStream`, `LongStream`, `DoubleStream`) for aggregations and heavy numeric work; they avoid boxing and offer specialized operations. See `streams_primitive_collectors.md` for primitive collectors and numeric examples.
- Use primitive-specialized terminal ops like `sum()`, `average()`, or `summaryStatistics()` for efficient aggregates.

Code safety and correctness

- Do not reuse streams after a terminal operation.
- Avoid side effects in intermediate operations; use pure functions in `map`/`filter`.
- Avoid shared mutable state from parallel pipelines; prefer collectors or thread-safe data structures.
- Prefer `forEachOrdered` or ordered collectors when the order of results matters in a parallel stream.

### Tiny example: preserve order in a parallel stream

```text
List<String> ordered = List.of("a","b","c","d");
ordered.parallelStream()
       .map(s -> s.toUpperCase())
       .forEachOrdered(System.out::println); // preserves encounter order
```

### Additional notes on terminology

- "Numeric streams" refers to `IntStream`, `LongStream`, and `DoubleStream` and their primitive-specialized operations.
- "Aggregates" means reduction-style terminal operations such as `sum`, `min`, `max`, `average`, `collect`, and `summaryStatistics`.
