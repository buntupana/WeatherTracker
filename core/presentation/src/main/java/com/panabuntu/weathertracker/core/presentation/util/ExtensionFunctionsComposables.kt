package com.panabuntu.weathertracker.core.presentation.util

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.LineBreak


fun TextStyle.balanced(): TextStyle {
    val customTitleLineBreak = LineBreak(
        strategy = LineBreak.Strategy.Balanced,
        strictness = LineBreak.Strictness.Strict,
        wordBreak = LineBreak.WordBreak.Phrase
    )
    return copy(
        lineBreak = customTitleLineBreak
    )
}

