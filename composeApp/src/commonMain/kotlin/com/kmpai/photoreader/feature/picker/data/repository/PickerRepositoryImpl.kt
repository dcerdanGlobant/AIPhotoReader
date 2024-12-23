package com.kmpai.photoreader.feature.picker.data.repository

import com.kmpai.photoreader.feature.picker.data.datasource.AIDatasource
import com.kmpai.photoreader.feature.picker.domain.model.Conversation
import com.kmpai.photoreader.feature.picker.domain.repository.PickerRepository

class PickerRepositoryImpl(private val datasource: AIDatasource) : PickerRepository {
    override suspend fun getPictureDescription(extension: String, imageByteArray: ByteArray): Result<Conversation> {
        return datasource.sendImageAndStartConversation(extension, imageByteArray)
    }

    override suspend fun sendConversation(conversation: Conversation): Result<Conversation> {
        return datasource.sendConversation(conversation)
    }
}
