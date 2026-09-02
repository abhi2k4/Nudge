# Architecture Overview

NUDGE is built on a local-first, privacy-preserving architecture.

## Core Flow

The journey of a capture through the system:

```mermaid
graph TD
    A[Capture UI] --> B[Local Store (Room)]
    B --> C[Processing Queue (WorkManager)]

    subgraph AI_Pipeline[AI Pipeline]
        C --> D[Pre-processing (ASR/OCR)]
        D --> E[Intent Extraction (On-device LLM)]
        E --> F[Context Resolution (Embeddings)]
    end

    F --> G[Action Engine]
    F --> H[Nudge Engine]

    G --> I[Task / Calendar UI]
    H --> J[Proactive Notifications]

    B -.-> K[Optional Cloud Sync (Supabase)]
```

1. **Capture**: A user takes a note, voice memo, screenshot, etc.
2. **Local Store**: The capture is immediately saved to the local Room database. Persistence is guaranteed, even offline.
3. **Processing Queue**: A WorkManager pipeline picks up pending captures.
4. **On-device AI**:
    - **Intent Engine**: Extracts actionable intents (e.g., "Remind me to buy groceries").
    - **Context Engine**: Links the capture to related past captures and intents using semantic embeddings.
5. **Action Engine**: Converts intents into schedulable actions.
6. **Nudge / Follow-up**: Surfaces relevant past context proactively.

## Cloud Escalation (Optional)

Cloud processing sits beside the local AI layer and is strictly opt-in. The `AIOrchestrator` determines whether a workload can be handled locally or needs cloud escalation (e.g., for very complex reasoning).
