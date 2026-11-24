# STRATEGY ASSISTANT — AI Architecture

## Current provider

Gemini Developer API, initially configured with `gemini-3.8-flash`. The model identifier is an environment setting, not an Android constant.

## Request flow

`Jetpack Compose UI → Android AI data layer → HTTPS backend → Provider adapter → Gemini API`

The Gemini API key exists only in the backend environment. It must never be stored in Android resources, BuildConfig, DataStore, source control, or the APK.

## Backend contract

### Health
`GET /health`

Returns provider, configured model, and a boolean configuration status. Never returns credentials.

### Chat
`POST /v1/chat`

Request:
```json
{
  "message": "How should I improve my opening strategy?",
  "history": [
    { "role": "user", "text": "I play ..." },
    { "role": "model", "text": "..." }
  ]
}
```

Response:
```json
{
  "reply": " ... ",
  "provider": "gemini",
  "model": "gemini-3.8-flash"
}
```

## Provider independence

The mobile app talks only to the Strategy Assistant backend contract. Provider selection, model selection, credentials, retries, and future paid providers stay server-side. A future OpenAI or other adapter should implement the same internal interface and response shape.

## Free-tier and privacy note

Gemini API free-tier availability, model access, and quotas depend on the Google AI Studio project and may change. Google's published pricing page says free-tier content may be used to improve its products. Do not send sensitive personal data or confidential game/account credentials. Verify the active project's quotas in AI Studio before testing.

## Production gate

Before public multi-user deployment: authentication, user-bound rate limits and quotas, request logging with prompt redaction, abuse monitoring, TLS-only deployment, managed secrets, privacy disclosure/consent, and automated backend tests.
