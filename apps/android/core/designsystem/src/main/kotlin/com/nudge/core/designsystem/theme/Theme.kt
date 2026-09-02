package com.nudge.core.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

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
    outline              = NudgeOutline,
)

@Composable
fun NudgeTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = NudgeColorScheme,
        typography = NudgeTypography,
        shapes = NudgeShapes,
        content = content,
    )
}
