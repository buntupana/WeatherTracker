package com.panabuntu.weathertracker.core.presentation.util

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowInsetsControllerCompat


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


@Composable
fun SetSystemBarsColors(
    statusBarColor: Color? = null,
    navigationBarColor: Color? = null,
    translucentNavigationBar: Boolean? = null
) {
    val view = LocalView.current
    if (!view.isInEditMode) { // Prevent running in Preview
        SideEffect {
            val window = (view.context as? Activity)?.window ?: return@SideEffect

            val insetsController = WindowInsetsControllerCompat(window, view)

            // Control Status Bar Icons
            if (statusBarColor != null) { // API 23+
                insetsController.isAppearanceLightStatusBars = statusBarColor.isLight()
            }

            // Control Navigation Bar Icons
            if (navigationBarColor != null) { // API 26+
                insetsController.isAppearanceLightNavigationBars = navigationBarColor.isLight()
            }

            if (
                Build.VERSION.SDK_INT < Build.VERSION_CODES.Q &&
                navigationBarColor != null
            ) {
                window.navigationBarColor = navigationBarColor.toArgb()
            } else if (
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q &&
                translucentNavigationBar != null
            ) {
                window.isNavigationBarContrastEnforced = translucentNavigationBar
            }
        }
    }
}

@Composable
fun Modifier.paddingValues(
    start: () -> Dp = { 0.dp },
    top: () -> Dp = { 0.dp },
    end: () -> Dp = { 0.dp },
    bottom: () -> Dp = { 0.dp }
): Modifier {

    val padding = remember {

        object : PaddingValues {
            override fun calculateTopPadding(): Dp = top()

            override fun calculateLeftPadding(layoutDirection: LayoutDirection): Dp {
                return if (layoutDirection == LayoutDirection.Ltr) start() else end()
            }

            override fun calculateRightPadding(layoutDirection: LayoutDirection): Dp {
                return if (layoutDirection == LayoutDirection.Ltr) end() else start()
            }

            override fun calculateBottomPadding(): Dp = bottom()
        }
    }
    return padding(padding)
}

