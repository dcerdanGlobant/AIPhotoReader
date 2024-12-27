package com.kmpai.photoreader.feature.picker.domain.repository

import com.kmpai.photoreader.feature.picker.domain.model.Conversation

interface PickerRepository {
    suspend fun sendImageAndStartConversation(extension: String, imageByteArray: ByteArray): Result<Conversation>
    suspend fun sendConversation(conversation: Conversation): Result<Conversation>
}
