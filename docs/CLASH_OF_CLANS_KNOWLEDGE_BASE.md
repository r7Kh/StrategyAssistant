# Clash of Clans Knowledge Base

## Purpose

Provide the Intelligence Channel with a small, structured, source-backed offline reference for Clash of Clans. This improves consistency and reduces unsupported guesses without paid web search.

## Current snapshot

- Data version: 2026-11-16
- Last reviewed: 2026-11-25
- Primary source: official Supercell Clash of Clans release notes.
- Kotlin source: `app/src/main/java/com/strategy/assistant/data/ai/ClashOfClansKnowledge.kt`
- Prompt integration: `DirectAiClient.kt`

The snapshot currently covers the November 2026 Clash of Titans update, selected Town Hall 19 facts, selected launch balance notes, and selected August 2026 update history. It is intentionally not a complete catalog of troops, buildings, levels, costs, equipment, or strategies.

## Data governance

1. Prefer official Supercell release notes and official game API documentation.
2. Store every factual entry with an ID, category, title, verification date, and source URL.
3. Keep exact numeric values only when the cited source explicitly states them.
4. Separate current facts from historical patch notes.
5. Never infer an exact unlock level, upgrade cost/time, damage, health, or max level from partial notes.
6. If the requested fact is absent, the assistant must say it is not in the installed knowledge snapshot.
7. Update the Kotlin source and this document together.
8. Review the snapshot date before each release; do not label it live or automatically updated.

## Important API boundary

Supercell's official Clash of Clans developer portal describes access to player and clan profiles, clan search, leaderboards, and leagues. This API should be treated as a separate future integration for player/clan data, not assumed to be a complete encyclopedia of every troop, building, level, upgrade cost, or battle strategy. API credentials and any IP allowlisting must never be embedded in the Android client.

Official portal: https://developer.clashofclans.com/

## Next expansion order

- Add verified catalog records for Town Hall progression, troops, spells, heroes, pets, equipment, buildings, traps, and defenses.
- Add per-record source links and version/verification metadata.
- Add retrieval by category and query keywords so only relevant facts enter the prompt.
- Add automated unit tests for stale-version handling, missing facts, and bounded prompt size.
- Consider official player/clan API integration only after a secure backend/IP strategy is approved. Do not add paid infrastructure without explicit approval.
