package com.panabuntu.weathertracker.core.presentation.snackbar

data class SnackbarAction(
    val name: String,
    val action: suspend () -> Unit
)
