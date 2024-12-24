package com.kmpai.photoreader.feature.picker.data.repository

import com.kmpai.photoreader.feature.picker.data.datasource.PickerDatasource
import com.kmpai.photoreader.feature.picker.domain.model.Conversation
import com.kmpai.photoreader.feature.picker.domain.repository.PickerRepository

class PickerRepositoryImpl(private val datasource: PickerDatasource) : PickerRepository {
    override suspend fun sendImageAndStartConversation(
        extension: String,
        imageByteArray: ByteArray
    ): Result<Conversation> {
        return datasource.sendImageAndStartConversation(extension, imageByteArray)
    }

    override suspend fun sendConversation(conversation: Conversation): Result<Conversation> {
        return datasource.sendConversation(conversation)
    }
}
