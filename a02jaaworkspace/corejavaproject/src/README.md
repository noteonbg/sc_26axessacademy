# Functional Programming in Java — Finance Edition (Beginner Friendly)


## What is Functional Programming (in one line)?

It is a **declarative** style ("say *what* you want") instead of the older **imperative** style
("write *every step* by hand"). The goal is code that is shorter, easier to read, easier to test,
and ready for parallel processing.

---

## The Six Concepts (mapped to the PDF)

1. **Functional Interface** — an interface with exactly one abstract method (e.g. an `InterestRule`
   that calculates interest). Marked with `@FunctionalInterface`. It is the "shape" that a lambda fills.

2. **Lambda Expression** — an anonymous function written as `(parameters) -> body`. It provides the
   one-line implementation of a functional interface, removing the boilerplate of anonymous classes.

3. **Built-in Functional Interfaces** (`java.util.function`) — Java already provides the four you
   need most: `Function<T,R>` (transform), `Predicate<T>` (test true/false), `Consumer<T>` (do
   something, return nothing), and `Supplier<T>` (produce a value from nothing).

4. **Method Reference** — a shorthand (`::`) for a lambda that only calls one existing method.
   Four kinds: static (`Class::method`), a specific object (`obj::method`), an arbitrary object of a
   type (`Class::method`), and a constructor (`Class::new`).

5. **Stream API** — a pipeline over a collection: a *source*, some *intermediate operations*
   (`filter`, `map`, `sorted`, `distinct`) and one *terminal operation* (`forEach`, `collect`,
   `reduce`). Streams never change the original data; they return a new result.

6. **Optional Class** — a box that either holds a value or is empty, used to avoid
   `NullPointerException`. You handle the "no value" case explicitly with `ifPresent`, `orElse`,
   `orElseGet`, `map`, etc.

---

## Folder / Package Structure

```
FunctionalProgrammingDemo/
├── README.md
└── src/
    └── com/
        └── campus/
            └── finance/
                ├── RunAllDemos.java              (runs all six demos in order)
                ├── model/
                │   ├── Account.java              (simple account data)
                │   ├── Transaction.java          (simple transaction data)
                │   └── SampleData.java           (ready-made lists to reuse)
                └── demo/
                    ├── Demo1_FunctionalInterface.java
                    ├── Demo2_LambdaExpression.java
                    ├── Demo3_BuiltInInterfaces.java
                    ├── Demo4_MethodReference.java
                    ├── Demo5_StreamApi.java
                    └── Demo6_Optional.java
```

The Java **package** for all files is `com.campus.finance` (with sub-packages `.model` and `.demo`).

---

## What Each Demo Shows (Finance Examples)

1. `Demo1_FunctionalInterface` — define an `InterestRule` and compute savings (4%) and FD (7%)
   interest; compares the old anonymous-class way with the new lambda way.
2. `Demo2_LambdaExpression` — zero, one and two parameter lambdas: pick a currency, compute a 10%
   bonus, add two instalments.
3. `Demo3_BuiltInInterfaces` — `Function` (bonus), `Predicate` (is high-value account),
   `Consumer` (print account), `Supplier` (generate an account number).
4. `Demo4_MethodReference` — all four method-reference types using accounts.
5. `Demo5_StreamApi` — `filter` high-value accounts, `map` to greetings, `reduce` the total balance,
   unique Mumbai customers sorted by name, bonus per customer, group customers by city, total spend.
6. `Demo6_Optional` — safely look up an account by id and avoid `NullPointerException`.

---

## How to Run

You need the **JDK 8 or newer** installed (`java -version` to check).

Open a terminal **inside the `FunctionalProgrammingDemo` folder**, then:

### Compile everything

```bash
javac -d out src/com/campus/finance/model/*.java src/com/campus/finance/demo/*.java src/com/campus/finance/RunAllDemos.java
```

This puts the compiled `.class` files into an `out` folder.

### Run all six demos together

```bash
java -cp out com.campus.finance.RunAllDemos
```

### Or run just one concept at a time

```bash
java -cp out com.campus.finance.demo.Demo1_FunctionalInterface
java -cp out com.campus.finance.demo.Demo2_LambdaExpression
java -cp out com.campus.finance.demo.Demo3_BuiltInInterfaces
java -cp out com.campus.finance.demo.Demo4_MethodReference
java -cp out com.campus.finance.demo.Demo5_StreamApi
java -cp out com.campus.finance.demo.Demo6_Optional
```

> Tip (Windows PowerShell): the commands above work the same. If `javac`/`java` are "not
> recognised", install the JDK and make sure its `bin` folder is on your `PATH`.

---

## Suggested Learning Path

1. Read `Demo1` and `Demo2` first — functional interface + lambda are the foundation of everything.
2. Then `Demo3` (the ready-made interfaces you'll actually use day to day).
3. Then `Demo4` (method references — just a shortcut for certain lambdas).
4. Then `Demo5` (Streams — where functional programming really pays off).
5. Finally `Demo6` (Optional — writing safe, crash-free lookups).

Change the numbers, add your own accounts in `SampleData.java`, and re-run — experimenting is the
fastest way to learn.
