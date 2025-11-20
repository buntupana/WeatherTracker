package com.panabuntu.weathertracker.core.presentation.util

import android.os.Bundle
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.navigation.NavType
import kotlinx.serialization.json.Json
import kotlin.reflect.KType
import kotlin.reflect.typeOf


fun Color.isLight(): Boolean {
    return luminance() > 0.5f
}

/** Return a black/white color that will be readable on top */
fun Color.getOnBackgroundColor(): Color {
    return if (isLight()) Color.Black else Color.White
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