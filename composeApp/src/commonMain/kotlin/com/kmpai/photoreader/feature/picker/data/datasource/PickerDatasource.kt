package com.kmpai.photoreader.feature.picker.data.datasource

import com.kmpai.photoreader.feature.picker.domain.model.Conversation

interface PickerDatasource {

    suspend fun sendImageAndStartConversation(extension: String, imageByteArray: ByteArray): Result<Conversation>
    suspend fun sendConversation(conversation: Conversation): Result<Conversation>
}
