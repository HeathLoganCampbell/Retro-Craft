# 0010. Hand packets to one game-loop thread

- **Status:** Accepted
- **Date:** 2022-11-01 (retrospective)
- **Deciders:** Heath Logan Campbell
- **Tags:** networking, concurrency

## Context

Netty delivers `channelRead` on its own event-loop threads, potentially one per connection.
World state (`World`, `ConnectionManager`, block edits) is not thread-safe: it is plain
arrays, `ArrayList`s and a static singleton map.

Option one was to lock the world. Option two was to keep world state owned by a single
thread and pass messages to it.

## Decision

Netty threads never touch game state. `PacketProcessor` looks up (or creates) a
`PlayerConnection` and pushes the packet onto that connection's
`ConcurrentLinkedQueue`. The server's main loop, which runs at 20 TPS (`sleep(50)`), drains
every connection's queue, calls `packet.process(...)` on its own thread, then flushes
outbound packets.

Client to server direction uses the same shape: the client writes packets via Netty, and
the server only mutates the world from the loop thread.

## Alternatives considered

- **Synchronize the world** - would put locks around 262k-block scans and make every block
  edit a potential contention point.
- **Actor per world** - the right long-term shape, but more machinery than one world needs.

## Consequences

**Positive**

- Game logic is effectively single-threaded, so no world locks and no torn reads.
- Packets for a player are processed in arrival order, which matters for place/break.
- Netty's threading model is fully contained in the pipeline; the rest of the code is
  ordinary sequential code.

**Negative**

- One global loop: a slow handler (a TNT chain, a full-world scan) delays every player.
- `Thread.sleep(50)` runs after the work, so the real tick period is 50ms plus processing
  time, and there is no catch-up if a tick overruns.
- Queues are unbounded, so a flooding client grows memory without backpressure.

**Neutral**

- Login is the exception: the first `PlayerConnectPacket` is processed on the Netty thread
  to create the connection, before any queuing happens.

## References

- Code: `src/main/java/com/craftclassic/common/network/PacketProcessor.java`
- Code: `src/main/java/com/craftclassic/common/network/player/PlayerConnection.java`
- Code: `src/main/java/com/craftclassic/server/RetroCraftServer.java`
