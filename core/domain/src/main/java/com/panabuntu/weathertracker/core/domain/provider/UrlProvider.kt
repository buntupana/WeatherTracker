package com.panabuntu.weathertracker.core.domain.provider

interface UrlProvider {
    val apiKey: String
    val baseUrlApi: String
    fun createIconUrl(icon: String, zoom: Int = 4): String
}