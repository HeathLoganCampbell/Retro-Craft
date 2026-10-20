# 0003. Render at 318x200 and upscale 4x

- **Status:** Accepted
- **Date:** 2019-06 (retrospective)
- **Deciders:** Heath Logan Campbell
- **Tags:** rendering, performance

## Context

A CPU raycast pays for every pixel. At 1280x800 the renderer would cast just over one
million rays per frame; at 318x200 it casts 63,600. The window, however, needs to be a
usable size on a normal monitor.

## Decision

The world renders into a fixed 318x200 framebuffer. `Main.SCALE = 4`, and the whole
frame is upscaled with nearest-neighbour sampling when it is drawn to the window
(`drawImage(frameBuffer, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, ...)`), giving a 1272x800
window.

The internal resolution is a compile-time constant. Both the `Screen` framebuffer and
`World`'s `zBuffer` are allocated from it.

The resolution was chosen for one reason - cost - and not as a stylistic target. The
pixelated look is a consequence of the budget, not a goal.

## Alternatives considered

- **Native-resolution rendering** - ~16x the ray count, not viable on the CPUs available
  in 2019.
- **Dynamic resolution scaling** - considered in spirit, not implemented; the constant is
  global and changing it at runtime would invalidate the z-buffer allocation.

## Consequences

**Positive**

- Roughly an order of magnitude cheaper per frame, which is what makes the CPU renderer
  usable at all.
- The chunky pixel look is incidental - the resolution was chosen purely to keep the
  per-pixel raycast affordable, not for style - but it happens to suit the 80s theme.

**Negative**

- UI and text are limited to a 5x6 bitmap font (see
  [0007](0007-texture-atlas-and-bitmap-font.md)).
- Resolution is global mutable-by-edit state: changing it means recompiling and
  re-tuning anything that assumes 318x200.

**Neutral**

- The window can be resized (or scaled) without changing the render cost, only the
  upscale factor.

## References

- Code: `src/main/java/com/craftclassic/client/Main.java` (`WIDTH`, `HEIGHT`, `SCALE`)
- Code: `src/main/java/com/craftclassic/client/graphics/Screen.java`
