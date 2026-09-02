# NUDGE

NUDGE is a mobile-first, on-device-first AI productivity companion. 

It captures screenshots, voice notes, photos, links, notes, and ideas, understands the intent behind them, connects related information over time, turns intent into actions, and surfaces forgotten intentions later.

## Core Loop
Capture → Understand → Remember → Connect → Act → Follow Up

## Architecture Overview

NUDGE follows a local-first architecture. The Android phone is the primary capture surface and processing unit. It leverages on-device AI to extract intent and resolve context without requiring cloud connectivity. 

The architecture is hardware-agnostic, supporting CPU (llama.cpp fallback) and ready for NPU/GPU accelerator backends.

The web app acts as the execution surface on laptops/desktops, synced optionally via Supabase.

For more details, see [Architecture](docs/architecture.md).

## Repository Structure

```
/apps
  /android          # Android application (Kotlin, Jetpack Compose, Hilt, Room)
  /web              # Next.js web application (TypeScript, Tailwind, shadcn/ui)
/packages
  /shared-types     # Shared DTO definitions across Android, Web, and Backend
/infrastructure
  /supabase         # Supabase migrations and local dev config
/docs               # Detailed documentation
```

## How to run Android

1. Open `/apps/android` in Android Studio.
2. Sync Gradle.
3. Build and run on an Android device or emulator (API 29+).

## How to run Web

1. Navigate to `/apps/web`: `cd apps/web`
2. Install dependencies: `npm install`
3. Run the development server: `npm run dev`

## Environment Variables

- Android: None required for local-only mode.
- Web: Refer to `/apps/web/.env.example` (TBD).
- Supabase: Uses default local dev credentials for `supabase start`.

## Current Implementation Status

This repository is currently a hackathon foundation. It establishes:
- Android multi-module architecture with clean boundaries.
- Room database schemas and DAOs for all core entities.
- WorkManager skeletons for the asynchronous processing pipeline.
- AI runtime interfaces (`OnDeviceLanguageModel`, `IntentExtractor`, etc.) and a mock implementation.
- A basic Next.js web app scaffold.
- Supabase migrations ready for deployment.

## Planned AI Runtime Integration

The AI layer is currently using a `MockLanguageModel`. The next phase involves integrating `llama.cpp` for CPU inference and evaluating NPU/GPU delegates for specific hardware.

For more details, see [AI Runtime](docs/ai-runtime.md).
