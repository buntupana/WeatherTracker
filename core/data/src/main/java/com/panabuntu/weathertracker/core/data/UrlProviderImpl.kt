package com.panabuntu.weathertracker.core.data

import com.panabuntu.weathertracker.core.domain.UrlProvider

class UrlProviderImpl: UrlProvider {
    override val apiKey: String = BuildConfig.ONE_CALL_3_API_KEY
    override val baseUrlApi: String = "https://api.openweathermap.org/data/3.0/onecall"
}