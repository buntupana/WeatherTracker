package com.panabuntu.weathertracker.core.domain

interface UrlProvider {
    val apiKey: String
    val baseUrlApi: String
}