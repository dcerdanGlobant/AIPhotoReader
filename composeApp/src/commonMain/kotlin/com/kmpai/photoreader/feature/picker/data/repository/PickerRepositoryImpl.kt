package com.kmpai.photoreader.feature.picker.data.repository

import com.kmpai.photoreader.feature.picker.data.datasource.PickerDatasource
import com.kmpai.photoreader.feature.picker.domain.model.CommonResult
import com.kmpai.photoreader.feature.picker.domain.model.Conversation
import com.kmpai.photoreader.feature.picker.domain.repository.PickerRepository
import kotlinx.coroutines.flow.Flow
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

@OptIn(ExperimentalObjCName::class)
@ObjCName("TestPickerRepository", swiftName = "TestPickerRepository")
class PickerRepositoryImpl(private val datasource: PickerDatasource) : PickerRepository {
    override suspend fun sendImageAndStartConversation(
        extension: String,
        imageByteArray: ByteArray
    ): Flow<CommonResult<Conversation>> {
        return datasource.sendImageAndStartConversation(extension, imageByteArray)
    }

    override suspend fun sendConversation(conversation: Conversation): Flow<CommonResult<Conversation>> {
        return datasource.sendConversation(conversation)
    }
}
