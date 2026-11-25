# Clash of Clans Knowledge Base

## Purpose

Provide the Intelligence Channel with a structured, source-backed offline reference for Clash of Clans. This improves consistency and reduces unsupported guesses without paid web search.

## Current snapshot

- Data version: 2026-11-25
- Last reviewed: 2026-11-25
- Primary source: official Supercell Clash of Clans release notes.
- Kotlin source: `app/src/main/java/com/strategy/assistant/data/ai/ClashOfClansKnowledge.kt`
- Prompt integration: `DirectAiClient.kt`
- Retrieval: `ClashOfClansKnowledge.contextFor(query)` selects entries using English and Arabic keywords; if no keyword matches, it falls back to the curated snapshot.

### Covered topics

- Town Hall 19 launch, defensive Town Hall troop choices, unlock conditions, and per-layout selection.
- Squad Barracks rules, launch squad roster, housing space, Town Hall unlock progression, and squad abilities.
- Selected Town Hall 19 troop, Super Troop, Hero, Pet, Siege Machine, and Spell levels published in the November 2026 release notes.
- Heroic Spell mechanics, effects, duration, radius, stacking rules, and unlock Town Hall.
- Town Hall 19 building/defense additions and selected published building/defense levels.
- Town Hall 18 Guardians, Meteor Golem, Totem Spell, Revenge Tower, Super Wizard Tower, and Crafted Defense Phase 2 history.
- April 2026 Sky Wagon and Electro Fangs details, selected level additions, and Legend League structure from that update.
- Regular vs Ranked Battles, Magic Shields, Revenge, and selected 2025 matchmaking changes.
- Selected August 2026 update history and November 2026 balance notes.

This remains a curated snapshot, not a complete catalog of every troop, building, level, cost, equipment item, or attack strategy. Each record carries a source URL and review date. Historical mechanics are identified as patch-specific and must not be represented as guaranteed current behavior.

## Data governance

1. Prefer official Supercell release notes and official game API documentation.
2. Store every factual entry with an ID, category, title, verification date, and source URL.
3. Keep exact numeric values only when the cited source explicitly states them.
4. Separate current facts from historical patch notes.
5. Never infer an exact unlock level, upgrade cost/time, damage, health, or max level from partial notes.
6. If the requested fact is absent, say it is not in the installed knowledge snapshot.
7. Update the Kotlin source and this document together.
8. Review the snapshot date before each release; do not label it live or automatically updated.
9. Expand keyword aliases and add retrieval tests whenever a new subject category is introduced.
10. Do not add paid search, remote retrieval, or mandatory backend dependencies as part of this knowledge layer.

## Important API boundary

Supercell's official Clash of Clans developer portal describes access to player and clan profiles, clan search, leaderboards, and leagues. This API should be treated as a separate future integration for player/clan data, not assumed to be a complete encyclopedia of every troop, building, level, upgrade cost, or battle strategy. API credentials and any IP allowlisting must never be embedded in the Android client.

Official portal: https://developer.clashofclans.com/

## Next expansion order

- Continue filling exact troop, spell, Hero, Pet, equipment, building, trap, and defense records from official source material.
- Add dedicated retrieval aliases for Arabic names and common transliterations.
- Add tests for stale-version handling, missing facts, prompt-size limits, and query matching.
- Consider official player/clan API integration only after a secure backend/IP strategy is approved. Do not add paid infrastructure without explicit approval.
