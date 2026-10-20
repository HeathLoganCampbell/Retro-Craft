# 0002. Render on the CPU with no graphics library

- **Status:** Accepted
- **Date:** 2019-06 (retrospective)
- **Deciders:** Heath Logan Campbell
- **Tags:** rendering, constraints

## Context

Retro Craft started as a learning project: build a voxel game in plain Java, without
Unity, without OpenGL, and without a game engine. The goal was to understand what a
renderer actually does, not to ship the fastest possible game. The only graphics
dependency available was the JDK itself (AWT/ImageIO).

Minecraft Classic-style worlds are mostly axis-aligned cubes on a grid, which makes a
CPU raycast against the grid unusually tractable: every block face is a plane, and the
grid can be traversed without building a scene graph.

## Decision

We render the world on the CPU, per pixel, directly into an `int[]` framebuffer.

`World.render()` iterates every screen pixel, builds a view ray from yaw/pitch and FOV,
and for each of the three axes computes the first block-face intersection within the
render distance. It samples the block's texture, applies a per-face shade, writes the
pixel, and records the distance in a `double[] zBuffer` so sprites can be depth-tested.

There is no vertex buffer, no triangle rasteriser, no depth-sorted polygon list, and no
GPU involvement.

## Alternatives considered

- **LWJGL / OpenGL** - rejected: the constraint was explicitly "no OpenGL", and the
  learning goal was the renderer itself.
- **Java2D polygon filling** - rejected: it would hide the rasterisation step, which was
  the interesting part.
- **A prebuilt engine (jMonkeyEngine, libGDX)** - rejected: defeats the purpose.

## Consequences

**Positive**

- No native dependencies; the game runs anywhere with a JDK and a screen.
- Every pixel is explainable and debuggable; lighting, fog and shading are just arithmetic.
- Forces a small internal resolution (see [0003](0003-low-internal-resolution.md)) purely to
  keep the per-pixel cost affordable; the chunky look is a side effect, not a design goal.

**Negative**

- Cost is roughly `pixels x axes x steps-to-first-hit`. The frame budget is spent per
  pixel, so resolution is the main performance lever, not polygon count.
- No shaders, no shadow maps, no GPU lighting. Effects must be faked per face.
- The renderer is coupled to the world representation: it reads `World` block data
  directly and is not reusable for other geometry.

**Neutral**

- Rendering already depends on `Main.WIDTH/HEIGHT` constants and on the player's camera,
  so it cannot be unit tested without a world and a camera.

## References

- Code: `src/main/java/com/craftclassic/common/world/World.java` (`render`, `renderSprite`)
- Code: `src/main/java/com/craftclassic/client/Minecraft.java`
