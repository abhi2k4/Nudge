package com.nudge.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// ─────────────────────────────────────────────────────────────────────────────
// NUDGE Design System
//
// Visual language:
//   - Warm white backgrounds
//   - Black/charcoal for primary text and surfaces
//   - Restrained yellow accent
//   - Large, readable typography
//   - Minimal cards, no dark neon AI aesthetic
// ─────────────────────────────────────────────────────────────────────────────

private val NudgeYellow   = Color(0xFFF5C842)   // warm, restrained accent
private val NudgeCharcoal = Color(0xFF1A1A1A)
private val NudgeWarmWhite = Color(0xFFFAF9F7)
private val NudgeSurface  = Color(0xFFF2F0ED)
private val NudgeOnSurface = Color(0xFF2C2C2C)
private val NudgeSubtle   = Color(0xFF8A8A8A)

private val NudgeColorScheme = lightColorScheme(
    primary              = NudgeCharcoal,
    onPrimary            = NudgeWarmWhite,
    primaryContainer     = NudgeYellow,
    onPrimaryContainer   = NudgeCharcoal,
    secondary            = NudgeSubtle,
    onSecondary          = NudgeWarmWhite,
    background           = NudgeWarmWhite,
    onBackground         = NudgeCharcoal,
    surface              = NudgeSurface,
    onSurface            = NudgeOnSurface,
    onSurfaceVariant     = NudgeSubtle,
    outline              = Color(0xFFD4D0CB),
)

@Composable
fun NudgeTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = NudgeColorScheme,
        content = content,
    )
}
