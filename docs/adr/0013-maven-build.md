# 0013. Build with Maven

- **Status:** Accepted
- **Date:** 2022-11-01 (retrospective)
- **Deciders:** Heath Logan Campbell
- **Tags:** tooling, build

## Context

The project was an Eclipse project: `.classpath`, `.project`, and `.settings/` committed to
the repository, with dependencies added by hand. That works for one developer on one IDE
and fails as soon as the project needs Netty and jnativehook, or needs to be built
somewhere other than Eclipse.

## Decision

Build with Maven, single module, using `pom.xml`:

- Dependencies: `io.netty:netty-all:4.1.91.Final`, `com.github.kwhat:jnativehook:2.2.2`.
- Java source/target 18 in the compiler plugin (properties declare 19 - an inconsistency
  to clean up).
- `mvn package` produces the jar; the client and server mains are launched from it.

## Alternatives considered

- **Gradle** - equally capable; Maven was chosen for familiarity and because the author's
  other Java projects use it.
- **Keep Eclipse metadata** - not reproducible outside one IDE.

## Consequences

**Positive**

- Builds are reproducible and IDE-independent; dependencies are declared rather than
  vendored.
- The project can be built in CI or on another machine with a JDK and Maven.

**Negative**

- No `maven-shade-plugin` or `assembly` configuration, so the jar does not bundle Netty or
  jnativehook; a runnable artifact still needs manual assembly.
- Compiler settings are declared twice (plugin `18`, properties `19`) and disagree.
- No CI workflow, so "it builds" is only verified locally.

**Neutral**

- The `.idea/` directory is still committed, so IDE files remain in the repository.

## References

- Code: `pom.xml`
- Commit: `8237bb5 Covert to maven project`
