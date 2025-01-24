package com.kmpai.photoreader.feature.picker.data.rest

import aiphotoreader.composeapp.generated.resources.Res
import aiphotoreader.composeapp.generated.resources.allStringResources
import kotlinx.coroutines.runBlocking
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.getString

expect class ApiKeyLoader() {
    suspend fun getApiKey(): String?
}
