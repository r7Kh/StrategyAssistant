# Intelligence Channel Upgrade

This branch improves the Intelligence Channel without changing `main`.

## Chat experience
- Animated royal-futuristic background with restrained violet and gold lighting.
- Animated thinking indicator while the selected AI provider is responding.
- Basic Markdown rendering for headings, emphasis, bullets, and numbered lists.
- Content-based text direction and start alignment for mixed Arabic/Latin responses.

## Research and cost policy
- Paid Google Search grounding has been removed from the app.
- There is no Web Research toggle, consent dialog, search tool request, grounding source parser, or search suggestion WebView.
- The AI must not claim to have performed a live search.
- Current Clash of Clans facts are supplied from a curated offline knowledge snapshot. Missing or uncertain facts must be described as unverified rather than invented.

## Verification
- Run Android CI build and unit tests on this branch.
- Device-level visual testing has not yet been performed.
