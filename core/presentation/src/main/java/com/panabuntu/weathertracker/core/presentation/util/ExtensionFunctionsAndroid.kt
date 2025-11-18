package com.panabuntu.weathertracker.core.presentation.util

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.luminance
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavType
import androidx.navigation.toRoute
import com.panabuntu.weathertracker.core.domain.util.decodeAllStrings
import com.panabuntu.weathertracker.core.presentation.navigation.Route
import kotlinx.serialization.json.Json
import kotlin.reflect.KType
import kotlin.reflect.typeOf

val Context.inDarkMode: Boolean
    get() = (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
val Context.inLightMode: Boolean
    get() = !inDarkMode

fun Color.isLight(): Boolean {
    return luminance() > 0.5f
}

/** Return a black/white color that will be readable on top */
fun Color.getOnBackgroundColor(): Color {
    return if (isLight()) Color.Black else Color.White
}

fun Modifier.brush(brush: Brush) = this
    .graphicsLayer(alpha = 0.99f)
    .drawWithCache {
        onDrawWithContent {
            drawContent()
            drawRect(brush, blendMode = BlendMode.SrcAtop)
        }
    }

inline fun <reified T : Route> SavedStateHandle.navArgs(
    typeMap: Map<KType, NavType<*>> = emptyMap()
): T {
    return toRoute<T>(typeMap).decodeAllStrings()
}

inline fun <reified T : Any> serializableType(
    isNullableAllowed: Boolean = false,
    json: Json = Json,
) = object : NavType<T>(isNullableAllowed = isNullableAllowed) {
    override fun get(bundle: Bundle, key: String) =
        bundle.getString(key)?.let<String, T>(Json::decodeFromString)

    override fun parseValue(value: String): T = json.decodeFromString(value)

    override fun serializeAsValue(value: T): String = json.encodeToString(value)

    override fun put(bundle: Bundle, key: String, value: T) {
        bundle.putString(key, json.encodeToString(value))
    }
}

inline fun <reified T : Any> navType(): Pair<KType, NavType<*>> {
    return typeOf<T>() to serializableType<T>()
}