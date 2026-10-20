# 0012. Keep the Applet shell behind an adapter

- **Status:** Accepted (legacy)
- **Date:** 2019-06 (retrospective)
- **Deciders:** Heath Logan Campbell
- **Tags:** client, platform

## Context

The project began as an AWT `Applet` - the natural choice in 2019 for a small Java game
that could run in a browser, and the shape of the code it was merged from
(`2e94c47 Clone and merge sources`). Browsers then removed Java applet support, but the
game loop, input wiring and lifecycle (`init`, `start`, `paint`) were built around
`Applet`.

Rewriting the shell would touch the game loop and input handling for no gameplay benefit.

## Decision

Keep `Minecraft extends Applet` and host it inside a `JFrame` using `AppletAdapter`, a stub
that satisfies the applet lifecycle so `init()`/`start()` work outside a browser.

Mouse look is handled outside AWT: a `java.awt.Robot` recentres the cursor, and
`jnativehook` global listeners capture mouse movement so the pointer can be locked to the
window - including on macOS (`413ff68 Support macs mouses`, 2023-10-30).

## Alternatives considered

- **Rewrite the client as a plain `JPanel`/`Canvas`** - correct long-term, but it would
  rewrite the loop and input for no visible change.
- **Keep it as a true applet** - no longer runnable in browsers.

## Consequences

**Positive**

- The existing lifecycle and input code kept working when the browser went away.
- One file (`AppletAdapter`) isolates the legacy dependency.

**Negative**

- `java.applet.Applet` is deprecated for removal; the client is tied to a JDK that still
  ships it.
- Global mouse hooks are a native dependency (`jnativehook`) with per-OS binaries, and on
  macOS they need accessibility permissions; failure modes are environmental, not logical.
- `Robot` and global hooks fight with window focus and multi-monitor setups, which is the
  source of the recurring "mouse issue" commits (`d251a60`, `3eb7384`, `f4aaf44`, `b94eb6e`).

**Neutral**

- The rendering and game logic do not depend on `Applet`; only the entry point does, which
  keeps the eventual rewrite contained.

## References

- Code: `src/main/java/com/craftclassic/client/Main.java`
- Code: `src/main/java/com/craftclassic/client/utils/AppletAdapter.java`
- Commits: `2e94c47`, `d251a60`, `3eb7384`
