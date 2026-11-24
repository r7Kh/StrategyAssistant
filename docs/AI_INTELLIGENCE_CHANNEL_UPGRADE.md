# Intelligence Channel Upgrade

This development branch upgrades the AI chat experience without changing `main`.

## Current scope

- Adds an animated, low-contrast royal-futuristic background to the Intelligence Channel.
- Adds a live thinking indicator while a provider request is in progress.
- Renders basic Markdown emphasis, headings, bullets, and numbered lists in assistant messages.
- Uses content-based text direction and start alignment for mixed Arabic/Latin text.
- Displays Google Search grounding sources and Google's search-suggestion widget when returned by Gemini.
- Adds an explicit Web Research opt-in for Gemini. It is OFF by default and can be disabled at any time.
- Keeps BYOK: requests are sent directly from the device to the selected provider.

## Web research and billing guard

Google Search grounding is enabled only when the user explicitly turns on WEB and confirms the disclosure dialog. The dialog explains that Gemini 3 Google Search grounding is unavailable on the free tier, and that an eligible paid-tier project currently includes 5,000 free search requests per month, then $14 per 1,000 requests. A single prompt can trigger multiple search queries. Billing, if any, is handled by the user's own Google API project.

If Web Research is OFF, the request does not include the Google Search tool and the system prompt explicitly tells the model not to claim it searched or verified current information.

The current implementation enables native grounding for Gemini only. Other provider integrations do not receive live search until a separate compatible search workflow is implemented and approved.

## Important verification notes

- Google Search grounding must return metadata for sources and search suggestions to be displayed. The UI displays source links and the returned Google Search suggestions HTML when present.
- Search availability, quotas, model support, and prices can change. Recheck Google's official documentation before release.
- CI/build checks are required before this branch is considered ready. No device-level visual testing has been performed as part of this work.
