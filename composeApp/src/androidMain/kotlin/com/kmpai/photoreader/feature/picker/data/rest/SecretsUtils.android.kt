package com.kmpai.photoreader.feature.picker.data.rest

import aiphotoreader.composeapp.generated.resources.Res
import aiphotoreader.composeapp.generated.resources.allStringResources
import android.util.Log
import co.touchlab.kermit.Logger
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.getString

actual class ApiKeyLoader {
    @OptIn(ExperimentalResourceApi::class)
    actual suspend fun getApiKey(): String? {
        return try {
            Res.allStringResources["globant_api_key"]?.let { getString(it) }
        } catch (e: Exception) {
            null // Resource does not exist
        }
    }
}