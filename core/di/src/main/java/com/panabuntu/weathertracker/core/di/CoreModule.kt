package com.panabuntu.weathertracker.core.di

import com.panabuntu.weathertracker.core.data.UrlProviderImpl
import com.panabuntu.weathertracker.core.domain.AppLogger
import com.panabuntu.weathertracker.core.domain.UrlProvider
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module


val coreModule = module {

    singleOf(::UrlProviderImpl) bind UrlProvider::class

    single<HttpClient> {

        val urlProvider: UrlProvider = get()
        val appLogger: AppLogger = get()

        HttpClient(OkHttp) {
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        appLogger.d(message)
                    }
                }
                level = LogLevel.INFO
            }
            install(DefaultRequest) {
                url {
                    url(urlProvider.baseUrlApi)
                    parameters.append("appid", urlProvider.apiKey)
                }
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
            install(ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                        encodeDefaults = true
                    }
                )
            }
        }
    }
}