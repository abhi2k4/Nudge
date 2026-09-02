# Data Model

The domain models are defined in `core/model` and mapped to Room entities in `core/database/entity`.

## Core Entities

### Capture
The primary unit of information entering the system.
- `id`: String (UUID)
- `type`: Enum (VOICE, SCREENSHOT, PHOTO, TEXT, LINK, DOCUMENT)
- `processingStatus`: Tracks progress through the WorkManager pipeline.
- `syncStatus`: Tracks sync state with Supabase.

### Intent
The extracted meaning behind a capture.
- `id`: String (UUID)
- `captureId`: Foreign Key to Capture
- `type`: Enum (REMINDER, TASK, NOTE, RESEARCH, BOOKING, PURCHASE, MEETING, UNKNOWN)
- `confidence`: Float (0.0-1.0)
- `status`: Enum (NEW, ACKNOWLEDGED, ACTIONED, DISMISSED, COMPLETED)

### Action
A concrete, schedulable task derived from an intent.
- `id`: String (UUID)
- `intentId`: Foreign Key to Intent
- `type`: Enum (SET_REMINDER, CREATE_CALENDAR_EVENT, etc.)
- `status`: Enum (PENDING, SCHEDULED, COMPLETED, CANCELLED, FAILED)

### NudgeContext
A semantic cluster of related captures, intents, and actions.
- `id`: String (UUID)
- `embedding`: FloatArray (used for semantic search)

### Relationship
A directed edge between two entities in the NUDGE graph.
- `sourceId`, `targetId`: Entity IDs
- `type`: Enum (SEMANTIC_SIMILAR, REFERENCES, SAME_PROJECT, FOLLOWS_UP, DUPLICATE)

### Nudge
A proactive surface of a forgotten or time-sensitive intent.
- `id`: String (UUID)
- `intentId`: Foreign Key to Intent
- `status`: Enum (SCHEDULED, SHOWN, ACKNOWLEDGED, DISMISSED, EXPIRED)
