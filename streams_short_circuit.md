# Java Streams – Short-Circuit Operations

## 8. Short-Circuit Operations

Short-circuit operations are stream operations that may cause the evaluation of a stream pipeline to stop before processing all elements. They are crucial for efficiency, especially with large or infinite streams.

### Terminal Short-Circuit Operations

These operations end the stream pipeline and return a result as soon as the outcome is determined.

- `findFirst()` — Returns the first element (if any) and stops further processing. Deterministic in sequential streams.
- `findAny()` — Returns any element. Deterministic in sequential streams, non-deterministic and may be optimized for concurrency in parallel streams (Java 8+). Use `findAny()` when you don't care about which element is returned; prefer `findFirst()` when encounter order matters.
- `anyMatch(Predicate)` — Returns `true` as soon as any element matches the predicate.
- `allMatch(Predicate)` — Returns `false` as soon as any element does not match the predicate.
- `noneMatch(Predicate)` — Returns `false` as soon as any element matches the predicate.

Short note on parallel behavior: some terminal short-circuit operations (notably `findAny`) can return different elements when the stream is parallel because work is done by multiple threads; `findFirst` preserves the encounter-first result but can be slower in parallel.

### Intermediate Short-Circuit Operations

These intermediate operations restrict or limit the number of elements passed downstream and can cause the pipeline to stop early when their condition is satisfied.

- `limit(n)` — Truncates the stream to at most `n` elements. Warning: in ordered parallel streams, additional elements may be processed internally to preserve correctness; the observable result will still be at most `n` elements.
- `takeWhile(Predicate)` (Java 9+) — Takes elements while the predicate is true, then stops.
- `dropWhile(Predicate)` (Java 9+) — Drops elements while the predicate is true, then processes the rest.

### Why are they important?

- Efficiency: they avoid unnecessary computation by halting work as soon as the result is known.
- Infinite streams: essential for obtaining finite results from infinite generators (e.g., `Stream.generate`, `iterate`).
- Performance: reduce CPU and memory usage on large datasets, especially when combined with early termination.

### Examples

```text
// Terminal short-circuit: Find the first even number
Optional<Integer> firstEven = Stream.of(1, 3, 5, 6, 7, 8)
    .filter(n -> n % 2 == 0)
    .findFirst(); // Optional[6]

// Parallel note: findAny() may return any matching element when stream is parallel
Optional<Integer> anyEven = Stream.of(1,2,3,4,5,6).parallel()
    .filter(n -> n % 2 == 0)
    .findAny(); // could be 2, 4, or 6 depending on scheduling

// Intermediate short-circuit: Limit to first 3 elements
List<Integer> firstThree = IntStream.range(1, 100)
    .limit(3)
    .boxed()
    .collect(Collectors.toList()); // [1, 2, 3]

// takeWhile / dropWhile examples (Java 9+)
List<Integer> taken = Stream.of(5,4,3,0,2,1)
    .takeWhile(n -> n > 0) // [5,4,3]
    .collect(Collectors.toList());

List<Integer> dropped = Stream.of(5,4,3,0,2,1)
    .dropWhile(n -> n > 0) // [0,2,1]
    .collect(Collectors.toList());
```

### Mini diagram: pipeline stops early

This simple diagram shows a pipeline where a short-circuit operation stops processing before all elements are examined:

```
Data source: [a, b, c, d, e, f]
    ↓
map/filter -> (processing)
    ↓
short-circuit (e.g., findFirst or limit) ← stops here early
    ↓
terminal (collect/forEach)
```

### Key points and best practices

- Short-circuiting is only possible with certain operations (see lists above).
- Short-circuiting is essential for working with infinite streams; always pair infinite generators with a short-circuit (e.g., `limit`, `findFirst`).
- Markers: `takeWhile` / `dropWhile` are Java 9+ — ensure your runtime supports them.
- Use `findAny()` for potential parallel performance advantages only when you don't depend on a specific element being returned.
