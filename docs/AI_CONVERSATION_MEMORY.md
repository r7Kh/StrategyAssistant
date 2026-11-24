# AI Conversation Memory

## Scope
AI Conversation Memory stores selected user-provided facts on the Android device. It is separate from chat transcript history: full conversations are not saved.

## Storage and privacy
- Uses AndroidX Preferences DataStore in the app's private files directory.
- Memory is excluded from Android cloud backup and device transfer.
- No Strategy Assistant backend or external memory service is used.
- Users can disable memory, add or edit entries, delete individual entries, or clear all entries from the Memory Manager.
- Maximum of 100 entries; each entry is limited to 240 characters.

## Automatic capture
When memory is enabled, the app checks only the user's own outgoing messages for a small set of explicit first-person statements (name, games played, and preferences). It does not analyze assistant responses or store whole messages. Captured entries are visible in Memory Manager and can be removed.
The initial phrase matcher is intentionally conservative and language-limited (English and Arabic); users can always add a memory manually.

## Request flow
User message -> explicit-fact capture -> read enabled saved memories -> append memory context to the provider system instruction -> direct BYOK request.
When memory is disabled, no saved memory context is sent to the AI provider and automatic capture is skipped.

## Limitations
- This feature does not persist full chat history.
- Automatic capture is pattern-based, not an AI extraction pipeline; some phrasing may not be recognized.
- Memory is device-local and is not synchronized between devices.
- Users should avoid saving sensitive personal information.
