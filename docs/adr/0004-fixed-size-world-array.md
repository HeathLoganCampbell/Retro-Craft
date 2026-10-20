# 0004. World as a flat fixed-size Block array

- **Status:** Accepted
- **Date:** 2019-06 (retrospective)
- **Deciders:** Heath Logan Campbell
- **Tags:** world, data-structures

## Context

The game needs fast random access to blocks for three very different access patterns:
single-block edits from the player, raycasting along a view ray, and a full scan of the
world each tick to run block physics.

A chunked representation (16x16x16 sections with palettes and meshes) is what the real
Minecraft uses, but it exists mainly to support infinite worlds, streaming from disk, and
GPU meshes - none of which apply here.

## Decision

A world is a single `Block[] blockData = new Block[262144]`, i.e. 64x64x64, indexed from a
`Location` (x, y, z). The field is public and mutated directly through `World.setBlock`.
`IWorld` exposes the interface; `FlatWorldType` and `NaturalWorldType` fill the array at
construction time.

There is exactly one world per process, held by the client (`Minecraft.world`) and by the
server loop.

## Alternatives considered

- **Chunked sections** - more machinery for streaming and meshing that the renderer does
  not use (it raycasts the flat array directly).
- **Sparse map of positions** - cheaper for empty worlds, much slower for the per-tick
  full scan and for raycasting.

## Consequences

**Positive**

- O(1) access with trivial index arithmetic; iteration is cache-friendly.
- No serialization, chunk loading, or world-boundary logic to get wrong.
- The raycast and the tick scan can both walk the same array without adapters.

**Negative**

- World size is hard-coded at 64 blocks in each axis and lives in memory, ~262k object
  references plus the `Block` singletons.
- No chunk streaming, no saving, no multiple worlds. "Chunkize the world" is still an open
  item in the README's Things to add.
- `World.tick()` visits every one of the 262,144 positions every tick, allocating a
  `Location` per block, just to call `onTick` on mostly-air blocks. This is the largest
  known CPU cost in the game and the first thing to fix if the world grows.

**Neutral**

- `blockData` being public means any code can bypass `setBlock` and the events it raises.

## References

- Code: `src/main/java/com/craftclassic/common/world/World.java`
- Code: `src/main/java/com/craftclassic/common/world/types/`
