# Clash of Clans Attack Intelligence

## Player profile

The AI Intelligence Channel exposes an **Attack Profile** editor for Clash of Clans. The profile is stored locally in a dedicated Preferences DataStore and is not mixed into the general gaming-account records.

The profile contains:
- Town Hall level (optional; currently accepts 2–19, matching the installed November 2026 knowledge snapshot).
- Farming objective: Gold, Elixir, Dark Elixir, or balanced resources.
- Available army and troop levels.
- Heroes and equipment.
- Spells and Siege Machine.
- Optional additional notes.

Blank fields remain unknown. The app does not infer troop levels, army capacity, spell capacity, hero availability, or equipment. Profile fields are labeled as user-provided and potentially outdated in the AI context. The latest user message takes precedence over a conflicting saved profile.

## AI request behavior

When the conversation is recognized as Clash of Clans, the locally saved profile context is appended to the request context. It is not attached to unrelated game requests. The existing attack planner uses the profile together with the latest message and recent conversation to guide a farming recommendation.

The planner should:
- Prioritize the selected resource goal rather than trophies or three-star results by default.
- Recommend only troops the player confirms are available.
- Avoid exceeding stated army or spell capacity.
- Distinguish collector-focused from storage-focused targets.
- Explain entry, deployment sequence, risks, and when to skip a base.
- Ask no more than two concise questions when essential details are missing.
- Never guarantee loot or attack outcomes.

## Screenshot analysis

The existing screenshot pipeline remains game-aware. For Clash of Clans images, it reports only visible observations, qualitative confidence, a supported entry direction, sequence, risks, and limitations. For other images, it uses the provider's ordinary image response and does not force the Clash of Clans report schema.

## Privacy and storage

- Player profile fields are stored locally with Android Preferences DataStore.
- Profile fields are sent to the configured AI provider only as context for recognized Clash of Clans requests.
- The profile does not include passwords, login credentials, or Supercell account access.
- The profile can be edited or cleared from the Attack Profile dialog.
- API keys remain managed by the existing encrypted provider store.
- This profile is separate from chat history and explicit AI memory.

## Validation

The profile model validates Town Hall range and field lengths, normalizes entered text, and has unit tests for prompt context, unknown fields, validation, and farming-goal labels. GitHub Actions remains the authoritative build and unit-test check for this branch.
