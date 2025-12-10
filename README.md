# Java Streams Documentation

This folder contains comprehensive documentation and examples for the Java Streams API, organized for easy navigation and interview preparation.

## Guides

- **Overview**: `streams_overview.md` — introduction to streams, key properties (lazy evaluation, non-destructive), pipeline stages, and diagrams. (See examples: `demo_programs/StreamCreationExample.java`)
- **Creation**: `streams_creation.md` — creating streams from collections, arrays, files, generators, and primitive types (`IntStream`, `LongStream`, `DoubleStream`). (See examples: `demo_programs/StreamCreationExample.java`)
- **Intermediate Operations**: `streams_intermediate_operations.md` — lazy intermediate ops like `map`, `filter`, `flatMap`, `sorted`, `distinct`, `peek`, and stream control methods. (See examples: `demo_programs/StreamIntermediateExample.java`)
- **Terminal Operations**: `streams_terminal_operations.md` — terminal ops including `forEach`, `collect`, `reduce`, `min`/`max`, `count`, `anyMatch`/`allMatch`/`noneMatch`, and custom collectors. (See examples: `demo_programs/StreamTerminalExample.java`)
- **Primitive Streams & Collectors**: `streams_primitive_collectors.md` — `IntStream`/`LongStream`/`DoubleStream`, `IntSummaryStatistics`, and efficient aggregation techniques. (See examples: `demo_programs/StreamPrimitiveCollectorsExample.java`)
- **Short-Circuiting**: `streams_short_circuit.md` — `findFirst`, `findAny`, `limit`, `takeWhile`, `dropWhile` and early termination patterns. (See examples: `demo_programs/StreamShortCircuitExample.java`)
- **Parallel Streams & Best Practices**: `streams_parallel.md` — guidance on parallel streams, stateful operations, resource handling, and when to prefer sequential processing. (See examples: `demo_programs/StreamParallelExample.java`)
- **Summary / Cheat-sheet**: `streams_summary.md` — concise quick reference, interview checklist, and common patterns.

## Example programs

- `demo_programs/StreamCreationExample.java` — demonstrates creation from collections, arrays, `IntStream.range`, and infinite generators.
- `demo_programs/StreamIntermediateExample.java` — shows `filter`, `map`, `flatMap`, `sorted`, `peek`, and `mapMulti` usages.
- `demo_programs/StreamTerminalExample.java` — illustrates `collect`, `reduce`, `forEach`, `findFirst`/`findAny`, and collector examples.
- `demo_programs/StreamPrimitiveCollectorsExample.java` — examples for primitive streams and summarizing collectors.
- `demo_programs/StreamShortCircuitExample.java` — demonstrates `limit`, `takeWhile`, `dropWhile`, and early termination patterns.
- `demo_programs/StreamParallelExample.java` — parallel stream demos and performance notes.

## Quiz

- `streams_quiz.md` — 50 MCQs and 50 essay prompts for self-assessment and interview practice.

## Prerequisites

- Java 8 or higher (Streams API introduced in Java 8). Note: some features in these guides require newer Java versions — marked as `(Java 9+)`, `(Java 10+)`, or `(Java 16+)` where applicable.
- Basic knowledge of Java collections and lambda expressions.
- IDE with Java support (IntelliJ IDEA, Eclipse, or VS Code).

## Quick Start (concise)

- Step 1: Read `streams_overview.md` → understand pipeline (Creation → Intermediate → Terminal). **Interview focus:** `streams_summary.md` (cheat-sheet).
- Step 2: Run `demo_programs/StreamCreationExample.java` → practice creation patterns.
- Step 3: Read `streams_intermediate_operations.md` and run `demo_programs/StreamIntermediateExample.java` → practice transformations & filters.
- Step 4: Read `streams_terminal_operations.md` and run `demo_programs/StreamTerminalExample.java` → practice `collect` vs `reduce` and matching/short-circuiting.
- Step 5: Cover `streams_primitive_collectors.md`, `streams_short_circuit.md`, and `streams_parallel.md` for advanced topics and performance.
- Step 6: Use `streams_summary.md` for last-minute interview review. **Interview focus:** cheat-sheet and common pitfalls (parallel/shared state, short-circuiting).

## Study roadmap (mini diagram)

```
Overview → Creation → Intermediate → Terminal → Advanced (Primitive, Short-circuit, Parallel) → Summary
```

## Java version notes (quick reference)

- `(Java 9+)` — `Stream.ofNullable()`, `takeWhile()`, `dropWhile()`, `Stream.iterate(seed, predicate, f)`
- `(Java 10+)` — `Collectors.toUnmodifiableList()` and other collectors improvements
- `(Java 16+)` — `Stream.mapMulti()` and `Stream.toList()` behavior as unmodifiable

## References

- Oracle Java Tutorials — Streams: https://docs.oracle.com/javase/tutorial/collections/streams/
- Java API (Stream package): https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/stream/package-summary.html

For interview prep, focus on `streams_summary.md` and run the corresponding examples in `demo_programs/` to internalize behavior and edge cases.
