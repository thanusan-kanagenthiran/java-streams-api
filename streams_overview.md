# Java Streams – Overview

## 1. Overview

Why were streams introduced?

Java Streams were introduced to provide a concise, functional-style API for processing sequences of elements. They make it easier to write readable, declarative pipelines for common data-processing tasks (filtering, mapping, grouping, and aggregation) and to take advantage of multicore processors via parallel execution without manually managing threads.

A **stream** in Java represents a sequence of elements supporting functional-style operations. Streams do not store elements; they operate on a data source such as collections, arrays, I/O channels, or generators.

### Key Properties

* **Laziness:** Streams are lazy—no intermediate operation runs until a terminal operation is invoked. This enables efficient, on-demand processing and optimization of the pipeline.
* **Single-use:** Streams cannot be reused after a terminal operation.
* **Sequential or Parallel:** Streams can be sequential or parallel. Parallel streams split work across multiple threads to utilize multiple CPU cores and can improve throughput for appropriate workloads. Parallel processing may change the order in which elements are processed and results are produced; to preserve encounter order use `forEachOrdered`, `findFirst`, or ordered collectors. Be careful with shared mutable state and thread-safety when using parallel streams.
* **Stateless operations:** Intermediate operations should be stateless — they should not depend on mutable state that changes across elements.
* **Non-interfering:** Operations must not modify the underlying data source during processing.

### Visualizing the Pipeline

The core concept of Streams is the pipeline: **Creation → Intermediate → Terminal**. Laziness means intermediate operations are not executed until the terminal operation triggers the flow.

```
Data Source
    ↓
Creation Operation (e.g., stream())
    ↓
Intermediate Operations (lazy, e.g., filter(), map())
    ↓
Terminal Operation (triggers execution, e.g., collect())
    ↓
Result
```

This diagram emphasizes that the pipeline is built lazily—nothing happens until the terminal operation pulls data through.

### Small example — a complete stream pipeline

```java
import java.util.*;
import java.util.stream.*;

public class StreamPipelineExample {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David", "Eve");

        // Creation: stream()  -> Intermediate: filter(), map()  -> Terminal: collect()
        List<String> result = names.stream()                         // Creation operation
            .filter(s -> s.length() <= 4)                            // Intermediate operation (stateless)
            .map(String::toUpperCase)                                // Intermediate operation (stateless)
            .sorted()                                                // Intermediate operation (stateful, orders elements)
            .collect(Collectors.toList());                          // Terminal operation (triggers execution)

        System.out.println(result); // [BOB, EVE]
    }
}
```

### Common use cases

Streams are commonly used for:

* Transforming collections (map/filter)
* Aggregation and summarization (sum, average, min/max, grouping)
* Building pipelines for I/O and data processing (lines from files, network streams)
* Parallel data processing for CPU-bound workloads
* Creating and consuming infinite or generated sequences (Stream.generate, Stream.iterate)
