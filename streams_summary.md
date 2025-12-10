# Java Streams – Summary and References

## 12. Final Stream Summary and Real-World Usage

### Core philosophy (Theory)

- Streams are functional pipelines.
- Lazy execution: intermediate ops are not executed until a terminal op runs.
- Single-use: streams cannot be reused after a terminal operation.
- Parallelism is optional; enabling it changes execution model and ordering.
- You are responsible for thread-safety when using shared state.

---

### Real-world usage (Practice)

Quick one-line examples for common operations (copy this section for interviews):

Creation (sources)

- `collection.stream()` — e.g., `list.stream()`
- `collection.parallelStream()` — use with caution (parallel) 
- `Arrays.stream(arr)` — arrays to stream
- `Stream.of(a,b,...)` — explicit elements
- `Stream.ofNullable(x)` (Java 9+) — null-safe single-element stream
- `map.entrySet().stream()` — iterate key/value pairs

Intermediate (one-line snippets)

- `filter`: `stream.filter(x -> condition)`
- `map`: `stream.map(x -> transform)`
- `flatMap`: `stream.flatMap(x -> streamOf(...))`
- `mapMulti` (Java 16+): emit 0..N outputs per input
- `distinct`: `stream.distinct()`
- `sorted`: `stream.sorted()`
- `limit(n)`: `stream.limit(5)`
- `skip(n)`: `stream.skip(2)`
- `takeWhile(predicate)` / `dropWhile(predicate)` (Java 9+)
- `peek` (debug only): `stream.peek(System.out::println)`

Terminal (one-line snippets)

- `collect`: `stream.collect(Collectors.toList())`
- `reduce`: `stream.reduce(identity, accumulator)`
- `forEach`: `stream.forEach(System.out::println)`
- `forEachOrdered`: `stream.forEachOrdered(...)` (preserve order)
- `findFirst` / `findAny`: `stream.findFirst()` / `stream.findAny()`
- `anyMatch` / `allMatch` / `noneMatch`: short-circuiting predicates
- `count()`, `min()`, `max()` — aggregates

Primitive streams

- `IntStream`, `LongStream`, `DoubleStream` — avoid boxing for numeric-heavy work

Collectors (common)

- `Collectors.toList()` / `Collectors.toSet()` / `Collectors.toMap(...)`
- `Collectors.joining()`
- `Collectors.groupingBy(...)` / `Collectors.partitioningBy(...)`
- `Collectors.collectingAndThen(...)`, `summarizingInt()`

---

### Mini pipeline diagram (quick recall)

```
Data source (creation)
    ↓
Intermediate ops (map, filter, sorted, limit, ...)
    ↓
Terminal op (collect, reduce, forEach, findFirst, ...)
```

Short-circuiting note: `anyMatch`, `allMatch`, `noneMatch`, `findFirst`, `findAny`, and intermediate ops like `limit`/`takeWhile`/`dropWhile` can terminate the pipeline early.

Parallel streams — caution

- Parallel streams partition work across threads/tasks (can improve throughput for CPU-heavy, stateless work).
- Do NOT mutate shared mutable state from within parallel pipeline operations (race conditions, lost updates).
- Ordering is non-deterministic in parallel streams unless you use ordered terminal ops (`forEachOrdered`, `findFirst`, ordered collectors), which may reduce parallel benefits.
- Ensure collectors used with parallel streams are thread-safe or provide a correct combiner (`Collector.of(...)`), or prefer concurrent collectors like `groupingByConcurrent`.

### Java version notes

- Methods marked with `(Java 9+)`, `(Java 10+)`, `(Java 16+)` require those Java versions or newer. Examples: `Stream.ofNullable()` (Java 9+), `Collectors.toUnmodifiableList()` (Java 10+), `Stream.mapMulti()` (Java 16+), `Stream.toList()` behavior as unmodifiable (Java 16+).

### Short checklist — Best practices

- Prefer pure functions in intermediate ops; avoid side effects.
- Use primitive streams for numeric-heavy tasks to avoid boxing.
- Close I/O-backed streams with try-with-resources.
- Measure before parallelizing — benchmark with real data.
- Prefer collectors (`collect`) for building containers over `reduce`.

---

### References and further reading

- Oracle Java Tutorials — Streams: https://docs.oracle.com/javase/tutorial/collections/streams/
- Java API (Stream package): https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/stream/package-summary.html
