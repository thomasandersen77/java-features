# java-features

Multi-modul Maven-playground for **Java 26** + **Spring Boot 4.1.1**.

## Krav

- [SDKMAN](https://sdkman.io/)
- Java **26.0.2-tem** (Temurin)
- Maven (via SDKMAN)

## Oppsett

```bash
cd java-features
sdk env          # bytter til java=26.0.2-tem fra .sdkmanrc
# eventuelt permanent auto-switch:
# echo "sdkman_auto_env=true" >> ~/.sdkman/etc/config
```

## Bygg og kjør

```bash
# hele reactoren
mvn test
mvn package

# kun language-features
mvn -pl language-features test

# kun Spring-slicene
mvn -pl spring-slices test
mvn -pl spring-slices spring-boot:run

# kjørbar Boot-jar
java -jar spring-slices/target/spring-slices-1.0-SNAPSHOT.jar
```

Parent setter `maven.compiler.release=26` og importerer Spring Boot **4.1.1** BOM.

## Modulstruktur

```
java-features/                         # parent POM (packaging pom)
  language-features/                   # 1) Pure Java language features — ingen Spring
    no.playground.features.*
    no.playground.features.shop.*      # domenemodell-øving (CartService m.m.)

  spring-slices/                       # 2) Spring Boot vertical slices
    no.playground.JavaFeaturesApplication
    no.playground.catalog.*            # ferdig referanse-slice  → /api/catalog-items
    no.playground.order.*              # skjelett-slice          → /api/orders
```

| Modul | Innhold | Spring? |
|-------|---------|---------|
| `language-features` | Språkfeatures, sealed/records/patterns, shop-øving | Nei |
| `spring-slices` | Catalog (ferdig) + Order (skjelett), H2/JPA/Web | Ja (Boot 4.1.1) |

Pakkene er uendret innad i hver modul — skillen er på **Maven-modulnivå**.
Kjør language-playground via `FeaturesApp` i IDE. Kjør API via `JavaFeaturesApplication` / `spring-boot:run`.

## Java-features fra 8 til 26

Kort oversikt over de viktigste språk- og plattform-featureene. LTS er markert.

### Java 8 (LTS, 2014)
- Lambdas, method references, functional interfaces
- Streams API, `Optional`
- Default/static methods i interfaces
- Nytt Date/Time API (`java.time`)
- Nashorn JS-engine, Base64, parallel arrays

### Java 9 (2017)
- Module system (JPMS / `module-info.java`)
- JShell, private interface methods
- Collection factory methods (`List.of`, `Set.of`, `Map.of`)
- Stream improvements (`takeWhile`, `dropWhile`, `ofNullable`)
- HTTP/2 client (incubator), multi-release JARs, G1 som default GC

### Java 10 (2018)
- Lokal type inference med `var`
- Unmodifiable collections (`List.copyOf`, `Collectors.toUnmodifiableList`)
- Application class-data sharing (AppCDS)

### Java 11 (LTS, 2018)
- HTTP Client standardisert
- `String` helpers (`isBlank`, `lines`, `strip`, `repeat`)
- `Files.readString` / `writeString`
- Single-file source-code launch (`java App.java`)
- Nest-based access control, epsilon GC, Flight Recorder

### Java 12 (2019)
- Switch expressions (preview)
- `String.indent` / `transform`, `Files.mismatch`
- Compact number formatting, Shenandoah GC (experimental)

### Java 13 (2019)
- Text blocks (preview)
- Switch yield, reimplementert legacy socket API
- Dynamic CDS archives

### Java 14 (2020)
- Switch expressions (standard)
- Records (preview), pattern matching for `instanceof` (preview)
- Helpful `NullPointerException`-meldinger
- JFR event streaming, packaging tool `jpackage` (incubator)

### Java 15 (2020)
- Text blocks (standard)
- Sealed classes (preview), hidden classes
- Edwards-Curve digitale signaturer (EdDSA)
- ZGC og Shenandoah production-ready (plattformavhengig)

### Java 16 (2021)
- Records (standard), pattern matching for `instanceof` (standard)
- `Stream.toList()`, `invoke` default methods, unix-domain sockets
- Vector API (incubator), foreign linker/memory (incubator)
- Strong encapsulation av JDK internals by default

### Java 17 (LTS, 2021)
- Sealed classes (standard)
- Pattern matching for switch (preview)
- Restore always-strict floating-point semantics
- New macOS rendering pipeline, AOT/experimental removal cleanup
- Context-specific deserialization filters

### Java 18 (2022)
- UTF-8 som default charset
- Simple web server (`jwebserver`)
- Code snippets in JavaDoc (`@snippet`)
- Pattern matching for switch (2. preview), vector/foreign API fortsatt incubator/preview

### Java 19 (2022)
- Virtual threads (preview), structured concurrency (incubator)
- Record patterns (preview)
- Pattern matching for switch (3. preview)
- Foreign Function & Memory API (preview)

### Java 20 (2023)
- Scoped values (incubator)
- Record patterns (2. preview), pattern matching for switch (4. preview)
- Virtual threads / structured concurrency fortsatt preview/incubator
- FFM API 2. preview

### Java 21 (LTS, 2023)
- Virtual threads (standard)
- Record patterns + pattern matching for switch (standard)
- Sequenced collections (`SequencedCollection` m.fl.)
- String templates (preview), unnamed patterns/variables (preview)
- Scoped values (preview), structured concurrency (preview)
- Generational ZGC, key encapsulation mechanisms (KEM)

### Java 22 (2024)
- Unnamed variables & patterns (standard)
- Foreign Function & Memory API (standard)
- Multi-file source-code programs
- Statements before `super(...)` (preview)
- Stream gatherers (preview), string templates (2. preview)
- Class-file API (preview), structured concurrency (2. preview)

### Java 23 (2024)
- Primitive types in patterns, instanceof and switch (preview)
- Markdown documentation comments
- Module import declarations (preview)
- Flexible constructor bodies (preview; tidligere «statements before super»)
- Stream gatherers (2. preview), class-file API (2. preview)
- Structured concurrency (3. preview), scoped values (3. preview)
- ZGC generational by default

### Java 24 (2025)
- Stream gatherers (standard)
- Class-file API (standard)
- Flexible constructor bodies (2. preview)
- Module import declarations (2. preview)
- Primitive types in patterns (2. preview)
- Structured concurrency (4. preview), scoped values (4. preview)
- Ahead-of-time class loading/linking improvements, compact object headers (experimental)
- Quantum-resistant crypto (ML-KEM/ML-DSA) lander gradvis i JDK

### Java 25 (LTS, 2025)
- Module import declarations (standard)
- Flexible constructor bodies (standard)
- Scoped values (standard)
- Compact source files / instance main methods (standardiseres i denne generasjonen)
- Structured concurrency fortsatt under modning (preview)
- Primitive patterns fortsatt preview
- Stabil LTS-plattform for produksjon etter 21

### Java 26 (2026) — denne playgrounden
- Fortsatt preview/modning av structured concurrency og relaterte concurrency-API-er
- Videreutvikling av pattern matching (inkl. primitive patterns der det fortsatt er preview)
- Performance- og runtime-forbedringer (GC, AOT/CDS, headers, startup)
- Løpende sikkerhets- og kryptooppdateringer oppå Java 25-basen

> Merk: Exact «standard vs preview»-status for de nyeste preview-API-ene kan skifte mellom early-access og GA. Sjekk [OpenJDK JEPs](https://openjdk.org/jeps/) for den konkrete builden du kjører (`java -version`).
