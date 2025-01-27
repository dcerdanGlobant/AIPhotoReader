package com.kmpai.photoreader.feature.picker.data.rest

expect class ApiKeyLoader() {
    suspend fun getApiKey(): String?
}
