// Top-level settings for the NUDGE Android multi-module project.
pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
    // libs.versions.toml is auto-discovered from gradle/libs.versions.toml — no explicit registration needed
}

rootProject.name = "nudge-android"

// ── App shell ─────────────────────────────────────────────────
include(":app")

// ── Core ──────────────────────────────────────────────────────
include(":core:common")
include(":core:model")
include(":core:database")
include(":core:datastore")
include(":core:filesystem")
include(":core:logging")

// ── Data ──────────────────────────────────────────────────────
include(":data:local")
include(":data:remote")
include(":data:repository")

// ── Domain ────────────────────────────────────────────────────
include(":domain")

// ── AI ────────────────────────────────────────────────────────
include(":ai:api")
include(":ai:model")
include(":ai:runtime")
include(":ai:intent")
include(":ai:embeddings")
include(":ai:speech")
include(":ai:orchestration")

// ── Sync ──────────────────────────────────────────────────────
include(":sync")

// ── Features ──────────────────────────────────────────────────
include(":feature:home")
include(":feature:capture")
include(":feature:memory")
include(":feature:context")
include(":feature:tasks")
include(":feature:nudges")
include(":feature:settings")
