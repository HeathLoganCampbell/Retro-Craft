# 0014. Share one `common` package between client and server

- **Status:** Accepted
- **Date:** 2022-11-01 (retrospective)
- **Deciders:** Heath Logan Campbell
- **Tags:** structure, modules

## Context

Multiplayer meant two entry points - a client and a server - that must agree on the world
model, block ids, entity ids and packet layout. Duplicating that code across two projects
would drift immediately, and the packet classes are needed on both sides by construction.

The project is one Maven module and one jar.

## Decision

Split the source into three packages inside a single module:

- `com.craftclassic.common` - world, blocks, entities, packets, network pipeline, events.
- `com.craftclassic.client` - rendering, input, assets, overlays, Netty client.
- `com.craftclassic.server` - the server bootstrap and loop.

`common` is the shared vocabulary. The client depends on it; the server depends on it.

## Alternatives considered

- **Multi-module Maven build** (`common`, `client`, `server` artifacts) - the correct
  boundary, but more build machinery than a two-main project needs.
- **Duplicate the model per side** - guaranteed drift in packet layouts.

## Consequences

**Positive**

- One definition of the packet format, block ids and world model; no generated code or
  schema to keep in sync.
- Refactoring a packet touches one class and both sides see it.

**Negative**

- The boundary is not enforced by the compiler, and it has already leaked: `common` imports
  client classes. `World` depends on `Main`, `Minecraft` and `Textures`; `Player` depends on
  `Input` and `Minecraft`; `common.utils.Location` is constructed with a `World` that itself
  holds a `Minecraft`. The server therefore cannot run without AWT client classes on the
  classpath.
- `Minecraft.INSTANCE` is a global singleton used from `common`, so entity logic can only
  exist in a process with a client.
- One artifact means the server ships with rendering code it never uses.

**Neutral**

- Fixing the leak is mostly mechanical: move rendering-dependent calls behind an interface
  (`renderer`, `inputSource`) that the client implements.

## References

- Code: `src/main/java/com/craftclassic/common/`
- Code: `src/main/java/com/craftclassic/common/world/World.java` (imports `client.Main`)
- Code: `src/main/java/com/craftclassic/common/entities/Player.java` (imports `client.Minecraft`)
