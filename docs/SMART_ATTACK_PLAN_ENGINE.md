# Smart Attack Plan Engine

The Clash of Clans attack-plan path converts a player request into a structured, reviewable plan. It is an AI-assisted planning layer, not a battle simulator or a guarantee of loot, stars, or success.

## Request routing

- Activated only when the current request is recognized as an attack/farming-planning request and the conversation is identified as Clash of Clans.
- Uses the existing local player profile, current message, recent chat context, and offline game knowledge when supplied.
- Screenshot observations are included only when an image is actually attached and must be grounded in visible evidence.
- Ordinary Clash of Clans questions continue using the normal assistant response path.

## Structured plan

The model is instructed to return JSON containing:

- Strategy summary and farming objective.
- Target-selection criteria and visible base observations with qualitative confidence.
- Army units and quantities, entry side, deployment phases, and spell timing.
- Risks, skip conditions, fallback, assumptions, and missing information.

The formatter extracts valid JSON and renders a readable report. Invalid or unrelated JSON falls back to the provider's original response.

## Safety and accuracy constraints

- Unknown troop quantities remain blank; no army is invented when the player has not supplied one.
- Do not infer troop levels, capacity, hidden traps, exact loot, exact coordinates, or numerical success probabilities.
- Confidence is qualitative, not a calibrated probability.
- If inputs are incomplete, the plan must be marked provisional and list at most two useful missing details.
- The offline knowledge snapshot may be dated or incomplete; verify important details in-game.

## Validation

Unit tests cover structured formatting, fenced JSON/provider preambles, missing quantities, empty armies, malformed output, and unrelated JSON fallback.
