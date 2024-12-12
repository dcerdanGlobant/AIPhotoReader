package com.kmpai.photoreader.core.framework.network

import co.touchlab.kermit.Logger
import io.ktor.client.plugins.api.createClientPlugin
import io.ktor.client.statement.request

val LoggerPlugin =
    createClientPlugin("CustomLoggerPlugin") {
        onRequest { request, content ->
            Logger.d(tag = "LoggerPlugin", null) { "=============REQUEST==============" }
            Logger.d(tag = "LoggerPlugin", null) { "${request.method.value} => ${request.url}" }
            Logger.d(tag = "LoggerPlugin", null) { "BODY => ${request.body}" }
            Logger.d(tag = "LoggerPlugin", null) { "=============END-REQUEST==============" }
        }

        onResponse { response ->
            Logger.d(tag = "LoggerPlugin", null) { "=============RESPONSE==============" }
            Logger.d(tag = "LoggerPlugin", null) { "${response.request.method.value} / ${response.status} => ${response.request.url}" }
            Logger.d(tag = "LoggerPlugin", null) { "BODY => $response" }
            Logger.d(tag = "LoggerPlugin", null) { "=============END-RESPONSE==============" }
        }
    }
