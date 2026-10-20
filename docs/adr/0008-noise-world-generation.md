# 0008. Generate worlds with layered noise and WorldTypes

- **Status:** Accepted
- **Date:** 2019-06-29 (retrospective)
- **Deciders:** Heath Logan Campbell
- **Tags:** world, generation

## Context

A flat world is enough to test movement and rendering, but the game needed terrain that
felt like the one the author actually enjoyed playing: Minecraft Classic's generator.
That generator was worth reproducing rather than approximating, because the feel comes
from its specific algorithm - two overlapping noise fields, a second pass that rounds
heights into terraces, and layering grass/dirt/stone over a fixed sea level - not from
"Perlin noise" in general.

The project also wanted to be able to switch between flat and natural worlds without
branching all over the world code.

## Decision

World generation is a strategy selected at construction:

- `WorldType` is the strategy interface; `FlatWorldType` and `NaturalWorldType` implement it.
- `NaturalWorldType` composes noise primitives: `PerlinNoise`, `OctaveNoise` (fractal
  octaves) and `CombinedNoise` (noise feeding into noise), all behind a `Noise` interface.
- `NaturalWorldType` is a deliberate port of Minecraft Classic's `LevelGen`, including its
  magic constants: the `1.3` horizontal scale, the `/6 - 4` and `/5 + 10 - 4` height fields,
  the `/8 > 0` switch between them, the `<< 1` terracing pass, and the `waterLevel = 32`
  offset. The fidelity is the point; the constants are not arbitrary noise tuning.
- Generation fills the fixed block array once, at world creation, from a seed.

## Alternatives considered

- **Loading real Minecraft world files** - rejected: the point was to generate terrain
  ourselves, and the renderer does not need real chunk formats.
- **Simplex/value noise** - Perlin was chosen because it was the algorithm being studied at
  the time.

## Consequences

**Positive**

- No asset files and no save format; a world is a seed plus an algorithm.
- The `Noise` interface makes the composition explicit and testable in isolation.
- Swapping generators is a one-line change at world construction.

**Negative**

- Terrain is limited to what layered 2D noise can express - no caves, ores, trees or biomes.
- Generation runs synchronously on the calling thread before the first frame, so large
  worlds would stall startup (not a problem at 64x64x64).
- Each `WorldType` reaches into `World` directly rather than returning blocks, so a new
  generator must know about the world's internals.

**Neutral**

- The seed is stored on the world but there is no UI or command to set it.

## References

- Code: `src/main/java/com/craftclassic/common/world/types/`
- Code: `src/main/java/com/craftclassic/common/world/noise/`
- Commits: `50fcdaf`, `167aebd`, `e177d32`
