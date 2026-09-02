package com.nudge.feature.nudges

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nudge.core.designsystem.component.NudgeTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NudgesScreen(
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            NudgeTopAppBar(title = "Nudges")
        },
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                Text(
                    text = "Actionable Intents",
                    style = MaterialTheme.typography.titleLarge
                )
                // TODO: List of intent cards
            }
        }
    }
}
