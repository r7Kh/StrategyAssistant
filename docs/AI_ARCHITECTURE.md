# STRATEGY ASSISTANT — BYOK AI Architecture

## Product decision
STRATEGY ASSISTANT uses Bring Your Own Key (BYOK). Each user supplies and pays for their own provider API usage. AI requests go directly from the Android device to the selected provider; the project does not proxy prompts or hold provider keys.

## Initial provider adapters
- Google Gemini REST API
- OpenAI Chat Completions API
- Anthropic Messages API
- OpenRouter (OpenAI-compatible)
- Custom OpenAI-compatible HTTPS endpoint

## Key storage
API keys are encrypted with AES-GCM using a non-exportable key held by Android Keystore. The encrypted value is stored in app-private SharedPreferences. Keys must never be included in logs, backups, analytics, source control, or exported app data. Android Keystore reduces extraction risk but cannot protect secrets from a fully compromised/rooted device or malicious instrumentation.

## Request flow
Jetpack Compose UI → DirectAiClient → selected provider HTTPS API.
No Strategy Assistant backend is required for chat. A future optional backend may support features that genuinely need server-side processing, but it must not become a mandatory proxy for BYOK chat.

## User responsibilities
Users create their own API keys and are responsible for provider billing, quotas, terms, and data handling. The app should clearly show which provider receives each prompt and warn users not to send sensitive information.

## Implementation note
Provider API compatibility and model availability can change. Keep model IDs editable and test each provider adapter before release.
