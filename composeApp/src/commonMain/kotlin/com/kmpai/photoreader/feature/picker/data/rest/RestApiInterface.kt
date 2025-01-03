package com.kmpai.photoreader.feature.picker.data.rest

import com.kmpai.photoreader.feature.picker.data.rest.model.APIMessage
import com.kmpai.photoreader.feature.picker.data.rest.model.APIResponse

interface RestApiInterface {

    suspend fun uploadImage(image: ByteArray, filename: String, contentType: String): String
    suspend fun sendMessagesToAI(messages: List<APIMessage>): APIResponse
}
