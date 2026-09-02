# Development Setup

## Prerequisites
- Android Studio Ladybug or newer
- Node.js 18+
- npm or yarn
- Supabase CLI (for local database development)

## Running the Android App
1. Open `/apps/android` in Android Studio.
2. Ensure you have SDK 36 (Android 16 "Baklava") installed.
3. Sync project with Gradle files.
4. Run the `app` configuration on an emulator or physical device.

## Running the Web App
```bash
cd apps/web
npm install
npm run dev
```
Access the web app at `http://localhost:3000`.

## Supabase Local Development
To run the database and services locally:
```bash
cd infrastructure/supabase
supabase start
```
This will apply all migrations and seed data locally.

## Environment Variables
(TBD - Will be added as external integrations are wired up)
