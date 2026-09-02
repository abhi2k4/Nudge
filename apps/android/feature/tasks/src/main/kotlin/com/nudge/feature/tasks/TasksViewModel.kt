package com.nudge.feature.tasks

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * ViewModel for the Tasks screen.
 *
 * Owns the UI state and mediates between the UI and domain use cases.
 * Business logic must live here, not in the @Composable.
 */
@HiltViewModel
class TasksViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(TasksUiState())
    val uiState: StateFlow<TasksUiState> = _uiState.asStateFlow()

    // TODO: Inject and call relevant use cases when building real UI
}

data class TasksUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)
