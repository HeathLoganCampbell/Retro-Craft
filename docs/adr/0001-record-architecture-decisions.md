# 0001. Record architecture decisions

- **Status:** Accepted
- **Date:** 2026-10-20 (retrospective)
- **Deciders:** Heath Logan Campbell
- **Tags:** process

## Context

Retro Craft is a seven-year solo project. Decisions such as "no OpenGL", "fixed 64x64x64
world" and "client-authoritative movement" were made quickly, in commit messages such as
`use block classes for more control of block controlls` (0fb772d) or `set world to flat land`
(3d9c272). The reasoning behind them is not recoverable from the code alone.

Without a record, the next person to touch the project - including its author after a
break - has to reverse-engineer intent from implementation, and is likely to undo a
deliberate trade-off by accident.

## Decision

We record architecturally significant decisions as Architecture Decision Records in
`docs/adr/`.

- One decision per file, numbered sequentially, using the template in `docs/adr/template.md`.
- Records are written in the past-tense context / present-tense decision style.
- Accepted records are immutable. A reversal is a new record that supersedes the old one.
- A decision is "architecturally significant" if changing it later would be expensive,
  or if it constrains how other parts of the code can be written.

## Consequences

**Positive**

- Intent survives refactors and long gaps between sessions.
- Trade-offs are explicit, so "why is this so slow?" has an answer that is not `git blame`.

**Negative**

- Records can drift from reality if not updated when code changes.
- Retrospective dates are approximate.

**Neutral**

- ADRs describe decisions, not code. Line numbers will rot; file paths will not.
