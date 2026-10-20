# 0011. Trust the client for movement

- **Status:** Accepted
- **Date:** 2022-11-01 (retrospective)
- **Deciders:** Heath Logan Campbell
- **Tags:** networking, security

## Context

Multiplayer was added to a game whose movement already existed, entirely client-side:
input, velocity, collision and gravity all run in `Player.tick()` on the client. The
server has no physics implementation and no notion of a client's input.

Two options: port the movement simulation to the server and send inputs, or let clients
report where they are.

## Decision

Clients are authoritative over their own position. Every frame the client sends an
`EntityPositionPacket` containing its entity id and x/y/z. The server overwrites the
entity id from the connection, then rebroadcasts the packet to every other client. The
server never validates the position, the speed, or the distance travelled.

Block edits are the exception in spirit but not in enforcement: the client applies the
edit locally and tells the server, which applies the same edit and broadcasts it.

## Alternatives considered

- **Server-authoritative movement** - would require moving `Player.tick` physics (and the
  collision code it depends on) to the server, plus input packets and client prediction.
  A significantly larger change than the one that shipped.
- **Hybrid (server simulates, client predicts)** - the correct answer for a public server;
  out of scope for a multiplayer test.

## Consequences

**Positive**

- Multiplayer works with no server-side physics, no prediction and no reconciliation;
  what you see locally is exactly what you send.
- Minimal change to the client: one extra write per frame plus a broadcast handler.

**Negative**

- Any client can teleport, fly, clip through blocks or edit at any distance. This is fine
  for playing with friends and unacceptable for a public server.
- Bandwidth scales with frame rate: at ~200 FPS each client sends ~200 position packets per
  second, each carrying a 4-byte entity id and three doubles.
- The server has no position to validate block edits against, so reach checks are not
  possible without adding the missing simulation.

**Neutral**

- The packet already carries unused `yaw`, `pitch` and velocity fields, suggesting the
  intended direction was richer state than is currently sent.

## References

- Code: `src/main/java/com/craftclassic/common/network/packet/packets/EntityPositionPacket.java`
- Code: `src/main/java/com/craftclassic/client/Minecraft.java` (`run`)
- Code: `src/main/java/com/craftclassic/common/entities/Player.java`
