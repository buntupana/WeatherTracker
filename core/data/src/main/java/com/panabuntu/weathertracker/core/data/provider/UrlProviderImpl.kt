package com.panabuntu.weathertracker.core.data.provider

import com.panabuntu.weathertracker.core.data.BuildConfig
import com.panabuntu.weathertracker.core.domain.provider.UrlProvider

class UrlProviderImpl: UrlProvider {
    override val apiKey: String = BuildConfig.ONE_CALL_3_API_KEY
    override val baseUrlApi: String = "https://api.openweathermap.org/data/3.0/onecall"
}