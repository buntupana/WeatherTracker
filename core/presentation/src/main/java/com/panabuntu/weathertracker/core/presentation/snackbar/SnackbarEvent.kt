package com.panabuntu.weathertracker.core.presentation.snackbar

import androidx.compose.material3.SnackbarDuration
import com.panabuntu.weathertracker.core.presentation.util.UiText

data class SnackbarEvent(
    val message: UiText,
    val action: SnackbarAction? = null,
    val snackbarDuration: SnackbarDuration = SnackbarDuration.Short
)
