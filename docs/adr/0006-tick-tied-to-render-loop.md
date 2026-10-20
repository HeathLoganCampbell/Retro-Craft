# 0006. Drive the client tick from the render loop

- **Status:** Accepted
- **Date:** 2019-06 (retrospective)
- **Deciders:** Heath Logan Campbell
- **Tags:** simulation, game-loop

## Context

A simulation needs a stable rate: physics constants, block update timers and fluid flow all
assume a known number of ticks per second. A renderer wants to run as fast as the machine
allows. The usual answer is a fixed-timestep accumulator with interpolation.

This project is single-threaded, has no interpolation, and was written to be simple enough
to reason about in one sitting.

## Decision

The client runs one loop on one thread that does, in order: `tick(ticks)`, `render()`,
send the player's position, `Thread.sleep(5)`.

Tick rate is therefore tied to frame rate - roughly 200 ticks/second when rendering keeps
up. Simulation timers are expressed in ticks, not seconds: scheduled work uses
`tickCount % 100` (about 0.5s) and `tickCount % 10` (about 50ms for fluids).

The server instead uses a fixed `Thread.sleep(50)` after processing, giving a nominal
20 TPS.

## Alternatives considered

- **Fixed timestep with an accumulator** - the standard solution; rejected at the time for
  simplicity, and because there is no interpolation to hide the stutter.
- **Separate simulation thread** - would require locking or message passing for world
  state, which the project avoided until multiplayer forced a queue (see
  [0010](0010-single-threaded-server-loop.md)).

## Consequences

**Positive**

- One thread, one loop, no concurrency bugs in the client.
- Simulation and rendering always agree on the current frame; nothing is interpolated, so
  nothing can be visually out of date.
- Easy to debug: pause the loop, everything stops.

**Negative**

- Game speed depends on hardware and on how much of the screen is visible. A slower
  machine plays the game in slow motion rather than dropping frames.
- Physics and fluid constants are tuned by feel against an assumed ~200 TPS; changing the
  renderer changes gameplay.
- `Thread.sleep(5)` is a floor, not a period: the actual tick rate is 5ms plus frame time,
  which is why `tickCount % N` timers are approximate.

**Neutral**

- Client and server run at different rates (~200 vs 20 TPS), so server-side logic cannot
  assume the client's tick cadence.

## References

- Code: `src/main/java/com/craftclassic/client/Minecraft.java` (`run`, `tick`)
- Code: `src/main/java/com/craftclassic/server/RetroCraftServer.java`
