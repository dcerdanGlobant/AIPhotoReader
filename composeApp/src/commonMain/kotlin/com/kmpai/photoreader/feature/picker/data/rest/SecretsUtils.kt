package com.kmpai.photoreader.feature.picker.data.rest

import aiphotoreader.composeapp.generated.resources.Res
import aiphotoreader.composeapp.generated.resources.allStringResources
import kotlinx.coroutines.runBlocking
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.getString

object SecretsUtils {
    private val apiKey: String by lazy {
        runBlocking {
            loadGlobantApiKey() ?: throw Exception("globant_api_key resource not found")
        }
    }

    @OptIn(ExperimentalResourceApi::class)
    private suspend fun loadGlobantApiKey(): String? {
        return try {
            Res.allStringResources["globant_api_key"]?.let { getString(it) }
        } catch (e: Exception) {
            null // Resource does not exist
        }
    }

    fun getGlobantApiKey(): String = apiKey
}
