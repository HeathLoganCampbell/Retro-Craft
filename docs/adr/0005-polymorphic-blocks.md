# 0005. Model blocks as polymorphic classes

- **Status:** Accepted
- **Date:** 2019-07-02 (retrospective)
- **Deciders:** Heath Logan Campbell
- **Tags:** world, design

## Context

Block behaviour started as data: an id, a texture, a few booleans. That works until blocks
need to *do* things - sand and gravel fall, water and lava spread, TNT explodes and chains,
bedrock cannot be broken. Encoding that as `if (id == SAND)` in the world tick does not
scale.

The commit that made the change is explicit about the motivation:
`0fb772d use block classes for more control of block controlls`.
The preceding commit, `7474756 define block types`, introduced the id table.

## Decision

Each block type is a class extending `Block`, and behaviour is overridden per type:

- `onPlace(PlaceEvent)` / `onBreak(BreakEvent)` gate edits and raise events.
- `onTick(Location)` runs per-block simulation - `Block` implements gravity, `LiquidBlock`
  implements spreading, `TNTBlock` triggers a chained explosion.
- Instances register themselves into a static `id -> Block` map from the constructor, and
  `Block.getBlockById` resolves ids from the wire and from saves.

Blocks are effectively singletons: one instance per type, shared by every position in the
world.

## Alternatives considered

- **Data-driven block definitions** (JSON with behaviour flags) - more flexible for modding,
  but the interesting behaviours (liquid spread, chain explosions) are code, not data.
- **An ECS** - a much larger rewrite for a solo project of this size.

## Consequences

**Positive**

- Adding a block is one new class; no central switch to edit.
- Behaviour lives next to the state it acts on, and events give an extension point.
- A block instance is stateless with respect to position, so memory per world position is
  one reference.

**Negative**

- 15 block subclasses for a small game, with duplicated boilerplate in constructors.
- Registration happens as a constructor side effect into a static map, which makes load
  order matter and makes blocks hard to instantiate in isolation in a test.
- Polymorphism is dispatched 262,144 times per tick (see
  [0004](0004-fixed-size-world-array.md)), almost always to a no-op `onTick`.

**Neutral**

- Because blocks are singletons, any per-position state (growth timers, burn time) has to
  live in the world, not on the block.

## References

- Code: `src/main/java/com/craftclassic/common/blocks/Block.java`
- Commits: `7474756`, `0fb772d`, `a399e1e`, `ec1e8a0`, `4ef47c8`
