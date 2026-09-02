package com.nudge.feature.context

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * ViewModel for the Contexts screen.
 *
 * Owns the UI state and mediates between the UI and domain use cases.
 * Business logic must live here, not in the @Composable.
 */
@HiltViewModel
class ContextsViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(ContextsUiState())
    val uiState: StateFlow<ContextsUiState> = _uiState.asStateFlow()

    // TODO: Inject and call relevant use cases when building real UI
}

data class ContextsUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)
