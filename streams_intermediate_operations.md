# Java Streams – Intermediate Operations

## 3. Intermediate Operations (Lazy)

Intermediate operations return a new stream and are lazy — they are not executed until a terminal operation runs. Grouped below for easier scanning.

### Transformations

- **`map` / `mapToX`**: Transforms each element (for example, object → property or to a primitive type).
- **`flatMap`**: Maps each element to a stream, then flattens resulting streams into a single stream.
- **`mapMulti`** (Java 16+): Maps a single input element to zero or more output elements using a consumer-based callback; often more efficient than `flatMap` for emitting multiple elements per input.

  Example (`mapMulti`, Java 16+):
  ```java
  // Expand words into characters
  Stream.of("hi","ok")
        .<Character>mapMulti((word, consumer) -> {
            for (char c : word.toCharArray()) consumer.accept(c);
        })
        .forEach(System.out::println);
  ```

### Filtering

- **`filter`**: Retains only elements matching a predicate (boolean condition).
- **`distinct`**: Removes duplicate elements (uses `equals`).
- **`takeWhile`** / **`dropWhile`** (Java 9+): `takeWhile` keeps elements while the predicate is true and then stops; `dropWhile` skips elements while the predicate is true and then keeps the rest.

  Example (`takeWhile` / `dropWhile`, Java 9+):
  ```java
  Stream.of(1,2,3,0,4,5)
        .takeWhile(n -> n > 0) // results: 1,2,3
        .forEach(System.out::println);

  Stream.of(1,2,3,0,4,5)
        .dropWhile(n -> n > 0) // results: 0,4,5
        .forEach(System.out::println);
  ```

### Slicing (size control)

- **`limit(n)`**: Truncates the stream to at most `n` elements.
- **`skip(n)`**: Skips the first `n` elements and returns the rest.

### Ordering and uniqueness

- **`sorted`**: Sorts elements either naturally or with a provided comparator. Sorting can be costly, especially on large or parallel streams.
- **`distinct`**: (listed above under filtering) Removes duplicates.

### Debugging helper

- **`peek`**: Performs an action for each element as it passes through, intended strictly for debugging or logging. Avoid using `peek` for business logic or to mutate shared state; side effects in `peek` are discouraged and can lead to surprising behavior, especially with parallel streams.

  Short note: If you find yourself using `peek` to implement logic, refactor to an explicit `map` or a terminal operation.

### Mini diagram (reinforces laziness)

Intermediate operations are lazy — they build a pipeline that is executed only when a terminal operation runs:

```
Intermediate operations (e.g., map, filter, sorted)
    →
Terminal operation (e.g., collect, forEach)  // triggers execution
```

## 4. Stream Control (`BaseStream` methods)

These methods affect stream execution or interoperability. Most are rarely needed; use them for specific scenarios.

- **`parallel()` / `sequential()`**: Switches the stream’s execution mode. Use `parallel()` for CPU-bound, stateless workloads on large datasets; otherwise prefer sequential execution.
- **`unordered()`**: Relaxes ordering constraints for potential optimizations; does not reorder elements.
- **`isParallel()`**: Checks if the stream is currently parallel.
- **`close()`**: Releases resources associated with the stream; important for streams backed by I/O (files, sockets). Prefer try-with-resources for closable streams.
- **`onClose(Runnable)`**: Registers a handler to run when the stream is closed; useful for cleanup when you create streams that acquire external resources. Rarely needed in typical collection pipelines.
- **`iterator()` / `spliterator()`**: Provides an `Iterator` or `Spliterator` for interoperability with older APIs or custom splitting logic; typically only needed when integrating with non-stream APIs or implementing custom collectors.

Short practical guidance:
- Use `onClose` when a stream wrapper needs to ensure external resources are released on close.
- Use `iterator()` when you must pass stream elements to legacy code that expects an `Iterator`.
- Use `spliterator()` if you need fine-grained control over partitioning or parallelization behavior.

**Version notes:** Methods marked `(Java 9+)` or `(Java 16+)` require the indicated Java version or newer.
