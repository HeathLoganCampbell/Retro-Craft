# 0015. Render other players as 2D sprites

- **Status:** Accepted
- **Date:** 2022-11-01 (retrospective)
- **Deciders:** Heath Logan Campbell
- **Tags:** rendering, entities

## Context

Multiplayer arrived in `1a87cdc Add Sprites and multiplayer`, which introduced other players
as visible entities. The renderer only knows how to do one thing: cast a ray per screen
pixel and shade the face of a block it hits (see
[0002](0002-pure-java-software-renderer.md)). It has no polygon pipeline, no mesh format
and no concept of a model.

A 3D player model would therefore have needed a second renderer: either a rasteriser for
transformed boxes, or a per-entity raycast that traverses a small bounding volume for every
pixel the entity covers. Both add cost and machinery to the part of the codebase that was
deliberately kept minimal.

At the same time, the project's target is the feel of late-90s/early-2000s games, and those
games rendered characters as camera-facing sprites. A billboard is both the cheap option
and the period-correct one, and the Doom-like look was something to aim for rather than
avoid.

## Decision

Other players are drawn as camera-facing billboard sprites, not geometry.

`World.renderSprite` projects the entity's world position into view space, then screen
space, and draws a 32x64 texture from `player.png` (scaled 4x) with a size proportional to
`1 / depth`. Pixels are sampled with an alpha test (`0xFF000000` is treated as transparent)
and each one is depth-tested against the same `zBuffer` the world renderer fills, writing
its depth back as it goes.

The local player (entity id 0) is skipped, and there is a single sprite frame - no
direction, animation or facing.

## Alternatives considered

- **A 3D block model per player** - the usual Minecraft approach. Rejected: it needs a
  polygon/raster path or an inner raycast per entity, which is a second renderer for a
  small visual gain.
- **A cube drawn with the existing block code** - cheaper to write than a model, but it
  reads as a block, not a character, and still needs per-entity raycasts.
- **Sprite sorting instead of depth testing** - would order sprites by distance and draw
  back-to-front. Rejected: the world z-buffer already exists, and using it avoids sorting
  and gives correct per-pixel occlusion against terrain for free.

## Consequences

**Positive**

- Cost is proportional to the sprite's area on screen, not to its geometry or to scene
  complexity; a distant player costs almost nothing.
- One PNG provides the entire character, so adding an entity type is an art change.
- The per-pixel z-test means players are occluded correctly by blocks and by each other
  without any sorting pass.
- The look is the intended one: characters as sprites is exactly how the games being
  recreated did it.

**Negative**

- Every player looks the same regardless of which way they face; there is no back, side or
  walk frame, because the sprite system has no notion of direction.
- The whole sprite is drawn at a single depth (the entity's view-space z), not per-pixel
  depth. It therefore clips hard against block edges and cannot half-intersect geometry.
- Scaling is `1/depth` against a fixed 32x64 texture, so sprites shimmer and snap at
  distance rather than filtering smoothly.
- Sprites bypass the world shading path: no fog, no per-face lighting, no shadows. A player
  at the edge of the render distance is as bright as one next to you.
- Transparency is a hard alpha test, so no soft edges or translucency.
- Sprite dimensions and the 4x scale are hard-coded in `renderSprite`.

**Neutral**

- Sprites are drawn after the world pass, relying on the z-buffer rather than draw order.
- The texture layout is a second consumer of the atlas approach in
  [0007](0007-texture-atlas-and-bitmap-font.md).

## References

- Code: `src/main/java/com/craftclassic/common/world/World.java` (`render`, `renderSprite`)
- Code: `src/main/java/com/craftclassic/client/assets/Textures.java`
- Commit: `1a87cdc Add Sprites and multiplayer`
