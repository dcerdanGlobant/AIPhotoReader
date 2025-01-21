package com.kmpai.photoreader.feature.picker.data.datasource

import com.kmpai.photoreader.feature.picker.domain.model.CommonResult
import com.kmpai.photoreader.feature.picker.domain.model.Conversation
import kotlinx.coroutines.flow.Flow

interface PickerDatasource {

    suspend fun sendImageAndStartConversation(extension: String, imageByteArray: ByteArray): Flow<CommonResult<Conversation>>
    suspend fun sendConversation(conversation: Conversation): Flow<CommonResult<Conversation>>
}
