package com.panabuntu.weathertracker.core.domain.provider

interface UrlProvider {
    val apiKey: String
    val baseUrlApi: String
}