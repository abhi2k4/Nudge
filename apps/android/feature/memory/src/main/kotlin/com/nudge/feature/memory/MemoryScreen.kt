package com.nudge.feature.memory

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nudge.core.designsystem.component.NudgeTextField
import com.nudge.core.designsystem.component.NudgeTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemoryScreen(
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            NudgeTopAppBar(title = "Memory")
        },
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                NudgeTextField(
                    value = "",
                    onValueChange = {},
                    placeholder = "Search your memory...",
                    singleLine = true
                )
                // TODO: Timeline list
            }
        }
    }
}
