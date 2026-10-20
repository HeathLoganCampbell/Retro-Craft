# 0007. Load PNG atlases and draw a bitmap font

- **Status:** Accepted
- **Date:** 2019-07-02 (retrospective)
- **Deciders:** Heath Logan Campbell
- **Tags:** rendering, assets

## Context

The renderer writes raw pixels, so it needs textures as pixel arrays, not as images it can
hand to a graphics API. Text is needed for the FPS counter, crosshair labels and debug
overlay, and must match the 318x200 pixel grid.

AWT's text rendering would antialias glyphs and introduce subpixel positioning, both of
which fight a 5x6 pixel font.

## Decision

Textures ship as PNGs on the classpath and are loaded once into `Bitmap` objects by a
static initialiser in `Textures`:

- `saved-texturess.png` - block atlas
- `player.png` - player sprite sheet
- `font.png` - font tilesheet
- `particle.png` - particles

Text is drawn by `FontRender`, which maps characters through a fixed string
(`A-Z`, `0-9` and a short list of punctuation), slices glyphs from `font.png` in a 26-column
grid, and copies pixels with a transparency test. Glyph size is a constant 5x6.

## Alternatives considered

- **`Graphics2D.drawString`** - rejected: antialiasing, font dependency, and it would draw
  outside the pixel grid.
- **Per-glyph image files** - many more resources and IO for no benefit.

## Consequences

**Positive**

- Text and textures are exactly as crisp as the rest of the frame; no blending surprises.
- One atlas load at startup; per-pixel copies thereafter.
- The font is data, so the look can be changed without touching rendering code.

**Negative**

- The character set is fixed and uppercase-only; lowercase input must be uppercased and
  any character outside the table renders as nothing or garbage.
- Glyph metrics are hard-coded (`letterWidth = 5`, `letterHeight = 6`); changing the font
  image requires changing the constants.
- Asset loading is a static initialiser that swallows `IOException` into a stack trace, so
  a missing resource fails late and quietly.
- The block atlas filename is misspelled (`saved-texturess.png`) and is now part of the
  contract.

**Neutral**

- Assets are bundled in the jar, so distribution is a single file.

## References

- Code: `src/main/java/com/craftclassic/client/assets/Textures.java`
- Code: `src/main/java/com/craftclassic/client/assets/FontRender.java`
- Commit: `24fea8b add font rendener`
