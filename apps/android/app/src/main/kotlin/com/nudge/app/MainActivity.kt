package com.nudge.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.nudge.app.navigation.AppNavGraph
import com.nudge.app.ui.theme.NudgeTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * The single Activity host for the NUDGE Android app.
 *
 * All navigation is handled by [AppNavGraph] via Navigation Compose.
 * No Fragment transactions; everything is Compose.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NudgeTheme {
                AppNavGraph()
            }
        }
    }
}
