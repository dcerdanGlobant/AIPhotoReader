package com.kmpai.photoreader.core.framework.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

val httpClient = HttpClient {
    install(HttpTimeout) {
        socketTimeoutMillis = 60_000
        requestTimeoutMillis = 60_000
    }
    // Logging plugin combined with kermit(KMP Logger library)
    install(Logging) {
        logger = Logger.DEFAULT
        level = LogLevel.ALL
        logger =
            object : Logger {
                override fun log(message: String) {
                    co.touchlab.kermit.Logger.i { "Kermit logger" }
                }
            }
    }

    install(LoggerPlugin)

    // We can configure the BASE_URL and also
    // the deafult headers by defaultRequest builder
    /*defaultRequest {
        header("Content-Type", "application/json")
        header("X-Api-Key", Constants.NEWS_API_KEY)
        url {
            host = Constants.BASE_URL
        }
    }*/
    // ContentNegotiation plugin for negotiationing media types between the client and server
    install(ContentNegotiation) {
        json(
            Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
                explicitNulls = false
            },
        )
    }
}