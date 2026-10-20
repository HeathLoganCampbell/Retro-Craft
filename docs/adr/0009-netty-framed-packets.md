# 0009. Use Netty with length-prefixed packets

- **Status:** Accepted
- **Date:** 2022-11-01 (retrospective)
- **Deciders:** Heath Logan Campbell
- **Tags:** networking, protocol

## Context

The game was single-player for three years. Adding multiplayer meant choosing a transport
and a message format. The author already knew the Minecraft protocol from reverse
engineering it in Nanocraft, and wanted a protocol that was explicit and easy to extend
rather than a serialization framework.

The choice of networking library was not neutral. Plain Java sockets (`java.net.Socket`,
`DataInputStream`) felt awkward to write against, and the author had significantly more
experience with Netty from previous server work. Netty was also the more enjoyable option:
it is flexible enough to compose exactly the pipeline this protocol needs, and fast enough
that the network layer is not the bottleneck. For a hobby project sustained over years,
enjoyment is a legitimate selection criterion - code you like writing gets finished.

TCP was acceptable: a voxel game can tolerate head-of-line blocking, and the project did
not want to implement reliability over UDP.

## Decision

Use Netty 4.1 over TCP with a small hand-rolled protocol. Netty is a deliberate dependency,
chosen for its pipeline model, performance and developer experience, not treated as a
generic transport:

- `Packet` is an abstract class with `encode(ByteBuf)`, `decode(ByteBuf)` and
  `process(PlayerConnection)`.
- `PacketRegistry` maps integer ids (1-6) to packet classes and to `Supplier`s used to
  instantiate inbound packets. Current packets: connect, disconnect, entity position,
  entity spawn, block break, block place.
- `Encapsulator` / `Decapsulator` frame messages with a 4-byte big-endian length prefix.
- The server enables `TCP_NODELAY` and sets `IP_TOS` to `0x18` (low delay, high throughput).
- Client and server share the same packet classes and the same codec classes.

## Alternatives considered

- **`java.net.Socket` with `DataInputStream` / `DataOutputStream`** - rejected: workable for
  six packets, but the blocking, thread-per-connection shape and the manual framing felt
  awkward, and it would have replaced Netty's pipeline with hand-rolled read loops.
- **`java.nio` channels + selector** - rejected: the same flexibility as Netty with far more
  boilerplate, in an area where the author had less experience.
- **Java serialization** - rejected: brittle across versions and opaque on the wire.
- **Protobuf / FlatBuffers** - rejected: a schema compiler for six messages, and it hides
  the byte layout the project wanted to control.
- **UDP with a custom reliability layer** - a much larger project than the game needed.

## Consequences

**Positive**

- The wire format is visible and controllable; adding a field is a local change.
- Encoding and decoding live next to the packet, so there is no separate schema to drift.
- Framing is explicit, so partial reads are handled by Netty's decoder rather than by game
  code.
- The pipeline model made the protocol cheap to express: framing, decoding and dispatch are
  three small handlers rather than a read loop.
- Netty is efficient enough that the network layer has never been the bottleneck, and it is
  a dependency the author knows well and enjoys working with.

**Negative**

- `netty-all` is a large dependency for six packet types, and the transport is now coupled
  to a framework rather than to the JDK.
- No protocol versioning or negotiation: client and server must be built from the same
  commit, and a mismatch fails at decode time rather than with a clear error.
- No compression and no encryption; `PacketRegistry` ids are hand-assigned and easy to
  collide.
- `PacketRegistry.getPacketId` reads `Integer.MIN_VALUE` from the map after a null check,
  which will throw `NullPointerException` (not the intended `IllegalArgumentException`)
  for an unregistered packet class.
- The only authentication is "first packet must be a connect packet" - see
  [0011](0011-client-authoritative-movement.md).

**Neutral**

- The registry is a global singleton (`PacketRegistry.INSTANCE`) shared by both sides.

## References

- Code: `src/main/java/com/craftclassic/common/network/packet/`
- Code: `src/main/java/com/craftclassic/common/network/pipeline/`
- Commit: `1a87cdc Add Sprites and multiplayer`
