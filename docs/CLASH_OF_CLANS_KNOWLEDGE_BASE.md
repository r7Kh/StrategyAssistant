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
- Legendary Equipment announcement: Blizzard Axe, rarity design intent, Town Hall unlock gates, upgrade caps and Ore costs, future Epic cost changes, and dated Ore-economy statements.

This remains a curated snapshot, not a complete catalog of every troop, building, level, cost, equipment item, or attack strategy. Each record carries a source URL and review date. Historical mechanics are identified as patch-specific and must not be represented as guaranteed current behavior.

## Farming attack planning

The AI request pipeline now adds a focused planning framework when a Clash of Clans attack/farming request is detected. It considers Town Hall, available army and levels, resource objective, spells/Siege Machine, hero availability, and target layout. It asks for no more than two concise missing-detail questions, can provide a provisional plan when appropriate, and structures a complete plan around army, target selection, deployment sequence, risks, and an alternative. It explicitly avoids claiming image analysis when no image is supplied, inventing troop capacity or levels, or promising fixed loot. This is an LLM-guidance layer, not a deterministic battle simulator or automatic base-image analyzer.

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

- Continue filling exact troop, spell, Hero, Pet, equipment, building, trap, and defense records from official source material, prioritizing complete individual item mechanics over broad patch summaries.
- Add dedicated retrieval aliases for Arabic names and common transliterations.
- Add tests for stale-version handling, missing facts, prompt-size limits, and query matching.
- Extend farming planning with structured player/base inputs and a deterministic validator before attempting automated army calculations or screenshot analysis.
- Consider official player/clan API integration only after a secure backend/IP strategy is approved. Do not add paid infrastructure without explicit approval.

## Village screenshot input (first implementation)

The Intelligence Channel can attach one JPG, PNG, or WebP image (maximum 8 MB) to a single request. The image is read through Android's system photo picker and passed transiently to the selected provider:

- Gemini: `inline_data` image part.
- Anthropic: base64 image content block.
- OpenAI-compatible providers, including OpenRouter: data-URL `image_url` content part.

The selected model must support image/vision input; model support varies by provider and custom endpoint. This feature sends the image to the provider selected by the user over HTTPS. The app does not add image bytes to chat history, saved memory, or local backup. The UI keeps only a text marker in its in-memory chat list. A provider may reject unsupported image input or oversized requests.

Image interpretation is advisory, not a guaranteed game-state detector. The request now includes a structured visual checklist: classify the screenshot context, separate clearly visible observations from uncertain/unidentifiable items, inspect resource buildings and defenses, use relative screen directions, and explain an entry-side recommendation only when the visible layout supports it. The model must avoid inventing resource quantities, building/troop levels, hidden traps, exact coordinates, or guaranteed pathing, and state when a building cannot be identified. Cropped or unclear images should trigger a concise request for a better view. This is still multimodal model reasoning, not a deterministic computer-vision pipeline, automatic loot counter, or battle simulator.


## Structured screenshot reports

When an image is attached, the AI is asked to return a structured JSON report containing screenshot context, visible observations, relative screen locations, qualitative confidence (`high`, `medium`, or `low`), visual evidence, resource targets, a cautiously recommended entry side, attack sequence, risks, limitations, and one missing player detail when needed. The app formats valid reports into readable sections. If the provider returns malformed or unrecognized JSON, the original response is shown instead. Confidence is a qualitative model estimate, not a calibrated probability or a guarantee of correct building identification; the model is instructed not to invent hidden objects, exact coordinates, levels, or loot values.
