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
- A strategy evaluation covering resource-goal fit, confirmed army fit, entry safety, and training efficiency.
- Up to two meaningfully different alternatives, with the situation each suits and its trade-off.

The formatter extracts valid JSON and renders a readable report. Invalid or unrelated JSON falls back to the provider's original response. Alternative output is capped at two entries.

## Evaluation rules

- Evaluation labels are qualitative: strong, moderate, weak, or unknown. They are not numerical scores or calibrated win probabilities.
- Only evaluate army fit when the player's army is known; only evaluate entry safety when layout evidence supports it.
- Explain the main trade-off and why the recommended plan fits the stated resource goal.
- Do not rank alternatives using invented metrics or claim a measured win rate.
- If evidence is insufficient, mark the relevant evaluation unknown and list the missing detail.

## Safety and accuracy constraints

- Unknown troop quantities remain blank; no army is invented when the player has not supplied one.
- Do not infer troop levels, capacity, hidden traps, exact loot, exact coordinates, or numerical success probabilities.
- Confidence is qualitative, not a calibrated probability.
- If inputs are incomplete, the plan must be marked provisional and list at most two useful missing details.
- The offline knowledge snapshot may be dated or incomplete; verify important details in-game.

## Validation

Unit tests cover structured formatting, evaluation labels, alternative rendering, fenced JSON/provider preambles, missing quantities, empty armies, malformed output, and unrelated JSON fallback.

## Deterministic plan validation

Before rendering, `CocAttackPlanValidator` checks the structured response for duplicate army entries, non-integer quantities, unsupported qualitative labels, excessive alternatives, and unusually long deployment sequences. It reports warnings without inventing values or silently rewriting the model response. It cannot verify player capacity or troop availability from free-form text; those remain explicit player checks until the profile uses structured unit records.

## Knowledge retrieval

The offline snapshot uses keyword matching with ranked, bounded retrieval. Up to five relevant entries are sent for a specific query; broad or unmatched questions receive a small core subset rather than the entire catalog. English and Arabic aliases are tested. Every selected entry retains its official source URL and review date. This remains curated retrieval, not a live or complete game database.

## Screenshot analysis improvements

The screenshot prompt groups evidence around resources, defenses, walls/compartments, and entry lanes. It explicitly rejects inferences about objects hidden by overlays or outside the image boundary, and keeps relative positions and qualitative confidence rather than exact coordinates or numeric certainty.

## Post-attack review

When a Clash of Clans message describes a completed attack or reports its result, the request pipeline adds a review framework. It compares the result with a prior plan only when that plan exists in the conversation, separates reported facts from visible evidence and interpretation, and identifies a small number of actionable lessons. It must not invent loot, destruction, troop losses, or causal explanations. Lessons are not claimed to be permanently learned or saved; persistence requires an explicit user request.

## Validation coverage

Unit tests cover duplicate units, malformed quantities, unsupported labels, excessive plan complexity, Arabic knowledge aliases, bounded retrieval, source metadata, and post-attack review routing.
